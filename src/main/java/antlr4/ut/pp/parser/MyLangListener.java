// Generated from C:/Programming/SprockellCompiler/src/main/java/antlr4/ut/pp/parser\MyLang.g4 by ANTLR 4.12.0
package antlr4.ut.pp.parser;
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
	 * Enter a parse tree produced by {@link MyLangParser#assignment_statement}.
	 * @param ctx the parse tree
	 */
	void enterAssignment_statement(MyLangParser.Assignment_statementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MyLangParser#assignment_statement}.
	 * @param ctx the parse tree
	 */
	void exitAssignment_statement(MyLangParser.Assignment_statementContext ctx);
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
	 * Enter a parse tree produced by {@link MyLangParser#var_call}.
	 * @param ctx the parse tree
	 */
	void enterVar_call(MyLangParser.Var_callContext ctx);
	/**
	 * Exit a parse tree produced by {@link MyLangParser#var_call}.
	 * @param ctx the parse tree
	 */
	void exitVar_call(MyLangParser.Var_callContext ctx);
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
	 * Enter a parse tree produced by {@link MyLangParser#fork_expression}.
	 * @param ctx the parse tree
	 */
	void enterFork_expression(MyLangParser.Fork_expressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MyLangParser#fork_expression}.
	 * @param ctx the parse tree
	 */
	void exitFork_expression(MyLangParser.Fork_expressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MyLangParser#get_thread_id_expression}.
	 * @param ctx the parse tree
	 */
	void enterGet_thread_id_expression(MyLangParser.Get_thread_id_expressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MyLangParser#get_thread_id_expression}.
	 * @param ctx the parse tree
	 */
	void exitGet_thread_id_expression(MyLangParser.Get_thread_id_expressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MyLangParser#join_statement}.
	 * @param ctx the parse tree
	 */
	void enterJoin_statement(MyLangParser.Join_statementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MyLangParser#join_statement}.
	 * @param ctx the parse tree
	 */
	void exitJoin_statement(MyLangParser.Join_statementContext ctx);
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
	 * Enter a parse tree produced by {@link MyLangParser#read_expression}.
	 * @param ctx the parse tree
	 */
	void enterRead_expression(MyLangParser.Read_expressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MyLangParser#read_expression}.
	 * @param ctx the parse tree
	 */
	void exitRead_expression(MyLangParser.Read_expressionContext ctx);
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
	 * Enter a parse tree produced by {@link MyLangParser#empty_statement}.
	 * @param ctx the parse tree
	 */
	void enterEmpty_statement(MyLangParser.Empty_statementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MyLangParser#empty_statement}.
	 * @param ctx the parse tree
	 */
	void exitEmpty_statement(MyLangParser.Empty_statementContext ctx);
}