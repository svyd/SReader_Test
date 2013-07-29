package com.sreader.chapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.trinetix.sreader.R;


public class Chapters extends Activity {
    private ArrayList<String>  chapters;
    ListView lv;
    ItemsAdapterChapter adapter;
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.store_chapters);
        
        Bundle extras = getIntent().getExtras();
        
        chapters=new ArrayList<String>();
        chapters = extras.getStringArrayList("chapters");
    	  
        
        initialVariable();// инициализация переменных
     
    }
	//инициализация переменных
	private void initialVariable(){
		
		
		lv = (ListView)findViewById(R.id.listView_storeOne);
		
		adapter = new ItemsAdapterChapter(this, R.layout.cell_tech_white, chapters);
		
		lv.setDivider(null);
        lv.setAdapter(adapter);
       
 
        lv.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                int position, long id) {
            	
            	
            	Intent intent = new Intent();
               
                  intent.putExtra("id_chapter", position);
               
                setResult(RESULT_OK, intent);
                finish();
           
            }
          });
		
           	
	}
	
	   public void onClickBack(View v){
	    	finish();
	    }

}