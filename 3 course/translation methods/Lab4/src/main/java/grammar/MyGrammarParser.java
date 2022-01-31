// Generated from C:/Users/Vlad/Desktop/Translation-Methods-main/Lab4/src/main/java/grammar\MyGrammar.g4 by ANTLR 4.9.2
package grammar;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class MyGrammarParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.9.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		GRAMMAR=1, FIELDS=2, NAME=3, REGEXP=4, OR=5, COLON=6, SEMI=7, RETURNS=8, 
		THEN=9, OPEN_BRACE=10, CLOSE_BRACE=11, OPEN_BRACKET=12, CLOSE_BRACKET=13, 
		OPEN_PAREN=14, CLOSE_PAREN=15, CODE=16, ATTR=17, HERIT_ATTR=18, EPS=19, 
		WS=20;
	public static final int
		RULE_run = 0, RULE_grammarName = 1, RULE_fields = 2, RULE_grammarRule = 3, 
		RULE_terminal = 4, RULE_nonTerminal = 5, RULE_rulE = 6, RULE_triple = 7, 
		RULE_name = 8, RULE_primitive = 9, RULE_code = 10, RULE_attr = 11, RULE_inheritedAttr = 12;
	private static String[] makeRuleNames() {
		return new String[] {
			"run", "grammarName", "fields", "grammarRule", "terminal", "nonTerminal", 
			"rulE", "triple", "name", "primitive", "code", "attr", "inheritedAttr"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'grammar'", "'fields'", null, null, "'|'", "':'", "';'", "'=>'", 
			"'->'", "'{'", "'}'", "'['", "']'", "'('", "')'", null, null, null, "'\u03B5'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "GRAMMAR", "FIELDS", "NAME", "REGEXP", "OR", "COLON", "SEMI", "RETURNS", 
			"THEN", "OPEN_BRACE", "CLOSE_BRACE", "OPEN_BRACKET", "CLOSE_BRACKET", 
			"OPEN_PAREN", "CLOSE_PAREN", "CODE", "ATTR", "HERIT_ATTR", "EPS", "WS"
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
	public String getGrammarFileName() { return "MyGrammar.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public MyGrammarParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class RunContext extends ParserRuleContext {
		public MyGrammar grammar;
		public GrammarNameContext grammarName() {
			return getRuleContext(GrammarNameContext.class,0);
		}
		public TerminalNode EOF() { return getToken(MyGrammarParser.EOF, 0); }
		public FieldsContext fields() {
			return getRuleContext(FieldsContext.class,0);
		}
		public List<GrammarRuleContext> grammarRule() {
			return getRuleContexts(GrammarRuleContext.class);
		}
		public GrammarRuleContext grammarRule(int i) {
			return getRuleContext(GrammarRuleContext.class,i);
		}
		public RunContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_run; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MyGrammarListener ) ((MyGrammarListener)listener).enterRun(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MyGrammarListener ) ((MyGrammarListener)listener).exitRun(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MyGrammarVisitor ) return ((MyGrammarVisitor<? extends T>)visitor).visitRun(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RunContext run() throws RecognitionException {
		RunContext _localctx = new RunContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_run);
		((RunContext)_localctx).grammar =  new MyGrammar();
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(26);
			grammarName(_localctx.grammar);
			setState(28);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==FIELDS) {
				{
				setState(27);
				fields(_localctx.grammar);
				}
			}

			setState(33);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==NAME) {
				{
				{
				setState(30);
				grammarRule(_localctx.grammar);
				}
				}
				setState(35);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(36);
			match(EOF);
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

	public static class GrammarNameContext extends ParserRuleContext {
		public MyGrammar grammar;
		public NameContext name;
		public TerminalNode GRAMMAR() { return getToken(MyGrammarParser.GRAMMAR, 0); }
		public NameContext name() {
			return getRuleContext(NameContext.class,0);
		}
		public TerminalNode SEMI() { return getToken(MyGrammarParser.SEMI, 0); }
		public GrammarNameContext(ParserRuleContext parent, int invokingState) { super(parent, invokingState); }
		public GrammarNameContext(ParserRuleContext parent, int invokingState, MyGrammar grammar) {
			super(parent, invokingState);
			this.grammar = grammar;
		}
		@Override public int getRuleIndex() { return RULE_grammarName; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MyGrammarListener ) ((MyGrammarListener)listener).enterGrammarName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MyGrammarListener ) ((MyGrammarListener)listener).exitGrammarName(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MyGrammarVisitor ) return ((MyGrammarVisitor<? extends T>)visitor).visitGrammarName(this);
			else return visitor.visitChildren(this);
		}
	}

	public final GrammarNameContext grammarName(MyGrammar grammar) throws RecognitionException {
		GrammarNameContext _localctx = new GrammarNameContext(_ctx, getState(), grammar);
		enterRule(_localctx, 2, RULE_grammarName);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(38);
			match(GRAMMAR);
			setState(39);
			((GrammarNameContext)_localctx).name = name();
			setState(40);
			match(SEMI);
			 _localctx.grammar.setName((((GrammarNameContext)_localctx).name!=null?_input.getText(((GrammarNameContext)_localctx).name.start,((GrammarNameContext)_localctx).name.stop):null)); 
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

	public static class FieldsContext extends ParserRuleContext {
		public MyGrammar grammar;
		public AttrContext attr;
		public TerminalNode FIELDS() { return getToken(MyGrammarParser.FIELDS, 0); }
		public AttrContext attr() {
			return getRuleContext(AttrContext.class,0);
		}
		public TerminalNode SEMI() { return getToken(MyGrammarParser.SEMI, 0); }
		public FieldsContext(ParserRuleContext parent, int invokingState) { super(parent, invokingState); }
		public FieldsContext(ParserRuleContext parent, int invokingState, MyGrammar grammar) {
			super(parent, invokingState);
			this.grammar = grammar;
		}
		@Override public int getRuleIndex() { return RULE_fields; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MyGrammarListener ) ((MyGrammarListener)listener).enterFields(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MyGrammarListener ) ((MyGrammarListener)listener).exitFields(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MyGrammarVisitor ) return ((MyGrammarVisitor<? extends T>)visitor).visitFields(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FieldsContext fields(MyGrammar grammar) throws RecognitionException {
		FieldsContext _localctx = new FieldsContext(_ctx, getState(), grammar);
		enterRule(_localctx, 4, RULE_fields);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(43);
			match(FIELDS);
			setState(44);
			((FieldsContext)_localctx).attr = attr();
			setState(45);
			match(SEMI);
			 _localctx.grammar.setFields((((FieldsContext)_localctx).attr!=null?_input.getText(((FieldsContext)_localctx).attr.start,((FieldsContext)_localctx).attr.stop):null)); 
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

	public static class GrammarRuleContext extends ParserRuleContext {
		public MyGrammar grammar;
		public TerminalContext terminal() {
			return getRuleContext(TerminalContext.class,0);
		}
		public NonTerminalContext nonTerminal() {
			return getRuleContext(NonTerminalContext.class,0);
		}
		public GrammarRuleContext(ParserRuleContext parent, int invokingState) { super(parent, invokingState); }
		public GrammarRuleContext(ParserRuleContext parent, int invokingState, MyGrammar grammar) {
			super(parent, invokingState);
			this.grammar = grammar;
		}
		@Override public int getRuleIndex() { return RULE_grammarRule; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MyGrammarListener ) ((MyGrammarListener)listener).enterGrammarRule(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MyGrammarListener ) ((MyGrammarListener)listener).exitGrammarRule(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MyGrammarVisitor ) return ((MyGrammarVisitor<? extends T>)visitor).visitGrammarRule(this);
			else return visitor.visitChildren(this);
		}
	}

	public final GrammarRuleContext grammarRule(MyGrammar grammar) throws RecognitionException {
		GrammarRuleContext _localctx = new GrammarRuleContext(_ctx, getState(), grammar);
		enterRule(_localctx, 6, RULE_grammarRule);
		try {
			setState(50);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,2,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(48);
				terminal(_localctx.grammar);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(49);
				nonTerminal(_localctx.grammar);
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

	public static class TerminalContext extends ParserRuleContext {
		public MyGrammar grammar;
		public NameContext name;
		public PrimitiveContext primitive;
		public NameContext name() {
			return getRuleContext(NameContext.class,0);
		}
		public TerminalNode COLON() { return getToken(MyGrammarParser.COLON, 0); }
		public PrimitiveContext primitive() {
			return getRuleContext(PrimitiveContext.class,0);
		}
		public TerminalNode SEMI() { return getToken(MyGrammarParser.SEMI, 0); }
		public TerminalContext(ParserRuleContext parent, int invokingState) { super(parent, invokingState); }
		public TerminalContext(ParserRuleContext parent, int invokingState, MyGrammar grammar) {
			super(parent, invokingState);
			this.grammar = grammar;
		}
		@Override public int getRuleIndex() { return RULE_terminal; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MyGrammarListener ) ((MyGrammarListener)listener).enterTerminal(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MyGrammarListener ) ((MyGrammarListener)listener).exitTerminal(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MyGrammarVisitor ) return ((MyGrammarVisitor<? extends T>)visitor).visitTerminal(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TerminalContext terminal(MyGrammar grammar) throws RecognitionException {
		TerminalContext _localctx = new TerminalContext(_ctx, getState(), grammar);
		enterRule(_localctx, 8, RULE_terminal);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(52);
			((TerminalContext)_localctx).name = name();
			setState(53);
			match(COLON);
			setState(54);
			((TerminalContext)_localctx).primitive = primitive();
			setState(55);
			match(SEMI);
			 _localctx.grammar.addTerminal((((TerminalContext)_localctx).name!=null?_input.getText(((TerminalContext)_localctx).name.start,((TerminalContext)_localctx).name.stop):null), (((TerminalContext)_localctx).primitive!=null?_input.getText(((TerminalContext)_localctx).primitive.start,((TerminalContext)_localctx).primitive.stop):null)); 
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

	public static class NonTerminalContext extends ParserRuleContext {
		public MyGrammar grammar;
		public NameContext name;
		public InheritedAttrContext inheritedAttr;
		public NameContext name() {
			return getRuleContext(NameContext.class,0);
		}
		public InheritedAttrContext inheritedAttr() {
			return getRuleContext(InheritedAttrContext.class,0);
		}
		public TerminalNode RETURNS() { return getToken(MyGrammarParser.RETURNS, 0); }
		public List<RulEContext> rulE() {
			return getRuleContexts(RulEContext.class);
		}
		public RulEContext rulE(int i) {
			return getRuleContext(RulEContext.class,i);
		}
		public TerminalNode SEMI() { return getToken(MyGrammarParser.SEMI, 0); }
		public List<TerminalNode> OR() { return getTokens(MyGrammarParser.OR); }
		public TerminalNode OR(int i) {
			return getToken(MyGrammarParser.OR, i);
		}
		public NonTerminalContext(ParserRuleContext parent, int invokingState) { super(parent, invokingState); }
		public NonTerminalContext(ParserRuleContext parent, int invokingState, MyGrammar grammar) {
			super(parent, invokingState);
			this.grammar = grammar;
		}
		@Override public int getRuleIndex() { return RULE_nonTerminal; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MyGrammarListener ) ((MyGrammarListener)listener).enterNonTerminal(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MyGrammarListener ) ((MyGrammarListener)listener).exitNonTerminal(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MyGrammarVisitor ) return ((MyGrammarVisitor<? extends T>)visitor).visitNonTerminal(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NonTerminalContext nonTerminal(MyGrammar grammar) throws RecognitionException {
		NonTerminalContext _localctx = new NonTerminalContext(_ctx, getState(), grammar);
		enterRule(_localctx, 10, RULE_nonTerminal);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(58);
			((NonTerminalContext)_localctx).name = name();
			setState(59);
			((NonTerminalContext)_localctx).inheritedAttr = inheritedAttr();
			setState(60);
			match(RETURNS);
			setState(61);
			rulE(_localctx.grammar, new MyGrammar.NonTerm((((NonTerminalContext)_localctx).name!=null?_input.getText(((NonTerminalContext)_localctx).name.start,((NonTerminalContext)_localctx).name.stop):null), (((NonTerminalContext)_localctx).inheritedAttr!=null?_input.getText(((NonTerminalContext)_localctx).inheritedAttr.start,((NonTerminalContext)_localctx).inheritedAttr.stop):null)));
			setState(66);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==OR) {
				{
				{
				setState(62);
				match(OR);
				setState(63);
				rulE(_localctx.grammar, new MyGrammar.NonTerm((((NonTerminalContext)_localctx).name!=null?_input.getText(((NonTerminalContext)_localctx).name.start,((NonTerminalContext)_localctx).name.stop):null), (((NonTerminalContext)_localctx).inheritedAttr!=null?_input.getText(((NonTerminalContext)_localctx).inheritedAttr.start,((NonTerminalContext)_localctx).inheritedAttr.stop):null)));
				}
				}
				setState(68);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(69);
			match(SEMI);
			 _localctx.grammar.setStart((((NonTerminalContext)_localctx).name!=null?_input.getText(((NonTerminalContext)_localctx).name.start,((NonTerminalContext)_localctx).name.stop):null)); 
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

	public static class RulEContext extends ParserRuleContext {
		public MyGrammar grammar;
		public MyGrammar.NonTerm nonTerm;
		public CodeContext code;
		public TripleContext triple() {
			return getRuleContext(TripleContext.class,0);
		}
		public TerminalNode EPS() { return getToken(MyGrammarParser.EPS, 0); }
		public CodeContext code() {
			return getRuleContext(CodeContext.class,0);
		}
		public RulEContext(ParserRuleContext parent, int invokingState) { super(parent, invokingState); }
		public RulEContext(ParserRuleContext parent, int invokingState, MyGrammar grammar, MyGrammar.NonTerm nonTerm) {
			super(parent, invokingState);
			this.grammar = grammar;
			this.nonTerm = nonTerm;
		}
		@Override public int getRuleIndex() { return RULE_rulE; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MyGrammarListener ) ((MyGrammarListener)listener).enterRulE(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MyGrammarListener ) ((MyGrammarListener)listener).exitRulE(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MyGrammarVisitor ) return ((MyGrammarVisitor<? extends T>)visitor).visitRulE(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RulEContext rulE(MyGrammar grammar,MyGrammar.NonTerm nonTerm) throws RecognitionException {
		RulEContext _localctx = new RulEContext(_ctx, getState(), grammar, nonTerm);
		enterRule(_localctx, 12, RULE_rulE);
		 List<MyGrammar.Triple> triples = new ArrayList<>(); 
		int _la;
		try {
			setState(80);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case NAME:
				enterOuterAlt(_localctx, 1);
				{
				setState(72);
				triple(triples);
				 _localctx.grammar.addRule(_localctx.nonTerm, new MyGrammar.Rule(triples)); 
				}
				break;
			case EPS:
				enterOuterAlt(_localctx, 2);
				{
				setState(75);
				match(EPS);
				setState(77);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==CODE) {
					{
					setState(76);
					((RulEContext)_localctx).code = code();
					}
				}

				 _localctx.grammar.addRule(_localctx.nonTerm, new MyGrammar.Rule(List.of(new MyGrammar.Triple("", "", (((RulEContext)_localctx).code!=null?_input.getText(((RulEContext)_localctx).code.start,((RulEContext)_localctx).code.stop):null))))); 
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

	public static class TripleContext extends ParserRuleContext {
		public List<MyGrammar.Triple> triples;
		public NameContext name;
		public InheritedAttrContext inheritedAttr;
		public CodeContext code;
		public List<NameContext> name() {
			return getRuleContexts(NameContext.class);
		}
		public NameContext name(int i) {
			return getRuleContext(NameContext.class,i);
		}
		public List<InheritedAttrContext> inheritedAttr() {
			return getRuleContexts(InheritedAttrContext.class);
		}
		public InheritedAttrContext inheritedAttr(int i) {
			return getRuleContext(InheritedAttrContext.class,i);
		}
		public List<CodeContext> code() {
			return getRuleContexts(CodeContext.class);
		}
		public CodeContext code(int i) {
			return getRuleContext(CodeContext.class,i);
		}
		public List<TerminalNode> THEN() { return getTokens(MyGrammarParser.THEN); }
		public TerminalNode THEN(int i) {
			return getToken(MyGrammarParser.THEN, i);
		}
		public TripleContext(ParserRuleContext parent, int invokingState) { super(parent, invokingState); }
		public TripleContext(ParserRuleContext parent, int invokingState, List<MyGrammar.Triple> triples) {
			super(parent, invokingState);
			this.triples = triples;
		}
		@Override public int getRuleIndex() { return RULE_triple; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MyGrammarListener ) ((MyGrammarListener)listener).enterTriple(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MyGrammarListener ) ((MyGrammarListener)listener).exitTriple(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MyGrammarVisitor ) return ((MyGrammarVisitor<? extends T>)visitor).visitTriple(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TripleContext triple(List<MyGrammar.Triple> triples) throws RecognitionException {
		TripleContext _localctx = new TripleContext(_ctx, getState(), triples);
		enterRule(_localctx, 14, RULE_triple);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(82);
			((TripleContext)_localctx).name = name();
			setState(84);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==HERIT_ATTR) {
				{
				setState(83);
				((TripleContext)_localctx).inheritedAttr = inheritedAttr();
				}
			}

			setState(87);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==CODE) {
				{
				setState(86);
				((TripleContext)_localctx).code = code();
				}
			}

			 _localctx.triples.add(new MyGrammar.Triple((((TripleContext)_localctx).name!=null?_input.getText(((TripleContext)_localctx).name.start,((TripleContext)_localctx).name.stop):null), (((TripleContext)_localctx).inheritedAttr!=null?_input.getText(((TripleContext)_localctx).inheritedAttr.start,((TripleContext)_localctx).inheritedAttr.stop):null), (((TripleContext)_localctx).code!=null?_input.getText(((TripleContext)_localctx).code.start,((TripleContext)_localctx).code.stop):null))); 
			setState(102);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==THEN) {
				{
				{
				setState(90);
				match(THEN);
				setState(91);
				((TripleContext)_localctx).name = name();
				setState(93);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==HERIT_ATTR) {
					{
					setState(92);
					((TripleContext)_localctx).inheritedAttr = inheritedAttr();
					}
				}

				setState(96);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==CODE) {
					{
					setState(95);
					((TripleContext)_localctx).code = code();
					}
				}

				 _localctx.triples.add(new MyGrammar.Triple((((TripleContext)_localctx).name!=null?_input.getText(((TripleContext)_localctx).name.start,((TripleContext)_localctx).name.stop):null), (((TripleContext)_localctx).inheritedAttr!=null?_input.getText(((TripleContext)_localctx).inheritedAttr.start,((TripleContext)_localctx).inheritedAttr.stop):null), (((TripleContext)_localctx).code!=null?_input.getText(((TripleContext)_localctx).code.start,((TripleContext)_localctx).code.stop):null))); 
				}
				}
				setState(104);
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

	public static class NameContext extends ParserRuleContext {
		public TerminalNode NAME() { return getToken(MyGrammarParser.NAME, 0); }
		public NameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_name; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MyGrammarListener ) ((MyGrammarListener)listener).enterName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MyGrammarListener ) ((MyGrammarListener)listener).exitName(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MyGrammarVisitor ) return ((MyGrammarVisitor<? extends T>)visitor).visitName(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NameContext name() throws RecognitionException {
		NameContext _localctx = new NameContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_name);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(105);
			match(NAME);
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

	public static class PrimitiveContext extends ParserRuleContext {
		public TerminalNode REGEXP() { return getToken(MyGrammarParser.REGEXP, 0); }
		public PrimitiveContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_primitive; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MyGrammarListener ) ((MyGrammarListener)listener).enterPrimitive(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MyGrammarListener ) ((MyGrammarListener)listener).exitPrimitive(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MyGrammarVisitor ) return ((MyGrammarVisitor<? extends T>)visitor).visitPrimitive(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PrimitiveContext primitive() throws RecognitionException {
		PrimitiveContext _localctx = new PrimitiveContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_primitive);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(107);
			match(REGEXP);
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

	public static class CodeContext extends ParserRuleContext {
		public TerminalNode CODE() { return getToken(MyGrammarParser.CODE, 0); }
		public CodeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_code; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MyGrammarListener ) ((MyGrammarListener)listener).enterCode(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MyGrammarListener ) ((MyGrammarListener)listener).exitCode(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MyGrammarVisitor ) return ((MyGrammarVisitor<? extends T>)visitor).visitCode(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CodeContext code() throws RecognitionException {
		CodeContext _localctx = new CodeContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_code);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(109);
			match(CODE);
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

	public static class AttrContext extends ParserRuleContext {
		public TerminalNode ATTR() { return getToken(MyGrammarParser.ATTR, 0); }
		public AttrContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_attr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MyGrammarListener ) ((MyGrammarListener)listener).enterAttr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MyGrammarListener ) ((MyGrammarListener)listener).exitAttr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MyGrammarVisitor ) return ((MyGrammarVisitor<? extends T>)visitor).visitAttr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AttrContext attr() throws RecognitionException {
		AttrContext _localctx = new AttrContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_attr);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(111);
			match(ATTR);
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

	public static class InheritedAttrContext extends ParserRuleContext {
		public TerminalNode HERIT_ATTR() { return getToken(MyGrammarParser.HERIT_ATTR, 0); }
		public InheritedAttrContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_inheritedAttr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MyGrammarListener ) ((MyGrammarListener)listener).enterInheritedAttr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MyGrammarListener ) ((MyGrammarListener)listener).exitInheritedAttr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MyGrammarVisitor ) return ((MyGrammarVisitor<? extends T>)visitor).visitInheritedAttr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InheritedAttrContext inheritedAttr() throws RecognitionException {
		InheritedAttrContext _localctx = new InheritedAttrContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_inheritedAttr);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(113);
			match(HERIT_ATTR);
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\26v\4\2\t\2\4\3\t"+
		"\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t\13\4"+
		"\f\t\f\4\r\t\r\4\16\t\16\3\2\3\2\5\2\37\n\2\3\2\7\2\"\n\2\f\2\16\2%\13"+
		"\2\3\2\3\2\3\3\3\3\3\3\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3\5\3\5\5\5\65\n\5"+
		"\3\6\3\6\3\6\3\6\3\6\3\6\3\7\3\7\3\7\3\7\3\7\3\7\7\7C\n\7\f\7\16\7F\13"+
		"\7\3\7\3\7\3\7\3\b\3\b\3\b\3\b\3\b\5\bP\n\b\3\b\5\bS\n\b\3\t\3\t\5\tW"+
		"\n\t\3\t\5\tZ\n\t\3\t\3\t\3\t\3\t\5\t`\n\t\3\t\5\tc\n\t\3\t\3\t\7\tg\n"+
		"\t\f\t\16\tj\13\t\3\n\3\n\3\13\3\13\3\f\3\f\3\r\3\r\3\16\3\16\3\16\2\2"+
		"\17\2\4\6\b\n\f\16\20\22\24\26\30\32\2\2\2s\2\34\3\2\2\2\4(\3\2\2\2\6"+
		"-\3\2\2\2\b\64\3\2\2\2\n\66\3\2\2\2\f<\3\2\2\2\16R\3\2\2\2\20T\3\2\2\2"+
		"\22k\3\2\2\2\24m\3\2\2\2\26o\3\2\2\2\30q\3\2\2\2\32s\3\2\2\2\34\36\5\4"+
		"\3\2\35\37\5\6\4\2\36\35\3\2\2\2\36\37\3\2\2\2\37#\3\2\2\2 \"\5\b\5\2"+
		"! \3\2\2\2\"%\3\2\2\2#!\3\2\2\2#$\3\2\2\2$&\3\2\2\2%#\3\2\2\2&\'\7\2\2"+
		"\3\'\3\3\2\2\2()\7\3\2\2)*\5\22\n\2*+\7\t\2\2+,\b\3\1\2,\5\3\2\2\2-.\7"+
		"\4\2\2./\5\30\r\2/\60\7\t\2\2\60\61\b\4\1\2\61\7\3\2\2\2\62\65\5\n\6\2"+
		"\63\65\5\f\7\2\64\62\3\2\2\2\64\63\3\2\2\2\65\t\3\2\2\2\66\67\5\22\n\2"+
		"\678\7\b\2\289\5\24\13\29:\7\t\2\2:;\b\6\1\2;\13\3\2\2\2<=\5\22\n\2=>"+
		"\5\32\16\2>?\7\n\2\2?D\5\16\b\2@A\7\7\2\2AC\5\16\b\2B@\3\2\2\2CF\3\2\2"+
		"\2DB\3\2\2\2DE\3\2\2\2EG\3\2\2\2FD\3\2\2\2GH\7\t\2\2HI\b\7\1\2I\r\3\2"+
		"\2\2JK\5\20\t\2KL\b\b\1\2LS\3\2\2\2MO\7\25\2\2NP\5\26\f\2ON\3\2\2\2OP"+
		"\3\2\2\2PQ\3\2\2\2QS\b\b\1\2RJ\3\2\2\2RM\3\2\2\2S\17\3\2\2\2TV\5\22\n"+
		"\2UW\5\32\16\2VU\3\2\2\2VW\3\2\2\2WY\3\2\2\2XZ\5\26\f\2YX\3\2\2\2YZ\3"+
		"\2\2\2Z[\3\2\2\2[h\b\t\1\2\\]\7\13\2\2]_\5\22\n\2^`\5\32\16\2_^\3\2\2"+
		"\2_`\3\2\2\2`b\3\2\2\2ac\5\26\f\2ba\3\2\2\2bc\3\2\2\2cd\3\2\2\2de\b\t"+
		"\1\2eg\3\2\2\2f\\\3\2\2\2gj\3\2\2\2hf\3\2\2\2hi\3\2\2\2i\21\3\2\2\2jh"+
		"\3\2\2\2kl\7\5\2\2l\23\3\2\2\2mn\7\6\2\2n\25\3\2\2\2op\7\22\2\2p\27\3"+
		"\2\2\2qr\7\23\2\2r\31\3\2\2\2st\7\24\2\2t\33\3\2\2\2\r\36#\64DORVY_bh";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}