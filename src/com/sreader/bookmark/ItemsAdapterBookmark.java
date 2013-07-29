package com.sreader.bookmark;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sreader.util.Utils;
import com.sreader.util.images;
import com.trinetix.sreader.R;

public class ItemsAdapterBookmark extends ArrayAdapter <ItemBookmark> {

	private ArrayList <ItemBookmark> items;
	private Context context;
	//public ImageLoader imageLoader;
	//public ImagesDownloader ImagesDownloader;
	private LayoutInflater inflater;

	    
	
	public ItemsAdapterBookmark(Context context, int textViewResourceId, ArrayList<ItemBookmark> items) {
		super(context, textViewResourceId, items);
		this.items = items;
		this.context = context;
	
		
		//imageLoader=new ImageLoader(context);
	//	ImagesDownloader=new ImagesDownloader(context);
		/*
		for (int i = 0; i<items.size(); i++) {
			imageLoader.PushToDownloadQueue(items.get(i).getImage_url());
		}*/		
		inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        
     /*   headerView = inflater.inflate(R.layout.tech_header, null);
        TextView htext = (TextView)headerView.findViewById(R.id.textHeaderView);
        htext.setText(headerText);
        htext.setTypeface(FoxFontsSingleton.getInstance(context).getFoxFontMedium());*/
        
        
	}
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		
		//int type = getItemViewType(position);
		
		View v = convertView;
		/*if (v == null) {
			
			switch (type) {
				case TYPE_HEADER:
					v = headerView;
					break;
				default:*/
					v = inflater.inflate(R.layout.cell_bookmark_item, null);
					//v.setMinimumHeight(getPixels(110));
					Log.v("position", position+"");
					ItemBookmark item = items.get(position);
		
				TextView item_title = (TextView)v.findViewById(R.id.item_title);
				item_title.setText(item.getTitle());
				TextView item_phrase = (TextView)v.findViewById(R.id.item_phrase);
				item_phrase.setText(item.getPhrase());
				TextView item_date = (TextView)v.findViewById(R.id.item_date);
			//	Date dte=Date.item.getId());
				//long l_n_g=Long.valueOf(item.getId());
				
				item_date.setText(Utils.getTimeDate(Long.valueOf(item.getId()), "HH:mm dd MMM yyyy"));
		
				
				
				
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
