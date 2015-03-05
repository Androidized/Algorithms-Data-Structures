package com.example.document;

import java.util.ArrayList;
import java.util.List;

public class Book {
	private int isbn;
	private List<Chapter> chapters;

	Book(int isbn, List<Chapter> chapter) {
		this.isbn = isbn;
		this.chapters = chapter;
	}

	Book() {
		this.chapters = new ArrayList<Chapter>();
	}

	public void setIsbn(int isbn) {
		this.isbn = isbn;
	}

	public int getIsbn() {
		return this.isbn;
	}

	public void addChapter(Chapter chapter) {
		this.chapters.add(chapter);
	}

	public int numberOfChapters() {
		return this.chapters.size();
	}
}
