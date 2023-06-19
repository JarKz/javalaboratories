package jarkz.lab8.document;

import org.junit.Assert;
import org.junit.Test;

public class SentenceTest {

	@Test
	public void testStrignEquals() {
		String testText = "\"Rural juror\" is a difficult :'phrase due to its' alliteration and the fact that the combination of \"rural\" and \"juror\" is not commonly used in everyday language.";
		Sentence sentence = new Sentence(testText);
		Assert.assertEquals(testText, sentence.getString());
	}

	@Test
	public void testFormulaEquals(){
		String sentenceText = "Lorem ipsum dolor sit amet, consectetur adip...";
		Sentence sentence = new Sentence(sentenceText);
		Assert.assertEquals(sentence.getFormula(), "%s, %s...");
	}
}
