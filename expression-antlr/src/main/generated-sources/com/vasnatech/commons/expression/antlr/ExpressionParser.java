// Generated from com/vasnatech/commons/expression/antlr/ExpressionParser.g4 by ANTLR 4.12.0
package com.vasnatech.commons.expression.antlr;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue"})
public class ExpressionParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.12.0", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		DECIMAL_LITERAL=1, FLOAT_LITERAL=2, BOOLEAN_LITERAL=3, STRING_LITERAL=4, 
		LPAREN=5, RPAREN=6, LBRACK=7, RBRACK=8, COMMA=9, DOT=10, QUOTE=11, AND=12, 
		OR=13, BANG=14, ASSIGN=15, EQ=16, NOTEQUAL=17, GT=18, LT=19, LE=20, GE=21, 
		ADD=22, SUB=23, MUL=24, DIV=25, MOD=26, WS=27, IDENTIFIER=28;
	public static final int
		RULE_expression = 0, RULE_constant = 1, RULE_chainable = 2, RULE_arguments = 3;
	private static String[] makeRuleNames() {
		return new String[] {
			"expression", "constant", "chainable", "arguments"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, null, null, null, null, "'('", "')'", "'['", "']'", "','", "'.'", 
			"'''", "'&&'", "'||'", "'!'", "'='", "'=='", "'!='", "'>'", "'<'", "'<='", 
			"'>='", "'+'", "'-'", "'*'", "'/'", "'%'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "DECIMAL_LITERAL", "FLOAT_LITERAL", "BOOLEAN_LITERAL", "STRING_LITERAL", 
			"LPAREN", "RPAREN", "LBRACK", "RBRACK", "COMMA", "DOT", "QUOTE", "AND", 
			"OR", "BANG", "ASSIGN", "EQ", "NOTEQUAL", "GT", "LT", "LE", "GE", "ADD", 
			"SUB", "MUL", "DIV", "MOD", "WS", "IDENTIFIER"
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
	public String getGrammarFileName() { return "ExpressionParser.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public ExpressionParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ExpressionContext extends ParserRuleContext {
		public ConstantContext constant() {
			return getRuleContext(ConstantContext.class,0);
		}
		public ChainableContext chainable() {
			return getRuleContext(ChainableContext.class,0);
		}
		public TerminalNode SUB() { return getToken(ExpressionParser.SUB, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode BANG() { return getToken(ExpressionParser.BANG, 0); }
		public TerminalNode LPAREN() { return getToken(ExpressionParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(ExpressionParser.RPAREN, 0); }
		public TerminalNode MUL() { return getToken(ExpressionParser.MUL, 0); }
		public TerminalNode DIV() { return getToken(ExpressionParser.DIV, 0); }
		public TerminalNode MOD() { return getToken(ExpressionParser.MOD, 0); }
		public TerminalNode ADD() { return getToken(ExpressionParser.ADD, 0); }
		public TerminalNode EQ() { return getToken(ExpressionParser.EQ, 0); }
		public TerminalNode NOTEQUAL() { return getToken(ExpressionParser.NOTEQUAL, 0); }
		public TerminalNode GT() { return getToken(ExpressionParser.GT, 0); }
		public TerminalNode GE() { return getToken(ExpressionParser.GE, 0); }
		public TerminalNode LT() { return getToken(ExpressionParser.LT, 0); }
		public TerminalNode LE() { return getToken(ExpressionParser.LE, 0); }
		public TerminalNode AND() { return getToken(ExpressionParser.AND, 0); }
		public TerminalNode OR() { return getToken(ExpressionParser.OR, 0); }
		public TerminalNode LBRACK() { return getToken(ExpressionParser.LBRACK, 0); }
		public TerminalNode RBRACK() { return getToken(ExpressionParser.RBRACK, 0); }
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionParserListener ) ((ExpressionParserListener)listener).enterExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionParserListener ) ((ExpressionParserListener)listener).exitExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ExpressionParserVisitor ) return ((ExpressionParserVisitor<? extends T>)visitor).visitExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpressionContext expression() throws RecognitionException {
		return expression(0);
	}

	private ExpressionContext expression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExpressionContext _localctx = new ExpressionContext(_ctx, _parentState);
		ExpressionContext _prevctx = _localctx;
		int _startState = 0;
		enterRecursionRule(_localctx, 0, RULE_expression, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(19);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case DECIMAL_LITERAL:
			case FLOAT_LITERAL:
			case BOOLEAN_LITERAL:
			case STRING_LITERAL:
				{
				setState(9);
				constant();
				}
				break;
			case IDENTIFIER:
				{
				setState(10);
				chainable(0);
				}
				break;
			case SUB:
				{
				setState(11);
				match(SUB);
				setState(12);
				expression(5);
				}
				break;
			case BANG:
				{
				setState(13);
				match(BANG);
				setState(14);
				expression(3);
				}
				break;
			case LPAREN:
				{
				setState(15);
				match(LPAREN);
				setState(16);
				expression(0);
				setState(17);
				match(RPAREN);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(40);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,2,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(38);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
					case 1:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(21);
						if (!(precpred(_ctx, 7))) throw new FailedPredicateException(this, "precpred(_ctx, 7)");
						setState(22);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 117440512L) != 0)) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(23);
						expression(8);
						}
						break;
					case 2:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(24);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(25);
						_la = _input.LA(1);
						if ( !(_la==ADD || _la==SUB) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(26);
						expression(7);
						}
						break;
					case 3:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(27);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(28);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 4128768L) != 0)) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(29);
						expression(5);
						}
						break;
					case 4:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(30);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(31);
						_la = _input.LA(1);
						if ( !(_la==AND || _la==OR) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(32);
						expression(3);
						}
						break;
					case 5:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(33);
						if (!(precpred(_ctx, 8))) throw new FailedPredicateException(this, "precpred(_ctx, 8)");
						setState(34);
						match(LBRACK);
						setState(35);
						expression(0);
						setState(36);
						match(RBRACK);
						}
						break;
					}
					} 
				}
				setState(42);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,2,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ConstantContext extends ParserRuleContext {
		public TerminalNode STRING_LITERAL() { return getToken(ExpressionParser.STRING_LITERAL, 0); }
		public TerminalNode BOOLEAN_LITERAL() { return getToken(ExpressionParser.BOOLEAN_LITERAL, 0); }
		public TerminalNode DECIMAL_LITERAL() { return getToken(ExpressionParser.DECIMAL_LITERAL, 0); }
		public TerminalNode FLOAT_LITERAL() { return getToken(ExpressionParser.FLOAT_LITERAL, 0); }
		public ConstantContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_constant; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionParserListener ) ((ExpressionParserListener)listener).enterConstant(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionParserListener ) ((ExpressionParserListener)listener).exitConstant(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ExpressionParserVisitor ) return ((ExpressionParserVisitor<? extends T>)visitor).visitConstant(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConstantContext constant() throws RecognitionException {
		ConstantContext _localctx = new ConstantContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_constant);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(43);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 30L) != 0)) ) {
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
	public static class ChainableContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(ExpressionParser.IDENTIFIER, 0); }
		public TerminalNode ASSIGN() { return getToken(ExpressionParser.ASSIGN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ArgumentsContext arguments() {
			return getRuleContext(ArgumentsContext.class,0);
		}
		public ChainableContext chainable() {
			return getRuleContext(ChainableContext.class,0);
		}
		public TerminalNode DOT() { return getToken(ExpressionParser.DOT, 0); }
		public ChainableContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_chainable; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionParserListener ) ((ExpressionParserListener)listener).enterChainable(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionParserListener ) ((ExpressionParserListener)listener).exitChainable(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ExpressionParserVisitor ) return ((ExpressionParserVisitor<? extends T>)visitor).visitChainable(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ChainableContext chainable() throws RecognitionException {
		return chainable(0);
	}

	private ChainableContext chainable(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ChainableContext _localctx = new ChainableContext(_ctx, _parentState);
		ChainableContext _prevctx = _localctx;
		int _startState = 4;
		enterRecursionRule(_localctx, 4, RULE_chainable, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(52);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,3,_ctx) ) {
			case 1:
				{
				setState(46);
				match(IDENTIFIER);
				}
				break;
			case 2:
				{
				setState(47);
				match(IDENTIFIER);
				setState(48);
				match(ASSIGN);
				setState(49);
				expression(0);
				}
				break;
			case 3:
				{
				setState(50);
				match(IDENTIFIER);
				setState(51);
				arguments();
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(68);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,5,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(66);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,4,_ctx) ) {
					case 1:
						{
						_localctx = new ChainableContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_chainable);
						setState(54);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(55);
						match(DOT);
						setState(56);
						match(IDENTIFIER);
						}
						break;
					case 2:
						{
						_localctx = new ChainableContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_chainable);
						setState(57);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(58);
						match(DOT);
						setState(59);
						match(IDENTIFIER);
						setState(60);
						match(ASSIGN);
						setState(61);
						expression(0);
						}
						break;
					case 3:
						{
						_localctx = new ChainableContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_chainable);
						setState(62);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(63);
						match(DOT);
						setState(64);
						match(IDENTIFIER);
						setState(65);
						arguments();
						}
						break;
					}
					} 
				}
				setState(70);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,5,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ArgumentsContext extends ParserRuleContext {
		public TerminalNode LPAREN() { return getToken(ExpressionParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(ExpressionParser.RPAREN, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(ExpressionParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(ExpressionParser.COMMA, i);
		}
		public ArgumentsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arguments; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionParserListener ) ((ExpressionParserListener)listener).enterArguments(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionParserListener ) ((ExpressionParserListener)listener).exitArguments(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ExpressionParserVisitor ) return ((ExpressionParserVisitor<? extends T>)visitor).visitArguments(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArgumentsContext arguments() throws RecognitionException {
		ArgumentsContext _localctx = new ArgumentsContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_arguments);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(71);
			match(LPAREN);
			setState(80);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 276840510L) != 0)) {
				{
				setState(72);
				expression(0);
				setState(77);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(73);
					match(COMMA);
					setState(74);
					expression(0);
					}
					}
					setState(79);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(82);
			match(RPAREN);
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

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 0:
			return expression_sempred((ExpressionContext)_localctx, predIndex);
		case 2:
			return chainable_sempred((ChainableContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean expression_sempred(ExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 7);
		case 1:
			return precpred(_ctx, 6);
		case 2:
			return precpred(_ctx, 4);
		case 3:
			return precpred(_ctx, 2);
		case 4:
			return precpred(_ctx, 8);
		}
		return true;
	}
	private boolean chainable_sempred(ChainableContext _localctx, int predIndex) {
		switch (predIndex) {
		case 5:
			return precpred(_ctx, 5);
		case 6:
			return precpred(_ctx, 4);
		case 7:
			return precpred(_ctx, 2);
		}
		return true;
	}

	public static final String _serializedATN =
		"\u0004\u0001\u001cU\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0001\u0000\u0001\u0000\u0001"+
		"\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001"+
		"\u0000\u0001\u0000\u0001\u0000\u0003\u0000\u0014\b\u0000\u0001\u0000\u0001"+
		"\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001"+
		"\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001"+
		"\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0005\u0000\'\b\u0000\n\u0000"+
		"\f\u0000*\t\u0000\u0001\u0001\u0001\u0001\u0001\u0002\u0001\u0002\u0001"+
		"\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0003\u00025\b"+
		"\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001"+
		"\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001"+
		"\u0002\u0005\u0002C\b\u0002\n\u0002\f\u0002F\t\u0002\u0001\u0003\u0001"+
		"\u0003\u0001\u0003\u0001\u0003\u0005\u0003L\b\u0003\n\u0003\f\u0003O\t"+
		"\u0003\u0003\u0003Q\b\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0000"+
		"\u0002\u0000\u0004\u0004\u0000\u0002\u0004\u0006\u0000\u0005\u0001\u0000"+
		"\u0018\u001a\u0001\u0000\u0016\u0017\u0001\u0000\u0010\u0015\u0001\u0000"+
		"\f\r\u0001\u0000\u0001\u0004`\u0000\u0013\u0001\u0000\u0000\u0000\u0002"+
		"+\u0001\u0000\u0000\u0000\u00044\u0001\u0000\u0000\u0000\u0006G\u0001"+
		"\u0000\u0000\u0000\b\t\u0006\u0000\uffff\uffff\u0000\t\u0014\u0003\u0002"+
		"\u0001\u0000\n\u0014\u0003\u0004\u0002\u0000\u000b\f\u0005\u0017\u0000"+
		"\u0000\f\u0014\u0003\u0000\u0000\u0005\r\u000e\u0005\u000e\u0000\u0000"+
		"\u000e\u0014\u0003\u0000\u0000\u0003\u000f\u0010\u0005\u0005\u0000\u0000"+
		"\u0010\u0011\u0003\u0000\u0000\u0000\u0011\u0012\u0005\u0006\u0000\u0000"+
		"\u0012\u0014\u0001\u0000\u0000\u0000\u0013\b\u0001\u0000\u0000\u0000\u0013"+
		"\n\u0001\u0000\u0000\u0000\u0013\u000b\u0001\u0000\u0000\u0000\u0013\r"+
		"\u0001\u0000\u0000\u0000\u0013\u000f\u0001\u0000\u0000\u0000\u0014(\u0001"+
		"\u0000\u0000\u0000\u0015\u0016\n\u0007\u0000\u0000\u0016\u0017\u0007\u0000"+
		"\u0000\u0000\u0017\'\u0003\u0000\u0000\b\u0018\u0019\n\u0006\u0000\u0000"+
		"\u0019\u001a\u0007\u0001\u0000\u0000\u001a\'\u0003\u0000\u0000\u0007\u001b"+
		"\u001c\n\u0004\u0000\u0000\u001c\u001d\u0007\u0002\u0000\u0000\u001d\'"+
		"\u0003\u0000\u0000\u0005\u001e\u001f\n\u0002\u0000\u0000\u001f \u0007"+
		"\u0003\u0000\u0000 \'\u0003\u0000\u0000\u0003!\"\n\b\u0000\u0000\"#\u0005"+
		"\u0007\u0000\u0000#$\u0003\u0000\u0000\u0000$%\u0005\b\u0000\u0000%\'"+
		"\u0001\u0000\u0000\u0000&\u0015\u0001\u0000\u0000\u0000&\u0018\u0001\u0000"+
		"\u0000\u0000&\u001b\u0001\u0000\u0000\u0000&\u001e\u0001\u0000\u0000\u0000"+
		"&!\u0001\u0000\u0000\u0000\'*\u0001\u0000\u0000\u0000(&\u0001\u0000\u0000"+
		"\u0000()\u0001\u0000\u0000\u0000)\u0001\u0001\u0000\u0000\u0000*(\u0001"+
		"\u0000\u0000\u0000+,\u0007\u0004\u0000\u0000,\u0003\u0001\u0000\u0000"+
		"\u0000-.\u0006\u0002\uffff\uffff\u0000.5\u0005\u001c\u0000\u0000/0\u0005"+
		"\u001c\u0000\u000001\u0005\u000f\u0000\u000015\u0003\u0000\u0000\u0000"+
		"23\u0005\u001c\u0000\u000035\u0003\u0006\u0003\u00004-\u0001\u0000\u0000"+
		"\u00004/\u0001\u0000\u0000\u000042\u0001\u0000\u0000\u00005D\u0001\u0000"+
		"\u0000\u000067\n\u0005\u0000\u000078\u0005\n\u0000\u00008C\u0005\u001c"+
		"\u0000\u00009:\n\u0004\u0000\u0000:;\u0005\n\u0000\u0000;<\u0005\u001c"+
		"\u0000\u0000<=\u0005\u000f\u0000\u0000=C\u0003\u0000\u0000\u0000>?\n\u0002"+
		"\u0000\u0000?@\u0005\n\u0000\u0000@A\u0005\u001c\u0000\u0000AC\u0003\u0006"+
		"\u0003\u0000B6\u0001\u0000\u0000\u0000B9\u0001\u0000\u0000\u0000B>\u0001"+
		"\u0000\u0000\u0000CF\u0001\u0000\u0000\u0000DB\u0001\u0000\u0000\u0000"+
		"DE\u0001\u0000\u0000\u0000E\u0005\u0001\u0000\u0000\u0000FD\u0001\u0000"+
		"\u0000\u0000GP\u0005\u0005\u0000\u0000HM\u0003\u0000\u0000\u0000IJ\u0005"+
		"\t\u0000\u0000JL\u0003\u0000\u0000\u0000KI\u0001\u0000\u0000\u0000LO\u0001"+
		"\u0000\u0000\u0000MK\u0001\u0000\u0000\u0000MN\u0001\u0000\u0000\u0000"+
		"NQ\u0001\u0000\u0000\u0000OM\u0001\u0000\u0000\u0000PH\u0001\u0000\u0000"+
		"\u0000PQ\u0001\u0000\u0000\u0000QR\u0001\u0000\u0000\u0000RS\u0005\u0006"+
		"\u0000\u0000S\u0007\u0001\u0000\u0000\u0000\b\u0013&(4BDMP";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}