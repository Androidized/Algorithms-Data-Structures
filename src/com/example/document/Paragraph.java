package com.example.document;

import java.util.ArrayList;
import java.util.List;

public class Paragraph {
	private List<StringBuilder> paragraphText;

	Paragraph(List<StringBuilder> paragraphText) {
		this.paragraphText = paragraphText;
	}

	Paragraph() {
		this.paragraphText = new ArrayList<StringBuilder>();
	}

	public void setParagraph(List<StringBuilder> paragraphText) {
		this.paragraphText = paragraphText;
	}

	public List<StringBuilder> getSentences() {
		return this.paragraphText;
	}

	public void addSentence(StringBuilder sentence) {
		this.paragraphText.add(sentence);
	}

	public int numberOfSentences() {
		if (this.paragraphText != null)
			return this.paragraphText.size();
		else return 0;
	}

	public int numberOfLines(int maxNumberOfLettersPerLine) {
		int numberOfLetters = 0;
		for (StringBuilder str : paragraphText)
			numberOfLetters += str.length();
		return (int) (numberOfLetters / maxNumberOfLettersPerLine);
	}
}
