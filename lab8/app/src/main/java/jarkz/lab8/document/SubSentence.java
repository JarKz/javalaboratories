package jarkz.lab8.document;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * SubSentence class for Sentence class.
 * Have ordered words.
 * <br>
 * Content of class are 'finals' because it don't have removal methods.
 * <br>
 * Usage:
 * <pre class="code">
 * SubSentence subsentence = new SubSentence();
 * subsentence.addWord(new Word("Kaleidoscope"));
 * subsentence.addWord(new Word("are"));
 * subsentence.addWord(new Word("beautiful"));
 * subsentence.getString() // -> "Kaleidoscope are beautiful"
 * </pre>
 */
public class SubSentence {

	private List<Word> words = new ArrayList<>();

	/**
	 * Empty constructor for building from zero using method {@ addWord()}
	 */
	public SubSentence() {
	}

	/**
	 * Building SubSentenct from part of sentence.
	 * @param part of sentence that will split to words
	 * @throws NullPointerException if param part is null
	 * @throws IllegalArgumentException if param part is blank
	 */
	public SubSentence(String part) {
		if (part == null) {
			throw new NullPointerException("String instance cannot be null");
		}
		if (part.isBlank() || part.isEmpty()) {
			throw new IllegalArgumentException("String instance cannot be empty or blank");
		}
		String[] subparts = part.split(" ");
		for (String subpart : subparts) {
			Word word = new Word(subpart);
			addWord(word);
		}
	}

	@Override
	public String toString() {
		return "SubSentence[words=" + words + "]";
	}

	/**
	 * Append word to container
	 * @param word as Word class that append to container
	 */
	public void addWord(Word word) {
		if (word == null)
			throw new NullPointerException("Word instance cannot be null");
		words.add(word);
	}

	/**
	 * Get word from container of SubSentence class.
	 * @param index as word position in container. Should be between 0 and container size exclusive.
	 * @return Word from specific position.
	 * @throws IndexOutOfBoundsException if entered index are lower than zero and higher or equals container size.
	 */
	public Word getWord(int index) {
		if (index < 0 || words.size() <= index) {
			throw new IndexOutOfBoundsException("Index out of bounds");
		}
		return words.get(index);
	}

	/**
	 * Get all words from container of SubSentence class.
	 * @return copy of words list.
	 */
	public List<Word> getWords() {
		return new ArrayList<Word>(words);
	}

	/**
	 * Transform words to string by joining with space.
	 * <br>
	 * @return string from word container with spaces between them.
	 */
	public String getString() {
		return words.stream().map(Word::getContent).collect(Collectors.joining(" "));
	}
}
