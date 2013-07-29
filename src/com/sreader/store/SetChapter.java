package com.sreader.store;

public class SetChapter {

	private int id_chapter;
	private int id_word;
	private int col_words;
	
	public SetChapter(int set_id_chapter, int set_id_word, int set_col_words){
		this.id_chapter=set_id_chapter;
		this.id_word=set_id_word;
		this.col_words=set_col_words;
			
	}

	public int getId_chapter() {
		return id_chapter;
	}

	public void setId_chapter(int id_chapter) {
		this.id_chapter = id_chapter;
	}

	public int getId_word() {
		return id_word;
	}

	public void setId_word(int id_word) {
		this.id_word = id_word;
	}
	public int getcol_words() {
		return col_words;
	}

	public void setcol_words(int col_words) {
		this.col_words = col_words;
	}
}
