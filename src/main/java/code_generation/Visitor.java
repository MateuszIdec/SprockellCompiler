package code_generation;

import antlr4.ut.pp.parser.*;
import errors.*;
import errors.OutOfMemoryError;
import org.antlr.v4.runtime.ParserRuleContext;

import java.util.ArrayList;
import java.util.Vector;

public class Visitor extends MyLangBaseVisitor<Attrs> {
    private final Vector<CompilerError> errorVector = new Vector<>();
    private final ArrayList<SymbolTable> symbolTables = new ArrayList<>();
    private final MemoryManager memoryManager = new MemoryManager();
    private int TID;
    private int threadCounter;
    private SymbolTable currSymbolTable  = null;

    public Vector<CompilerError> getErrorVector() {
        return new Vector<>(errorVector);
    }
    public void clearErrorVector() {
        errorVector.clear();
    }
    public void clearSymbolTables() {
        symbolTables.clear();
    }

    @Override
    public Attrs visitModule(MyLangParser.ModuleContext ctx) {
        try {
            memoryManager.createNewLocalMemoryManager();
            threadCounter = 1;
            TID = 0;

            symbolTables.add(new SymbolTable());
            currSymbolTable = symbolTables.get(this.TID);

            int globalVarAddress = memoryManager.allocateGlobalVariable();
            addTIDSymbolToSymbolTable(globalVarAddress);
            CodeGenerator.MachineCode.Action.progStart(globalVarAddress);
            super.visitModule(ctx);
            CodeGenerator.MachineCode.Action.progEnd(globalVarAddress);

        } catch (Exception e) {
            System.err.println("Parsing failed " + e);
        }
        return null;
    }

    private void addTIDSymbolToSymbolTable(int globalVarAddress)
    {
        Symbol tidSymbol = new Symbol();
        tidSymbol.address = globalVarAddress;
        tidSymbol.type = Type.THREAD_STATUS;
        tidSymbol.isShared = false;
        currSymbolTable.add("TID", tidSymbol );
    }

    @Override
    public Attrs visitBody(MyLangParser.BodyContext ctx) {
        currSymbolTable.openScope();
        for (int i = 0 ; i< ctx.getChildCount();i++)
            visit(ctx.getChild(i));
        currSymbolTable.closeScope();
        return null;
    }

    @Override
    public Attrs visitVar_def(MyLangParser.Var_defContext ctx) {
        Attrs attrs = new Attrs();
        Symbol symbol = new Symbol();
        int sharedVarCase = 0;

        if(ctx.getChild(0).getText().equals("shared")) {
            symbol.isShared = true;
            sharedVarCase = 1;
        }

        String name = ctx.getChild(1 + sharedVarCase).getText();

        // Check if variable is already defined in local scope
        if(currSymbolTable.checkLocalScope(name)) {
            RedefinitonError error = new RedefinitonError(ctx, attrs);
            attrs.type = Type.ERROR;
            attrs.name = name;
            errorVector.add(error);
            return attrs;
        }

        Attrs RHSattrs = visit(ctx.getChild(3 + sharedVarCase));

        int address;
        // In case of shared variable, we allocate it in global memory
        if(sharedVarCase == 1)
        {
            try {
                address = memoryManager.allocateGlobalVariable();
            } catch (Exception e)
            {
                errorVector.add(new OutOfMemoryError(ctx, new Attrs()));
                attrs.type = Type.ERROR;
                return attrs;
            }
            CodeGenerator.MachineCode.popRegister("regA");
            CodeGenerator.MachineCode.writeInstrFromRegA(address);
        }
        else {
            int firstMemoryAddress = memoryManager.createNewVariable(TID, 1);
            address = firstMemoryAddress;
            int lastMemoryAddress;
            if(RHSattrs.size == 1)
                lastMemoryAddress = firstMemoryAddress;
            else
                lastMemoryAddress = firstMemoryAddress + RHSattrs.size - 1;

            for(int currentAddress = lastMemoryAddress; currentAddress >= firstMemoryAddress; currentAddress--) {
                CodeGenerator.MachineCode.popRegister("regA");
                CodeGenerator.MachineCode.storeFromRegA(currentAddress);
            }
        }

        // Type of name is inferred from the RHS
        attrs.type = RHSattrs.type;
        attrs.name = name;

        symbol.type = attrs.type;
        symbol.address = address;

        // Add newly defined variable into a symbol table for this scope
        currSymbolTable.add(attrs.name, symbol);
        return attrs;
    }


    @Override
    public Attrs visitExpression(MyLangParser.ExpressionContext ctx) {
        return visit(ctx.logical_or_expression());
    }


    @Override
    public Attrs visitLogical_or_expression(MyLangParser.Logical_or_expressionContext ctx) {
        Attrs attrs;

        if(ctx.children.size() == 1) {
            attrs = visit(ctx.getChild(0));
        }
        else {
            attrs = manyOperation(ctx);
        }
        return attrs;
    }

    @Override
    public Attrs visitLogical_and_expression(MyLangParser.Logical_and_expressionContext ctx) {
        Attrs attrs;

        if(ctx.children.size() == 1) {
            attrs = visit(ctx.getChild(0));
        } else {
            attrs = manyOperation(ctx);
        }
        return attrs;
    }

    @Override
    public Attrs visitRelational_expr(MyLangParser.Relational_exprContext ctx) {
        Attrs attrs;

        if(ctx.children.size() == 1) {
            attrs = visit(ctx.getChild(0));
        }
        else {
            attrs = manyOperation(ctx);
        }
        return attrs;
    }

    @Override
    public Attrs visitIf_statement(MyLangParser.If_statementContext ctx) {
        // TODO add scope, maybe make a if in body, if parent is if statement then dont make new scope
        Attrs attrs = new Attrs();
        if(ctx.getChildCount() == 3) {
            visit(ctx.getChild(1));
            int branchInstructionNumber = CodeGenerator.getCurrentCodeSize();

            CodeGenerator.MachineCode.popRegister("regA");
            CodeGenerator.MachineCode.computeEqual();

            CodeGenerator.MachineCode.Action.ifStatementBegin();
            visit(ctx.getChild(2));

            CodeGenerator.MachineCode.Action.ifStatementEnd(branchInstructionNumber);
            attrs.type = Type.BOOL;
        }
        else {
            // TODO error
        }

        return attrs;
    }


    @Override
    public Attrs visitRelational_operator(MyLangParser.Relational_operatorContext ctx) {
        return new Attrs();
    }

    @Override
    public Attrs visitAdditive_expr(MyLangParser.Additive_exprContext ctx) {
        Attrs attrs;

        if(ctx.children.size() == 1) {
            attrs = visit(ctx.getChild(0));
        } else {
            attrs = manyOperation(ctx);
        }
        return attrs;
    }

    @Override
    public Attrs visitMulti_expr(MyLangParser.Multi_exprContext ctx) {
        Attrs attrs;

        if(ctx.children.size() == 1) {
            attrs = visit(ctx.getChild(0));
        } else
            attrs = manyOperation(ctx);

        return attrs;
    }

    @Override
    public Attrs visitPostfix_expr(MyLangParser.Postfix_exprContext ctx) {
        Attrs attrs = new Attrs();
        int child_count = ctx.getChildCount();

        if(child_count == 1) {
            attrs = visit(ctx.getChild(0));
        }
        else if (child_count == 4)
        {
            String varName = ctx.getChild(0).getText();
            visit(ctx.getChild(2));
            if(currSymbolTable.contains(varName)) {
                TypeError error = new TypeError(ctx, attrs);
                errorVector.add(error);
                attrs.type = Type.ERROR;
                return attrs;
            } else {
                attrs.type = Type.ERROR;
                NameNotFoundError error = new NameNotFoundError(ctx, attrs);
                errorVector.add(error);
                return attrs;
            }
        }
        return attrs;
    }

    @Override
    public Attrs visitAtomic_expr(MyLangParser.Atomic_exprContext ctx) {
        if(ctx.getChildCount() == 1)
           return visit(ctx.getChild(0));

        if (ctx.getChildCount() == 3)
            return visit(ctx.getChild(1));

        return null;
    }

    @Override
    public Attrs visitRead_expression(MyLangParser.Read_expressionContext ctx) {
        CodeGenerator.MachineCode.Action.readNumber();
        Attrs attrs = new Attrs();
        attrs.type = Type.INT;
        return attrs;
    }

    @Override
    public Attrs visitGet_thread_id_expression(MyLangParser.Get_thread_id_expressionContext ctx) {
        Attrs attrs = new Attrs();
        attrs.type = Type.INT;
        int address = currSymbolTable.getAddress("TID");
        CodeGenerator.MachineCode.Action.threadID(address);
        return attrs;
    }

    @Override
    public Attrs visitVar_call(MyLangParser.Var_callContext ctx) {

        Attrs attrs = new Attrs();
        attrs.name = ctx.getChild(0).getText();

        int address;
        if(currSymbolTable.contains(attrs.name)) {
            Symbol variable = currSymbolTable.getSymbol(attrs.name);
            address = variable.address;
            attrs.type = variable.type;

            if(ctx.getChildCount() == 4) {
                Attrs indexNumber = visit(ctx.getChild(2));

                if(!indexNumber.type.equals(Type.INT)) {
                    errorVector.add(new TypeError(ctx, attrs));
                }
                CodeGenerator.MachineCode.popRegister("regA");
                CodeGenerator.MachineCode.loadFromAddressInRegister("regA", "regA");
            }

            else if(currSymbolTable.isShared(attrs.name)){
                CodeGenerator.MachineCode.readInstrWithDirAddr(address);
                CodeGenerator.MachineCode.receiveRegister("regA");
            }
            else
                CodeGenerator.MachineCode.loadDirAddr(Integer.toString(address));

            CodeGenerator.MachineCode.pushRegister("regA");
        }
        else
        {
            NameNotFoundError error = new NameNotFoundError(ctx, attrs);
            attrs.type = Type.ERROR;
            errorVector.add(error);
        }
        return attrs;
    }

    @Override
    public Attrs visitPrimitive_type(MyLangParser.Primitive_typeContext ctx) {
        Attrs attrs = new Attrs();
        String primitiveTypeValue = "0";

        if(ctx.INT() != null) {
            attrs.type = Type.INT;
            primitiveTypeValue = ctx.INT().getText();
        }
        else if (ctx.BOOL() != null) {
            attrs.type = Type.BOOL;
            if (ctx.BOOL().getText().equals("True"))
                primitiveTypeValue = "1";
        }

        CodeGenerator.MachineCode.loadImmediate(primitiveTypeValue, "regA");
        CodeGenerator.MachineCode.pushRegister("regA");

        return attrs;
    }

    @Override
    public Attrs visitCompound_statement(MyLangParser.Compound_statementContext ctx) {
        return visit(ctx.getChild(1));
    }

    @Override
    public Attrs visitStatement(MyLangParser.StatementContext ctx) {
        return visit(ctx.getChild(0));
    }




    @Override
    public Attrs visitWhile_statement(MyLangParser.While_statementContext ctx) {
        int startOfWhile = CodeGenerator.getCurrentCodeSize();

        visit(ctx.getChild(1));

        int branchInstructionNr = CodeGenerator.getCurrentCodeSize();

        CodeGenerator.MachineCode.Action.whileStatementBegin();

        visit(ctx.getChild(2));

        CodeGenerator.MachineCode.Action.whileStatementEnd(startOfWhile, branchInstructionNr);
        return null;
    }

    @Override
    public Attrs visitFork_expression(MyLangParser.Fork_expressionContext ctx) {
        Attrs attrs = new Attrs();
        int scopeTID = this.TID; // copy
        SymbolTable scopeST = currSymbolTable;
        int newThreadIsRunningAddress;
        try {
            newThreadIsRunningAddress = memoryManager.allocateGlobalVariable();
            memoryManager.createNewLocalMemoryManager();
            SymbolTable newST = SymbolTable.provideSymbolTableForFork(currSymbolTable, newThreadIsRunningAddress);
            symbolTables.add(newST);
            currSymbolTable = newST;
            TID = threadCounter;
            threadCounter ++;

            CodeGenerator.MachineCode.Action.forkInitialization(newThreadIsRunningAddress);
            visit(ctx.compound_statement());
            CodeGenerator.MachineCode.Action.forkFinish(newThreadIsRunningAddress);

            this.TID = scopeTID;
            this.currSymbolTable = scopeST;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        attrs.type = Type.THREAD_STATUS;
        CodeGenerator.MachineCode.Action.forkEnd(newThreadIsRunningAddress);
        return attrs;
    }

    @Override
    public Attrs visitJoin_statement(MyLangParser.Join_statementContext ctx) {
        Attrs fork = visit(ctx.getChild(1));

        if(getType(fork) != Type.THREAD_STATUS)
        {
            TypeError error = new TypeError(ctx, fork, Type.THREAD_STATUS);
            errorVector.add(error);
        }
        CodeGenerator.MachineCode.Action.join();

        return null;
    }

    @Override
    public Attrs visitLock_statement(MyLangParser.Lock_statementContext ctx) {
        Attrs attrs = new Attrs();
        attrs.name = ctx.IDENTIFIER().getText();

        if(currSymbolTable.contains(attrs.name))
        {
            Symbol s = currSymbolTable.getSymbol(attrs.name);
            attrs.type = s.type;
            if (s.type == Type.BOOL && s.isShared)
            {
                if(ctx.LOCK() != null)
                {
                    CodeGenerator.MachineCode.Action.testLockLoop(s.address);
                }
                else
                {
                    CodeGenerator.MachineCode.Action.placeLock(s.address);
                }
            }
            else
            {
                TypeError error = new LockTypeError(ctx, attrs);
                errorVector.add(error);
            }
        }
        else
        {
            NameNotFoundError error = new NameNotFoundError(ctx, attrs);
            errorVector.add(error);
        }
        return attrs;
    }

    private Type getType(Attrs attrs){
        SymbolTable symbolTable = symbolTables.get(this.TID);

        if (attrs.name != null)
            return symbolTable.getType(attrs.name);
        return attrs.type;
    }

    private boolean areCompatible(Attrs attr1, Attrs attrs2)
    {
        Type type1 = getType(attr1);
        Type type2 = getType(attrs2);

        if(type1.equals(Type.THREAD_STATUS))
            type1 = Type.INT;
        if(type2.equals(Type.THREAD_STATUS))
            type2 = Type.INT;

        return type1.equals(type2);
    }

    private Attrs manyOperation(ParserRuleContext ctx)
    {
        Attrs LHS = visit(ctx.getChild(0));

        if(LHS.type == Type.ERROR)
            return LHS;

        Attrs RHS;
        String operationCode = "";
        for(int i = 2; i < ctx.getChildCount(); i+=2)
        {
            operationCode = CodeGenerator.MachineCode.getOperationCode(ctx.getChild(i-1).getText());

            RHS = visit(ctx.getChild(i));
            // TODO the same as with assignment, check compatible types here. USE ctx.getChild(i+1)

            if(RHS.type == Type.ERROR)
                return RHS;

            if(!areCompatible(LHS, RHS))
            {
                TypeError error = new TypeError(ctx, LHS, RHS);
                errorVector.add(error);
            }
            CodeGenerator.MachineCode.popRegister("regB");
            CodeGenerator.MachineCode.popRegister("regA");
            CodeGenerator.MachineCode.computeOperationCode(operationCode, "regA", "regB", "regC");
            CodeGenerator.MachineCode.pushRegister("regA");
            LHS = RHS;
        }

        assert operationCode != null;
        if(operationCode.equals("Add") || operationCode.equals("Sub") | operationCode.equals("Mul"))
            return LHS;

        LHS.type = Type.BOOL;
        return LHS;
    }

    @Override
    public Attrs visitPrint_statement(MyLangParser.Print_statementContext ctx) {
        Attrs attrs = visit(ctx.getChild(1));

        if(attrs.type.equals(Type.ERROR))
            return null;
        //TODO load value from THREAD_STATUS in register and print it
        else if(attrs.type.equals(Type.THREAD_STATUS)) {
            CodeGenerator.MachineCode.Action.printNumber();
//            errorVector.add(new PrintError(ctx, attrs));
        }
        if(attrs.type.equals(Type.INT) | attrs.type.equals(Type.BOOL))
            CodeGenerator.MachineCode.Action.printNumber();
        return null;
    }

    @Override
    public Attrs visitAssignment_statement(MyLangParser.Assignment_statementContext ctx) {
        boolean primitiveCase = true;
        if(ctx.getChildCount() == 7)
            primitiveCase = false;

        Attrs attrs = new Attrs();;
        Attrs value;

        if(primitiveCase)
            value = visit(ctx.getChild(2));
        else
            value = visit(ctx.getChild(5));

        attrs.name =  ctx.getChild(0).getText();

        if(!currSymbolTable.contains(attrs.name)) {
            NameNotFoundError error = new NameNotFoundError(ctx, attrs);
            attrs.type = Type.ERROR;
            errorVector.add(error);
            return attrs;
        }
        attrs.type = currSymbolTable.getType(attrs.name);

        if (!areCompatible(attrs ,value)) {
            TypeError error = new TypeError(ctx, attrs, value);
            errorVector.add(error);
            attrs.type = Type.ERROR;
            return attrs;
        }
        SymbolTable st = symbolTables.get(TID);
        Symbol s = st.getSymbol(attrs.name);

        if(primitiveCase) {
            CodeGenerator.MachineCode.popRegister("regA");
            if(s.isShared) {
                CodeGenerator.MachineCode.writeInstrFromRegA(s.address);
            }
            else {
                CodeGenerator.MachineCode.storeFromRegA(s.address);
            }
        }
        else {
            CodeGenerator.MachineCode.popRegister("regB");
            visit(ctx.getChild(2));
            CodeGenerator.MachineCode.popRegister("regA");
            CodeGenerator.MachineCode.loadImmediate(String.valueOf(s.address), "regC");
            CodeGenerator.MachineCode.computeOperationCode("Add", "regA", "regC", "regA");
            CodeGenerator.MachineCode.storeToMemoryLocationInRegister("regB", "regA");
        }

        return attrs;
        }
    @Override
    public Attrs visitArray(MyLangParser.ArrayContext ctx) {
        Attrs attrs = new Attrs();
        Attrs childAttrs = visit(ctx.getChild(1));
        Type type = childAttrs.type;

        for(int x = 3; x < ctx.getChildCount() - 1; x+=2) {
            if(ctx.getChild(x).getText().equals(","))
                x++;

            childAttrs = visit(ctx.getChild(x));

            if(type != childAttrs.type) {
                TypeError typeError = new ArrayTypeError(ctx, childAttrs, type);
                errorVector.add(typeError);
                attrs.type = Type.ERROR;
                return attrs;
            }
        }

        attrs.type = type;
        attrs.size = (ctx.getChildCount() - 1) / 2;

        return attrs;
    }
}