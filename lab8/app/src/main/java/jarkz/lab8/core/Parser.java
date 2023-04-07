package jarkz.lab8.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import jarkz.lab8.document.Chapter;
import jarkz.lab8.document.Paragraph;
import jarkz.lab8.document.Text;

/**
 * {@code Parser} can read data from file which follows a specific template.
 * <br>
 * <br>
 * Template must be like:
 * 
 * <pre>
 * [Title]
 *
 * [Description]
 * 
 * [Chapter]
 * [Text]
 * 
 * [Chapter]
 * [Text]
 * ...
 * </pre>
 *
 * If your data not follows this template, parser will be return a incorrect
 * result.
 */
public class Parser {

	private final String resourceName;

	/**
	 * Creates a new {@code Parser} instance with resource name for reading/parsing
	 * data
	 * which follows a specific template.
	 * 
	 * @param resourceName as String with extension from resource folder that will
	 *                     be passed to Stream for parsing data
	 * @throws NullPointerException     resourceName is null or file not found
	 * @throws IllegalArgumentException resourceName is blank or file is empty
	 */
	public Parser(String resourceName) throws IOException {
		if (resourceName == null)
			throw new NullPointerException("resourceName cannot be null");
		if (resourceName.isBlank())
			throw new IllegalArgumentException("resourceName cannot be blank");
		checkResourceName(resourceName);

		this.resourceName = resourceName;
	}

	/**
	 * Uses Streams for reading/parsing data from file. Data text will divide by
	 * chunks and generating the {@link jarkz.lab8.document.Word},
	 * {@link jarkz.lab8.document.Sentence},
	 * {@link jarkz.lab8.document.SubSentence},
	 * {@link jarkz.lab8.document.Paragraph}, {@link jarkz.lab8.document.Chapter}
	 * and {@link jarkz.lab8.document.Text} instances, follows abstraction that
	 * describes in above classes.
	 * 
	 * @return a Text instance when parsed correctly.
	 * @throws IOException some errors in data structure (read about a template in
	 *                     {@code Parser} class documentation)
	 */
	public Text parseResource() throws IOException {
		final String title;
		final String description;
		List<Chapter> chapters = new ArrayList<>();

		ClassLoader classLoader = ClassLoader.getSystemClassLoader();
		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(classLoader.getResourceAsStream(resourceName)))) {

			title = getTitle(reader);
			description = getDescription(reader);
			chapters = readChapters(reader);

		} catch (IOException e) {
			e.printStackTrace();
			throw new IOException("An error occurred in parsing resource.");
		}

		Text text = new Text(title, description);
		chapters.forEach(c -> text.addChapter(c));
		return text;
	}

	/**
	 * @param resourceName as String with extension from resource folder that will
	 *                     be passed to Stream for parsing data
	 * @throws NullPointerException file not found
	 * @throw IllegalArgumentException file is empty
	 */
	private void checkResourceName(String resourceName) throws IOException {
		ClassLoader classLoader = ClassLoader.getSystemClassLoader();
		try (InputStream inputStream = classLoader.getResourceAsStream(resourceName)) {

			if (inputStream == null)
				throw new NullPointerException("resourceName not found!");

			try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
				if (reader.lines().allMatch(l -> l.isBlank()))
					throw new IllegalArgumentException("File is empty!");
			}

		} catch (IOException e) {
			throw new IOException("An error occurred with checking resource.");
		}
	}

	/**
	 * Reades the text chunks named chapters and returns as list of {@link Chapter}
	 * instances.
	 * 
	 * @param reader as BufferedReader stream that will read chapters. Stream must
	 *               be
	 *               not closed or null.
	 * @return a list of Chapter instances from file
	 * @throws IOException          some errors like: line larger than 200 symbols,
	 *                              problems
	 *                              with chapter title or text,
	 *                              stream is closed
	 * @throws NullPointerException BufferedReader is null
	 */
	private List<Chapter> readChapters(BufferedReader reader) throws IOException {
		if (reader == null)
			throw new NullPointerException("BufferedReader is null");
		int number = 1;
		final int maximumForMark = 200;
		List<Chapter> chapters = new ArrayList<>();
		try {
			while (reader.ready()) {
				reader.mark(maximumForMark);
				String currentLine = reader.readLine();
				if (currentLine.isBlank()) {
					continue;
				}
				if (!reader.markSupported()) {
					throw new IOException("Line have total symbols larger than 200 characters.");
				}
				reader.reset();

				chapters.add(readChapter(number++, reader));
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new IOException("An error occurred in reading chapters.");
		}
		return chapters;
	}

	/**
	 * Read one text chunk and divide it by subchunks as named paragraphs
	 * ({@link Paragraph}). After collecting into {@link Chapter} class instance.
	 * 
	 * @param number as int since one
	 * @param reader as BufferedReader stream that will read chapters. Stream must
	 *               be not closed or null.
	 * @return a Chapter that contains the data from chunk
	 * @throws IOException          some errors like: problems
	 *                              with chapter title or text,
	 *                              stream is closed
	 * @throws NullPointerException reader is null
	 */
	private Chapter readChapter(int number, BufferedReader reader) throws IOException {
		if (reader == null)
			throw new NullPointerException("BufferedReader must be not null");
		String title = "";
		List<Paragraph> paragraphs = new ArrayList<>();
		String currentParagraphText = "";
		boolean foundChapterText = false;
		try {
			title = getTitle(reader);
			String bufferText = "";
			while (reader.ready()) {
				String line = reader.readLine();
				boolean isBlank = line.isBlank();
				if (isBlank && !foundChapterText) {
					continue;
				}
				if (isBlank && foundChapterText) {
					break;
				}
				if (!foundChapterText) {
					foundChapterText = true;
				}

				if (containsTrailingHyphen(line)) {
					bufferText += removeTrailingHyphen(line);
				} else if (startsWithWhitespace(line) && !currentParagraphText.isBlank()) {
					currentParagraphText += bufferText;
					paragraphs.add(new Paragraph(currentParagraphText));
					currentParagraphText = line;
					bufferText = "";
				} else {
					currentParagraphText += bufferText + line;
					bufferText = "";
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new IOException("An error occurred in reading chapter.");
		}
		Chapter chapter = new Chapter(number, title);
		paragraphs.forEach(p -> chapter.addParagraph(p));
		return chapter;
	}

	/**
	 * Checking of containing carriage return symbol. It's "\\n".
	 * 
	 * @param line as String
	 * @return `true` if matches carriage return symbol, otherwise `false`
	 */
	private boolean startsWithWhitespace(String line) {
		return line.startsWith(" ") || line.startsWith("\t");
	}

	/**
	 * Reads description from text.
	 *
	 * @param reader as BufferedReader stream that will read chapters. Stream must
	 *               be not closed or null.
	 * @return description as String from data chunk
	 * @throws IOException some errors like: problems with description text, stream
	 *                     is closed
	 */
	private String getDescription(BufferedReader reader) throws IOException {
		String description = "";
		boolean foundDescription = false;
		try {
			String bufferText = "";
			while (reader.ready()) {
				String line = reader.readLine();
				boolean isBlank = line.isBlank();
				if (isBlank && !foundDescription) {
					continue;
				}
				if (isBlank && foundDescription) {
					break;
				}
				if (!foundDescription) {
					foundDescription = true;
				}

				if (containsTrailingHyphen(line)) {
					bufferText += removeTrailingHyphen(line);
				} else {
					description += bufferText + line;
					bufferText = "";
				}
			}
		} catch (IOException e) {
			throw new IOException("An error occurred in reading description.");
		}
		return description;
	}

	/**
	 * Uses when text wraps to new line and have hyphens for it. Checking for
	 * collecting separated lines to single line.
	 * <br>
	 * **Note:** also checking that is not em dash.
	 *
	 * @param line as String
	 * @return `true` if line contains a trailing hyphen, `false` otherwise
	 */
	private boolean containsTrailingHyphen(String line) {
		char[] lineAsChars = line.toCharArray();
		for (int index = lineAsChars.length - 1; index >= 0; index--) {
			if (lineAsChars[index] == '-' && lineAsChars[index - 1] != ' ') {
				return true;
			} else if (lineAsChars[index] == ' ') {
				continue;
			} else {
				return false;
			}
		}
		return false;
	}

	/**
	 * Removing trailing hyphen from line. Use this method only after
	 * {@code containsTrailingHyphen()}.
	 *
	 * @param line as String
	 * @return line without trailing hyphen.
	 */
	private String removeTrailingHyphen(String line) {
		for (int index = line.length() - 1; index >= 0; index--) {
			if (line.charAt(index) == '-') {
				return line.substring(0, index).trim();
			}
		}
		return line;
	}

	/**
	 * @param reader as BufferedReader stream that will read chapters. Stream must
	 *               be not closed or null.
	 * @return title as String from data
	 * @throws IOException some errors like: problems with tile text, stream is
	 *                     closed
	 */
	private String getTitle(BufferedReader reader) throws IOException {
		String title = "";
		try {
			while (reader.ready()) {
				String line = reader.readLine();
				if (line.isBlank()) {
					continue;
				}
				title = line;
				break;
			}
		} catch (IOException e) {
			throw new IOException("An error occurred in reading title.");
		}
		return title;
	}
}
