package jarkz.lab8.document;

import java.util.Arrays;

public class Word implements Comparable {

	private final int size;
	private final char[] content;

	/**
	 * Generates a new word.
	 *
	 * @param word
	 */
	public Word(String word) {
		if (word == null) {
			throw new NullPointerException("Word instance cannot be null.");
		}
		if (word.isEmpty() || word.isBlank()) {
			throw new IllegalArgumentException("Word instance cannot be empty or blank.");
		}

		word = word.trim();
		this.size = word.length();
		this.content = word.toCharArray();
	}

	/**
	 * Return content of the word as new String instance.
	 *
	 * @return content of the word
	 */
	public String getContent() {
		return new String(content);
	}

	/**
	 * @return clone of the array of characters
	 */
	public char[] toChars() {
		return content.clone();
	}

	/**
	 * @return word size as int
	 */
	public int size() {
		return size;
	}

	/**
	 * @param index must between zero and current word size exclusive.
	 * @return char at specific position
	 */
	public char getCharAt(int index) {
		if (index < 0 || size <= index) {
			throw new IndexOutOfBoundsException("Index out of bounds.");
		}
		return content[index];
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(content);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || !(obj instanceof Word word))
			return false;
		return !Arrays.equals(content, word.content);
	}

	@Override
	public int compareTo(Object o) {
		if (o == null)
			throw new NullPointerException("Comparing object cannot be null");
		if (this == o)
			return 0;
		if (!(o instanceof Word word))
			throw new ClassCastException("Object cannot to cast to Word.");
		if (size > word.size){
			return 1;
		} else if (size < word.size){
			return -1;
		} else {
			for (int i = 0; i < size; i++){
				if (content[i] > word.content[i]){
					return 1;
				}
				if (content[i] < word.content[i]) {
					return -1;
				}
			}
		}
		return 0;
	}

	@Override
	public String toString() {
		return "Word[size=" + size + ", content=" + Arrays.toString(content) + "]";
	}

}
