package jarkz.lab8.core;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jarkz.lab8.document.Chapter;
import jarkz.lab8.document.PunctuationMark;
import jarkz.lab8.document.Sentence;
import jarkz.lab8.document.SubSentence;
import jarkz.lab8.document.Text;
import jarkz.lab8.document.Word;

public class Worker {

	private static final String QUESTION_MARK_UNIVERSAL_PATTERN = "(.*)?" + PunctuationMark.QUESTION.getPattern()
			+ "(.*)?";
	private final Set<Word> words = new ConcurrentSkipListSet<>();

	private final Text text;
	private final Logger logger = Logger.getLogger("WORKER");

	public Worker(Text text) {
		if (text == null) {
			throw new NullPointerException("Text instance cannot be null");
		}
		this.text = text;
	}

	public void printWordsWithSize(int size) {
		List<Chapter> chapters = text.getChapters();
		chapters.parallelStream()
				.forEach(c -> c.getParagraphs()
						.parallelStream()
						.forEach(p -> p.getSentences()
								.parallelStream()
								.filter(Worker::filterByQuestionMark)
								.map(Worker::getWordsFromSentenceWithQuestionMark)
								.forEach(w -> w.parallelStream()
										.filter(word -> word.size() == size)
										.forEach(filterdWord -> {
											if (words.add(filterdWord)) {
												logger.info("PASSED WORD:" + filterdWord.getContent());
											}
										}))));
		logger.info("RESULT WORDS: " + words.toString());
	}

	private static boolean filterByQuestionMark(Sentence sentence) {
		if (sentence.containsSubsentence()) {
			return sentence.getFormula().matches(QUESTION_MARK_UNIVERSAL_PATTERN);
		} else if (sentence.getEndMark() == PunctuationMark.QUESTION) {
			return true;
		}
		return false;
	}

	private static List<Word> getWordsFromSentenceWithQuestionMark(Sentence sentence) {
		if (sentence.containsSubsentence()) {
			String[] splittedFormula = sentence.getFormula().split(PunctuationMark.QUESTION.getPattern());

			int countSubsentences = 0;
			Matcher matcher = Pattern.compile("%s").matcher(splittedFormula[0]);
			while (matcher.find()) {
				countSubsentences++;
			}

			Optional<List<SubSentence>> probableSubsentences = sentence.getSubSentencesInRange(0, countSubsentences);
			if (probableSubsentences.isPresent()) {
				List<SubSentence> subsentences = probableSubsentences.get();
				int count = 1;
				Collections.reverse(subsentences);
				for (SubSentence subsentence : subsentences) {
					String subsentenceString = subsentence.getString().trim();
					char firstChar = subsentenceString.charAt(0);
					if (Character.isUpperCase(firstChar)) {
						break;
					}
					count++;
				}
				Collections.reverse(subsentences);
				int size = subsentences.size();
				subsentences = subsentences.subList(size - count, size);
				return subsentences.stream().flatMap(s -> s.getWords().stream()).toList();
			}
		} else {
			return sentence.getWords();
		}
		return null;
	}
}
