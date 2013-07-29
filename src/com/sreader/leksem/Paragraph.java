package com.sreader.leksem;

import java.util.ArrayList;
import java.util.StringTokenizer;


public class Paragraph {
	private ArrayList<Sentence> sentences;
	
	public Paragraph(String text){
		sentences=new ArrayList<Sentence>();
		StringTokenizer tokens=null;
		String strToPrint = null;
		tokens=new StringTokenizer(text, ".");
		while(tokens.hasMoreTokens()){
			strToPrint=tokens.nextToken();
			sentences.add(new Sentence(strToPrint));	
		}
		
	}

	public String getParagraphText(){
		String res = "";
		for (Sentence cur:sentences) res+=cur.getSentenceText();
		return res;
	}
	public ArrayList<Sentence> getSentences(){
		return sentences;
	}
}
