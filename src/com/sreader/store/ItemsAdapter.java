package com.sreader.store;

import java.io.File;
import java.util.ArrayList;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sreader.util.images;
import com.trinetix.sreader.R;

public class ItemsAdapter extends ArrayAdapter <Item> {

	private ArrayList <Item> items;
	private Context context;
	//public ImageLoader imageLoader;
	//public ImagesDownloader ImagesDownloader;
	private LayoutInflater inflater;

	    
	
	public ItemsAdapter(Context context, int textViewResourceId, ArrayList<Item> items) {
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
		
		
	
		View v = convertView;

					v = inflater.inflate(R.layout.cell_tech_item, null);
					
				
					Log.v("position", position+"");
					Item item = items.get(position);
							
			
				ImageView imageview = (ImageView)v.findViewById(R.id.imageitemtech);
				images.setImageFromFile(context, imageview, item.getImage_url());	
				
				TextView item_format = (TextView)v.findViewById(R.id.item_format);
				item_format.setText("Формат: "+item.getType());
				TextView item_title = (TextView)v.findViewById(R.id.item_title);
				item_title.setText(item.getTitle());
				TextView item_author = (TextView)v.findViewById(R.id.item_author);
				item_author.setText(item.getAuthor());
				TextView item_persent = (TextView)v.findViewById(R.id.item_persent);
				item_persent.setText("Прочитано: "+item.getReadpercent()+" %");
				
			
		return v;
		
	}
	

}
