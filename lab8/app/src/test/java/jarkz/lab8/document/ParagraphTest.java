package jarkz.lab8.document;

import org.junit.Assert;
import org.junit.Test;

public class ParagraphTest {

	@Test
	public void testGetStringParagraph() {
		String paragraphText = "The rain fell steadily outside, tapping against the windowpane with a soft rhythm. Inside, the cozy living room was warm and inviting, filled with soft lighting and comfortable furniture. A book lay open on the coffee table, waiting to be read. The gentle flicker of a scented candle added to the relaxed ambiance. It was the perfect evening to curl up with a good book and let the world fade away.";
		Paragraph paragraph = new Paragraph(paragraphText);
		Assert.assertEquals(paragraphText, paragraph.getString());
	}
}
