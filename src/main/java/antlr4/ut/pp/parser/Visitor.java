package antlr4.ut.pp.parser;

import errors.CompilerError;
import code_generation.MemoryManager;
import errors.RedefinitonError;
import errors.NameNotFoundError;
import org.antlr.v4.runtime.ParserRuleContext;
import errors.TypeError;
import ut.pp.*;
import java.util.Objects;
import java.util.ArrayList;
import java.util.Vector;

public class Visitor extends MyLangBaseVisitor <Attrs> {
    public Vector<CompilerError> error_vector = new Vector<>();
    public ArrayList<SymbolTable> symbolTables = new ArrayList<>();
    ArrayList<ArrayList<String>> code = new ArrayList<>(new ArrayList<>());
    public MemoryManager memoryManager = new MemoryManager();

    /**
     * @return code for all threads
     */
    public ArrayList<String> getCode() {
        ArrayList<String> result = new ArrayList<>();

        for(ArrayList<String> threadCode : code) {
            result.add("prog = " + threadCode.toString());
        }
        return result;
    }
    /**
     * @return code for a given {@code threadId}
     */
    public String getCode(int threadId) {
        return "prog = " + code.get(threadId).toString();
    }

    @Override
    public Attrs visitModule(MyLangParser.ModuleContext ctx) {
        symbolTables.add(new SymbolTable());
        memoryManager.createNewLocalMemoryManager();
        code.add(new ArrayList<>());
        int TID = symbolTables.size()-1;

        try {
            ArrayList<String> currCode = code.get(TID);
            String allocatedReg = memoryManager.allocateRegister(TID);
            String loadOneToReg = "Load (ImmValue 1) " + allocatedReg;
            int globalVarAddress = memoryManager.allocateGlobalVariable();
            String updateGlobalVar = "WriteInstr " + allocatedReg +  " (DirAddr "+ globalVarAddress + ")";
            memoryManager.deallocateRegister(TID ,allocatedReg);
            currCode.add(loadOneToReg);
            currCode.add(updateGlobalVar);
            super.visitModule(ctx);
            String epilog = "WriteInstr " + "reg0 " +  "(DirAddr "+ globalVarAddress + ")";
            currCode.add(epilog);
            currCode.add("EndProg");
        } catch (Exception e) {
            System.err.println("Parsing failed " + e);
        }
        return null;
    }


    @Override
    public Attrs visitBody(MyLangParser.BodyContext ctx) {
        SymbolTable symbolTable = symbolTables.get(symbolTables.size() -1);
        boolean is_parent_for_loop = false;
//        if(ctx.getParent().getParent() instanceof MyLangParser.For_statementContext)
//            is_parent_for_loop = true;
        System.out.println("Entering scope");
        if(!is_parent_for_loop)
            symbolTable.openScope();


        for (int i = 0 ; i< ctx.getChildCount();i++)
            visit(ctx.getChild(i));
        if(!is_parent_for_loop)
            symbolTable.closeScope();
        System.out.println("Exiting scope ");
        return null;
    }
    @Override
    public Attrs visitVar_def(MyLangParser.Var_defContext ctx) {
        int TID = symbolTables.size() - 1 ;
        SymbolTable symbolTable = symbolTables.get(TID);
        Attrs attrs = new Attrs();
        Symbol symbol = new Symbol();
        int sharedVarCase = 0;

        if(ctx.getChild(0).getText().equals("shared")) {
            symbol.isShared = true;
            sharedVarCase = 1;
        }

        String name = ctx.getChild(1 + sharedVarCase).getText();

        // Check if variable is already defined in local scope
        if(symbolTable.checkLocalScope(name)) {
            attrs.type = Type.ERROR;
            attrs.name = name;

            RedefinitonError error = new RedefinitonError(ctx, attrs);
            error_vector.add(error);
            System.err.println(error.getText());
            return attrs;
        }
        Attrs RHSattrs = visit(ctx.getChild(3 + sharedVarCase));

        int address = memoryManager.createNewVariable(TID , 1);
        ArrayList<String> currentCode = code.get(TID);
        currentCode.add("Pop regA");

        String storeInstruction = "Store regA (DirAddr " + address + ")";
        currentCode.add(storeInstruction);

        // Type of name is inferred from the RHS
        attrs.type = RHSattrs.type;
        attrs.name = name;

        symbol.type = attrs.type;
        symbol.address = address;

        symbolTable.add(attrs.name, symbol);
        System.out.println("New variable defined: \"" + attrs.name + "\" " + attrs.type);
        return attrs;
    }

//    @Override
//    public Attrs visitGet_thread_id_expression(MyLangParser.Get_thread_id_expressionContext ctx) {
//        Attrs attrs = new Attrs();
//        int TID = symbolTables.size() - 1;
//        String allocatedReg = memoryManager.allocateRegister(TID);
//        String putValueIntoRegInstruction = "Load (ImmValue " + TID +") " + allocatedReg;
//        code.get(TID).add(putValueIntoRegInstruction);
//        attrs.type = Type.INT;
//        attrs.regName = allocatedReg;
//        return attrs;
//    }

    @Override
    public Attrs visitExpression(MyLangParser.ExpressionContext ctx) {
        return visit(ctx.getChild(0));
    }

    @Override
    public Attrs visitExpression_statement(MyLangParser.Expression_statementContext ctx) {
        if(ctx.getChildCount()==2)
            return visit(ctx.getChild(0));
        else
            return new Attrs();
    }

    private String printVariable(Attrs attrs) {
        return attrs.name + " ";
    }

    @Override
    public Attrs visitAssignment_expr(MyLangParser.Assignment_exprContext ctx) {
        int TID = symbolTables.size() - 1;

        Attrs attrs = new Attrs();
        if(ctx.getChildCount() == 1) {
//            System.out.println("Assignment: Single child, value: " + ctx.getText());
            attrs = visit(ctx.getChild(0));
        }
        else if(ctx.getChildCount() == 3) {
            Attrs value = visit(ctx.getChild(2));
            attrs.name =  ctx.getChild(0).getText();

            if(!symbolTables.get(TID).contains(attrs.name)) {

                attrs.type = Type.ERROR;
                NameNotFoundError error = new NameNotFoundError(ctx, attrs);
                System.err.println(error.getText());
                error_vector.add(error);
                return attrs;
            }

            System.out.println("Assignment: " + printVariable(attrs));
            // TODO change this so it uses tables to determine whether an *= or += can be performed
            if (!are_compatible(attrs ,value)) {
                attrs.type = Type.INT;
                TypeError error = new TypeError(ctx, attrs, value);
                error_vector.add(error);
                System.err.println(error.getText());
                attrs.type = Type.ERROR;
            }
            else {
                ArrayList<String> currCode = code.get(TID);

                int memoryAddress = symbolTables.get(TID).getAddress(attrs.name);
                currCode.add("Pop regA");

                String storeNewValueInVarMemAddress = "Store regA (DirAddr " + memoryAddress + ")";
                code.get(TID).add(storeNewValueInVarMemAddress);
            }
        }
        return attrs;
    }

    @Override
    public Attrs visitAssignment_operator(MyLangParser.Assignment_operatorContext ctx) {
        Attrs attrs = new Attrs();
        attrs.name = ctx.getText();
        return attrs;
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
        int TID = symbolTables.size() - 1;
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
            int TID = symbolTables.size()-1;
            ArrayList<String> currCode = code.get(TID);
            Attrs expression = visit(ctx.getChild(1));
            currCode.add("Pop regA");
            currCode.add("Compute Equal regA reg0 regA");
            int currentInstructionNr = currCode.size();
            String branchInstruction = "Branch regA ";
            currCode.add(branchInstruction);
            Attrs compoundStatement = visit(ctx.getChild(2));
            int instructionNrAfterIfBody = currCode.size();
            int label = instructionNrAfterIfBody - currentInstructionNr;
            branchInstruction += "( Rel " + label + " )";
            currCode.set(currentInstructionNr, branchInstruction);
            attrs.type = Type.BOOL; // TODO why? If not usable then include in for loop
        }
        // Visit all else/ elif stateents
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
        Attrs attrs = new Attrs();
        return attrs;
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
        Attrs attrs = new Attrs();
        if(ctx.children.size() == 1) {
            attrs = visit(ctx.getChild(0));
        }
        else
        {
            // TODO update attrs.type if we add floats
            attrs = manyOperation(ctx);
        }
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
                if(!is_array_like(array_like))
                {
                    TypeError error = new TypeError(ctx, array_like, Type.ARRAY); // TODO not only array but all arraylike
                    error_vector.add(error);
                    System.err.println(error.getText());
                }
                Attrs int_like = visit(ctx.getChild(2));
                if (!is_int_like(int_like))
                {
                    // Maybe add 'indexing' rule as intermedieate step so that context is changed - error more direct
                    TypeError error = new TypeError(ctx, int_like, Type.INT); // TODO not only array but all arraylike
                    error_vector.add(error);
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

        int TID = symbolTables.size()-1;
        ArrayList<String> currCode = code.get(TID);
        currCode.add("ReadInstr numberIO");
        currCode.add("Receive regA");
        currCode.add("Push regA");
        Attrs attrs = new Attrs();
        attrs.type = Type.INT;
        return attrs;
    }

    @Override
    public Attrs visitVar_call(MyLangParser.Var_callContext ctx) {
        Attrs attrs = new Attrs();
        int TID = symbolTables.size() - 1;
        attrs.name = ctx.getText();

        // check whether var name in scope
        // find its corresponding address
        // load value from found address and put it into newly allocated register
        if(symbolTables.get(TID).contains(attrs.name)) {
            ArrayList<String> currCode = code.get(TID);
            SymbolTable st = symbolTables.get(TID);
            int address = st.getAddress(attrs.name);

            attrs.type = st.getType(attrs.name);

            String loadInstruction = "Load (DirAddr " + address + ") regA";
            currCode.add(loadInstruction);
            currCode.add("Push regA");
        }
        else
        {
            NameNotFoundError error = new NameNotFoundError(ctx, attrs);
            attrs.type = Type.ERROR;
            error_vector.add(error);
            System.err.println(error.getText());
        }
        return attrs;
    }

    @Override
    public Attrs visitPrimitive_type(MyLangParser.Primitive_typeContext ctx) {
        int TID = symbolTables.size() -1;
        Attrs attrs = new Attrs();

        String primitiveTypeValue = "0";
        if(ctx.INT() != null) {
            attrs.type = Type.INT;
            primitiveTypeValue = ctx.INT().getText();
        } else if (ctx.BOOL() != null) {
            attrs.type = Type.BOOL;
            if (ctx.BOOL().getText().equals("True"))
                primitiveTypeValue = "1";
        }
        ArrayList<String> currentCode = code.get(TID);
        String putValueIntoRegInstruction = "Load (ImmValue " + primitiveTypeValue +") regA";
        currentCode.add(putValueIntoRegInstruction);
        currentCode.add("Push regA");
        return attrs;
    }


    @Override
    public Attrs visitCompound_statement(MyLangParser.Compound_statementContext ctx) {
        return visit(ctx.getChild(1));
    }

    @Override
    public Attrs visitDefinition_statement(MyLangParser.Definition_statementContext ctx) {
        return visit(ctx.getChild(0));
    }

    @Override
    public Attrs visitFunc_def(MyLangParser.Func_defContext ctx) {
        SymbolTable symbolTable = symbolTables.get(symbolTables.size() -1);
        // TODO add name to symbolTable
        symbolTable.openScope();
        Attrs name = visit(ctx.getChild(1));
        Attrs pars = visit(ctx.getChild(3));
        Attrs body = visit(ctx.getChild(6));
        symbolTable.closeScope();
        return new Attrs();
    }

    @Override
    public Attrs visitParameters(MyLangParser.ParametersContext ctx) {
        for(int i = 0; i < ctx.getChildCount();i+=2)
        {
            visit(ctx.getChild(i));
        }
        return new Attrs();
    }

    @Override
    public Attrs visitParameter(MyLangParser.ParameterContext ctx) {
        SymbolTable symbolTable = symbolTables.get(symbolTables.size() -1);
        // TODO how to determine type of variable before function call?
        symbolTable.add(ctx.getText(), null);
        return new Attrs();
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
            if(type != childAttrs.type) {
                attrs.type = Type.ERROR;

            }
        }
        return attrs;
    }


    @Override
    public Attrs visitWhile_statement(MyLangParser.While_statementContext ctx) {
        // TODO check what attributes should be in if statement, maybe visit while, for and if just for code gen?
        int TID = symbolTables.size()-1;
        ArrayList<String> currCode = code.get(TID);
        int startOfWhile = currCode.size();
        Attrs expression = visit(ctx.getChild(1));
        int branchInstructionNr = currCode.size();
        currCode.add("Pop regA");
        currCode.add("Compute Equal regA reg0 regA");
        String branchInstruction = "Branch regA ";
        currCode.add(branchInstruction);
        Attrs compoundStatement = visit(ctx.getChild(2));
        String jumpInstruction = "Jump (Abs " + startOfWhile + ")";
        currCode.add(jumpInstruction);

        int instructionNrAfterBody = currCode.size();
        branchInstruction += "( Abs " + instructionNrAfterBody + " )";
        currCode.set(branchInstructionNr + 2, branchInstruction);
        return null;
    }

    @Override
    public Attrs visitFor_statement(MyLangParser.For_statementContext ctx) {
        SymbolTable symbolTable = symbolTables.get(symbolTables.size() -1);
        symbolTable.openScope();
        for(int i = 0; i < ctx.getChildCount(); i++)
        {
            visit(ctx.getChild(i));
        }
        symbolTable.closeScope();
        return new Attrs();
    }

    @Override
    public Attrs visitReturn_statement(MyLangParser.Return_statementContext ctx) {
        // TODO what  to return? Code generation for sure.
        return super.visitReturn_statement(ctx);
    }

    @Override
    public Attrs visitFork_expression(MyLangParser.Fork_expressionContext ctx) {
        Attrs attrs = new Attrs();
        visit(ctx.getChild(1));
        attrs.type = Type.FORK;
        return attrs;
    }

    @Override
    public Attrs visitJoin_statement(MyLangParser.Join_statementContext ctx) {
        Attrs fork = visit(ctx.getChild(1));

        if(getType(fork) != Type.FORK)
        {
            TypeError error = new TypeError(ctx, fork, Type.FORK);
            System.err.println(error.getText());
            error_vector.add(error);
        }
        return fork;
    }

    @Override
    public Attrs visitLock_statement(MyLangParser.Lock_statementContext ctx) {
        SymbolTable symbolTable = symbolTables.get(symbolTables.size() -1);
        Attrs attrs = new Attrs();

        attrs.name = ctx.getChild(1).getText();

        // If identifier not found in all scopes then add new error
        if(symbolTable.contains(attrs.name)) {
            attrs.type = symbolTable.getType(attrs.name);
        }
        else
        {
            attrs.type = Type.ERROR;
            NameNotFoundError error = new NameNotFoundError(ctx, attrs);
            error_vector.add(error);
            System.err.println(error.getText());
        }

        if(attrs.type == Type.FORK)
        {
            TypeError error = new TypeError(ctx, attrs, Type.INT);
            System.err.println(error.getText());
            error_vector.add(error);
        }
        return attrs;
    }

    private Type getType(Attrs attrs){
        SymbolTable symbolTable = symbolTables.get(symbolTables.size() -1);
        if (attrs.name != null)
            return symbolTable.getType(attrs.name);
        return attrs.type;
    }
    private boolean are_compatible(Attrs attr1, Attrs attrs2)
    {
        //TODO improve

        return getType(attr1) == getType(attrs2);
    }
    private Attrs manyOperation(ParserRuleContext ctx)
    {
        int TID = symbolTables.size() -1;
        ArrayList<String> curr_code = code.get(TID);

        Attrs LHS = visit(ctx.getChild(0));
        if(LHS.type == Type.ERROR)
            return LHS;

        Attrs RHS;
        for(int i = 2; i < ctx.getChildCount(); i+=2)
        {
            String operationCode = "";
            String operation = ctx.getChild(i-1).getText();
            switch (operation) {
                case "+":
                    operationCode = "Add ";
                    break;
                case "-":
                    operationCode = "Sub ";
                    break;
                case "*":
                    operationCode = "Mul ";
                    break;
                case "==":
                    operationCode = "Equal ";
                    break;
                case "!=":
                    operationCode = "NEq ";
                    break;
                case ">":
                    operationCode = "Gt ";
                    break;
                case "<":
                    operationCode = "Lt ";
                    break;
                case "||":
                    operationCode = "And ";
                    break;
                case "&&":
                    operationCode = "Or ";
                    break;
            }
            RHS = visit(ctx.getChild(i));
            // TODO the same as with assignment, check compatible types here. USE ctx.getChild(i+1)

            if(RHS.type == Type.ERROR)
                return RHS;

            if(!are_compatible(LHS, RHS))
            {
                TypeError error = new TypeError(ctx, LHS, RHS);
                error_vector.add(error);
                System.err.println(error.getText());
            }
            curr_code.add("Pop regB");
            curr_code.add("Pop regA");
            String instr = "Compute " + operationCode + "regA regB regA";
            curr_code.add(instr);
            curr_code.add("Push regA");

            LHS = RHS;
        }
        return LHS;
    }
    private boolean is_array_like(Attrs attrs)
    {
        // TODO impove;
        return attrs.type == Type.ARRAY  || attrs.type == Type.STRING;
    }
    private boolean is_int_like(Attrs attrs)
    {
        // TODO impove;
        return attrs.type == Type.INT  || attrs.type == Type.BOOL;
    }

    @Override
    public Attrs visitPrint_statement(MyLangParser.Print_statementContext ctx) {
        int TID = symbolTables.size()-1;

        Attrs attrs = visit(ctx.getChild(1));
        if(attrs.type == Type.ERROR)
        {
            attrs.type = Type.ERROR;
            System.err.println("Print statement received expression with no regName specified");
            return attrs;
        }
        // Check if the expression to be printed is in register or in memory
        ArrayList<String> curr_code =  code.get(TID);
        curr_code.add("Pop regA");
        String writeInstr = "WriteInstr regA numberIO";
        curr_code.add(writeInstr);
        return attrs;
    }
    
}
// Dont forget to regenerate ANTLR grammar