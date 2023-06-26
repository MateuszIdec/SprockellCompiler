package main.antlr4.ut.pp.parser;

import org.antlr.v4.runtime.tree.ParseTree;
import ut.pp.SymbolTable;
import java.util.ArrayList;

public class Visitor extends MyLangBaseVisitor <Visitor.Attrs> {
    SymbolTable symbolTable = new SymbolTable();
    static class Attrs {
        public String name;
        public String operator;
        public SymbolTable.Type type;
        public ArrayList <String> value = new ArrayList<>();
    }

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
        Attrs attrs = visit(ctx.getChild(3));
        attrs.operator = ctx.getChild(2).getText();
        attrs.name = name;

        symbolTable.add(name, new SymbolTable.Var(attrs.type, attrs.value));
        System.out.println("Definition: " + name + " " + attrs.type + " " + attrs.value);

        if(attrs.type == SymbolTable.Type.ERROR) {
            System.err.println("In " + "\"var " + name + " = "+ attrs.value + "\"" + " types don't match");
        }

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

    String printVariable(Attrs attrs) {
        return attrs.name + " " + attrs.operator + " " + attrs.value.get(0);
    }
    @Override
    public Attrs visitAssignment_expr(MyLangParser.Assignment_exprContext ctx) {
        Attrs attrs = new Attrs();
        if(ctx.getChildCount() == 1) {
            attrs = visit(ctx.getChild(0));
        } else if(ctx.getChildCount() == 3) {
            Attrs name = visit(ctx.getChild(0));
            Attrs operator = visit(ctx.getChild(1));
            Attrs value = visit(ctx.getChild(2));

            attrs.name = name.name;
            attrs.type = value.type;
            attrs.value = value.value;
            attrs.operator = operator.operator;

            System.out.println("Assignment: " + printVariable(attrs));
            int varCheck = symbolTable.check(name.name, value.type);
            if(varCheck == 1) {
                attrs.type = SymbolTable.Type.ERROR;
					System.err.println("In \"" + printVariable(attrs) + "\" variable \"" + name.name + "\" is not in scope");
				} else if (varCheck == 2) {
                    attrs.type = SymbolTable.Type.ERROR;
					System.err.println("In \"" + printVariable(attrs) + "\" expected " + symbolTable.getType(name.name) + ", got " + value.type);
				}
        }
        return attrs;
    }

    @Override
    public Attrs visitAssignment_operator(MyLangParser.Assignment_operatorContext ctx) {
        Attrs attrs = new Attrs();
        attrs.operator = ctx.getText();
        return attrs;
    }

    @Override
    public Attrs visitLogical_or_expression(MyLangParser.Logical_or_expressionContext ctx) {
        Attrs attrs = new Attrs();
        if(ctx.children.size() == 1) {
            attrs = visit(ctx.getChild(0));
        }
        return attrs;
    }

    @Override
    public Attrs visitLogical_and_expression(MyLangParser.Logical_and_expressionContext ctx) {
        Attrs attrs = new Attrs();
        if(ctx.children.size() == 1) {
            attrs = visit(ctx.getChild(0));
        }
        return attrs;
    }

    @Override
    public Attrs visitRelational_expr(MyLangParser.Relational_exprContext ctx) {
        Attrs attrs = new Attrs();
        if(ctx.getChildCount() == 1) {
            attrs = visit(ctx.getChild(0));
        } else if(ctx.getChildCount() == 3) {
            System.out.println("If statement");
            Attrs LHS = visit(ctx.getChild(0));
            Attrs operator = visit(ctx.getChild(1));
            Attrs RHS = visit(ctx.getChild(2));
            String name;
            ArrayList value;
            SymbolTable.Type type;

            if(LHS.name != null) {
                name = LHS.name;
                type = RHS.type;
                value = RHS.value;

            } else {
                name = RHS.name;
                type = LHS.type;
                value = RHS.value;
            }
            int varCheck = symbolTable.check(name, type);
            attrs.name = name;
            attrs.type = type;
            attrs.value = value;
            attrs.operator = operator.operator;

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
        Attrs expression = visit(ctx.getChild(1));
        Attrs compoundStatement = visit(ctx.getChild(2));
        attrs.type = SymbolTable.Type.BOOL;
        attrs.value = expression.value;
        return attrs;
    }

    @Override
    public Attrs visitRelational_operator(MyLangParser.Relational_operatorContext ctx) {
        Attrs attrs = new Attrs();
        attrs.operator = ctx.getText();
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
        attrs.value.add(ctx.getText());

        if(ctx.INT() != null) {
            attrs.type = SymbolTable.Type.INT;
        } else if (ctx.BOOL() != null) {
            attrs.type = SymbolTable.Type.BOOL;
        }
        return attrs;
    }

    @Override
    public Attrs visitCompound_statement(MyLangParser.Compound_statementContext ctx) {
        return super.visitCompound_statement(ctx);
    }

    @Override
    public Attrs visitDefinition_statement(MyLangParser.Definition_statementContext ctx) {
        return visit(ctx.getChild(0));
    }

    @Override
    public Attrs visitStatement(MyLangParser.StatementContext ctx) {
        return visit(ctx.getChild(0));
    }

    @Override
    public Attrs visitCompound_type(MyLangParser.Compound_typeContext ctx) {
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
        attrs.value.add(childAttrs.value.get(0));

        for(int x = 1; x < ctx.getChildCount(); x++) {
            if(ctx.getChild(x).getText().equals(","))
                x++;
            childAttrs = visit(ctx.getChild(x));
            if(type != childAttrs.type)
                attrs.type = SymbolTable.Type.ERROR;

            attrs.value.add(childAttrs.value.get(0));
        }
        return attrs;
    }

}
