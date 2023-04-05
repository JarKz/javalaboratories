package jarkz.lab8.document;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Sentence class realize two situations: simple sentence and complex.<br>
 * For simple sentence uses only word container like {@code SubSentence} and
 * end punctuation mark.
 * <br>
 * <br>
 * For complex sentence uses {@code SubSentence} and formula for collecting to
 * single String.
 * <br>
 * It's like switch between two implementations.
 * <br>
 * Usage:
 * 
 * <pre class="code">
 * String simpleSentence = "Lorem ipsum dolor sit amet, consectetur adip...";
 * Sentence sentence = new Sentence(simpleSentence);
 * Assert.assertEquals(sentese.getFormula(), "%s, %s...");
 * </pre>
 */
public class Sentence {

	private final String ANY = "(.*)?";
	private final String END_MARKS_UNIVERSAL_PATTERN = ANY + "[.?!]" + ANY;
	private final String NOT_END_MARKS_PATTERN = "[,;:`'\"()\\[\\]{}\\-/]";
	private final String NOT_END_MARKS_UNIVERSAL_PATTERN = ANY + NOT_END_MARKS_PATTERN + ANY;

	private final String ALL_MARKS_PATTERN = "[.?!,;:`'\"()\\[\\]{}\\-/]";

	private List<Word> words = new ArrayList<>();
	private PunctuationMark endMark;

	private List<SubSentence> subsentences = new ArrayList<>();
	private String formula;
	private boolean containsSubsentence = false;

	/**
	 * Constructor will make decision about choosing of two situations using pattern
	 * which contains some punctuation marks without end marks and.
	 * <br>
	 * If sentence have some punctuation marks (not ends), then will be create a
	 * container with
	 * SubSentences. In other case - a container of words.
	 * 
	 * @param sentence as String
	 * @throws NullPointerException     if sentence param is null
	 * @throws IllegalArgumentException if sentence is blank or
	 *                                  don't have end mark like period, exclamation
	 *                                  and question marks
	 */
	public Sentence(String sentence) {
		if (sentence == null)
			throw new NullPointerException("String instance cannot be null");
		if (sentence.isBlank() || sentence.isEmpty())
			throw new IllegalArgumentException("String instance cannot be empty or blank");
		if (!sentence.matches(END_MARKS_UNIVERSAL_PATTERN))
			throw new IllegalArgumentException("Sentence does not end mark.");
		sentence = sentence.trim();
		String sentenceWithoutLastMark = sentence.substring(0, sentence.length() - 1);
		if (sentenceWithoutLastMark.matches(NOT_END_MARKS_UNIVERSAL_PATTERN)) {
			containsSubsentence = true;
			createSubsentences(sentence);
		} else {
			createSingleSentence(sentence);
		}
	}

	@Override
	public String toString() {
		return "Sentence[words=" + words + ", endMark=" + endMark + ", subsentences=" + subsentences + ", formula="
				+ formula + ", containsSubsentence=" + containsSubsentence + "]";
	}

	/**
	 * In some ways, class may generate subsentences instead of single sentence. Use
	 * this method for determining ways of decisions.
	 *
	 * @return `true` if contains subsentences, `false` otherwise
	 */
	public boolean containsSubsentence() {
		return containsSubsentence;
	}

	/**
	 * Transform container to string by formatting by formula.
	 * <br>
	 * 
	 * @return string from containers by formatting by formula.
	 */
	public String getString() {
		if (containsSubsentence) {
			Object[] subsentencesAsString = subsentences.stream().map(SubSentence::getString).toArray();
			return String.format(formula, subsentencesAsString);
		} else {
			return words.stream().map(Word::getContent).collect(Collectors.joining(" ")) + endMark.getPresentatnion();
		}
	}

	/**
	 * In two cases, words will be returned from containers, even container have
	 * subsentences.
	 *
	 * @return copy of words from container
	 */
	public List<Word> getWords() {
		if (containsSubsentence) {
			return subsentences.stream().map(SubSentence::getWords).flatMap(Collection::stream).toList();
		}
		return new ArrayList<>(words);
	}

	/**
	 * Returns a formula if Sentence have a container of SubSentence,
	 * else empty string.
	 * 
	 * @return return a copy of formula
	 */
	public String getFormula() {
		return new String(formula);
	}

	/**
	 * Returns a end punctuation mark.
	 *
	 * @return instance of {@link PunctuationMark} if single sentence, else null
	 */
	public PunctuationMark getEndMark() {
		return endMark;
	}

	/**
	 * Returns a copy of container.
	 *
	 * @return a optional copy of {@link SubSentence} instances container, if
	 *         contains. Otherwise `empty` value.
	 */
	public Optional<List<SubSentence>> getSubSentences() {
		if (containsSubsentence) {
			return Optional.of(new ArrayList<>(subsentences));
		}
		return Optional.empty();
	}

	/**
	 * Create a copy of selecter range container and return it. `min` and `max`
	 * values passed like {@code list.subList(min, max)}.
	 *
	 * @param min as int between zero and container size
	 * @param max as int between zero and container size
	 * @return a optional of selected range copy of {@link SubSentence} instances
	 *         container, if contains. Otherwise `empty` value.
	 */
	public Optional<List<SubSentence>> getSubSentencesInRange(int min, int max) {
		if (!containsSubsentence) {
			return Optional.empty();
		}
		int size = subsentences.size();
		if (min < 0 || size < min) {
			throw new IllegalArgumentException("Min value must be between zero and container size. Min value: " + min + ", container size:" + subsentences.size());
		}
		if (max < 0 || size < max) {
			throw new IllegalArgumentException("Max value must be between zero and container size. Max value: " + max + ", container size:" + subsentences.size());
		}
		if (min >= max) {
			throw new IllegalArgumentException("Min value must less than max value. Min value: " + min + ", max value: " + max + ", container size:" + subsentences.size());
		}

		return Optional.of(new ArrayList<>(subsentences.subList(min, max)));
	}

	private void createSubsentences(String sentence) {
		if (formula == null) {
			formula = "";
		}
		String WITH_DELIMITER = "((?<=%1$s)|(?=%1$s))";
		String[] partsOfSentence = sentence.split(String.format(WITH_DELIMITER, ALL_MARKS_PATTERN));

		boolean beforeMarkSpacing = false;

		for (String part : partsOfSentence) {
			if (!part.matches(ALL_MARKS_PATTERN)) {
				boolean afterMarkSpacing = part.charAt(0) == ' ';

				if (afterMarkSpacing) {
					formula += " ";
				}
				formula += "%s";

				beforeMarkSpacing = part.charAt(part.length() - 1) == ' ';

				part = part.trim();
				SubSentence subsentence = new SubSentence(part);
				subsentences.add(subsentence);
			} else {
				if (beforeMarkSpacing) {
					formula += " ";
					beforeMarkSpacing = false;
				}
				formula += part;
			}
		}
	}

	private void createSingleSentence(String sentence) {
		List<PunctuationMark> endMarks = new ArrayList<>(List.of(
				PunctuationMark.PERIOD,
				PunctuationMark.EXCLAMATION,
				PunctuationMark.QUESTION));
		String lastChar = sentence.substring(sentence.length() - 1, sentence.length());
		for (PunctuationMark mark : endMarks) {
			if (lastChar.equals(mark.getPresentatnion())) {
				endMark = mark;
				break;
			}
		}
		if (endMark == null) {
			throw new IllegalArgumentException("Every sentence must have a end punctuation mark.");
		}
		String sentenceWithoutLastMark = sentence.substring(0, sentence.length() - 1);
		String[] words = sentenceWithoutLastMark.split(" ");
		for (String wordAsString : words) {
			wordAsString = wordAsString.trim();
			Word word = new Word(wordAsString);
			this.words.add(word);
		}
	}
}
