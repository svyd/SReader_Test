package com.sreader.SearchFile;

import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.sreader.store.Item;
import com.trinetix.sreader.R;

public class FileDetailed extends Activity/* implements OnTouchListener*/ {
    /** Called when the activity is first created. */
	
	private ItemFile itemFromList;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_detailed);
        
        if (savedInstanceState == null) {
		    Bundle extras = getIntent().getExtras();
		    if(extras == null) {		    	
		    	//itemId= null;
		    	itemFromList = null;
		    } else {
		       // itemId = extras.getString("itemId");
		        itemFromList = extras.getParcelable("itemFromList");
		    }
		} else {
		  //  itemId = savedInstanceState.getString("itemId");
		    itemFromList = savedInstanceState.getParcelable("itemFromList");
		}
        
        
   	InputStream ims;
		Drawable d=null;
		try {
			ims = this.getAssets().open("cover.jpg");
			d = Drawable.createFromStream(ims, null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    
	TextView textview = (TextView)findViewById(R.id.item_title);
	ImageView imageview = (ImageView)findViewById(R.id.imageitemtech);
	imageview.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
	imageview.setImageDrawable(d);
	textview.setText(itemFromList.getFile());
	
//	((TextView)v.findViewById(R.id.textView_itemId)).setText(item.getId());
	/*TextView textview2=(TextView)findViewById(R.id.item_description);
	textview2.setText(itemFromList.getAuthor());*/
        
        
        
     /*   Button button_read=(Button)findViewById(R.id.button_read);
        button_read.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				// TODO Auto-generated method stub
				
		//		Intent intent = new Intent(getApplicationContext(), RunRead.class);
        		
        		/*intent.putExtra("itemId", itemId);
        		intent.putExtra("itemFromList", item);
            	  */          	
        //		startActivity(intent);
				
		/*	}
        	
        });*/
	}
   
}