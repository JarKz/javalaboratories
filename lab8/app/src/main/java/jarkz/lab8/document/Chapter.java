package jarkz.lab8.document;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * {@code Chapter} contains a container of {@code Paragraph} instances, title
 * and
 * number.
 * <br>
 * Number must be incremental since one.
 * <br>
 * Usage:
 * 
 * <pre class="code">
 * String someString = "Lorem ipsum dolor sit amet, consectetur adipiscing elit," 
 * 	+ "sed diam nonumy eirmod tempor incididunt ut labore et dolore magna aliqu."
 * Chapter chapter = new Chapter(someString, "Lorem", 1);
 * Assert.assertEquals(someString, chapter.getString());
 * </pre>
 */
public class Chapter {

	private int number;
	private String title;
	private List<Paragraph> paragraphs = new ArrayList<>();

	/**
	 * Creates {@code Chapter} instance with title, number and empty container of
	 * paragraphs.
	 * <br>
	 * <br>
	 * Use it for passing large text by chunks using {@code Paragraph} instances.
	 *
	 * @param number as int since one
	 * @param title  as String, not blank
	 *
	 * @throws NullPointerException     title or chapterText is null
	 * @throws IllegalArgumentException number less than one, or title is blank, or
	 *                                  chapterText is blank
	 */
	public Chapter(int number, String title) {
		setNumber(number);
		setTitle(title);
	}

	/**
	 * Creates {@code Chapter} instance with title, number and container of
	 * {@code Paragraph} instances.
	 * <br>
	 * <br>
	 * The param chapterText may be have a special character "\\n" and if having
	 * then
	 * text will splits by this character.
	 * 
	 * @param number      as int since one
	 * @param title       as String, not blank
	 * @param chapterText as String
	 *
	 * @throws NullPointerException     title or chapterText is null
	 * @throws IllegalArgumentException number less than one, or title is blank, or
	 *                                  chapterText is blank
	 */
	public Chapter(int number, String title, String chapterText) {
		setNumber(number);
		setTitle(title);

		if (chapterText == null)
			throw new NullPointerException("String instance cannot be null");
		if (chapterText.isBlank())
			throw new IllegalArgumentException("String instance cannot be empty or blank");

		String[] paragraphsText = chapterText.split("\n");
		for (String paragraphText : paragraphsText) {
			paragraphText = paragraphText.trim();
			Paragraph paragraph = new Paragraph(paragraphText);
			paragraphs.add(paragraph);
		}
	}

	/**
	 * Returns a number of the current chapter.
	 * 
	 * @return number as int
	 */
	public int getNumber() {
		return number;
	}

	/**
	 * Requires the number that greater than number of previous chapter and must be
	 * since one.
	 * 
	 * @param number as int
	 */
	public void setNumber(int number) {
		if (number <= 0)
			throw new IllegalArgumentException("Number must be positive");
		this.number = number;
	}

	/**
	 * Returns a copy of the title String instance.
	 * 
	 * @return title of the {@code Chapter} as String
	 */
	public String getTitle() {
		return new String(title);
	}

	/**
	 * Set current title of the {@code Chapter}.
	 * 
	 * @param title as String
	 * @throws NullPointerException     title is null
	 * @throws IllegalArgumentException title is blank
	 */
	public void setTitle(String title) {
		if (title == null)
			throw new NullPointerException("String instance cannot be null");
		if (title.isBlank())
			throw new IllegalArgumentException("String instance cannot be empty or blank");

		this.title = title;
	}

	/**
	 * Returns a copy of the {@code Paragraph} instances container.
	 * 
	 * @return a copy of {@code Paragraph} instances container.
	 */
	public List<Paragraph> getParagraphs() {
		return new ArrayList<>(paragraphs);
	}

	/**
	 * Adding new {@code Paragraph} instance to end of container.
	 * 
	 * @param paragraph as {@code Paragraph} instance
	 */
	public void addParagraph(Paragraph paragraph) {
		if (paragraph == null)
			throw new NullPointerException("Paragraph instance cannot be null");
		paragraphs.add(paragraph);
	}

	/**
	 * Removes matched {@code Paragraph} instance from container.
	 * 
	 * @param paragraph as {@code Paragraph} instance
	 */
	public void removeParagraph(Paragraph paragraph) {
		if (paragraph == null)
			throw new NullPointerException("Paragraph instance cannot be null.");
		paragraphs.remove(paragraph);
	}

	/**
	 * Collecting the text from {@code Paragraph} instances joining by special
	 * character "\\n".
	 * <br>
	 * Note: the returned text will be without any tab characters.
	 * 
	 * @return text of {@code Chapter} as String
	 */
	public String getString() {
		return paragraphs.stream().map(Paragraph::getString).collect(Collectors.joining("\n"));
	}
}
