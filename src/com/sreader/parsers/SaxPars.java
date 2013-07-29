package com.sreader.parsers;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.sreader.leksem.Author;
import com.sreader.leksem.Book;
import com.sreader.leksem.Chapter;

public class SaxPars  extends DefaultHandler {
String  ElementName="";
private Book book;
private Section section;
private Author authors=new Author();
int id_chapter=0; 
int id_next_chapter=1;
private StringBuilder ch_str= new StringBuilder();
boolean descript=false;
boolean sect=false;


public SaxPars(Book books){
	this.book=books;
	}
	
	@Override
	public void startDocument() throws SAXException {
	 // System.out.println("Start parse XML...");
	}
	
	@Override
	public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
		ElementName=qName;
	
		if (qName.equalsIgnoreCase("description")) descript=true;
		if (qName.equalsIgnoreCase("section")) {
			//id_chapter++;
			ch_str=new StringBuilder();
	   	    section=new Section();
		
			sect=true;
			descript=false;
		}
		

	 
	}
	
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
	 
		String readChars = new String(ch,start,length);
		
		if (descript){
			if (ElementName.equalsIgnoreCase("genre")) book.genres.add(new String(ch,start,length));
			if (ElementName.equalsIgnoreCase("first-name")) authors.setFirst_name(new String(ch,start,length));
			if (ElementName.equalsIgnoreCase("last-name")) authors.setLast_name(new String(ch,start,length));
			if (ElementName.equalsIgnoreCase("book-title")) book.setBook_title(new String(ch,start,length));
			if (ElementName.equalsIgnoreCase("p") || ElementName.equalsIgnoreCase("i")) ch_str.append(new String(ch,start,length)+"\n");
			
		}
		
		if ((sect) && (!descript)){
			//if (ElementName.equalsIgnoreCase("title")) 
			if (ElementName.equalsIgnoreCase("p") || ElementName.equalsIgnoreCase("i")) ch_str.append(new String(ch,start,length)+"\n");
			
		}
		
		
		
	
	 	}
	
	@Override
	public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
		
		if (qName.equalsIgnoreCase("title") && sect){
			section.setTitle(ch_str.toString());
			ch_str=new StringBuilder();
		}
		
	if (qName.equalsIgnoreCase("section")){
		section.setDescription(ch_str.toString());
		book.add(new Chapter(section.getTitle(), section.getDescription()));
		ch_str=new StringBuilder();
		
	}
	
	if (descript){
		
		if (qName.equalsIgnoreCase("annotation"))
		{
		book.setAnnotation(ch_str.toString()+"");
		ch_str=new StringBuilder();
		}
		
		if (qName.equalsIgnoreCase("author"))
		{
		book.authors.add(authors);
		authors=new Author();
		}
	}
	
	
		ElementName = "";
		
	}
	
	@Override
	public void endDocument() {
	  System.out.println("Stop parse XML...");
	}
}

class Section{
	private String title;
	private String description;
	
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	
	
}
