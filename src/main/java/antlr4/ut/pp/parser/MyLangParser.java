// Generated from C:/Programming/SprockellCompiler/src/main/java/antlr4/ut/pp/parser\MyLang.g4 by ANTLR 4.12.0
package antlr4.ut.pp.parser;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue"})
public class MyLangParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.12.0", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, T__21=22, T__22=23, T__23=24, 
		T__24=25, T__25=26, T__26=27, BOOL=28, FORK=29, JOIN=30, TID=31, LOCK=32, 
		UNLOCK=33, SHARED=34, POINTER=35, IDENTIFIER=36, INT=37, STRING=38, ESCAPE_SEQUENCE=39, 
		COMMENT=40, WS=41;
	public static final int
		RULE_module = 0, RULE_statement = 1, RULE_body = 2, RULE_compound_statement = 3, 
		RULE_expression_statement = 4, RULE_expression = 5, RULE_logical_or_expression = 6, 
		RULE_logical_and_expression = 7, RULE_relational_expr = 8, RULE_additive_expr = 9, 
		RULE_multi_expr = 10, RULE_postfix_expr = 11, RULE_atomic_expr = 12, RULE_var_call = 13, 
		RULE_assignment_operator = 14, RULE_relational_operator = 15, RULE_additive_operator = 16, 
		RULE_multi_operator = 17, RULE_iteration_statement = 18, RULE_while_statement = 19, 
		RULE_if_statement = 20, RULE_elif_part = 21, RULE_else_part = 22, RULE_fork_expression = 23, 
		RULE_get_thread_id_expression = 24, RULE_join_statement = 25, RULE_lock_statement = 26, 
		RULE_var_def = 27, RULE_print_statement = 28, RULE_read_expression = 29, 
		RULE_primitive_type = 30, RULE_compound_type = 31, RULE_array = 32;
	private static String[] makeRuleNames() {
		return new String[] {
			"module", "statement", "body", "compound_statement", "expression_statement", 
			"expression", "logical_or_expression", "logical_and_expression", "relational_expr", 
			"additive_expr", "multi_expr", "postfix_expr", "atomic_expr", "var_call", 
			"assignment_operator", "relational_operator", "additive_operator", "multi_operator", 
			"iteration_statement", "while_statement", "if_statement", "elif_part", 
			"else_part", "fork_expression", "get_thread_id_expression", "join_statement", 
			"lock_statement", "var_def", "print_statement", "read_expression", "primitive_type", 
			"compound_type", "array"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'{'", "'}'", "';'", "'||'", "'&&'", "'['", "']'", "'('", "')'", 
			"'&'", "'='", "'=='", "'!='", "'>='", "'<='", "'>'", "'<'", "'+'", "'-'", 
			"'while'", "'if'", "'elif'", "'else'", "'var'", "'print'", "'read'", 
			"','", null, "'fork'", "'join'", "'Tid'", "'lock'", "'unlock'", "'shared'", 
			"'*'", null, null, null, "'\\\"'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, "BOOL", "FORK", "JOIN", "TID", "LOCK", "UNLOCK", 
			"SHARED", "POINTER", "IDENTIFIER", "INT", "STRING", "ESCAPE_SEQUENCE", 
			"COMMENT", "WS"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "MyLang.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public MyLangParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ModuleContext extends ParserRuleContext {
		public BodyContext body() {
			return getRuleContext(BodyContext.class,0);
		}
		public ModuleContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_module; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MyLangListener ) ((MyLangListener)listener).enterModule(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MyLangListener ) ((MyLangListener)listener).exitModule(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MyLangVisitor ) return ((MyLangVisitor<? extends T>)visitor).visitModule(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ModuleContext module() throws RecognitionException {
		ModuleContext _localctx = new ModuleContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_module);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(66);
			body();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class StatementContext extends ParserRuleContext {
		public Var_defContext var_def() {
			return getRuleContext(Var_defContext.class,0);
		}
		public Compound_statementContext compound_statement() {
			return getRuleContext(Compound_statementContext.class,0);
		}
		public Expression_statementContext expression_statement() {
			return getRuleContext(Expression_statementContext.class,0);
		}
		public Iteration_statementContext iteration_statement() {
			return getRuleContext(Iteration_statementContext.class,0);
		}
		public If_statementContext if_statement() {
			return getRuleContext(If_statementContext.class,0);
		}
		public Print_statementContext print_statement() {
			return getRuleContext(Print_statementContext.class,0);
		}
		public Lock_statementContext lock_statement() {
			return getRuleContext(Lock_statementContext.class,0);
		}
		public Join_statementContext join_statement() {
			return getRuleContext(Join_statementContext.class,0);
		}
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MyLangListener ) ((MyLangListener)listener).enterStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MyLangListener ) ((MyLangListener)listener).exitStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MyLangVisitor ) return ((MyLangVisitor<? extends T>)visitor).visitStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_statement);
		try {
			setState(76);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__23:
			case SHARED:
				enterOuterAlt(_localctx, 1);
				{
				setState(68);
				var_def();
				}
				break;
			case T__0:
				enterOuterAlt(_localctx, 2);
				{
				setState(69);
				compound_statement();
				}
				break;
			case T__2:
			case T__5:
			case T__7:
			case T__9:
			case T__25:
			case BOOL:
			case FORK:
			case TID:
			case POINTER:
			case IDENTIFIER:
			case INT:
			case STRING:
				enterOuterAlt(_localctx, 3);
				{
				setState(70);
				expression_statement();
				}
				break;
			case T__19:
				enterOuterAlt(_localctx, 4);
				{
				setState(71);
				iteration_statement();
				}
				break;
			case T__20:
				enterOuterAlt(_localctx, 5);
				{
				setState(72);
				if_statement();
				}
				break;
			case T__24:
				enterOuterAlt(_localctx, 6);
				{
				setState(73);
				print_statement();
				}
				break;
			case LOCK:
			case UNLOCK:
				enterOuterAlt(_localctx, 7);
				{
				setState(74);
				lock_statement();
				}
				break;
			case JOIN:
				enterOuterAlt(_localctx, 8);
				{
				setState(75);
				join_statement();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class BodyContext extends ParserRuleContext {
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public BodyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_body; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MyLangListener ) ((MyLangListener)listener).enterBody(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MyLangListener ) ((MyLangListener)listener).exitBody(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MyLangVisitor ) return ((MyLangVisitor<? extends T>)visitor).visitBody(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BodyContext body() throws RecognitionException {
		BodyContext _localctx = new BodyContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_body);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(79); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(78);
				statement();
				}
				}
				setState(81); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & 549607966026L) != 0) );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Compound_statementContext extends ParserRuleContext {
		public BodyContext body() {
			return getRuleContext(BodyContext.class,0);
		}
		public Compound_statementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_compound_statement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MyLangListener ) ((MyLangListener)listener).enterCompound_statement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MyLangListener ) ((MyLangListener)listener).exitCompound_statement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MyLangVisitor ) return ((MyLangVisitor<? extends T>)visitor).visitCompound_statement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Compound_statementContext compound_statement() throws RecognitionException {
		Compound_statementContext _localctx = new Compound_statementContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_compound_statement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(83);
			match(T__0);
			setState(84);
			body();
			setState(85);
			match(T__1);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Expression_statementContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public Expression_statementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression_statement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MyLangListener ) ((MyLangListener)listener).enterExpression_statement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MyLangListener ) ((MyLangListener)listener).exitExpression_statement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MyLangVisitor ) return ((MyLangVisitor<? extends T>)visitor).visitExpression_statement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Expression_statementContext expression_statement() throws RecognitionException {
		Expression_statementContext _localctx = new Expression_statementContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_expression_statement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(88);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 518415975744L) != 0)) {
				{
				setState(87);
				expression();
				}
			}

			setState(90);
			match(T__2);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ExpressionContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(MyLangParser.IDENTIFIER, 0); }
		public Assignment_operatorContext assignment_operator() {
			return getRuleContext(Assignment_operatorContext.class,0);
		}
		public Atomic_exprContext atomic_expr() {
			return getRuleContext(Atomic_exprContext.class,0);
		}
		public Postfix_exprContext postfix_expr() {
			return getRuleContext(Postfix_exprContext.class,0);
		}
		public Logical_or_expressionContext logical_or_expression() {
			return getRuleContext(Logical_or_expressionContext.class,0);
		}
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MyLangListener ) ((MyLangListener)listener).enterExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MyLangListener ) ((MyLangListener)listener).exitExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MyLangVisitor ) return ((MyLangVisitor<? extends T>)visitor).visitExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpressionContext expression() throws RecognitionException {
		ExpressionContext _localctx = new ExpressionContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_expression);
		try {
			setState(100);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,4,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				{
				setState(92);
				match(IDENTIFIER);
				setState(93);
				assignment_operator();
				setState(97);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,3,_ctx) ) {
				case 1:
					{
					setState(94);
					atomic_expr();
					}
					break;
				case 2:
					{
					setState(95);
					postfix_expr();
					}
					break;
				case 3:
					{
					setState(96);
					logical_or_expression();
					}
					break;
				}
				}
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(99);
				logical_or_expression();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Logical_or_expressionContext extends ParserRuleContext {
		public List<Logical_and_expressionContext> logical_and_expression() {
			return getRuleContexts(Logical_and_expressionContext.class);
		}
		public Logical_and_expressionContext logical_and_expression(int i) {
			return getRuleContext(Logical_and_expressionContext.class,i);
		}
		public Logical_or_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_logical_or_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MyLangListener ) ((MyLangListener)listener).enterLogical_or_expression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MyLangListener ) ((MyLangListener)listener).exitLogical_or_expression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MyLangVisitor ) return ((MyLangVisitor<? extends T>)visitor).visitLogical_or_expression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Logical_or_expressionContext logical_or_expression() throws RecognitionException {
		Logical_or_expressionContext _localctx = new Logical_or_expressionContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_logical_or_expression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(102);
			logical_and_expression();
			setState(107);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__3) {
				{
				{
				setState(103);
				match(T__3);
				setState(104);
				logical_and_expression();
				}
				}
				setState(109);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Logical_and_expressionContext extends ParserRuleContext {
		public List<Relational_exprContext> relational_expr() {
			return getRuleContexts(Relational_exprContext.class);
		}
		public Relational_exprContext relational_expr(int i) {
			return getRuleContext(Relational_exprContext.class,i);
		}
		public Logical_and_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_logical_and_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MyLangListener ) ((MyLangListener)listener).enterLogical_and_expression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MyLangListener ) ((MyLangListener)listener).exitLogical_and_expression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MyLangVisitor ) return ((MyLangVisitor<? extends T>)visitor).visitLogical_and_expression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Logical_and_expressionContext logical_and_expression() throws RecognitionException {
		Logical_and_expressionContext _localctx = new Logical_and_expressionContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_logical_and_expression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(110);
			relational_expr();
			setState(115);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__4) {
				{
				{
				setState(111);
				match(T__4);
				setState(112);
				relational_expr();
				}
				}
				setState(117);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Relational_exprContext extends ParserRuleContext {
		public List<Additive_exprContext> additive_expr() {
			return getRuleContexts(Additive_exprContext.class);
		}
		public Additive_exprContext additive_expr(int i) {
			return getRuleContext(Additive_exprContext.class,i);
		}
		public List<Relational_operatorContext> relational_operator() {
			return getRuleContexts(Relational_operatorContext.class);
		}
		public Relational_operatorContext relational_operator(int i) {
			return getRuleContext(Relational_operatorContext.class,i);
		}
		public Relational_exprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_relational_expr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MyLangListener ) ((MyLangListener)listener).enterRelational_expr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MyLangListener ) ((MyLangListener)listener).exitRelational_expr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MyLangVisitor ) return ((MyLangVisitor<? extends T>)visitor).visitRelational_expr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Relational_exprContext relational_expr() throws RecognitionException {
		Relational_exprContext _localctx = new Relational_exprContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_relational_expr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(118);
			additive_expr();
			setState(124);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 258048L) != 0)) {
				{
				{
				setState(119);
				relational_operator();
				setState(120);
				additive_expr();
				}
				}
				setState(126);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Additive_exprContext extends ParserRuleContext {
		public List<Multi_exprContext> multi_expr() {
			return getRuleContexts(Multi_exprContext.class);
		}
		public Multi_exprContext multi_expr(int i) {
			return getRuleContext(Multi_exprContext.class,i);
		}
		public List<Additive_operatorContext> additive_operator() {
			return getRuleContexts(Additive_operatorContext.class);
		}
		public Additive_operatorContext additive_operator(int i) {
			return getRuleContext(Additive_operatorContext.class,i);
		}
		public Additive_exprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_additive_expr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MyLangListener ) ((MyLangListener)listener).enterAdditive_expr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MyLangListener ) ((MyLangListener)listener).exitAdditive_expr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MyLangVisitor ) return ((MyLangVisitor<? extends T>)visitor).visitAdditive_expr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Additive_exprContext additive_expr() throws RecognitionException {
		Additive_exprContext _localctx = new Additive_exprContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_additive_expr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(127);
			multi_expr();
			setState(133);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__17 || _la==T__18) {
				{
				{
				setState(128);
				additive_operator();
				setState(129);
				multi_expr();
				}
				}
				setState(135);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Multi_exprContext extends ParserRuleContext {
		public List<Postfix_exprContext> postfix_expr() {
			return getRuleContexts(Postfix_exprContext.class);
		}
		public Postfix_exprContext postfix_expr(int i) {
			return getRuleContext(Postfix_exprContext.class,i);
		}
		public List<Multi_operatorContext> multi_operator() {
			return getRuleContexts(Multi_operatorContext.class);
		}
		public Multi_operatorContext multi_operator(int i) {
			return getRuleContext(Multi_operatorContext.class,i);
		}
		public Multi_exprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_multi_expr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MyLangListener ) ((MyLangListener)listener).enterMulti_expr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MyLangListener ) ((MyLangListener)listener).exitMulti_expr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MyLangVisitor ) return ((MyLangVisitor<? extends T>)visitor).visitMulti_expr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Multi_exprContext multi_expr() throws RecognitionException {
		Multi_exprContext _localctx = new Multi_exprContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_multi_expr);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(136);
			postfix_expr();
			setState(142);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,9,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(137);
					multi_operator();
					setState(138);
					postfix_expr();
					}
					} 
				}
				setState(144);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,9,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Postfix_exprContext extends ParserRuleContext {
		public Var_callContext var_call() {
			return getRuleContext(Var_callContext.class,0);
		}
		public Primitive_typeContext primitive_type() {
			return getRuleContext(Primitive_typeContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public Atomic_exprContext atomic_expr() {
			return getRuleContext(Atomic_exprContext.class,0);
		}
		public Postfix_exprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_postfix_expr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MyLangListener ) ((MyLangListener)listener).enterPostfix_expr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MyLangListener ) ((MyLangListener)listener).exitPostfix_expr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MyLangVisitor ) return ((MyLangVisitor<? extends T>)visitor).visitPostfix_expr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Postfix_exprContext postfix_expr() throws RecognitionException {
		Postfix_exprContext _localctx = new Postfix_exprContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_postfix_expr);
		try {
			setState(154);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,11,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(145);
				var_call();
				setState(146);
				match(T__5);
				setState(149);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,10,_ctx) ) {
				case 1:
					{
					setState(147);
					primitive_type();
					}
					break;
				case 2:
					{
					setState(148);
					expression();
					}
					break;
				}
				setState(151);
				match(T__6);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(153);
				atomic_expr();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Atomic_exprContext extends ParserRuleContext {
		public Primitive_typeContext primitive_type() {
			return getRuleContext(Primitive_typeContext.class,0);
		}
		public Var_callContext var_call() {
			return getRuleContext(Var_callContext.class,0);
		}
		public Get_thread_id_expressionContext get_thread_id_expression() {
			return getRuleContext(Get_thread_id_expressionContext.class,0);
		}
		public Read_expressionContext read_expression() {
			return getRuleContext(Read_expressionContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public Compound_typeContext compound_type() {
			return getRuleContext(Compound_typeContext.class,0);
		}
		public Fork_expressionContext fork_expression() {
			return getRuleContext(Fork_expressionContext.class,0);
		}
		public Atomic_exprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_atomic_expr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MyLangListener ) ((MyLangListener)listener).enterAtomic_expr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MyLangListener ) ((MyLangListener)listener).exitAtomic_expr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MyLangVisitor ) return ((MyLangVisitor<? extends T>)visitor).visitAtomic_expr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Atomic_exprContext atomic_expr() throws RecognitionException {
		Atomic_exprContext _localctx = new Atomic_exprContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_atomic_expr);
		try {
			setState(166);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case BOOL:
			case INT:
				enterOuterAlt(_localctx, 1);
				{
				setState(156);
				primitive_type();
				}
				break;
			case T__9:
			case POINTER:
			case IDENTIFIER:
				enterOuterAlt(_localctx, 2);
				{
				setState(157);
				var_call();
				}
				break;
			case TID:
				enterOuterAlt(_localctx, 3);
				{
				setState(158);
				get_thread_id_expression();
				}
				break;
			case T__25:
				enterOuterAlt(_localctx, 4);
				{
				setState(159);
				read_expression();
				}
				break;
			case T__7:
				enterOuterAlt(_localctx, 5);
				{
				setState(160);
				match(T__7);
				setState(161);
				expression();
				setState(162);
				match(T__8);
				}
				break;
			case T__5:
			case STRING:
				enterOuterAlt(_localctx, 6);
				{
				setState(164);
				compound_type();
				}
				break;
			case FORK:
				enterOuterAlt(_localctx, 7);
				{
				setState(165);
				fork_expression();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Var_callContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(MyLangParser.IDENTIFIER, 0); }
		public TerminalNode POINTER() { return getToken(MyLangParser.POINTER, 0); }
		public Var_callContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_var_call; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MyLangListener ) ((MyLangListener)listener).enterVar_call(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MyLangListener ) ((MyLangListener)listener).exitVar_call(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MyLangVisitor ) return ((MyLangVisitor<? extends T>)visitor).visitVar_call(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Var_callContext var_call() throws RecognitionException {
		Var_callContext _localctx = new Var_callContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_var_call);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(169);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__9 || _la==POINTER) {
				{
				setState(168);
				_la = _input.LA(1);
				if ( !(_la==T__9 || _la==POINTER) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
			}

			setState(171);
			match(IDENTIFIER);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Assignment_operatorContext extends ParserRuleContext {
		public Assignment_operatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assignment_operator; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MyLangListener ) ((MyLangListener)listener).enterAssignment_operator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MyLangListener ) ((MyLangListener)listener).exitAssignment_operator(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MyLangVisitor ) return ((MyLangVisitor<? extends T>)visitor).visitAssignment_operator(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Assignment_operatorContext assignment_operator() throws RecognitionException {
		Assignment_operatorContext _localctx = new Assignment_operatorContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_assignment_operator);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(173);
			match(T__10);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Relational_operatorContext extends ParserRuleContext {
		public Relational_operatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_relational_operator; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MyLangListener ) ((MyLangListener)listener).enterRelational_operator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MyLangListener ) ((MyLangListener)listener).exitRelational_operator(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MyLangVisitor ) return ((MyLangVisitor<? extends T>)visitor).visitRelational_operator(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Relational_operatorContext relational_operator() throws RecognitionException {
		Relational_operatorContext _localctx = new Relational_operatorContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_relational_operator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(175);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 258048L) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Additive_operatorContext extends ParserRuleContext {
		public Additive_operatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_additive_operator; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MyLangListener ) ((MyLangListener)listener).enterAdditive_operator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MyLangListener ) ((MyLangListener)listener).exitAdditive_operator(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MyLangVisitor ) return ((MyLangVisitor<? extends T>)visitor).visitAdditive_operator(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Additive_operatorContext additive_operator() throws RecognitionException {
		Additive_operatorContext _localctx = new Additive_operatorContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_additive_operator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(177);
			_la = _input.LA(1);
			if ( !(_la==T__17 || _la==T__18) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Multi_operatorContext extends ParserRuleContext {
		public TerminalNode POINTER() { return getToken(MyLangParser.POINTER, 0); }
		public Multi_operatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_multi_operator; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MyLangListener ) ((MyLangListener)listener).enterMulti_operator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MyLangListener ) ((MyLangListener)listener).exitMulti_operator(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MyLangVisitor ) return ((MyLangVisitor<? extends T>)visitor).visitMulti_operator(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Multi_operatorContext multi_operator() throws RecognitionException {
		Multi_operatorContext _localctx = new Multi_operatorContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_multi_operator);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(179);
			match(POINTER);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Iteration_statementContext extends ParserRuleContext {
		public While_statementContext while_statement() {
			return getRuleContext(While_statementContext.class,0);
		}
		public Iteration_statementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_iteration_statement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MyLangListener ) ((MyLangListener)listener).enterIteration_statement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MyLangListener ) ((MyLangListener)listener).exitIteration_statement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MyLangVisitor ) return ((MyLangVisitor<? extends T>)visitor).visitIteration_statement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Iteration_statementContext iteration_statement() throws RecognitionException {
		Iteration_statementContext _localctx = new Iteration_statementContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_iteration_statement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(181);
			while_statement();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class While_statementContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public Compound_statementContext compound_statement() {
			return getRuleContext(Compound_statementContext.class,0);
		}
		public While_statementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_while_statement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MyLangListener ) ((MyLangListener)listener).enterWhile_statement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MyLangListener ) ((MyLangListener)listener).exitWhile_statement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MyLangVisitor ) return ((MyLangVisitor<? extends T>)visitor).visitWhile_statement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final While_statementContext while_statement() throws RecognitionException {
		While_statementContext _localctx = new While_statementContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_while_statement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(183);
			match(T__19);
			setState(184);
			expression();
			setState(185);
			compound_statement();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class If_statementContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public Compound_statementContext compound_statement() {
			return getRuleContext(Compound_statementContext.class,0);
		}
		public List<Elif_partContext> elif_part() {
			return getRuleContexts(Elif_partContext.class);
		}
		public Elif_partContext elif_part(int i) {
			return getRuleContext(Elif_partContext.class,i);
		}
		public Else_partContext else_part() {
			return getRuleContext(Else_partContext.class,0);
		}
		public If_statementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_if_statement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MyLangListener ) ((MyLangListener)listener).enterIf_statement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MyLangListener ) ((MyLangListener)listener).exitIf_statement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MyLangVisitor ) return ((MyLangVisitor<? extends T>)visitor).visitIf_statement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final If_statementContext if_statement() throws RecognitionException {
		If_statementContext _localctx = new If_statementContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_if_statement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(187);
			match(T__20);
			setState(188);
			expression();
			setState(189);
			compound_statement();
			setState(193);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__21) {
				{
				{
				setState(190);
				elif_part();
				}
				}
				setState(195);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(197);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__22) {
				{
				setState(196);
				else_part();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Elif_partContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public Compound_statementContext compound_statement() {
			return getRuleContext(Compound_statementContext.class,0);
		}
		public Elif_partContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_elif_part; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MyLangListener ) ((MyLangListener)listener).enterElif_part(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MyLangListener ) ((MyLangListener)listener).exitElif_part(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MyLangVisitor ) return ((MyLangVisitor<? extends T>)visitor).visitElif_part(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Elif_partContext elif_part() throws RecognitionException {
		Elif_partContext _localctx = new Elif_partContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_elif_part);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(199);
			match(T__21);
			setState(200);
			expression();
			setState(201);
			compound_statement();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Else_partContext extends ParserRuleContext {
		public Compound_statementContext compound_statement() {
			return getRuleContext(Compound_statementContext.class,0);
		}
		public Else_partContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_else_part; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MyLangListener ) ((MyLangListener)listener).enterElse_part(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MyLangListener ) ((MyLangListener)listener).exitElse_part(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MyLangVisitor ) return ((MyLangVisitor<? extends T>)visitor).visitElse_part(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Else_partContext else_part() throws RecognitionException {
		Else_partContext _localctx = new Else_partContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_else_part);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(203);
			match(T__22);
			setState(204);
			compound_statement();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Fork_expressionContext extends ParserRuleContext {
		public TerminalNode FORK() { return getToken(MyLangParser.FORK, 0); }
		public Compound_statementContext compound_statement() {
			return getRuleContext(Compound_statementContext.class,0);
		}
		public Fork_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_fork_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MyLangListener ) ((MyLangListener)listener).enterFork_expression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MyLangListener ) ((MyLangListener)listener).exitFork_expression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MyLangVisitor ) return ((MyLangVisitor<? extends T>)visitor).visitFork_expression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Fork_expressionContext fork_expression() throws RecognitionException {
		Fork_expressionContext _localctx = new Fork_expressionContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_fork_expression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(206);
			match(FORK);
			setState(207);
			compound_statement();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Get_thread_id_expressionContext extends ParserRuleContext {
		public TerminalNode TID() { return getToken(MyLangParser.TID, 0); }
		public Get_thread_id_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_get_thread_id_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MyLangListener ) ((MyLangListener)listener).enterGet_thread_id_expression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MyLangListener ) ((MyLangListener)listener).exitGet_thread_id_expression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MyLangVisitor ) return ((MyLangVisitor<? extends T>)visitor).visitGet_thread_id_expression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Get_thread_id_expressionContext get_thread_id_expression() throws RecognitionException {
		Get_thread_id_expressionContext _localctx = new Get_thread_id_expressionContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_get_thread_id_expression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(209);
			match(TID);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Join_statementContext extends ParserRuleContext {
		public TerminalNode JOIN() { return getToken(MyLangParser.JOIN, 0); }
		public Var_callContext var_call() {
			return getRuleContext(Var_callContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public Join_statementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_join_statement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MyLangListener ) ((MyLangListener)listener).enterJoin_statement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MyLangListener ) ((MyLangListener)listener).exitJoin_statement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MyLangVisitor ) return ((MyLangVisitor<? extends T>)visitor).visitJoin_statement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Join_statementContext join_statement() throws RecognitionException {
		Join_statementContext _localctx = new Join_statementContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_join_statement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(211);
			match(JOIN);
			setState(214);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,16,_ctx) ) {
			case 1:
				{
				setState(212);
				var_call();
				}
				break;
			case 2:
				{
				setState(213);
				expression();
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Lock_statementContext extends ParserRuleContext {
		public TerminalNode LOCK() { return getToken(MyLangParser.LOCK, 0); }
		public TerminalNode IDENTIFIER() { return getToken(MyLangParser.IDENTIFIER, 0); }
		public TerminalNode UNLOCK() { return getToken(MyLangParser.UNLOCK, 0); }
		public Lock_statementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_lock_statement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MyLangListener ) ((MyLangListener)listener).enterLock_statement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MyLangListener ) ((MyLangListener)listener).exitLock_statement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MyLangVisitor ) return ((MyLangVisitor<? extends T>)visitor).visitLock_statement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Lock_statementContext lock_statement() throws RecognitionException {
		Lock_statementContext _localctx = new Lock_statementContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_lock_statement);
		try {
			setState(220);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LOCK:
				enterOuterAlt(_localctx, 1);
				{
				setState(216);
				match(LOCK);
				setState(217);
				match(IDENTIFIER);
				}
				break;
			case UNLOCK:
				enterOuterAlt(_localctx, 2);
				{
				setState(218);
				match(UNLOCK);
				setState(219);
				match(IDENTIFIER);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Var_defContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(MyLangParser.IDENTIFIER, 0); }
		public Atomic_exprContext atomic_expr() {
			return getRuleContext(Atomic_exprContext.class,0);
		}
		public Postfix_exprContext postfix_expr() {
			return getRuleContext(Postfix_exprContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode POINTER() { return getToken(MyLangParser.POINTER, 0); }
		public TerminalNode SHARED() { return getToken(MyLangParser.SHARED, 0); }
		public Var_defContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_var_def; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MyLangListener ) ((MyLangListener)listener).enterVar_def(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MyLangListener ) ((MyLangListener)listener).exitVar_def(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MyLangVisitor ) return ((MyLangVisitor<? extends T>)visitor).visitVar_def(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Var_defContext var_def() throws RecognitionException {
		Var_defContext _localctx = new Var_defContext(_ctx, getState());
		enterRule(_localctx, 54, RULE_var_def);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(223);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==SHARED) {
				{
				setState(222);
				match(SHARED);
				}
			}

			setState(225);
			match(T__23);
			}
			setState(228);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==POINTER) {
				{
				setState(227);
				match(POINTER);
				}
			}

			setState(230);
			match(IDENTIFIER);
			setState(231);
			match(T__10);
			setState(235);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,20,_ctx) ) {
			case 1:
				{
				setState(232);
				atomic_expr();
				}
				break;
			case 2:
				{
				setState(233);
				postfix_expr();
				}
				break;
			case 3:
				{
				setState(234);
				expression();
				}
				break;
			}
			setState(237);
			match(T__2);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Print_statementContext extends ParserRuleContext {
		public Atomic_exprContext atomic_expr() {
			return getRuleContext(Atomic_exprContext.class,0);
		}
		public Postfix_exprContext postfix_expr() {
			return getRuleContext(Postfix_exprContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public Print_statementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_print_statement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MyLangListener ) ((MyLangListener)listener).enterPrint_statement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MyLangListener ) ((MyLangListener)listener).exitPrint_statement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MyLangVisitor ) return ((MyLangVisitor<? extends T>)visitor).visitPrint_statement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Print_statementContext print_statement() throws RecognitionException {
		Print_statementContext _localctx = new Print_statementContext(_ctx, getState());
		enterRule(_localctx, 56, RULE_print_statement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(239);
			match(T__24);
			setState(243);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,21,_ctx) ) {
			case 1:
				{
				setState(240);
				atomic_expr();
				}
				break;
			case 2:
				{
				setState(241);
				postfix_expr();
				}
				break;
			case 3:
				{
				setState(242);
				expression();
				}
				break;
			}
			setState(245);
			match(T__2);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Read_expressionContext extends ParserRuleContext {
		public Read_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_read_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MyLangListener ) ((MyLangListener)listener).enterRead_expression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MyLangListener ) ((MyLangListener)listener).exitRead_expression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MyLangVisitor ) return ((MyLangVisitor<? extends T>)visitor).visitRead_expression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Read_expressionContext read_expression() throws RecognitionException {
		Read_expressionContext _localctx = new Read_expressionContext(_ctx, getState());
		enterRule(_localctx, 58, RULE_read_expression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(247);
			match(T__25);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Primitive_typeContext extends ParserRuleContext {
		public TerminalNode INT() { return getToken(MyLangParser.INT, 0); }
		public TerminalNode BOOL() { return getToken(MyLangParser.BOOL, 0); }
		public Primitive_typeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_primitive_type; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MyLangListener ) ((MyLangListener)listener).enterPrimitive_type(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MyLangListener ) ((MyLangListener)listener).exitPrimitive_type(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MyLangVisitor ) return ((MyLangVisitor<? extends T>)visitor).visitPrimitive_type(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Primitive_typeContext primitive_type() throws RecognitionException {
		Primitive_typeContext _localctx = new Primitive_typeContext(_ctx, getState());
		enterRule(_localctx, 60, RULE_primitive_type);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(249);
			_la = _input.LA(1);
			if ( !(_la==BOOL || _la==INT) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Compound_typeContext extends ParserRuleContext {
		public ArrayContext array() {
			return getRuleContext(ArrayContext.class,0);
		}
		public TerminalNode STRING() { return getToken(MyLangParser.STRING, 0); }
		public Compound_typeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_compound_type; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MyLangListener ) ((MyLangListener)listener).enterCompound_type(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MyLangListener ) ((MyLangListener)listener).exitCompound_type(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MyLangVisitor ) return ((MyLangVisitor<? extends T>)visitor).visitCompound_type(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Compound_typeContext compound_type() throws RecognitionException {
		Compound_typeContext _localctx = new Compound_typeContext(_ctx, getState());
		enterRule(_localctx, 62, RULE_compound_type);
		try {
			setState(253);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__5:
				enterOuterAlt(_localctx, 1);
				{
				setState(251);
				array();
				}
				break;
			case STRING:
				enterOuterAlt(_localctx, 2);
				{
				setState(252);
				match(STRING);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ArrayContext extends ParserRuleContext {
		public List<Primitive_typeContext> primitive_type() {
			return getRuleContexts(Primitive_typeContext.class);
		}
		public Primitive_typeContext primitive_type(int i) {
			return getRuleContext(Primitive_typeContext.class,i);
		}
		public List<Atomic_exprContext> atomic_expr() {
			return getRuleContexts(Atomic_exprContext.class);
		}
		public Atomic_exprContext atomic_expr(int i) {
			return getRuleContext(Atomic_exprContext.class,i);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public ArrayContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_array; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MyLangListener ) ((MyLangListener)listener).enterArray(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MyLangListener ) ((MyLangListener)listener).exitArray(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MyLangVisitor ) return ((MyLangVisitor<? extends T>)visitor).visitArray(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArrayContext array() throws RecognitionException {
		ArrayContext _localctx = new ArrayContext(_ctx, getState());
		enterRule(_localctx, 64, RULE_array);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(255);
			match(T__5);
			setState(272);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 518415975744L) != 0)) {
				{
				setState(259);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,23,_ctx) ) {
				case 1:
					{
					setState(256);
					primitive_type();
					}
					break;
				case 2:
					{
					setState(257);
					atomic_expr();
					}
					break;
				case 3:
					{
					setState(258);
					expression();
					}
					break;
				}
				setState(269);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__26) {
					{
					{
					setState(261);
					match(T__26);
					setState(265);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,24,_ctx) ) {
					case 1:
						{
						setState(262);
						primitive_type();
						}
						break;
					case 2:
						{
						setState(263);
						atomic_expr();
						}
						break;
					case 3:
						{
						setState(264);
						expression();
						}
						break;
					}
					}
					}
					setState(271);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(274);
			match(T__6);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\u0004\u0001)\u0115\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b\u0002"+
		"\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002\u000f\u0007\u000f"+
		"\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0002\u0012\u0007\u0012"+
		"\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014\u0002\u0015\u0007\u0015"+
		"\u0002\u0016\u0007\u0016\u0002\u0017\u0007\u0017\u0002\u0018\u0007\u0018"+
		"\u0002\u0019\u0007\u0019\u0002\u001a\u0007\u001a\u0002\u001b\u0007\u001b"+
		"\u0002\u001c\u0007\u001c\u0002\u001d\u0007\u001d\u0002\u001e\u0007\u001e"+
		"\u0002\u001f\u0007\u001f\u0002 \u0007 \u0001\u0000\u0001\u0000\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0003\u0001M\b\u0001\u0001\u0002\u0004\u0002P\b\u0002\u000b"+
		"\u0002\f\u0002Q\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001"+
		"\u0004\u0003\u0004Y\b\u0004\u0001\u0004\u0001\u0004\u0001\u0005\u0001"+
		"\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0003\u0005b\b\u0005\u0001"+
		"\u0005\u0003\u0005e\b\u0005\u0001\u0006\u0001\u0006\u0001\u0006\u0005"+
		"\u0006j\b\u0006\n\u0006\f\u0006m\t\u0006\u0001\u0007\u0001\u0007\u0001"+
		"\u0007\u0005\u0007r\b\u0007\n\u0007\f\u0007u\t\u0007\u0001\b\u0001\b\u0001"+
		"\b\u0001\b\u0005\b{\b\b\n\b\f\b~\t\b\u0001\t\u0001\t\u0001\t\u0001\t\u0005"+
		"\t\u0084\b\t\n\t\f\t\u0087\t\t\u0001\n\u0001\n\u0001\n\u0001\n\u0005\n"+
		"\u008d\b\n\n\n\f\n\u0090\t\n\u0001\u000b\u0001\u000b\u0001\u000b\u0001"+
		"\u000b\u0003\u000b\u0096\b\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0003"+
		"\u000b\u009b\b\u000b\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001"+
		"\f\u0001\f\u0001\f\u0001\f\u0003\f\u00a7\b\f\u0001\r\u0003\r\u00aa\b\r"+
		"\u0001\r\u0001\r\u0001\u000e\u0001\u000e\u0001\u000f\u0001\u000f\u0001"+
		"\u0010\u0001\u0010\u0001\u0011\u0001\u0011\u0001\u0012\u0001\u0012\u0001"+
		"\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0014\u0001\u0014\u0001"+
		"\u0014\u0001\u0014\u0005\u0014\u00c0\b\u0014\n\u0014\f\u0014\u00c3\t\u0014"+
		"\u0001\u0014\u0003\u0014\u00c6\b\u0014\u0001\u0015\u0001\u0015\u0001\u0015"+
		"\u0001\u0015\u0001\u0016\u0001\u0016\u0001\u0016\u0001\u0017\u0001\u0017"+
		"\u0001\u0017\u0001\u0018\u0001\u0018\u0001\u0019\u0001\u0019\u0001\u0019"+
		"\u0003\u0019\u00d7\b\u0019\u0001\u001a\u0001\u001a\u0001\u001a\u0001\u001a"+
		"\u0003\u001a\u00dd\b\u001a\u0001\u001b\u0003\u001b\u00e0\b\u001b\u0001"+
		"\u001b\u0001\u001b\u0001\u001b\u0003\u001b\u00e5\b\u001b\u0001\u001b\u0001"+
		"\u001b\u0001\u001b\u0001\u001b\u0001\u001b\u0003\u001b\u00ec\b\u001b\u0001"+
		"\u001b\u0001\u001b\u0001\u001c\u0001\u001c\u0001\u001c\u0001\u001c\u0003"+
		"\u001c\u00f4\b\u001c\u0001\u001c\u0001\u001c\u0001\u001d\u0001\u001d\u0001"+
		"\u001e\u0001\u001e\u0001\u001f\u0001\u001f\u0003\u001f\u00fe\b\u001f\u0001"+
		" \u0001 \u0001 \u0001 \u0003 \u0104\b \u0001 \u0001 \u0001 \u0001 \u0003"+
		" \u010a\b \u0005 \u010c\b \n \f \u010f\t \u0003 \u0111\b \u0001 \u0001"+
		" \u0001 \u0000\u0000!\u0000\u0002\u0004\u0006\b\n\f\u000e\u0010\u0012"+
		"\u0014\u0016\u0018\u001a\u001c\u001e \"$&(*,.02468:<>@\u0000\u0004\u0002"+
		"\u0000\n\n##\u0001\u0000\f\u0011\u0001\u0000\u0012\u0013\u0002\u0000\u001c"+
		"\u001c%%\u011e\u0000B\u0001\u0000\u0000\u0000\u0002L\u0001\u0000\u0000"+
		"\u0000\u0004O\u0001\u0000\u0000\u0000\u0006S\u0001\u0000\u0000\u0000\b"+
		"X\u0001\u0000\u0000\u0000\nd\u0001\u0000\u0000\u0000\ff\u0001\u0000\u0000"+
		"\u0000\u000en\u0001\u0000\u0000\u0000\u0010v\u0001\u0000\u0000\u0000\u0012"+
		"\u007f\u0001\u0000\u0000\u0000\u0014\u0088\u0001\u0000\u0000\u0000\u0016"+
		"\u009a\u0001\u0000\u0000\u0000\u0018\u00a6\u0001\u0000\u0000\u0000\u001a"+
		"\u00a9\u0001\u0000\u0000\u0000\u001c\u00ad\u0001\u0000\u0000\u0000\u001e"+
		"\u00af\u0001\u0000\u0000\u0000 \u00b1\u0001\u0000\u0000\u0000\"\u00b3"+
		"\u0001\u0000\u0000\u0000$\u00b5\u0001\u0000\u0000\u0000&\u00b7\u0001\u0000"+
		"\u0000\u0000(\u00bb\u0001\u0000\u0000\u0000*\u00c7\u0001\u0000\u0000\u0000"+
		",\u00cb\u0001\u0000\u0000\u0000.\u00ce\u0001\u0000\u0000\u00000\u00d1"+
		"\u0001\u0000\u0000\u00002\u00d3\u0001\u0000\u0000\u00004\u00dc\u0001\u0000"+
		"\u0000\u00006\u00df\u0001\u0000\u0000\u00008\u00ef\u0001\u0000\u0000\u0000"+
		":\u00f7\u0001\u0000\u0000\u0000<\u00f9\u0001\u0000\u0000\u0000>\u00fd"+
		"\u0001\u0000\u0000\u0000@\u00ff\u0001\u0000\u0000\u0000BC\u0003\u0004"+
		"\u0002\u0000C\u0001\u0001\u0000\u0000\u0000DM\u00036\u001b\u0000EM\u0003"+
		"\u0006\u0003\u0000FM\u0003\b\u0004\u0000GM\u0003$\u0012\u0000HM\u0003"+
		"(\u0014\u0000IM\u00038\u001c\u0000JM\u00034\u001a\u0000KM\u00032\u0019"+
		"\u0000LD\u0001\u0000\u0000\u0000LE\u0001\u0000\u0000\u0000LF\u0001\u0000"+
		"\u0000\u0000LG\u0001\u0000\u0000\u0000LH\u0001\u0000\u0000\u0000LI\u0001"+
		"\u0000\u0000\u0000LJ\u0001\u0000\u0000\u0000LK\u0001\u0000\u0000\u0000"+
		"M\u0003\u0001\u0000\u0000\u0000NP\u0003\u0002\u0001\u0000ON\u0001\u0000"+
		"\u0000\u0000PQ\u0001\u0000\u0000\u0000QO\u0001\u0000\u0000\u0000QR\u0001"+
		"\u0000\u0000\u0000R\u0005\u0001\u0000\u0000\u0000ST\u0005\u0001\u0000"+
		"\u0000TU\u0003\u0004\u0002\u0000UV\u0005\u0002\u0000\u0000V\u0007\u0001"+
		"\u0000\u0000\u0000WY\u0003\n\u0005\u0000XW\u0001\u0000\u0000\u0000XY\u0001"+
		"\u0000\u0000\u0000YZ\u0001\u0000\u0000\u0000Z[\u0005\u0003\u0000\u0000"+
		"[\t\u0001\u0000\u0000\u0000\\]\u0005$\u0000\u0000]a\u0003\u001c\u000e"+
		"\u0000^b\u0003\u0018\f\u0000_b\u0003\u0016\u000b\u0000`b\u0003\f\u0006"+
		"\u0000a^\u0001\u0000\u0000\u0000a_\u0001\u0000\u0000\u0000a`\u0001\u0000"+
		"\u0000\u0000be\u0001\u0000\u0000\u0000ce\u0003\f\u0006\u0000d\\\u0001"+
		"\u0000\u0000\u0000dc\u0001\u0000\u0000\u0000e\u000b\u0001\u0000\u0000"+
		"\u0000fk\u0003\u000e\u0007\u0000gh\u0005\u0004\u0000\u0000hj\u0003\u000e"+
		"\u0007\u0000ig\u0001\u0000\u0000\u0000jm\u0001\u0000\u0000\u0000ki\u0001"+
		"\u0000\u0000\u0000kl\u0001\u0000\u0000\u0000l\r\u0001\u0000\u0000\u0000"+
		"mk\u0001\u0000\u0000\u0000ns\u0003\u0010\b\u0000op\u0005\u0005\u0000\u0000"+
		"pr\u0003\u0010\b\u0000qo\u0001\u0000\u0000\u0000ru\u0001\u0000\u0000\u0000"+
		"sq\u0001\u0000\u0000\u0000st\u0001\u0000\u0000\u0000t\u000f\u0001\u0000"+
		"\u0000\u0000us\u0001\u0000\u0000\u0000v|\u0003\u0012\t\u0000wx\u0003\u001e"+
		"\u000f\u0000xy\u0003\u0012\t\u0000y{\u0001\u0000\u0000\u0000zw\u0001\u0000"+
		"\u0000\u0000{~\u0001\u0000\u0000\u0000|z\u0001\u0000\u0000\u0000|}\u0001"+
		"\u0000\u0000\u0000}\u0011\u0001\u0000\u0000\u0000~|\u0001\u0000\u0000"+
		"\u0000\u007f\u0085\u0003\u0014\n\u0000\u0080\u0081\u0003 \u0010\u0000"+
		"\u0081\u0082\u0003\u0014\n\u0000\u0082\u0084\u0001\u0000\u0000\u0000\u0083"+
		"\u0080\u0001\u0000\u0000\u0000\u0084\u0087\u0001\u0000\u0000\u0000\u0085"+
		"\u0083\u0001\u0000\u0000\u0000\u0085\u0086\u0001\u0000\u0000\u0000\u0086"+
		"\u0013\u0001\u0000\u0000\u0000\u0087\u0085\u0001\u0000\u0000\u0000\u0088"+
		"\u008e\u0003\u0016\u000b\u0000\u0089\u008a\u0003\"\u0011\u0000\u008a\u008b"+
		"\u0003\u0016\u000b\u0000\u008b\u008d\u0001\u0000\u0000\u0000\u008c\u0089"+
		"\u0001\u0000\u0000\u0000\u008d\u0090\u0001\u0000\u0000\u0000\u008e\u008c"+
		"\u0001\u0000\u0000\u0000\u008e\u008f\u0001\u0000\u0000\u0000\u008f\u0015"+
		"\u0001\u0000\u0000\u0000\u0090\u008e\u0001\u0000\u0000\u0000\u0091\u0092"+
		"\u0003\u001a\r\u0000\u0092\u0095\u0005\u0006\u0000\u0000\u0093\u0096\u0003"+
		"<\u001e\u0000\u0094\u0096\u0003\n\u0005\u0000\u0095\u0093\u0001\u0000"+
		"\u0000\u0000\u0095\u0094\u0001\u0000\u0000\u0000\u0096\u0097\u0001\u0000"+
		"\u0000\u0000\u0097\u0098\u0005\u0007\u0000\u0000\u0098\u009b\u0001\u0000"+
		"\u0000\u0000\u0099\u009b\u0003\u0018\f\u0000\u009a\u0091\u0001\u0000\u0000"+
		"\u0000\u009a\u0099\u0001\u0000\u0000\u0000\u009b\u0017\u0001\u0000\u0000"+
		"\u0000\u009c\u00a7\u0003<\u001e\u0000\u009d\u00a7\u0003\u001a\r\u0000"+
		"\u009e\u00a7\u00030\u0018\u0000\u009f\u00a7\u0003:\u001d\u0000\u00a0\u00a1"+
		"\u0005\b\u0000\u0000\u00a1\u00a2\u0003\n\u0005\u0000\u00a2\u00a3\u0005"+
		"\t\u0000\u0000\u00a3\u00a7\u0001\u0000\u0000\u0000\u00a4\u00a7\u0003>"+
		"\u001f\u0000\u00a5\u00a7\u0003.\u0017\u0000\u00a6\u009c\u0001\u0000\u0000"+
		"\u0000\u00a6\u009d\u0001\u0000\u0000\u0000\u00a6\u009e\u0001\u0000\u0000"+
		"\u0000\u00a6\u009f\u0001\u0000\u0000\u0000\u00a6\u00a0\u0001\u0000\u0000"+
		"\u0000\u00a6\u00a4\u0001\u0000\u0000\u0000\u00a6\u00a5\u0001\u0000\u0000"+
		"\u0000\u00a7\u0019\u0001\u0000\u0000\u0000\u00a8\u00aa\u0007\u0000\u0000"+
		"\u0000\u00a9\u00a8\u0001\u0000\u0000\u0000\u00a9\u00aa\u0001\u0000\u0000"+
		"\u0000\u00aa\u00ab\u0001\u0000\u0000\u0000\u00ab\u00ac\u0005$\u0000\u0000"+
		"\u00ac\u001b\u0001\u0000\u0000\u0000\u00ad\u00ae\u0005\u000b\u0000\u0000"+
		"\u00ae\u001d\u0001\u0000\u0000\u0000\u00af\u00b0\u0007\u0001\u0000\u0000"+
		"\u00b0\u001f\u0001\u0000\u0000\u0000\u00b1\u00b2\u0007\u0002\u0000\u0000"+
		"\u00b2!\u0001\u0000\u0000\u0000\u00b3\u00b4\u0005#\u0000\u0000\u00b4#"+
		"\u0001\u0000\u0000\u0000\u00b5\u00b6\u0003&\u0013\u0000\u00b6%\u0001\u0000"+
		"\u0000\u0000\u00b7\u00b8\u0005\u0014\u0000\u0000\u00b8\u00b9\u0003\n\u0005"+
		"\u0000\u00b9\u00ba\u0003\u0006\u0003\u0000\u00ba\'\u0001\u0000\u0000\u0000"+
		"\u00bb\u00bc\u0005\u0015\u0000\u0000\u00bc\u00bd\u0003\n\u0005\u0000\u00bd"+
		"\u00c1\u0003\u0006\u0003\u0000\u00be\u00c0\u0003*\u0015\u0000\u00bf\u00be"+
		"\u0001\u0000\u0000\u0000\u00c0\u00c3\u0001\u0000\u0000\u0000\u00c1\u00bf"+
		"\u0001\u0000\u0000\u0000\u00c1\u00c2\u0001\u0000\u0000\u0000\u00c2\u00c5"+
		"\u0001\u0000\u0000\u0000\u00c3\u00c1\u0001\u0000\u0000\u0000\u00c4\u00c6"+
		"\u0003,\u0016\u0000\u00c5\u00c4\u0001\u0000\u0000\u0000\u00c5\u00c6\u0001"+
		"\u0000\u0000\u0000\u00c6)\u0001\u0000\u0000\u0000\u00c7\u00c8\u0005\u0016"+
		"\u0000\u0000\u00c8\u00c9\u0003\n\u0005\u0000\u00c9\u00ca\u0003\u0006\u0003"+
		"\u0000\u00ca+\u0001\u0000\u0000\u0000\u00cb\u00cc\u0005\u0017\u0000\u0000"+
		"\u00cc\u00cd\u0003\u0006\u0003\u0000\u00cd-\u0001\u0000\u0000\u0000\u00ce"+
		"\u00cf\u0005\u001d\u0000\u0000\u00cf\u00d0\u0003\u0006\u0003\u0000\u00d0"+
		"/\u0001\u0000\u0000\u0000\u00d1\u00d2\u0005\u001f\u0000\u0000\u00d21\u0001"+
		"\u0000\u0000\u0000\u00d3\u00d6\u0005\u001e\u0000\u0000\u00d4\u00d7\u0003"+
		"\u001a\r\u0000\u00d5\u00d7\u0003\n\u0005\u0000\u00d6\u00d4\u0001\u0000"+
		"\u0000\u0000\u00d6\u00d5\u0001\u0000\u0000\u0000\u00d73\u0001\u0000\u0000"+
		"\u0000\u00d8\u00d9\u0005 \u0000\u0000\u00d9\u00dd\u0005$\u0000\u0000\u00da"+
		"\u00db\u0005!\u0000\u0000\u00db\u00dd\u0005$\u0000\u0000\u00dc\u00d8\u0001"+
		"\u0000\u0000\u0000\u00dc\u00da\u0001\u0000\u0000\u0000\u00dd5\u0001\u0000"+
		"\u0000\u0000\u00de\u00e0\u0005\"\u0000\u0000\u00df\u00de\u0001\u0000\u0000"+
		"\u0000\u00df\u00e0\u0001\u0000\u0000\u0000\u00e0\u00e1\u0001\u0000\u0000"+
		"\u0000\u00e1\u00e2\u0005\u0018\u0000\u0000\u00e2\u00e4\u0001\u0000\u0000"+
		"\u0000\u00e3\u00e5\u0005#\u0000\u0000\u00e4\u00e3\u0001\u0000\u0000\u0000"+
		"\u00e4\u00e5\u0001\u0000\u0000\u0000\u00e5\u00e6\u0001\u0000\u0000\u0000"+
		"\u00e6\u00e7\u0005$\u0000\u0000\u00e7\u00eb\u0005\u000b\u0000\u0000\u00e8"+
		"\u00ec\u0003\u0018\f\u0000\u00e9\u00ec\u0003\u0016\u000b\u0000\u00ea\u00ec"+
		"\u0003\n\u0005\u0000\u00eb\u00e8\u0001\u0000\u0000\u0000\u00eb\u00e9\u0001"+
		"\u0000\u0000\u0000\u00eb\u00ea\u0001\u0000\u0000\u0000\u00ec\u00ed\u0001"+
		"\u0000\u0000\u0000\u00ed\u00ee\u0005\u0003\u0000\u0000\u00ee7\u0001\u0000"+
		"\u0000\u0000\u00ef\u00f3\u0005\u0019\u0000\u0000\u00f0\u00f4\u0003\u0018"+
		"\f\u0000\u00f1\u00f4\u0003\u0016\u000b\u0000\u00f2\u00f4\u0003\n\u0005"+
		"\u0000\u00f3\u00f0\u0001\u0000\u0000\u0000\u00f3\u00f1\u0001\u0000\u0000"+
		"\u0000\u00f3\u00f2\u0001\u0000\u0000\u0000\u00f4\u00f5\u0001\u0000\u0000"+
		"\u0000\u00f5\u00f6\u0005\u0003\u0000\u0000\u00f69\u0001\u0000\u0000\u0000"+
		"\u00f7\u00f8\u0005\u001a\u0000\u0000\u00f8;\u0001\u0000\u0000\u0000\u00f9"+
		"\u00fa\u0007\u0003\u0000\u0000\u00fa=\u0001\u0000\u0000\u0000\u00fb\u00fe"+
		"\u0003@ \u0000\u00fc\u00fe\u0005&\u0000\u0000\u00fd\u00fb\u0001\u0000"+
		"\u0000\u0000\u00fd\u00fc\u0001\u0000\u0000\u0000\u00fe?\u0001\u0000\u0000"+
		"\u0000\u00ff\u0110\u0005\u0006\u0000\u0000\u0100\u0104\u0003<\u001e\u0000"+
		"\u0101\u0104\u0003\u0018\f\u0000\u0102\u0104\u0003\n\u0005\u0000\u0103"+
		"\u0100\u0001\u0000\u0000\u0000\u0103\u0101\u0001\u0000\u0000\u0000\u0103"+
		"\u0102\u0001\u0000\u0000\u0000\u0104\u010d\u0001\u0000\u0000\u0000\u0105"+
		"\u0109\u0005\u001b\u0000\u0000\u0106\u010a\u0003<\u001e\u0000\u0107\u010a"+
		"\u0003\u0018\f\u0000\u0108\u010a\u0003\n\u0005\u0000\u0109\u0106\u0001"+
		"\u0000\u0000\u0000\u0109\u0107\u0001\u0000\u0000\u0000\u0109\u0108\u0001"+
		"\u0000\u0000\u0000\u010a\u010c\u0001\u0000\u0000\u0000\u010b\u0105\u0001"+
		"\u0000\u0000\u0000\u010c\u010f\u0001\u0000\u0000\u0000\u010d\u010b\u0001"+
		"\u0000\u0000\u0000\u010d\u010e\u0001\u0000\u0000\u0000\u010e\u0111\u0001"+
		"\u0000\u0000\u0000\u010f\u010d\u0001\u0000\u0000\u0000\u0110\u0103\u0001"+
		"\u0000\u0000\u0000\u0110\u0111\u0001\u0000\u0000\u0000\u0111\u0112\u0001"+
		"\u0000\u0000\u0000\u0112\u0113\u0005\u0007\u0000\u0000\u0113A\u0001\u0000"+
		"\u0000\u0000\u001bLQXadks|\u0085\u008e\u0095\u009a\u00a6\u00a9\u00c1\u00c5"+
		"\u00d6\u00dc\u00df\u00e4\u00eb\u00f3\u00fd\u0103\u0109\u010d\u0110";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}