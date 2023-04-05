package jarkz.lab8.document;

/**
 * Enumeration which contains some punctuation marks.
 * <br>
 * Usage:
 * 
 * <pre class="code">
 * PunctuationMark mark = PunctuationMark.EXCLAMATION;
 * String sentence = "Some text!";
 * Pattern pattern = Pattern.compile(mark.getPattern());
 * Matcher matcher = pattern.matcher(sentence);
 * assertThat(matcher.matches()).isEqualTo(true);
 * </pre>
 *
 * Note: for bracketes, use method {@code getBrackets()}.
 */
public enum PunctuationMark {
	PERIOD("[.]", "."),
	COMMA(",", ","),
	EXCLAMATION("!", "!"),
	QUESTION("[?]", "?"),
	SEMICOLON(";", ";"),
	COLON(":", ":"),
	APOSTROPHE("`", "`"),
	SINGLE_QUOTATION("'", "'"),
	DOUBLE_QUOTATION("\"", "\""),
	PARENTHESES("[()]", "()"),
	BRACKETS("[\\[\\]]", "[]"),
	BRACES("[{}]", "{}"),
	HYPHEN("-", "-"),
	DASH("-–", "--"),
	ELLIPSIS("(\\.\\.\\.)", "..."),
	SLASH("/", "/");

	/**
	 * Brackets class represent an abstraction of the brackets.
	 * <br>
	 * Usage:
	 * 
	 * <pre class="code">
	 * Brackets brackets = PunctuationMark.PARENTHESES.getBrackets();
	 * String someText = "(expression)";
	 * assertThat(brackets.getLeft()).isEqualTo(someText.getCharAt(0));
	 * </pre>
	 */
	public class Brackets {

		private char leftBracket;
		private char rightBracket;

		private Brackets(char left, char right) {
			this.leftBracket = left;
			this.rightBracket = right;
		}

		/**
		 * @return left bracket
		 */
		public char getLeftBracket() {
			return leftBracket;
		}

		/**
		 * @return right bracket
		 */
		public char getRightBracket() {
			return rightBracket;
		}
	}

	private final String pattern;
	private final String presentatnion;

	private PunctuationMark(String pattern, String presentatnion) {
		this.pattern = pattern;
		this.presentatnion = presentatnion;
	}

	/**
	 * Returned pattern present only punctuation mark.<br>
	 * For example, for {@code PunctuationMark.COLON} the patther will be present
	 * ":".
	 *
	 * For get a universal pattern, use command {@code getUniversalPattern()}.
	 *
	 * @return the pattern of selected type
	 */
	public String getPattern() {
		return pattern;
	}

	/**
	 * Returns the pattern like "(.*)?" + pattern of selected type + "(.*)?".
	 * <br>
	 * For example, for {@code PunctuationMark.SEMICOLON} the universal pattern will
	 * be
	 * "(.*)?:(.*)?"
	 *
	 * @return the universal pattern of selected type
	 */
	public String getUniversalPattern() {
		String any = "(.*)?";
		return any + pattern + any;
	}

	/**
	 * Represent selected type as string. If you want get Brackets or Parentheses,
	 * please,
	 * use {@code getBrackets()} instead this command.
	 * 
	 * @return the presenatation of selected type. For example, PERIOD present "."
	 */
	public String getPresentatnion() {
		return presentatnion;
	}

	/**
	 * Class Brackets provide two methods: {@code getLeftBrackets()} and
	 * {@code getRightBrackets()}
	 * Useful for reading the code.
	 * 
	 * @return a class Brackets
	 */
	public Brackets getBrackets() {
		return new Brackets(presentatnion.charAt(0), presentatnion.charAt(1));
	}
}
