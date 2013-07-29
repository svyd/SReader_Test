package com.sreader.visibleAllText;

import java.util.ArrayList;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Html;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.trinetix.sreader.R;
public class MyCustomAdapter  extends BaseAdapter {
	 Context c;
	 public int color=R.drawable.bg_ligth_fill2;
	 ViewHolder holder = null;
    private ArrayList<String> mData = new ArrayList<String>();
    private LayoutInflater mInflater;

    public MyCustomAdapter(Context context, ArrayList<String> arrstr) {
    	this.c=context;
    	this.mData=arrstr;
        mInflater = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void addItem(final String item) {
        mData.add(item);
        notifyDataSetChanged();
    }

    public int getCount() {
        return mData.size();
    }

    public String getItem(int position) {
        return (String) mData.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        System.out.println("getView " + position + " " + convertView);
        
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.cell_toscroll, null);
            holder = new ViewHolder();
            holder.textView = (TextView)convertView.findViewById(R.id.myitem_title);
            holder.textView.setTextSize(20);
            
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }
        
    
        String text_to_vis=mData.get(position);
        if (text_to_vis.indexOf("#")>0) {
        	text_to_vis.replace("\n", "");
        	text_to_vis.replace("\r", "");
        	holder.textView.setTextAppearance(c, R.style.Title);
        	holder.textView.setPadding(0, getPixels(15), 0, 0);
        	holder.textView.setGravity(Gravity.CENTER_HORIZONTAL);
        holder.textView.setText(text_to_vis.substring(0, text_to_vis.length()-1));
        }
        else{
        	holder.textView.setTextAppearance(c, R.style.Authortext);
        	holder.textView.setPadding(0, 0, 0, 0);
        	holder.textView.setGravity(Gravity.FILL_HORIZONTAL);
        	holder.textView.setTypeface(Typeface.SERIF);
        	holder.textView.setText(text_to_vis);
        }
        
        convertView.setBackgroundResource(color);
        return convertView;
    }
    
    public int getHtextline(){
    return	holder.textView.getLineHeight();
    }
    public int getTextLineCount(){
        return	holder.textView.getLineCount();
        
        }
    
    public int getTextLine(){
        //return	holder.textView.getLineCount();
        return holder.textView.length();
        }
    public String getText(){
    	return holder.textView.getText().toString();
    }
    public TextView getTextView(){
    	return holder.textView;
    }
    
	private int getPixels(int dipValue){
        Resources r = c.getResources();
        int px = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, 
        r.getDisplayMetrics());
        return px;
}

}

