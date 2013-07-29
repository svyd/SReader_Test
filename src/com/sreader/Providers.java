package com.sreader;

import android.content.Context;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

import com.sreader.Preferences.MyPreferences;

public abstract class Providers {

	public static OnSeekBarChangeListener getSeekBarChangeListener_SH_O(final Context c){
		
		return new OnSeekBarChangeListener() {
  			
  		    public void onStopTrackingTouch(SeekBar seekBar) {
  		           
  		    }

  		    public void onStartTrackingTouch(SeekBar seekBar) {
  		            //add code here
  		    }

  		    public void onProgressChanged(SeekBar seekBark, int progress, boolean fromUser) {
  		    	(new MyPreferences(c)).setSh_O(progress);
  		    }
  		 };
		
	}
	
	public static OnSeekBarChangeListener getSeekBarChangeListenerTextSize(final Context c, int set_min_size){
		final int min_size=set_min_size;
		return new OnSeekBarChangeListener() {
  			
  		    public void onStopTrackingTouch(SeekBar seekBar) {
  		           
  		    }

  		    public void onStartTrackingTouch(SeekBar seekBar) {
  		            //add code here
  		    }

  		    public void onProgressChanged(SeekBar seekBark, int progress, boolean fromUser) {
  		    	int size=progress+min_size;
  		    	(new MyPreferences(c)).setFontSize(size);
  		    }
  		 };
		
	}
	
}
