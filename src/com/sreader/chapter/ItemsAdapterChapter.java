package com.sreader.chapter;

import java.util.ArrayList;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.trinetix.sreader.R;

public class ItemsAdapterChapter extends ArrayAdapter <String> {

	private ArrayList <String> items;
	private Context context;

	private LayoutInflater inflater;

	    
	
	public ItemsAdapterChapter(Context context, int textViewResourceId, ArrayList<String> items) {
		super(context, textViewResourceId, items);
		this.items = items;
		this.context = context;
	
		
		inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        
        
	}
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		
			
		View v = convertView;
		
					v = inflater.inflate(R.layout.cell_chapter_item, null);
					//v.setMinimumHeight(getPixels(50));
			
				TextView item_title = (TextView)v.findViewById(R.id.item_title);
				item_title.setText(items.get(position).substring(0, items.get(position).indexOf("\n")));
				
		return v;
		
	}
	
///////////преобразование dp в px для установки параметров///////////////
	
	private int getPixels(int dipValue){
      Resources r = context.getResources();
      int px = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, 
      r.getDisplayMetrics());
      return px;
}
}
