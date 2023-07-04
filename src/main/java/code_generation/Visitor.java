package code_generation;

import antlr4.ut.pp.parser.MyLangBaseVisitor;
import antlr4.ut.pp.parser.MyLangParser;
import errors.*;
import errors.OutOfMemoryError;
import org.antlr.v4.runtime.ParserRuleContext;

import java.util.Objects;
import java.util.ArrayList;
import java.util.Vector;

public class Visitor extends MyLangBaseVisitor<Attrs> {
    private final Vector<CompilerError> errorVector = new Vector<>();
    private final ArrayList<SymbolTable> symbolTables = new ArrayList<>();
    private final ArrayList<ArrayList<String>> code = new ArrayList<>(new ArrayList<>());
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

            code.add(new ArrayList<>());

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
        tidSymbol.type = Type.FORK;
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
            attrs.type = Type.ERROR;
            attrs.name = name;

            RedefinitonError error = new RedefinitonError(ctx, attrs);
            errorVector.add(error);
            System.err.println(error.getText());
            return attrs;
        }
        Attrs RHSattrs = visit(ctx.getChild(3 + sharedVarCase));
        int address;
        if(sharedVarCase == 1)
        {
            try {
                address = memoryManager.allocateGlobalVariable();
            }catch (Exception e)
            {
                errorVector.add(new OutOfMemoryError(ctx, new Attrs()));
                attrs.type = Type.ERROR;
                return attrs;
            }
            CodeGenerator.MachineCode.writeInstrFromRegA(address);
        }
        else {
            address = memoryManager.createNewVariable(TID, 1);
            ArrayList<String> currentCode = code.get(TID);
            currentCode.add("Pop regA");
            String storeInstruction = "Store regA (DirAddr " + address + ")";
            currentCode.add(storeInstruction);
            CodeGenerator.MachineCode.popRegister("regA");
            CodeGenerator.MachineCode.storeFromRegA(address);
        }
        // Type of name is inferred from the RHS
        attrs.type = RHSattrs.type;
        attrs.name = name;

        symbol.type = attrs.type;
        symbol.address = address;

        currSymbolTable.add(attrs.name, symbol);
        return attrs;
    }

    @Override
    public Attrs visitExpression(MyLangParser.ExpressionContext ctx) {
        Attrs attrs = new Attrs();

        if(ctx.getChildCount() == 1)
            attrs = visit(ctx.getChild(0));
        else if(ctx.getChildCount() == 3) {
            Attrs value = visit(ctx.getChild(2));
            attrs.name =  ctx.getChild(0).getText();

            if(!currSymbolTable.contains(attrs.name)) {
                attrs.type = Type.ERROR;
                NameNotFoundError error = new NameNotFoundError(ctx, attrs);
                System.err.println(error.getText());
                errorVector.add(error);
                return attrs;
            }
            if (!areCompatible(attrs ,value)) {
                TypeError error = new TypeError(ctx, attrs, value);
                errorVector.add(error);
                System.err.println(error.getText());
                attrs.type = Type.ERROR;
            }
            else {

                SymbolTable st = symbolTables.get(TID);
                Symbol s = st.getSymbol(attrs.name);

                CodeGenerator.MachineCode.popRegister("regA");
                if(s.isShared) {
                    CodeGenerator.MachineCode.writeInstrFromRegA(s.address);
                }
                else {
                    CodeGenerator.MachineCode.storeFromRegA(s.address);
                }
            }
        }
        return attrs;
    }

    @Override
    public Attrs visitExpression_statement(MyLangParser.Expression_statementContext ctx) {
        if(ctx.getChildCount()==2)
            visit(ctx.getChild(0));
        return null;
    }

    @Override
    public Attrs visitLogical_or_expression(MyLangParser.Logical_or_expressionContext ctx) {
        Attrs attrs;

        if(ctx.children.size() == 1) {
            attrs = visit(ctx.getChild(0));
        }
        else
        {
            attrs = manyOperation(ctx);
        }
        return attrs;
    }

    @Override
    public Attrs visitLogical_and_expression(MyLangParser.Logical_and_expressionContext ctx) {
        Attrs attrs;

        if(ctx.children.size() == 1) {
            attrs = visit(ctx.getChild(0));
        }
        else
        {
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
        else
        {
            attrs = manyOperation(ctx);
        }
        return attrs;
    }

    @Override
    public Attrs visitIf_statement(MyLangParser.If_statementContext ctx) {
        // TODO add scope, maybe make a if in body, if parent is if statement then dont make new scope
        Attrs attrs = new Attrs();

        // Check if there is elif or else
        if(ctx.getChildCount() == 3) {
            visit(ctx.getChild(1));
            CodeGenerator.MachineCode.popRegister("regA");
            CodeGenerator.MachineCode.computeEqual();

//            int currentInstructionNr = currCode.size();
//            CodeGenerator.MachineCode.branchReserveLine();
            CodeGenerator.MachineCode.Action.ifStatementBegin();
            visit(ctx.getChild(2));

//            int instructionNrAfterIfBody = currCode.size();
//            int label = instructionNrAfterIfBody - currentInstructionNr;
//            branchInstruction += "( Rel " + label + " )";
//            currCode.set(currentInstructionNr, branchInstruction);
            CodeGenerator.MachineCode.Action.ifStatementEnd();
//            CodeGenerator.MachineCode.Action.branchClaimReserved(currentInstructionNr, branchInstruction);
            attrs.type = Type.BOOL; // TODO why? If not usable then include in for loop
        }
        // Visit all else/ elif statements
        else {
            // TODO close scope
            for(int x = 3; x < ctx.getChildCount(); x++)
                visit(ctx.getChild(x));
        }

        return attrs;
    }

    @Override
    public Attrs visitElse_part(MyLangParser.Else_partContext ctx) {
        // TODO open and close scope
        return visit(ctx.getChild(1));
    }

    @Override
    public Attrs visitElif_part(MyLangParser.Elif_partContext ctx) {

        // TODO first determine what should be in if_statement, open and close scope
        return null;
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
        }
        else
        {
            attrs = manyOperation(ctx);
        }
        return attrs;
    }

    @Override
    public Attrs visitMulti_expr(MyLangParser.Multi_exprContext ctx) {
        Attrs attrs;
        if(ctx.children.size() == 1) {
            attrs = visit(ctx.getChild(0));
        }
        else
            attrs = manyOperation(ctx);

        return attrs;
    }

    @Override
    public Attrs visitPostfix_expr(MyLangParser.Postfix_exprContext ctx) {
        Attrs attrs = new Attrs();
        int child_count = ctx.getChildCount();
        if(child_count == 1) {
            attrs = visit(ctx.getChild(0));
        } else if (child_count == 4)
        {
            if (Objects.equals(ctx.getChild(1).getText(), "["))
            {
                // array-like type, child[0] has to be array-like, so String or array, child[2] has to be int-like
                Attrs array_like = visit(ctx.getChild(0));
                if(!isArrayLike(array_like))
                {
                    TypeError error = new TypeError(ctx, array_like, Type.ARRAY); // TODO not only array but all arraylike
                    errorVector.add(error);
                    System.err.println(error.getText());
                }
                Attrs int_like = visit(ctx.getChild(2));
                if (!isIntLike(int_like))
                {
                    // Maybe add 'indexing' rule as intermedieate step so that context is changed - error more direct
                    TypeError error = new TypeError(ctx, int_like, Type.INT); // TODO not only array but all arraylike
                    errorVector.add(error);
                    System.err.println(error.getText());
                }
            }
        }
        return attrs;
    }

    @Override
    public Attrs visitAtomic_expr(MyLangParser.Atomic_exprContext ctx) {
        Attrs attrs = new Attrs();

        if(ctx.getChildCount() == 1) {
            attrs = visit(ctx.getChild(0));
        }
        else if (ctx.getChildCount() == 3)
            attrs = visit(ctx.getChild(1));

        return attrs;
    }

    @Override
    public Attrs visitRead_expression(MyLangParser.Read_expressionContext ctx) {
        CodeGenerator.MachineCode.Action.readIO();
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
        attrs.name = ctx.getText();

        if(currSymbolTable.contains(attrs.name)) {
            int address = currSymbolTable.getAddress(attrs.name);
            attrs.type = currSymbolTable.getType(attrs.name);

            if(currSymbolTable.isShared(attrs.name)){
                CodeGenerator.MachineCode.readInstrWithDirAddr(address);
                CodeGenerator.MachineCode.receiveRegister("regA");
            }
            else
                CodeGenerator.MachineCode.loadDirAddr(address);

            CodeGenerator.MachineCode.pushRegister("regA");
        }
        else
        {
            NameNotFoundError error = new NameNotFoundError(ctx, attrs);
            attrs.type = Type.ERROR;
            errorVector.add(error);
            System.err.println(error.getText());
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

        CodeGenerator.MachineCode.loadImmediate(primitiveTypeValue);
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
    public Attrs visitCompound_type(MyLangParser.Compound_typeContext ctx) {
        // TODO what about array?
        Attrs attrs = new Attrs();
        if(ctx.STRING() != null)
            attrs.type = Type.STRING; // why you build attr if it is not returned?
        return visit(ctx.getChild(0));
    }

    @Override
    public Attrs visitArray(MyLangParser.ArrayContext ctx) {
        return visit(ctx.getChild(1));
    }

    @Override
    public Attrs visitArgs(MyLangParser.ArgsContext ctx) {
        // TODO move the responsibility of checking if the same type to visit array
        Attrs attrs = new Attrs();
        Attrs childAttrs = visit(ctx.getChild(0));
        Type type = childAttrs.type;

        for(int x = 1; x < ctx.getChildCount(); x++) {
            if(ctx.getChild(x).getText().equals(","))
                x++;

            childAttrs = visit(ctx.getChild(x));

            if(type != childAttrs.type)
                attrs.type = Type.ERROR;
        }
        return attrs;
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
            ArrayList<String> newThreadCode = new ArrayList<>();
            code.add(newThreadCode);
//            currCode = newThreadCode;
            TID = threadCounter;
            threadCounter ++;

            //TODO remove this code assuring correct behavior
            newThreadCode.add("ReadInstr (DirAddr "+ newThreadIsRunningAddress + ")");
            newThreadCode.add("Receive regA");
            newThreadCode.add("Compute Equal regA reg0 regA");
            newThreadCode.add("Branch regA (Rel (-3))");
            CodeGenerator.MachineCode.Action.forkInitialization(newThreadIsRunningAddress);
            visit(ctx.compound_statement());
            newThreadCode.add("WriteInstr " + "reg0 " +  "(DirAddr "+ newThreadIsRunningAddress + ")");
            newThreadCode.add("EndProg");
            CodeGenerator.MachineCode.Action.forkFinish(newThreadIsRunningAddress);

            this.TID = scopeTID;
//            this.currCode = scopeCode;
            this.currSymbolTable = scopeST;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        attrs.type = Type.FORK;
        CodeGenerator.MachineCode.Action.forkEnd(newThreadIsRunningAddress);
        return attrs;
    }

    @Override
    public Attrs visitJoin_statement(MyLangParser.Join_statementContext ctx) {
        Attrs fork = visit(ctx.getChild(1));

        if(getType(fork) != Type.FORK)
        {
            TypeError error = new TypeError(ctx, fork, Type.FORK);
            System.err.println(error.getText());
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
                TypeError error = new TypeError(ctx, attrs, attrs);
                errorVector.add(error);
                System.err.println(error.getTextForLock());
            }
        }
        else
        {
            NameNotFoundError error = new NameNotFoundError(ctx, attrs);
            errorVector.add(error);
            System.err.println(error.getText());
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
        //TODO improve
        return getType(attr1).equals(getType(attrs2));
    }

    private Attrs manyOperation(ParserRuleContext ctx)
    {
        Attrs LHS = visit(ctx.getChild(0));

        if(LHS.type == Type.ERROR)
            return LHS;

        Attrs RHS;
        for(int i = 2; i < ctx.getChildCount(); i+=2)
        {
            String operationCode = CodeGenerator.MachineCode.getOperationCode(ctx.getChild(i-1).getText());

            RHS = visit(ctx.getChild(i));
            // TODO the same as with assignment, check compatible types here. USE ctx.getChild(i+1)

            if(RHS.type == Type.ERROR)
                return RHS;

            if(!areCompatible(LHS, RHS))
            {
                TypeError error = new TypeError(ctx, LHS, RHS);
                errorVector.add(error);
                System.err.println(error.getText());
            }
            CodeGenerator.MachineCode.popRegister("regB");
            CodeGenerator.MachineCode.popRegister("regA");
            CodeGenerator.MachineCode.computeOperationCode(operationCode);
            CodeGenerator.MachineCode.pushRegister("regA");
            LHS = RHS;
        }
        return LHS;
    }

    private boolean isArrayLike(Attrs attrs)
    {
        // TODO impove;
        return attrs.type == Type.ARRAY  || attrs.type == Type.STRING;
    }

    private boolean isIntLike(Attrs attrs)
    {
        // TODO impove;
        return attrs.type == Type.INT  || attrs.type == Type.BOOL;
    }

    @Override
    public Attrs visitPrint_statement(MyLangParser.Print_statementContext ctx) {
        Attrs attrs = visit(ctx.getChild(1));

        if(attrs.type.equals(Type.ERROR))
            return null;
        CodeGenerator.MachineCode.Action.writeIO();
        return null;
    }
}