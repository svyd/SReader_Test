package com.sreader.util;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import android.widget.TextView;

import com.sreader.store.Item;

public abstract  class Utils {
	
	public static void sortarray (ArrayList<Item>  c, int sort)
	{
    final int i=sort;
    Collections.sort(c, new Comparator(){

		public int compare(Object lhs, Object rhs) {
			// TODO Auto-generated method stub
			int res_comp=0;
			Item p1 = (Item) lhs;
        	Item p2 = (Item) rhs;
        	switch(i){
        	case 0:
        		res_comp=p1.getTitle().compareTo(p2.getTitle());
        	break;
        	case 1:
        		res_comp=p1.getAuthor().compareTo(p2.getAuthor());
        	break;
        	case 2:
        		res_comp=p1.getGenre().compareTo(p2.getGenre());
        	break;
        	case 5:
        		res_comp=p1.getId().compareTo(p2.getId());
        	break;
        	}
        	
           return res_comp;
			//return p1.getTitle().compareTo(p2.getTitle());
		}

    });

}
	
	public static String getCurrentDate(){
		Date date=new Date();
		long cur_time=date.getTime();
			return String.valueOf(cur_time);
	}
	
	 public static String getTimeDate(long milliSeconds, String dateFormat)
	    {
		 // Create a DateFormatter object for displaying date in specified format.
		    DateFormat formatter = new SimpleDateFormat(dateFormat);

		    // Create a calendar object that will convert the date and time value in milliseconds to date. 
		     Calendar calendar = Calendar.getInstance();
		     calendar.setTimeInMillis(milliSeconds);
		     return formatter.format(calendar.getTime());
	    }
	 
	 public static boolean getTextWidth(TextView textview, String text, int widthdisplay){
			Rect bounds = new Rect();
			Paint textPaint = textview.getPaint();
			textPaint.getTextBounds(text,0,text.length(),bounds);
			int width = bounds.width();
			if (width<widthdisplay)	 return false;
		    
		    return true;
		
		}
	 
	 public static int getWidthDisplay(Context c){
		
		 Display display = ((WindowManager) c.getSystemService(c.WINDOW_SERVICE)).getDefaultDisplay();
		 return display.getWidth();
	 }
}
