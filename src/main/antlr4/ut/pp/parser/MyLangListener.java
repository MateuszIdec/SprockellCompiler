// Generated from /Users/szlukawski/Dropbox (LUKAW)/Nauka/Erasmus/M8/Compiler_Construction/Project/SprockellCompiler/src/main/antlr4/ut/pp/parser/MyLang.g4 by ANTLR 4.12.0
package main.antlr4.ut.pp.parser;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link MyLangParser}.
 */
public interface MyLangListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link MyLangParser#module}.
	 * @param ctx the parse tree
	 */
	void enterModule(MyLangParser.ModuleContext ctx);
	/**
	 * Exit a parse tree produced by {@link MyLangParser#module}.
	 * @param ctx the parse tree
	 */
	void exitModule(MyLangParser.ModuleContext ctx);
	/**
	 * Enter a parse tree produced by {@link MyLangParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(MyLangParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MyLangParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(MyLangParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MyLangParser#body}.
	 * @param ctx the parse tree
	 */
	void enterBody(MyLangParser.BodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link MyLangParser#body}.
	 * @param ctx the parse tree
	 */
	void exitBody(MyLangParser.BodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link MyLangParser#compound_statement}.
	 * @param ctx the parse tree
	 */
	void enterCompound_statement(MyLangParser.Compound_statementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MyLangParser#compound_statement}.
	 * @param ctx the parse tree
	 */
	void exitCompound_statement(MyLangParser.Compound_statementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MyLangParser#expression_statement}.
	 * @param ctx the parse tree
	 */
	void enterExpression_statement(MyLangParser.Expression_statementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MyLangParser#expression_statement}.
	 * @param ctx the parse tree
	 */
	void exitExpression_statement(MyLangParser.Expression_statementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MyLangParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(MyLangParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MyLangParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(MyLangParser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MyLangParser#assignment_expr}.
	 * @param ctx the parse tree
	 */
	void enterAssignment_expr(MyLangParser.Assignment_exprContext ctx);
	/**
	 * Exit a parse tree produced by {@link MyLangParser#assignment_expr}.
	 * @param ctx the parse tree
	 */
	void exitAssignment_expr(MyLangParser.Assignment_exprContext ctx);
	/**
	 * Enter a parse tree produced by {@link MyLangParser#logical_or_expression}.
	 * @param ctx the parse tree
	 */
	void enterLogical_or_expression(MyLangParser.Logical_or_expressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MyLangParser#logical_or_expression}.
	 * @param ctx the parse tree
	 */
	void exitLogical_or_expression(MyLangParser.Logical_or_expressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MyLangParser#logical_and_expression}.
	 * @param ctx the parse tree
	 */
	void enterLogical_and_expression(MyLangParser.Logical_and_expressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MyLangParser#logical_and_expression}.
	 * @param ctx the parse tree
	 */
	void exitLogical_and_expression(MyLangParser.Logical_and_expressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MyLangParser#relational_expr}.
	 * @param ctx the parse tree
	 */
	void enterRelational_expr(MyLangParser.Relational_exprContext ctx);
	/**
	 * Exit a parse tree produced by {@link MyLangParser#relational_expr}.
	 * @param ctx the parse tree
	 */
	void exitRelational_expr(MyLangParser.Relational_exprContext ctx);
	/**
	 * Enter a parse tree produced by {@link MyLangParser#additive_expr}.
	 * @param ctx the parse tree
	 */
	void enterAdditive_expr(MyLangParser.Additive_exprContext ctx);
	/**
	 * Exit a parse tree produced by {@link MyLangParser#additive_expr}.
	 * @param ctx the parse tree
	 */
	void exitAdditive_expr(MyLangParser.Additive_exprContext ctx);
	/**
	 * Enter a parse tree produced by {@link MyLangParser#multi_expr}.
	 * @param ctx the parse tree
	 */
	void enterMulti_expr(MyLangParser.Multi_exprContext ctx);
	/**
	 * Exit a parse tree produced by {@link MyLangParser#multi_expr}.
	 * @param ctx the parse tree
	 */
	void exitMulti_expr(MyLangParser.Multi_exprContext ctx);
	/**
	 * Enter a parse tree produced by {@link MyLangParser#postfix_expr}.
	 * @param ctx the parse tree
	 */
	void enterPostfix_expr(MyLangParser.Postfix_exprContext ctx);
	/**
	 * Exit a parse tree produced by {@link MyLangParser#postfix_expr}.
	 * @param ctx the parse tree
	 */
	void exitPostfix_expr(MyLangParser.Postfix_exprContext ctx);
	/**
	 * Enter a parse tree produced by {@link MyLangParser#atomic_expr}.
	 * @param ctx the parse tree
	 */
	void enterAtomic_expr(MyLangParser.Atomic_exprContext ctx);
	/**
	 * Exit a parse tree produced by {@link MyLangParser#atomic_expr}.
	 * @param ctx the parse tree
	 */
	void exitAtomic_expr(MyLangParser.Atomic_exprContext ctx);
	/**
	 * Enter a parse tree produced by {@link MyLangParser#assignment_operator}.
	 * @param ctx the parse tree
	 */
	void enterAssignment_operator(MyLangParser.Assignment_operatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link MyLangParser#assignment_operator}.
	 * @param ctx the parse tree
	 */
	void exitAssignment_operator(MyLangParser.Assignment_operatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link MyLangParser#relational_operator}.
	 * @param ctx the parse tree
	 */
	void enterRelational_operator(MyLangParser.Relational_operatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link MyLangParser#relational_operator}.
	 * @param ctx the parse tree
	 */
	void exitRelational_operator(MyLangParser.Relational_operatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link MyLangParser#additive_operator}.
	 * @param ctx the parse tree
	 */
	void enterAdditive_operator(MyLangParser.Additive_operatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link MyLangParser#additive_operator}.
	 * @param ctx the parse tree
	 */
	void exitAdditive_operator(MyLangParser.Additive_operatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link MyLangParser#multi_operator}.
	 * @param ctx the parse tree
	 */
	void enterMulti_operator(MyLangParser.Multi_operatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link MyLangParser#multi_operator}.
	 * @param ctx the parse tree
	 */
	void exitMulti_operator(MyLangParser.Multi_operatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link MyLangParser#iteration_statement}.
	 * @param ctx the parse tree
	 */
	void enterIteration_statement(MyLangParser.Iteration_statementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MyLangParser#iteration_statement}.
	 * @param ctx the parse tree
	 */
	void exitIteration_statement(MyLangParser.Iteration_statementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MyLangParser#while_statement}.
	 * @param ctx the parse tree
	 */
	void enterWhile_statement(MyLangParser.While_statementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MyLangParser#while_statement}.
	 * @param ctx the parse tree
	 */
	void exitWhile_statement(MyLangParser.While_statementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MyLangParser#for_statement}.
	 * @param ctx the parse tree
	 */
	void enterFor_statement(MyLangParser.For_statementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MyLangParser#for_statement}.
	 * @param ctx the parse tree
	 */
	void exitFor_statement(MyLangParser.For_statementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MyLangParser#if_statement}.
	 * @param ctx the parse tree
	 */
	void enterIf_statement(MyLangParser.If_statementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MyLangParser#if_statement}.
	 * @param ctx the parse tree
	 */
	void exitIf_statement(MyLangParser.If_statementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MyLangParser#elif_part}.
	 * @param ctx the parse tree
	 */
	void enterElif_part(MyLangParser.Elif_partContext ctx);
	/**
	 * Exit a parse tree produced by {@link MyLangParser#elif_part}.
	 * @param ctx the parse tree
	 */
	void exitElif_part(MyLangParser.Elif_partContext ctx);
	/**
	 * Enter a parse tree produced by {@link MyLangParser#else_part}.
	 * @param ctx the parse tree
	 */
	void enterElse_part(MyLangParser.Else_partContext ctx);
	/**
	 * Exit a parse tree produced by {@link MyLangParser#else_part}.
	 * @param ctx the parse tree
	 */
	void exitElse_part(MyLangParser.Else_partContext ctx);
	/**
	 * Enter a parse tree produced by {@link MyLangParser#par_statement}.
	 * @param ctx the parse tree
	 */
	void enterPar_statement(MyLangParser.Par_statementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MyLangParser#par_statement}.
	 * @param ctx the parse tree
	 */
	void exitPar_statement(MyLangParser.Par_statementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MyLangParser#lock_statement}.
	 * @param ctx the parse tree
	 */
	void enterLock_statement(MyLangParser.Lock_statementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MyLangParser#lock_statement}.
	 * @param ctx the parse tree
	 */
	void exitLock_statement(MyLangParser.Lock_statementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MyLangParser#definition_statement}.
	 * @param ctx the parse tree
	 */
	void enterDefinition_statement(MyLangParser.Definition_statementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MyLangParser#definition_statement}.
	 * @param ctx the parse tree
	 */
	void exitDefinition_statement(MyLangParser.Definition_statementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MyLangParser#func_def}.
	 * @param ctx the parse tree
	 */
	void enterFunc_def(MyLangParser.Func_defContext ctx);
	/**
	 * Exit a parse tree produced by {@link MyLangParser#func_def}.
	 * @param ctx the parse tree
	 */
	void exitFunc_def(MyLangParser.Func_defContext ctx);
	/**
	 * Enter a parse tree produced by {@link MyLangParser#var_def}.
	 * @param ctx the parse tree
	 */
	void enterVar_def(MyLangParser.Var_defContext ctx);
	/**
	 * Exit a parse tree produced by {@link MyLangParser#var_def}.
	 * @param ctx the parse tree
	 */
	void exitVar_def(MyLangParser.Var_defContext ctx);
	/**
	 * Enter a parse tree produced by {@link MyLangParser#return_statement}.
	 * @param ctx the parse tree
	 */
	void enterReturn_statement(MyLangParser.Return_statementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MyLangParser#return_statement}.
	 * @param ctx the parse tree
	 */
	void exitReturn_statement(MyLangParser.Return_statementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MyLangParser#print_statement}.
	 * @param ctx the parse tree
	 */
	void enterPrint_statement(MyLangParser.Print_statementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MyLangParser#print_statement}.
	 * @param ctx the parse tree
	 */
	void exitPrint_statement(MyLangParser.Print_statementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MyLangParser#primitive_type}.
	 * @param ctx the parse tree
	 */
	void enterPrimitive_type(MyLangParser.Primitive_typeContext ctx);
	/**
	 * Exit a parse tree produced by {@link MyLangParser#primitive_type}.
	 * @param ctx the parse tree
	 */
	void exitPrimitive_type(MyLangParser.Primitive_typeContext ctx);
	/**
	 * Enter a parse tree produced by {@link MyLangParser#compound_type}.
	 * @param ctx the parse tree
	 */
	void enterCompound_type(MyLangParser.Compound_typeContext ctx);
	/**
	 * Exit a parse tree produced by {@link MyLangParser#compound_type}.
	 * @param ctx the parse tree
	 */
	void exitCompound_type(MyLangParser.Compound_typeContext ctx);
	/**
	 * Enter a parse tree produced by {@link MyLangParser#array}.
	 * @param ctx the parse tree
	 */
	void enterArray(MyLangParser.ArrayContext ctx);
	/**
	 * Exit a parse tree produced by {@link MyLangParser#array}.
	 * @param ctx the parse tree
	 */
	void exitArray(MyLangParser.ArrayContext ctx);
	/**
	 * Enter a parse tree produced by {@link MyLangParser#parameters}.
	 * @param ctx the parse tree
	 */
	void enterParameters(MyLangParser.ParametersContext ctx);
	/**
	 * Exit a parse tree produced by {@link MyLangParser#parameters}.
	 * @param ctx the parse tree
	 */
	void exitParameters(MyLangParser.ParametersContext ctx);
	/**
	 * Enter a parse tree produced by {@link MyLangParser#parameter}.
	 * @param ctx the parse tree
	 */
	void enterParameter(MyLangParser.ParameterContext ctx);
	/**
	 * Exit a parse tree produced by {@link MyLangParser#parameter}.
	 * @param ctx the parse tree
	 */
	void exitParameter(MyLangParser.ParameterContext ctx);
	/**
	 * Enter a parse tree produced by {@link MyLangParser#args}.
	 * @param ctx the parse tree
	 */
	void enterArgs(MyLangParser.ArgsContext ctx);
	/**
	 * Exit a parse tree produced by {@link MyLangParser#args}.
	 * @param ctx the parse tree
	 */
	void exitArgs(MyLangParser.ArgsContext ctx);
}