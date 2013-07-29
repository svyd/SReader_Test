package com.sreader.parsers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import net.htmlparser.jericho.CharacterReference;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.Source;

import org.xml.sax.SAXException;

import com.sreader.store.Item;

public abstract class getDataXmlToDb {
	public static void getInfoItem(Item item, String filename, String filedir, String file_nameImg, String dir_nameImg){
		//Item item=new Item();
		File dir = new File(filedir);
		File file = new File(dir,filename); 
		SAXParserFactory factory = SAXParserFactory.newInstance();
	        try {
	        SAXParser parser = factory.newSAXParser();
	        getBinarySax saxp = new getBinarySax(item, file_nameImg, dir_nameImg);

	       
				parser.parse(file, saxp);
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	}
	
	public static void getInfoHtml(Item item, String filename, String filedir){
		File dir = new File(filedir);
		File file = new File(dir,filename);
		Source source = null;
		try {
			source=new Source(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
			net.htmlparser.jericho.Element titleElement=source.getFirstElement(HTMLElementName.TITLE);
			if (titleElement!=null)item.setTitle(CharacterReference.decodeCollapseWhiteSpace(titleElement.getContent()));
		}
		
		
		
		
	
	
	
	public static void getArrayChaptersItem(ArrayList<String> chapters, String filename, String filedir){
		//Item item=new Item();
		File dir = new File(filedir);
		File file = new File(dir,filename); 
		SAXParserFactory factory = SAXParserFactory.newInstance();
	        try {
	        SAXParser parser = factory.newSAXParser();
	        getArrayChaptersSax saxp = new getArrayChaptersSax(chapters);

	       
				parser.parse(file, saxp);
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	}
}
