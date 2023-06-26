package main.antlr4.ut.pp.parser;

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
        String name = ctx.getChild(1).getText();
        Attrs attrs = new Attrs();

        // Check if variable is already defined in local scope
        if(symbolTable.checkLocalScope(name)) {
            attrs.type = SymbolTable.Type.ERROR;
            Token start = ctx.start;
            int lineNr = start.getLine();
            int columnNr = start.getCharPositionInLine();
            error_vector.add(new RedefinitonError(lineNr, columnNr, attrs));
            // System.err.println("Variable " + name + " is already defined in this scope"); // May be rmoved
            return attrs;
        }
        attrs = visit(ctx.getChild(3));

        // type in RHS case
        if(attrs.name == null) {
            attrs.name = name;
            symbolTable.add(name, attrs.type);
        }
        // variable in RHS case
        else {
            // Check if variable on RHS exists
            if(symbolTable.contains(attrs.name)) {
                symbolTable.add(name, attrs.type);
            }
            else {
                // System.err.println("In \"var " + name + " = " + attrs.name +  "\" \"" + attrs.name + "\" is not defined");
                attrs.type = SymbolTable.Type.ERROR;
                return attrs;
            }
        }
        System.out.println("Definition: " + name + " " + attrs.type );

        return attrs;
    }
    @Override
    public Attrs visitExpression(MyLangParser.ExpressionContext ctx) {
        Attrs attrs = new Attrs();
        if(ctx.children.size() == 1) {
            attrs = visit(ctx.getChild(0));
        }
        return attrs;
    }

    @Override
    public Attrs visitExpression_statement(MyLangParser.Expression_statementContext ctx) {
        return visit(ctx.getChild(0));
    }

    String printVariable(Attrs attrs) {
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
            int varCheck = symbolTable.check(name.name, value.type);
            if (varCheck == 2) {
                attrs.type = SymbolTable.Type.ERROR;
                Token start = ctx.start;
                int lineNr = start.getLine();
                int columnNr = start.getCharPositionInLine();
                error_vector.add(new TypeError(lineNr, columnNr, attrs));
                System.err.println("In \"" + printVariable(attrs) + "\" expected " + symbolTable.getType(name.name) + ", got " + value.type);
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
        else if(ctx.getChildCount() == 3) {
            // System.out.println("If statement");
            Attrs LHS = visit(ctx.getChild(0));
            Attrs RHS = visit(ctx.getChild(2));
            String name;
            SymbolTable.Type type;

            if(LHS.name != null) {
                name = LHS.name;
                type = RHS.type;

            } else {
                name = RHS.name;
                type = LHS.type;
            }
            int varCheck = symbolTable.check(name, type);
            attrs.name = name;
            attrs.type = type;

            if(varCheck == 1) {
                System.err.println("In \"if " + printVariable(attrs) + "\" variable \"" + name + "\" is not in scope");
                attrs.type = SymbolTable.Type.ERROR;
            } else if (varCheck == 2) {
                System.err.println("Cannot compare \"" + printVariable(attrs) + "\" expected " + symbolTable.getType(name) + ", got " + type);
                attrs.type = SymbolTable.Type.ERROR;
            }
        }
        return attrs;
    }

    @Override
    public Attrs visitIf_statement(MyLangParser.If_statementContext ctx) {
        Attrs attrs = new Attrs();
        // Check if there is elif or else
        if(ctx.getChildCount() == 3) {
            Attrs expression = visit(ctx.getChild(1));
            Attrs compoundStatement = visit(ctx.getChild(2));
            attrs.type = SymbolTable.Type.BOOL;
        }
        // Visit all else/ elif stateents
        else {
            for(int x = 3; x < ctx.getChildCount(); x++)
                visit(ctx.getChild(x));
        }
//        for(int i = 0; i < ctx.getChildCount(); i +=2)
//        {
//            Attrs LHS = visit(ctx.getChild(0));
//            Attrs operator = visit(ctx.getChild(1));
//            Attrs RHS = visit(ctx.getChild(2));
//        }
        return attrs;
    }

    @Override
    public Attrs visitElse_part(MyLangParser.Else_partContext ctx) {
        return visit(ctx.getChild(1));
    }

    @Override
    public Attrs visitElif_part(MyLangParser.Elif_partContext ctx) {
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
        return attrs;
    }

    @Override
    public Attrs visitMulti_expr(MyLangParser.Multi_exprContext ctx) {
        Attrs attrs = new Attrs();
        if(ctx.children.size() == 1) {
            attrs = visit(ctx.getChild(0));
        }
        return attrs;
    }

    @Override
    public Attrs visitPostfix_expr(MyLangParser.Postfix_exprContext ctx) {
        Attrs attrs = new Attrs();
        if(ctx.children.size() == 1) {
            attrs = visit(ctx.getChild(0));
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
                Token start = ctx.start;
                int lineNr = start.getLine();
                int columnNr = start.getCharPositionInLine();
                error_vector.add(new NameNotFoundError(lineNr, columnNr, attrs));
                System.err.println("Variable: "+ attrs.name + " not found in all scopes!");
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
    public Attrs visitStatement(MyLangParser.StatementContext ctx) {
        Attrs attrs = visit(ctx.getChild(0));
        return attrs;
    }

    @Override
    public Attrs visitCompound_type(MyLangParser.Compound_typeContext ctx) {
        Attrs attrs = new Attrs();
        if(ctx.STRING() != null)
            attrs.type = SymbolTable.Type.STRING;
        return visit(ctx.getChild(0));
    }

    @Override
    public Attrs visitArray(MyLangParser.ArrayContext ctx) {
        return visit(ctx.getChild(1));
    }

    @Override
    public Attrs visitArgs(MyLangParser.ArgsContext ctx) {
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
}
