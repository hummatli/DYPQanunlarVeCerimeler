package com.mobapphome.avtolowpenal.other;


public class ALChapterArticle {
	private ALArticle article;
	private ALChapter chapter;
	
	public ALChapterArticle(ALArticle article, ALChapter chapter) {
		this.article = article;
		this.chapter = chapter;
	}

	public ALArticle getArticle() {
		return article;
	}

	public ALChapter getChapter() {
		return chapter;
	}
	
}
