package com.sreader.Preferences;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class MyPreferences {
	private Context c;
	SharedPreferences Preference;
	
	public MyPreferences(Context context){
		this.c=context;
		this.Preference=c.getSharedPreferences("Preference", Activity.MODE_PRIVATE);
	}
	
	
	
	public void setFontTypeface(int id_typeface){
		Editor ed=this.Preference.edit();
		ed.putInt("typeface", id_typeface);
		ed.commit();
	}
	
	public int getFontTypeface(){
		return this.Preference.getInt("typeface", 0);
	}
	
	public void setFontSize(int size){
		Editor ed=this.Preference.edit();
		ed.putInt("size", size);
		ed.commit();
		
	}
	

   public int getColorSheme(){
	return this.Preference.getInt("ColorSheme", 0);
}
   
	public void setColorSheme(int ColorSheme){
		Editor ed=this.Preference.edit();
		ed.putInt("ColorSheme", ColorSheme);
		ed.commit();
		
	}
	

   public int getFontSize(){
	return this.Preference.getInt("size", 0);
}
   
	public void setSh_O(int sh_o){
		Editor ed=this.Preference.edit();
		ed.putInt("sh_o", sh_o);
		ed.commit();
		
	}
	

   public int getSh_O(){
	return this.Preference.getInt("sh_o", 0);
}
   
	public void setSpeedText(int speedText){
		Editor ed=this.Preference.edit();
		ed.putInt("speedText", speedText);
		ed.commit();
		
	}
	
	 public int getSpeedText(){
			return this.Preference.getInt("speedText", 0);
		}

	 public void setDefaultPreferences(){
		    Editor ed=this.Preference.edit();
			ed.putInt("speedText", Constants.DEFAULT_SPEED_TEXT);
			ed.putInt("size", Constants.DEFAULT_SIZE_TEXT);
			ed.putInt("typeface", Constants.DEFAULT_TYPEFACE_TEXT);
			ed.putInt("ColorSheme", Constants.DEFAULT_COLOR_SHEME);
			
			ed.commit();
	 }
}
