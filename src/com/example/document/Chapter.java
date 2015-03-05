package com.example.document;

import java.util.ArrayList;
import java.util.List;

public class Chapter {
	private int chapterNumber;
	private List<Page> pages;

	Chapter(int chapterNumber, List<Page> pages) {
		this.chapterNumber = chapterNumber;
		this.pages = pages;
	}

	Chapter() {
		this.pages = new ArrayList<Page>();
	}

	public void addPage(Page page) {
		this.pages.add(page);
	}

	public int numberOfPages() {
		return this.pages.size();
	}

	public void setChapterNumber(int chapterNumber) {
		this.chapterNumber = chapterNumber;
	}

	public int getChapterNumber() {
		return this.chapterNumber;
	}
}
