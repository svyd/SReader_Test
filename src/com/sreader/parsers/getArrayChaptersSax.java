package com.sreader.parsers;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.sreader.store.Item;
import com.sreader.util.images;

public class getArrayChaptersSax  extends DefaultHandler {
String  ElementName="";
private ArrayList<String> chapters=new ArrayList<String>();
private StringBuilder ch_str= new StringBuilder();
boolean descript=false;
boolean sect=false;


public getArrayChaptersSax(ArrayList<String> set_chapters){
	this.chapters=set_chapters;
	
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
	   	  	sect=true;
			descript=false;
		}
			}
		
	
	
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		if ((sect) && (!descript)){
			//if (ElementName.equalsIgnoreCase("title")) 
			if (ElementName.equalsIgnoreCase("p") || ElementName.equalsIgnoreCase("i")) ch_str.append(new String(ch,start,length)+"\n");
			
		}
	 	}
	
	@Override
	public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
		
		if (qName.equalsIgnoreCase("title") && sect){
			chapters.add(ch_str.toString());
			ch_str=new StringBuilder();
		}
		
	}
	
	@Override
	public void endDocument() {
	  System.out.println("Stop parse XML...");
	  //item.setImage_url(images.getNameFileSave(dirImages, fileImages, images.getBitmapFromBinar(str_binary)));
	  
	}
	
}


