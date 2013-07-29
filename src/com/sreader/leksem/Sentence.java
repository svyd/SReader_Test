package com.sreader.leksem;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class Sentence {
private ArrayList<String> words;
StringTokenizer tokens=null;
public Sentence(String text){
	words=new ArrayList<String>();
	
	String strToPrint = null;
	tokens=new StringTokenizer(text+".", " ");
	while(tokens.hasMoreTokens()){
		strToPrint=tokens.nextToken();
		if ((strToPrint.length()<4) && (tokens.hasMoreTokens())) strToPrint+=" "+tokens.nextToken();
		words.add(strToPrint);
	}
	
}

public String getSentenceText(){
	String res = "";
	for (String cur:words) res+=" "+cur;
	return res;
}

public ArrayList<String> getWords(){
	return words;
}
}
