package com.sreader.leksem;

import java.util.ArrayList;

import com.sreader.parsers.parseFb2;


public class Book  extends ArrayList<Chapter>  {

	public ArrayList<String> genres=new ArrayList<String>(); 
	public ArrayList<Author> authors=new ArrayList<Author>();
	private String book_title;
	private String annotation;
	private String date;
	private String format;
	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	private String filename;
	private String filedir;

public ArrayList<String> getGenres() {
		return genres;
	}

	public void setGenres(ArrayList<String> genres) {
		this.genres = genres;
	}

	public ArrayList<Author> getAuthors() {
		return authors;
	}

	public void setAuthors(ArrayList<Author> authors) {
		this.authors = authors;
	}

	public String getBook_title() {
		return book_title;
	}

	public void setBook_title(String book_title) {
		this.book_title = book_title;
	}

	public String getAnnotation() {
		return annotation;
	}

	public void setAnnotation(String annotation) {
		this.annotation = annotation;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

public Book(String setfilename, String setfiledir){
	this.filename=setfilename;
	this.filedir=setfiledir;
	//chapters=new ArrayList<Chapter>();
	runParseFile();
}

private void runParseFile(){
	
		if (filename.endsWith(".fb2")){
			parseFb2.parseFb2File(this,filename,filedir);
		}
	
}

public Chapter getChapter(int id){
	
	return this.get(id);
	
}
}
