package com.sreader.SearchFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import com.sreader.store.Item;
import com.trinetix.sreader.R;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ItemsFileAdapter extends ArrayAdapter <ItemFile>  {
	private ArrayList <ItemFile> items;
	private Context context;
	//public ImageLoader imageLoader;
	//public ImagesDownloader ImagesDownloader;
	private LayoutInflater inflater;

	private View headerView;
	private String headerText;
	
	
	public ItemsFileAdapter(Context context, int textViewResourceId, ArrayList<ItemFile> items, String header) {
		super(context, textViewResourceId, items);
		this.items = items;
		this.context = context;
		this.headerText = header;
		
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
		
		
	/*	int type = getItemViewType(position);*/
		
		View v = convertView;
		/*if (v == null) {
			
			switch (type) {
				case TYPE_HEADER:
					v = headerView;
					break;
				default:*/
					v = inflater.inflate(R.layout.cell_tech_item, null);
					v.setMinimumHeight(getPixels(110));
					Log.v("position", position+"");
					ItemFile item = items.get(position);
							
				//	TextView tv = (TextView)v.findViewById(R.id.textView_price);
				//	tv.setTypeface(FoxFontsSingleton.getInstance(context).getFoxFontMedium());
					
				//	((TextView)v.findViewById(R.id.textView_itemId)).setTypeface(FoxFontsSingleton.getInstance(context).getFoxFontMedium());
					
		/*	}
		}
		
		

	/*	if (type == TYPE_LIST) {

			Item item = items.get(position-1);
			if (item != null) {
*/  
					InputStream ims;
					Drawable d=null;
					try {
						ims = context.getAssets().open("cover.jpg");
						d = Drawable.createFromStream(ims, null);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	                
	            
				TextView textview = (TextView)v.findViewById(R.id.item_title);
				ImageView imageview = (ImageView)v.findViewById(R.id.imageitemtech);
				imageview.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
				imageview.setImageDrawable(d);
				textview.setText(item.getFile());
				
			//	((TextView)v.findViewById(R.id.textView_itemId)).setText(item.getId());
				//((TextView)v.findViewById(R.id.item_description)).setText(item.getAuthor());
				//((TextView)v.findViewById(R.id.textView_price)).setText(Integer.toString(item.getPrice()));
			
				//imageLoader.DisplayImage(item.getImage_url(), imageview);
				//ImagesDownloader.download(item.getImage_url(), imageview);

				
	/*		}
		} else {

		}*/
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
