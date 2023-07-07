// Generated from C:/Programming/SprockellCompiler/src/main/java/antlr4/ut/pp/parser\MyLang.g4 by ANTLR 4.12.0
package antlr4.ut.pp.parser;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link MyLangParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface MyLangVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link MyLangParser#module}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitModule(MyLangParser.ModuleContext ctx);
	/**
	 * Visit a parse tree produced by {@link MyLangParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(MyLangParser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MyLangParser#body}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBody(MyLangParser.BodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link MyLangParser#compound_statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCompound_statement(MyLangParser.Compound_statementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MyLangParser#assignment_statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignment_statement(MyLangParser.Assignment_statementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MyLangParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression(MyLangParser.ExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MyLangParser#logical_or_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogical_or_expression(MyLangParser.Logical_or_expressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MyLangParser#logical_and_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogical_and_expression(MyLangParser.Logical_and_expressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MyLangParser#relational_expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRelational_expr(MyLangParser.Relational_exprContext ctx);
	/**
	 * Visit a parse tree produced by {@link MyLangParser#additive_expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAdditive_expr(MyLangParser.Additive_exprContext ctx);
	/**
	 * Visit a parse tree produced by {@link MyLangParser#multi_expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMulti_expr(MyLangParser.Multi_exprContext ctx);
	/**
	 * Visit a parse tree produced by {@link MyLangParser#postfix_expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPostfix_expr(MyLangParser.Postfix_exprContext ctx);
	/**
	 * Visit a parse tree produced by {@link MyLangParser#atomic_expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAtomic_expr(MyLangParser.Atomic_exprContext ctx);
	/**
	 * Visit a parse tree produced by {@link MyLangParser#var_call}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVar_call(MyLangParser.Var_callContext ctx);
	/**
	 * Visit a parse tree produced by {@link MyLangParser#assignment_operator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignment_operator(MyLangParser.Assignment_operatorContext ctx);
	/**
	 * Visit a parse tree produced by {@link MyLangParser#relational_operator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRelational_operator(MyLangParser.Relational_operatorContext ctx);
	/**
	 * Visit a parse tree produced by {@link MyLangParser#additive_operator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAdditive_operator(MyLangParser.Additive_operatorContext ctx);
	/**
	 * Visit a parse tree produced by {@link MyLangParser#multi_operator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMulti_operator(MyLangParser.Multi_operatorContext ctx);
	/**
	 * Visit a parse tree produced by {@link MyLangParser#while_statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhile_statement(MyLangParser.While_statementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MyLangParser#if_statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIf_statement(MyLangParser.If_statementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MyLangParser#fork_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFork_expression(MyLangParser.Fork_expressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MyLangParser#get_thread_id_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGet_thread_id_expression(MyLangParser.Get_thread_id_expressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MyLangParser#join_statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJoin_statement(MyLangParser.Join_statementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MyLangParser#lock_statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLock_statement(MyLangParser.Lock_statementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MyLangParser#var_def}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVar_def(MyLangParser.Var_defContext ctx);
	/**
	 * Visit a parse tree produced by {@link MyLangParser#print_statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrint_statement(MyLangParser.Print_statementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MyLangParser#read_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRead_expression(MyLangParser.Read_expressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MyLangParser#primitive_type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimitive_type(MyLangParser.Primitive_typeContext ctx);
	/**
	 * Visit a parse tree produced by {@link MyLangParser#array}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArray(MyLangParser.ArrayContext ctx);
	/**
	 * Visit a parse tree produced by {@link MyLangParser#empty_statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEmpty_statement(MyLangParser.Empty_statementContext ctx);
}