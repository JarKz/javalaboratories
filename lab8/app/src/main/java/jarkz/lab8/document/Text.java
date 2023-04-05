package jarkz.lab8.document;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * {@code Text} have the container of the {@code Chapter} instances, description
 * and title.
 * <br>
 * <br>
 * Since the texts may be largest, this class don't have a constructor that get
 * text as String. Use the access methods (e.g. {@code addChapter(String)} or
 * {@code removeChapter(String)}).
 * <br>
 * <br>
 * Usage:
 * 
 * <pre class="code">
 * String someText = "....";
 * String anotherSomeText = "....";
 * int number = 1;
 * Chapter firstChapter = new Chapter(number++, "First Chapter", someText);
 * Chapter secondChapter = new Chapter(number++, "Second Chapter", anotherSomeText);
 * Text text = new Text("Text title");
 * Text.addChapter(firstChapter);
 * Text.addChapter(secondChapter);
 * ...
 * </pre>
 */
public class Text {

	private String title;
	private String description;
	private List<Chapter> chapters = new ArrayList<>();

	/**
	 * Creates the Text instance with entered title. For adding chapters use method
	 * {@code addChapter} because entered text may be large and will make low
	 * performance.
	 *
	 * @param title as String
	 *
	 * @throws NullPointerException     title is null
	 * @throws IllegalArgumentException title is blank
	 */
	public Text(String title) {
		setTitle(title);
	}

	/**
	 * Creates the Text instance with entered title. For adding chapters use method
	 * {@code addChapter} because entered text may be large and will make low
	 * performance.
	 *
	 * @param title       as String
	 * @param description as String
	 *
	 * @throws NullPointerException     title or description is null
	 * @throws IllegalArgumentException title or description is blank
	 */
	public Text(String title, String description) {
		setTitle(title);
		setDescription(description);
	}

	/**
	 * Returns a copy of {@code Text} instance title.
	 *
	 * @return title as String
	 */
	public String getTitle() {
		return new String(title);
	}

	/**
	 * Set the title for current {@code Text} instance.
	 *
	 * @param title as String
	 *
	 * @throws NullPointerException     title is null
	 * @throws IllegalArgumentException title is blank
	 */
	public void setTitle(String title) {
		if (title == null)
			throw new NullPointerException("Title cannot be null");
		if (title.isBlank())
			throw new IllegalArgumentException("Title cannot be empty or blank");

		this.title = title;
	}

	/**
	 * Return a copy of container.
	 *
	 * @return a copy of {@Chapter} instances container
	 */
	public List<Chapter> getChapters() {
		return new ArrayList<>(chapters);
	}

	/**
	 * Adding new chapter to end of the container.
	 *
	 * @param chapter as {@code Chapter} instance
	 *
	 * @throws NullPointerException chapter is null
	 */
	public void addChapter(Chapter chapter) {
		if (chapter == null)
			throw new NullPointerException("Chapter instance cannot be null");

		chapters.add(chapter);
	}

	/**
	 * Removes matched {@code Chapter} instance from the container.
	 *
	 * @param chapter as {@code Chapter} instance
	 *
	 * @throws NullPointerException chapter is null
	 */
	public void removeChapter(Chapter chapter) {
		if (chapter == null)
			throw new NullPointerException("Chapter instance cannot be null");

		chapters.remove(chapter);
	}

	/**
	 * Returns the Stream of String because text may be large. I advise get only
	 * chunks step by step.
	 *
	 * @return text stream of String
	 */
	public Stream<String> getString() {
		return chapters.stream().map(Chapter::getString);
	}

	/**
	 * Return copy of String instance description.
	 * 
	 * @return a copy of description
	 */
	public String getDescription() {
		return new String(description);
	}

	private void setDescription(String description) {
		if (description == null)
			throw new NullPointerException("description is null");
		if (description.isBlank())
			throw new IllegalArgumentException("description is blank");

		this.description = description;
	}
}
