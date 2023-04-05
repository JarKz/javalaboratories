package jarkz.lab8.document;

import jarkz.lab8.document.PunctuationMark.Brackets;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

/**
 * Paragraph contains a container of {@code Sentence} instances.
 * <br>
 * In abstraction, Paragraph have some sentences and every sentence
 * must be have a end punctuation mark (period, exclamation and
 * question mark). Allowed suggestions which have a quotation marks, but
 * it must be right (e.g. total count must be even). Closed parentheses,
 * brackets and braces are allowed.
 * <br>
 * Usage:
 *
 * <pre class="code">
 * String longText = "Lorem ipsum dolor sit amet. consectetur adipiscing"
 * 		+ "elit, sed do eiusmod tempor incididunt.";
 * Paragraph paragraph = new Paragraph(longText);
 * Assert.assertEquals(longText, paragraph.getString());
 * </pre>
 */
public class Paragraph {

	private final String END_MARKS = ".?!";
	private final String DELIMITER = "`'\"(){}\\[\\]";
	private final String DELIMITER_PATTERN = "[.?!'\"`()\\[\\]{}]";

	private final List<Character> QUOTATIONS = new ArrayList<>(
			List.of(
					PunctuationMark.SINGLE_QUOTATION,
					PunctuationMark.DOUBLE_QUOTATION,
					PunctuationMark.APOSTROPHE))
			.stream().map(mark -> mark.getPresentatnion().charAt(0)).toList();

	private final List<Brackets> BRACKETS = new ArrayList<>(
			List.of(
					PunctuationMark.PARENTHESES, PunctuationMark.BRACKETS, PunctuationMark.BRACES))
			.stream().map(PunctuationMark::getBrackets).toList();

	private List<Sentence> sentences;

	// Get only one monolith string
	/**
	 * Gets text and will parse it, splitting by special punctuation marks.
	 * After, will save to container.
	 * 
	 * @param textParagraph as String
	 *
	 * @throws NullPointerException     textParagraph is null
	 * @throws IllegalArgumentException - textParagraph is blank
	 */
	public Paragraph(String textParagraph) {
		if (textParagraph == null)
			throw new NullPointerException("String instance cannot be null");
		if (textParagraph.isBlank())
			throw new IllegalArgumentException("String instance textParagraph cannot be blank");
		List<String> stringSentences = splitToStringSentences(textParagraph);
		convertToSentences(stringSentences);
	}

	@Override
	public String toString() {
		return "Paragraph[sentences=" + sentences + "]";
	}

	/**
	 * Adding entered {@link Sentence} instance to end of container.
	 *
	 * @param sentence as {@link Sentence}
	 */
	public void addSentence(Sentence sentence) {
		if (sentence == null)
			throw new NullPointerException("Sentence instance cannot be null");
		sentences.add(sentence);
	}

	/**
	 * Removing entered {@link Sentence} instance if container contains it.
	 *
	 * @param sentence
	 */
	public void removeSentence(Sentence sentence){
		if (sentence == null)
			throw new NullPointerException("Sentence instance cannot be null");
		sentences.remove(sentence);
	}

	/**
	 * Returns a copy of  {@link Sentence} instances container.
	 *
	 * @return a copy of {@link Sentence} instances container
	 */
	public List<Sentence> getSentences(){
		return new ArrayList<>(sentences);
	}

	/**
	 * @return string, collected from container by joining with space.
	 */
	public String getString() {
		return sentences.stream().map(Sentence::getString).collect(Collectors.joining(" "));
	}

	/**
	 * Converting checked String to Sentence instance and move to container.
	 * 
	 * @param stringSentences is separated text by special rule in method
	 *                        {@code splitToStringSentences(String)}
	 */
	private void convertToSentences(List<String> stringSentences) {
		sentences = stringSentences.stream().map(Sentence::new).collect(Collectors.toList());
	}

	/**
	 * Split the entered paragraph text by special rule and check the result.
	 * <br>
	 * Note: If passed text are invalid then returned value will be empty list.
	 * <br>
	 *
	 * @param paragraph as String
	 * @return list of checked String sentences. Empty list if passed paragraph are
	 *         invalid
	 */
	private List<String> splitToStringSentences(String paragraph) {
		List<String> sentences = new ArrayList<>();

		String WITH_DELIMITER = "((?<=%1$s)|(?=%1$s))";
		// Split by delimiter pattern
		String currentSentence = "";
		// String[] parts = paragraph.split(String.format(WITH_DELIMITER,
		// DELIMITER_UNIVERSAL_PATTERN));
		String[] parts = paragraph.split(String.format(WITH_DELIMITER, DELIMITER_PATTERN));
		for (String part : parts) {
			currentSentence += part;

			boolean normalQuotes = checkQuotesPairs(currentSentence);
			boolean normalBrackets = checkBracketsPairs(currentSentence);
			boolean haveEndMark = checkEndMarks(currentSentence);

			if (normalQuotes && normalBrackets && haveEndMark) {
				sentences.add(currentSentence);
				currentSentence = "";
			}
		}
		return sentences;
	}

	/**
	 * Using {@code Stack} class for checking the parity of quotation marks.
	 *
	 * @param sentence as String to check
	 * @return true if quotation marks count are even and otherwise false
	 */
	private boolean checkQuotesPairs(String sentence) {
		Stack<Character> quotationStack = new Stack<>();
		sentence = sentence.trim();

		for (char sentenceChar : sentence.toCharArray()) {
			if (QUOTATIONS.contains(sentenceChar)) {
				if (quotationStack.isEmpty()) {
					quotationStack.push(sentenceChar);
				} else if (sentenceChar == quotationStack.peek()) {
					quotationStack.pop();
				} else {
					quotationStack.push(sentenceChar);
				}
			}
		}
		return quotationStack.isEmpty();
	}

	/**
	 * Using {@code Stack} class for checking the enclosing of brackets.
	 *
	 * @param sentence as String to check
	 * @return true if all brackets normally closed and otherwise false
	 */
	private boolean checkBracketsPairs(String sentence) {
		Stack<Character> bracketStack = new Stack<>();
		sentence = sentence.trim();

		for (char sentenceChar : sentence.toCharArray()) {
			if (BRACKETS.stream()
					.anyMatch(
							b -> b.getLeftBracket() == sentenceChar || b.getRightBracket() == sentenceChar)) {
				if (bracketStack.isEmpty()) {
					bracketStack.push(sentenceChar);
				} else if (BRACKETS.stream().anyMatch(b -> b.getLeftBracket() == sentenceChar)) {
					bracketStack.push(sentenceChar);
				} else if (BRACKETS.stream()
						.anyMatch(
								b -> b.getLeftBracket() == bracketStack.peek()
										&& b.getRightBracket() == sentenceChar)) {
					bracketStack.pop();
				} else {
					return false;
				}
			}
		}
		return bracketStack.isEmpty();
	}

	/**
	 * Checking by right to left array. If char are not end mark, but are some
	 * special punctuation marks like quotations or brackets will skip.
	 *
	 * @param sentence as String
	 * @return true if end punctuation mark contains and otherwise false
	 */
	private boolean checkEndMarks(String sentence) {
		sentence = sentence.trim();
		char[] tokens = sentence.toCharArray();
		for (int index = tokens.length - 1; index >= 0; index--) {
			char sentenceChar = tokens[index];
			if (sentenceChar == ' ' || DELIMITER.chars().anyMatch(c -> c == sentenceChar)) {
				continue;
			} else if (END_MARKS.chars().anyMatch(c -> c == sentenceChar)) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}
}
