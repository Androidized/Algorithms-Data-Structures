package com.example.document;

import java.util.List;

public class Page {
	int maxNumberOfLines;
	int maxNumberOfLettersPerLine;
	List<Paragraph> paragraphs;

	Page(int maxNumberOfLines, int maxNumberOfLettersPerLine) {
		this.maxNumberOfLines = maxNumberOfLines;
		this.maxNumberOfLettersPerLine = maxNumberOfLettersPerLine;
	}

	public Paragraph addParagraph(Paragraph paragraph) {
		Paragraph paragraphToAdd = null;
		Paragraph paragraphToReturn = null;
		int currentNumberOfLines = 0;
		for (Paragraph p : paragraphs)
			currentNumberOfLines += p.numberOfLines(maxNumberOfLettersPerLine);
		if (currentNumberOfLines +
				paragraph.numberOfLines(maxNumberOfLettersPerLine)
				< maxNumberOfLines) {
			paragraphs.add(paragraph);
		} else {
			boolean ignoreNextSentences = false;
			paragraphToAdd = new Paragraph();
			paragraphToReturn = new Paragraph();
			for (StringBuilder sentence : paragraph.getSentences()) {
				if (!ignoreNextSentences && currentNumberOfLines +
						(sentence.length() / maxNumberOfLettersPerLine)
						< maxNumberOfLines) {
					paragraphToAdd.addSentence(sentence);
					currentNumberOfLines +=
							(sentence.length() / maxNumberOfLettersPerLine);
				} else {
					ignoreNextSentences = true;
					paragraphToReturn.addSentence(sentence);
				}
			}
			if (paragraphToAdd.numberOfSentences() != 0)
				paragraphs.add(paragraphToAdd);
		}
		return paragraphToReturn;
	}
}
