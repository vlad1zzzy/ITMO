// Generated from C:/Users/Vlad/Desktop/Translation-Methods-main/Lab4/src/main/java/grammar\MyGrammar.g4 by ANTLR 4.9.2
package grammar;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class MyGrammarLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.9.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		GRAMMAR=1, FIELDS=2, NAME=3, REGEXP=4, OR=5, COLON=6, SEMI=7, RETURNS=8, 
		THEN=9, OPEN_BRACE=10, CLOSE_BRACE=11, OPEN_BRACKET=12, CLOSE_BRACKET=13, 
		OPEN_PAREN=14, CLOSE_PAREN=15, CODE=16, ATTR=17, HERIT_ATTR=18, EPS=19, 
		WS=20;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"GRAMMAR", "FIELDS", "NAME", "REGEXP", "OR", "COLON", "SEMI", "RETURNS", 
			"THEN", "OPEN_BRACE", "CLOSE_BRACE", "OPEN_BRACKET", "CLOSE_BRACKET", 
			"OPEN_PAREN", "CLOSE_PAREN", "CODE", "ATTR", "HERIT_ATTR", "EPS", "WS"
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


	public MyGrammarLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "MyGrammar.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\26\u008a\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3"+
		"\3\3\3\3\3\3\3\3\3\3\3\3\3\3\4\6\4<\n\4\r\4\16\4=\3\4\7\4A\n\4\f\4\16"+
		"\4D\13\4\3\5\3\5\7\5H\n\5\f\5\16\5K\13\5\3\5\3\5\3\6\3\6\3\7\3\7\3\b\3"+
		"\b\3\t\3\t\3\t\3\n\3\n\3\n\3\13\3\13\3\f\3\f\3\r\3\r\3\16\3\16\3\17\3"+
		"\17\3\20\3\20\3\21\3\21\7\21i\n\21\f\21\16\21l\13\21\3\21\3\21\3\22\3"+
		"\22\7\22r\n\22\f\22\16\22u\13\22\3\22\3\22\3\23\3\23\7\23{\n\23\f\23\16"+
		"\23~\13\23\3\23\3\23\3\24\3\24\3\25\6\25\u0085\n\25\r\25\16\25\u0086\3"+
		"\25\3\25\6Ijs|\2\26\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r"+
		"\31\16\33\17\35\20\37\21!\22#\23%\24\'\25)\26\3\2\5\4\2C\\c|\6\2\62;C"+
		"\\aac|\5\2\13\f\17\17\"\"\2\u0090\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2"+
		"\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3"+
		"\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2"+
		"\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2"+
		"\2\3+\3\2\2\2\5\63\3\2\2\2\7;\3\2\2\2\tE\3\2\2\2\13N\3\2\2\2\rP\3\2\2"+
		"\2\17R\3\2\2\2\21T\3\2\2\2\23W\3\2\2\2\25Z\3\2\2\2\27\\\3\2\2\2\31^\3"+
		"\2\2\2\33`\3\2\2\2\35b\3\2\2\2\37d\3\2\2\2!f\3\2\2\2#o\3\2\2\2%x\3\2\2"+
		"\2\'\u0081\3\2\2\2)\u0084\3\2\2\2+,\7i\2\2,-\7t\2\2-.\7c\2\2./\7o\2\2"+
		"/\60\7o\2\2\60\61\7c\2\2\61\62\7t\2\2\62\4\3\2\2\2\63\64\7h\2\2\64\65"+
		"\7k\2\2\65\66\7g\2\2\66\67\7n\2\2\678\7f\2\289\7u\2\29\6\3\2\2\2:<\t\2"+
		"\2\2;:\3\2\2\2<=\3\2\2\2=;\3\2\2\2=>\3\2\2\2>B\3\2\2\2?A\t\3\2\2@?\3\2"+
		"\2\2AD\3\2\2\2B@\3\2\2\2BC\3\2\2\2C\b\3\2\2\2DB\3\2\2\2EI\7$\2\2FH\13"+
		"\2\2\2GF\3\2\2\2HK\3\2\2\2IJ\3\2\2\2IG\3\2\2\2JL\3\2\2\2KI\3\2\2\2LM\7"+
		"$\2\2M\n\3\2\2\2NO\7~\2\2O\f\3\2\2\2PQ\7<\2\2Q\16\3\2\2\2RS\7=\2\2S\20"+
		"\3\2\2\2TU\7?\2\2UV\7@\2\2V\22\3\2\2\2WX\7/\2\2XY\7@\2\2Y\24\3\2\2\2Z"+
		"[\7}\2\2[\26\3\2\2\2\\]\7\177\2\2]\30\3\2\2\2^_\7]\2\2_\32\3\2\2\2`a\7"+
		"_\2\2a\34\3\2\2\2bc\7*\2\2c\36\3\2\2\2de\7+\2\2e \3\2\2\2fj\5\25\13\2"+
		"gi\13\2\2\2hg\3\2\2\2il\3\2\2\2jk\3\2\2\2jh\3\2\2\2km\3\2\2\2lj\3\2\2"+
		"\2mn\5\27\f\2n\"\3\2\2\2os\5\31\r\2pr\13\2\2\2qp\3\2\2\2ru\3\2\2\2st\3"+
		"\2\2\2sq\3\2\2\2tv\3\2\2\2us\3\2\2\2vw\5\33\16\2w$\3\2\2\2x|\5\35\17\2"+
		"y{\13\2\2\2zy\3\2\2\2{~\3\2\2\2|}\3\2\2\2|z\3\2\2\2}\177\3\2\2\2~|\3\2"+
		"\2\2\177\u0080\5\37\20\2\u0080&\3\2\2\2\u0081\u0082\7\u03b7\2\2\u0082"+
		"(\3\2\2\2\u0083\u0085\t\4\2\2\u0084\u0083\3\2\2\2\u0085\u0086\3\2\2\2"+
		"\u0086\u0084\3\2\2\2\u0086\u0087\3\2\2\2\u0087\u0088\3\2\2\2\u0088\u0089"+
		"\b\25\2\2\u0089*\3\2\2\2\n\2=BIjs|\u0086\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}