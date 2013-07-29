package com.sreader.parsers;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import com.sreader.leksem.Book;

public class parseFb2 {
	public static void parseFb2File(Book book, String filename, String filedir){
		File dir = new File(filedir);
		File file = new File(dir,filename); 
		SAXParserFactory factory = SAXParserFactory.newInstance();
	        try {
	        SAXParser parser = factory.newSAXParser();
	        SaxPars saxp = new SaxPars(book);

	       
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
