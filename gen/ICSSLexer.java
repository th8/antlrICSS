// Generated from /home/thomas/IDEAProjects/antlrICSS/src/main/antlr4/nl/han/ica/icss/parser/ICSS.g4 by ANTLR 4.7.2
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class ICSSLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.7.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		OPEN_BRACE=1, CLOSE_BRACE=2, SEMICOLON=3, COLON=4, PLUS=5, MIN=6, MUL=7, 
		ASSIGNMENT_OPERATOR=8, PIXELSIZE=9, PERCENTAGE=10, SCALAR=11, COLOR=12, 
		ID_IDENT=13, CLASS_IDENT=14, LOWER_IDENT=15, CAPITAL_IDENT=16, WS=17;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"OPEN_BRACE", "CLOSE_BRACE", "SEMICOLON", "COLON", "PLUS", "MIN", "MUL", 
			"ASSIGNMENT_OPERATOR", "PIXELSIZE", "PERCENTAGE", "SCALAR", "COLOR", 
			"ID_IDENT", "CLASS_IDENT", "LOWER_IDENT", "CAPITAL_IDENT", "WS"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'{'", "'}'", "';'", "':'", "'+'", "'-'", "'*'", "':='"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "OPEN_BRACE", "CLOSE_BRACE", "SEMICOLON", "COLON", "PLUS", "MIN", 
			"MUL", "ASSIGNMENT_OPERATOR", "PIXELSIZE", "PERCENTAGE", "SCALAR", "COLOR", 
			"ID_IDENT", "CLASS_IDENT", "LOWER_IDENT", "CAPITAL_IDENT", "WS"
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


	public ICSSLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "ICSS.g4"; }

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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\23s\b\1\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\3\2\3\2\3\3\3\3\3\4\3\4\3\5\3\5\3\6\3\6\3\7\3\7\3\b\3\b\3\t\3\t\3\t\3"+
		"\n\6\n8\n\n\r\n\16\n9\3\n\3\n\3\n\3\13\6\13@\n\13\r\13\16\13A\3\13\3\13"+
		"\3\f\6\fG\n\f\r\f\16\fH\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\16\3\16\6\16"+
		"U\n\16\r\16\16\16V\3\17\3\17\6\17[\n\17\r\17\16\17\\\3\20\3\20\7\20a\n"+
		"\20\f\20\16\20d\13\20\3\21\3\21\7\21h\n\21\f\21\16\21k\13\21\3\22\6\22"+
		"n\n\22\r\22\16\22o\3\22\3\22\2\2\23\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n"+
		"\23\13\25\f\27\r\31\16\33\17\35\20\37\21!\22#\23\3\2\t\3\2\62;\4\2\62"+
		";ch\5\2//\62;c|\3\2c|\3\2C\\\6\2\62;C\\aac|\5\2\13\f\17\17\"\"\2z\2\3"+
		"\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2"+
		"\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31"+
		"\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2"+
		"\3%\3\2\2\2\5\'\3\2\2\2\7)\3\2\2\2\t+\3\2\2\2\13-\3\2\2\2\r/\3\2\2\2\17"+
		"\61\3\2\2\2\21\63\3\2\2\2\23\67\3\2\2\2\25?\3\2\2\2\27F\3\2\2\2\31J\3"+
		"\2\2\2\33R\3\2\2\2\35X\3\2\2\2\37^\3\2\2\2!e\3\2\2\2#m\3\2\2\2%&\7}\2"+
		"\2&\4\3\2\2\2\'(\7\177\2\2(\6\3\2\2\2)*\7=\2\2*\b\3\2\2\2+,\7<\2\2,\n"+
		"\3\2\2\2-.\7-\2\2.\f\3\2\2\2/\60\7/\2\2\60\16\3\2\2\2\61\62\7,\2\2\62"+
		"\20\3\2\2\2\63\64\7<\2\2\64\65\7?\2\2\65\22\3\2\2\2\668\t\2\2\2\67\66"+
		"\3\2\2\289\3\2\2\29\67\3\2\2\29:\3\2\2\2:;\3\2\2\2;<\7r\2\2<=\7z\2\2="+
		"\24\3\2\2\2>@\t\2\2\2?>\3\2\2\2@A\3\2\2\2A?\3\2\2\2AB\3\2\2\2BC\3\2\2"+
		"\2CD\7\'\2\2D\26\3\2\2\2EG\t\2\2\2FE\3\2\2\2GH\3\2\2\2HF\3\2\2\2HI\3\2"+
		"\2\2I\30\3\2\2\2JK\7%\2\2KL\t\3\2\2LM\t\3\2\2MN\t\3\2\2NO\t\3\2\2OP\t"+
		"\3\2\2PQ\t\3\2\2Q\32\3\2\2\2RT\7%\2\2SU\t\4\2\2TS\3\2\2\2UV\3\2\2\2VT"+
		"\3\2\2\2VW\3\2\2\2W\34\3\2\2\2XZ\7\60\2\2Y[\t\4\2\2ZY\3\2\2\2[\\\3\2\2"+
		"\2\\Z\3\2\2\2\\]\3\2\2\2]\36\3\2\2\2^b\t\5\2\2_a\t\4\2\2`_\3\2\2\2ad\3"+
		"\2\2\2b`\3\2\2\2bc\3\2\2\2c \3\2\2\2db\3\2\2\2ei\t\6\2\2fh\t\7\2\2gf\3"+
		"\2\2\2hk\3\2\2\2ig\3\2\2\2ij\3\2\2\2j\"\3\2\2\2ki\3\2\2\2ln\t\b\2\2ml"+
		"\3\2\2\2no\3\2\2\2om\3\2\2\2op\3\2\2\2pq\3\2\2\2qr\b\22\2\2r$\3\2\2\2"+
		"\13\29AHV\\bio\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}