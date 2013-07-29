package com.sreader.leksem;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class Chapter {
	private String name;
	private ArrayList<String> words;
	
	
	public Chapter(String setname, String settext){
		this.name=setname;
		words=new ArrayList<String>(); 
		String text=settext.replace("\n", " ");
		StringTokenizer tokens=null;
		String stradd="";
		tokens=new StringTokenizer(text, " ");
		while(tokens.hasMoreTokens()){
			stradd=tokens.nextToken();
			if ((stradd.length()<2) && (tokens.hasMoreTokens())) stradd+=" "+tokens.nextToken();  
			words.add(stradd);
			}
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public String getChapterText(){
		String res = "";
		for (String cur:words) res+=cur;
		return res;
	}
	
	public String getWord(int id){
		return words.get(id);
	}
public ArrayList<String> getWords(){
	return words;
}
}
