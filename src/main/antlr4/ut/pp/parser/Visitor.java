package main.antlr4.ut.pp.parser;

import Errors.CompilerError;
import Errors.NameNotFoundError;
import Errors.RedefinitonError;
import Errors.TypeError;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
import ut.pp.*;

import java.util.Vector;

public class Visitor extends MyLangBaseVisitor <Attrs> {
    public Vector<CompilerError> error_vector = new Vector<>();
    public SymbolTable symbolTable = new SymbolTable();

    @Override
    public Attrs visitBody(MyLangParser.BodyContext ctx) {
        System.out.println("Entering scope");
        symbolTable.openScope();

        for (ParseTree child : ctx.children) {
            visit(child);
        }
        symbolTable.closeScope();
        System.out.println("Exiting scope ");
        return null;
    }
    @Override
    public Attrs visitVar_def(MyLangParser.Var_defContext ctx) {
        int sharedVarCase = 0;

        if(ctx.getChild(0).getText().equals("shared")) {
            sharedVarCase = 1;
        }

        String name = ctx.getChild(1 + sharedVarCase).getText();
        Attrs attrs = new Attrs();
        // Check if variable is already defined in local scope
        if(symbolTable.checkLocalScope(name)) {
            attrs.type = SymbolTable.Type.ERROR;

            RedefinitonError error = new RedefinitonError(ctx, attrs);
            error_vector.add(error);
            System.err.println(error.getText());
            return attrs;
        }
        Attrs RHSattrs = visit(ctx.getChild(3+ sharedVarCase));
        // type of name is inferred from the RHS
        attrs.type = RHSattrs.type;
        symbolTable.add(attrs.name, attrs.type);
        return attrs;
    }
    @Override
    public Attrs visitExpression(MyLangParser.ExpressionContext ctx) {
        return visit(ctx.getChild(0));
    }

    @Override
    public Attrs visitExpression_statement(MyLangParser.Expression_statementContext ctx) {
        if(ctx.getChildCount()==1)
            return visit(ctx.getChild(0));
        else
            return new Attrs();
    }

    private String printVariable(Attrs attrs) {
        return attrs.name + " ";
    }

    @Override
    public Attrs visitAssignment_expr(MyLangParser.Assignment_exprContext ctx) {
        Attrs attrs = new Attrs();
        if(ctx.getChildCount() == 1) {
            attrs = visit(ctx.getChild(0));
        }
        else if(ctx.getChildCount() == 3) {
            Attrs name = visit(ctx.getChild(0));
            Attrs value = visit(ctx.getChild(2));

            attrs.name = name.name;
            attrs.type = value.type;

            System.out.println("Assignment: " + printVariable(attrs));
            // TODO change this so it uses tables to determine whether an *= or += can be performed
            if (!are_compatible(name,value)) {
                attrs.type = SymbolTable.Type.ERROR;
                TypeError error = new TypeError(ctx, name, value);
                error_vector.add(error);
                System.err.println(error.getText());
            }
        }
        return attrs;
    }

    @Override
    public Attrs visitAssignment_operator(MyLangParser.Assignment_operatorContext ctx) {
        Attrs attrs = new Attrs();
        return attrs;
    }

    @Override
    public Attrs visitLogical_or_expression(MyLangParser.Logical_or_expressionContext ctx) {
        // TODO: something wrong here
        Attrs attrs = new Attrs();
        for (int i = 0; i < ctx.children.size(); i++){
            attrs = visit(ctx.getChild(i));
            if(attrs.name != null)
                break;
        }
        return attrs;
    }

    @Override
    public Attrs visitLogical_and_expression(MyLangParser.Logical_and_expressionContext ctx) {
        // TODO: something wrong here
        Attrs attrs = new Attrs();
        for (int i = 0; i < ctx.children.size(); i++){
            attrs = visit(ctx.getChild(i));
            if(attrs.name == null)
                break;
        }
        return attrs;
    }

    @Override
    public Attrs visitRelational_expr(MyLangParser.Relational_exprContext ctx) {
        Attrs attrs = new Attrs();

        if(ctx.getChildCount() == 1) {
            attrs = visit(ctx.getChild(0));
        }
        else{
            attrs.type = SymbolTable.Type.BOOL;
            manyOperation(ctx, new Object()); // TODO change object to ENUM OperationType
        }
        return attrs;
    }

    @Override
    public Attrs visitIf_statement(MyLangParser.If_statementContext ctx) {
        // TODO add scope, maybe make a if in body, if parent is if statement then dont make new scope
        Attrs attrs = new Attrs();
        // Check if there is elif or else
        if(ctx.getChildCount() == 3) {
            Attrs expression = visit(ctx.getChild(1));
            Attrs compoundStatement = visit(ctx.getChild(2));
            attrs.type = SymbolTable.Type.BOOL; // TODO why? If not usable then include in for loop
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
        Attrs attrs = new Attrs();
        if(ctx.children.size() == 1) {
            attrs = visit(ctx.getChild(0));
        }
        else
        {
            // TODO update attrs.type if we add floats
            manyOperation(ctx, new Object());
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
            manyOperation(ctx, new Object());
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
            if (ctx.getChild(1).getText() == "[") // TODO .equals("[") instead of ==
            {
                // array-like type, child[0] has to be array-like, so String or array, child[2] has to be int-like
                Attrs array_like = visit(ctx.getChild(0));
                if(!is_array_like(array_like))
                {
                    TypeError error = new TypeError(ctx, array_like, SymbolTable.Type.ARRAY); // TODO not only array but all arraylike
                    error_vector.add(error);
                    System.err.println(error.getText());
                }
                Attrs int_like = visit(ctx.getChild(2));
                if (!is_int_like(int_like))
                {
                    // Maybe add 'indexing' rule as intermedieate step so that context is changed - error more direct
                    TypeError error = new TypeError(ctx, int_like, SymbolTable.Type.INT); // TODO not only array but all arraylike
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
        if(ctx.IDENTIFIER() != null) {
            attrs.name = ctx.getText();

            // If identifier not found in all scopes then add new error
            if(!symbolTable.contains(attrs.name))
            {
                NameNotFoundError error = new NameNotFoundError(ctx, attrs);
                error_vector.add(error);
                System.err.println(error.getText());
            }
        }
        else if(ctx.getChildCount() == 1) {
            attrs = visit(ctx.getChild(0));
        }
        else if (ctx.getChildCount() == 3)
            attrs = visit(ctx.getChild(1));

        return attrs;
    }

    @Override
    public Attrs visitPrimitive_type(MyLangParser.Primitive_typeContext ctx) {
        Attrs attrs = new Attrs();

        if(ctx.INT() != null) {
            attrs.type = SymbolTable.Type.INT;
        } else if (ctx.BOOL() != null) {
            attrs.type = SymbolTable.Type.BOOL;
        }
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
        // TODO how to determine type of variable before function call?
        symbolTable.add(ctx.getText(), null);
        return new Attrs();
    }


    @Override
    public Attrs visitStatement(MyLangParser.StatementContext ctx) {
        Attrs attrs = visit(ctx.getChild(0));
        return attrs;
    }

    @Override
    public Attrs visitCompound_type(MyLangParser.Compound_typeContext ctx) {
        // TODO what about array?
        Attrs attrs = new Attrs();
        if(ctx.STRING() != null)
            attrs.type = SymbolTable.Type.STRING; // why you build attr if it is not returned?
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
        SymbolTable.Type type = childAttrs.type;

        for(int x = 1; x < ctx.getChildCount(); x++) {
            if(ctx.getChild(x).getText().equals(","))
                x++;
            childAttrs = visit(ctx.getChild(x));
            if(type != childAttrs.type) {
                attrs.type = SymbolTable.Type.ERROR;

            }
        }
        return attrs;
    }


    @Override
    public Attrs visitWhile_statement(MyLangParser.While_statementContext ctx) {
        // TODO check what attributes should be in if statement, maybe visit while, for and if just for code gen?
        return super.visitWhile_statement(ctx);
    }

    @Override
    public Attrs visitFor_statement(MyLangParser.For_statementContext ctx) {
        return super.visitFor_statement(ctx);
    }

    @Override
    public Attrs visitReturn_statement(MyLangParser.Return_statementContext ctx) {
        // TODO what  to return? Code generation for sure.
        return super.visitReturn_statement(ctx);
    }

    private SymbolTable.Type getType(Attrs attrs){
        if (attrs.name != null)
            return symbolTable.getType(attrs.name);
        return attrs.type;
    }
    private boolean are_compatible(Attrs attr1, Attrs attrs2)
    {
        //TODO improve
        return getType(attr1) == getType(attrs2);
    }
    private void manyOperation(ParserRuleContext ctx, Object operationType)
    {
        Attrs LHS = visit(ctx.getChild(0));
        Attrs RHS;
        for(int i = 2; i < ctx.getChildCount(); i+=2)
        {
            RHS = visit(ctx.getChild(i));
            // TODO the same as with assignment, check compatible types here. USE ctx.getChild(i+1)
            if(!are_compatible(LHS, RHS))
            {
                TypeError error = new TypeError(ctx, LHS, RHS);
                error_vector.add(error);
                System.err.println(error.getText());
            }
            LHS = RHS;
        }
    }
    private boolean is_array_like(Attrs attrs)
    {
        // TODO impove;
        return attrs.type == SymbolTable.Type.ARRAY  || attrs.type == SymbolTable.Type.STRING;
    }
    private boolean is_int_like(Attrs attrs)
    {
        // TODO impove;
        return attrs.type == SymbolTable.Type.INT  || attrs.type == SymbolTable.Type.BOOL;
    }

}
// Dont forget to regenerate ANTLR grammar