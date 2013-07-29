package com.sreader.parsers;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.sreader.store.Item;
import com.sreader.util.images;

public class getBinarySax  extends DefaultHandler {
String  ElementName="";
private ArrayList<String> arr_binar=new ArrayList<String>();
String readChars;
boolean binary=false;
boolean title_info=false;
boolean author_tag=false;
boolean annotation_tag=false;
boolean translator=false;
boolean publish_info=false;
public Item item;
private Author author=new Author();
private StringBuilder ch_str= new StringBuilder();
private String dirImages;
private String fileImages;
String str_binary="";



public getBinarySax(Item set_item, String file_nameImg, String dir_nameImg){
	this.item=set_item;
	this.fileImages=file_nameImg;
	this.dirImages=dir_nameImg;
	}
	
	@Override
	public void startDocument() throws SAXException {
	 // System.out.println("Start parse XML...");
	}
	
	@Override
	public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
		ElementName=qName;
		if (qName.equalsIgnoreCase("title-info")) title_info=true;
		if (qName.equalsIgnoreCase("translator")) translator=true;
		if (qName.equalsIgnoreCase("binary")) binary=true;
		if (qName.equalsIgnoreCase("publish-info")) publish_info=true;
		
	/*	if (qName.equalsIgnoreCase("author")) { author_tag=true;
		author=new Author();
		}
		*/
		if (qName.equalsIgnoreCase("annotation")) {
			annotation_tag=true;
			
			ch_str=new StringBuilder();
	   	/*   	author_tag=false;*/		}
	
	/*	if (qName.equalsIgnoreCase("binary")) binary=true;*/
			}
		
	
	
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
	    readChars = new String(ch,start,length);
	if ((title_info) && (!(translator))){
		if (ElementName.equalsIgnoreCase("book-title")) item.setTitle(new String(ch,start,length));
	    
		//if (author_tag) {
	       if (ElementName.equalsIgnoreCase("first-name")) item.setAuthor(new String(ch,start,length));
	       if (ElementName.equalsIgnoreCase("last-name"))  item.setAuthor(item.getAuthor()+" "+new String(ch,start,length))/*author.setlast_name(new String(ch,start,length))*/;
	       if (ElementName.equalsIgnoreCase("genre")) item.setGenre(new String(ch,start,length));
	       if (ElementName.equalsIgnoreCase("lang")) item.setLang(new String(ch,start,length));
	       
	   // }
	    if (annotation_tag) if (ElementName.equalsIgnoreCase("p") || ElementName.equalsIgnoreCase("i")) ch_str.append(new String(ch,start,length)+"\n");
	    }
	if (publish_info){
		if (ElementName.equalsIgnoreCase("publisher")) item.setPublisher(new String(ch,start,length)); 
		
		if (ElementName.equalsIgnoreCase("last-name")) if  (item.getPublisher()==null || item.getPublisher().length()==0) item.setPublisher(new String(ch,start,length));
	}
	 if (binary) if (ElementName.equalsIgnoreCase("binary")) ch_str.append(new String(ch,start,length));
	 	}
	
	@Override
	public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
		if (qName.equalsIgnoreCase("publish-info")) publish_info=false; 
		if (title_info){
			if (qName.equalsIgnoreCase("title-info")) title_info=false;
			
			if (qName.equalsIgnoreCase("annotation"))
			{
				item.setAnotation(ch_str.toString());
				ch_str= new StringBuilder();
				annotation_tag=false;
			}
		
	/*if (qName.equalsIgnoreCase("author"))
			{
		        String first_name=author.getfirst_name();
		        String last_name=author.getlast_name();
				item.setAuthor(first_name+" "+last_name);
				author=new Author();
				author_tag=false;
			}*/
		}
		
		if (binary) if (qName.equalsIgnoreCase("binary"))
			{
			if (str_binary.length()==0) {
				str_binary=ch_str.toString();
				images.getNameFileSave(dirImages, fileImages, images.getBitmapFromBinar(str_binary));
				item.setImage_url(dirImages+"/"+fileImages);
			}
			
				ch_str= new StringBuilder();
				binary=false;
			}
	/* if (binary) if (qName.equalsIgnoreCase("binary")) {
		 arr_binar.add(readChars);
		 binary=false;
	 }
		*/
	
		ElementName = "";
		
	}
	
	@Override
	public void endDocument() {
	  System.out.println("Stop parse XML...");
	  //item.setImage_url(images.getNameFileSave(dirImages, fileImages, images.getBitmapFromBinar(str_binary)));
	  
	}
	
}

class Author{
	private String first_name;
	private String last_name;
	
	
	public String getfirst_name() {
		return first_name;
	}

	public void setfirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getlast_name() {
		return last_name;
	}

	public void setlast_name(String last_name) {
		this.last_name = last_name;
	}
}

