# Task

Create program of processing text of programming textbook with using classes: Char, Word, Sentence, Paragraph, Lexeme, Listing, Punctuation marksand others. In all tasks with text formation replace tabs and space sequences with one space.

* In all question sentences from text find and print without repeating words with entered size.

# My decision:
## Objects

I make scheme of entities:

`Text -> Chapter -> Paragraph -> Sentence -> Word -> Character`

I will remove last entity `Character`, because productivity drop down with it. In Java, objective variables especially if they are much require a lot of memory, processor and other capacities. But I will add other class (enum), more logically for this task: `PunctuationMark`. This class does have any punctuation marks.

A `Word` class must be simple and useful. I will add below attributes:
- size: final int;
- word: final char[];

Simple, but better for performance. Variables are final because according of the meaning of the Words are unmodifiable.

Next class is `Sentence`. It present:
- words: list of `Word`;
- endMark: `PunctuationMark`;
- subsentences: list of `SubSentence`;
- formula: String;
- containsSubsentence: private boolean;

In this part, I will make new class - `SubSentence`. In some cases it must be useful because no all sentence are simples. Some sentences may be have commas, colon, quote characters (single or double), and other special characters exclusive periods, exclamation marks, question marks. To collect we need a formula field. In this case formula is String with special signs like "%s". I know that we can work without formula and place `SubSentences` step by step, but some cases it make hard algorithm. For example, sentence have quotation and period before last of quotation character. It's like hard for implementing, but with formula it's will be easy. For better algorithm work, we place private field containsSubsentence, it's like switch between two deceisions.

`SubSentence` present:
- words: list of Word;

Simple. I know that I could make inheritance from collection class List. But, in abstraction will look bad. In Java I cannot make inheritance and make all methods from superclass are privates. Therefore class `SubSentence` will contain List class.

`Paragraph` present:
- sentences: list of `Sentence`;

Also simple. I don't know that `Paragraph` else contains. It's like `SubSentence`, but in other abstraction.

`Chapter` present:
- paragraphs: list of `Paragraph`;
- title: String;
- number: int;

In this case `Chapter` have number. It must be incremental value since one. In some texts chapters have title and for it I added title field.

`Text` present:
- chapters: list of `Chapter`;
- title: String;
- description: String;

Text like a book, it must have title and description. Same as previous presentation, it have list of `Chapters`.

## Data parsing

We did dealt with previous objects. Next part are filling or, in other words, data parsing. Filetype must be .txt.
Template:

```txt
[Title]

[Description]

[Chapter]
[Text]

[Chapter]
[Text]
...
```

Important things:
 - empty line is separator between objects which saying Parser class how do it;
 - one line must be not larger than 200 sybmols.
Text will be placed in `/resources` directory.

# Task completion

✅ Completed
