package com.sreader.chapter;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.sreader.RunRead;
import com.sreader.RunRead.LoadContentBook;
import com.sreader.leksem.Book;
import com.sreader.parsers.getDataXmlToDb;
import com.trinetix.sreader.R;


public class ChaptersFromBd extends Activity {
    private ArrayList<String>  chapters;
    ListView lv;
    String id_item="";
    String filename="";
    String filedir="";
    String book_title="";
    
    ItemsAdapterChapter adapter;
    public ProgressDialog dialogSearch;
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.store_chapters);
        
        Bundle extras = getIntent().getExtras();
        
        id_item = extras.getString("id_item");
        filename = extras.getString("filename");
        filedir = extras.getString("filedir");
        book_title= extras.getString("book_title");
        
        initialVariable();
        
        getContentBook();
     
    }
	//инициализация переменных
	private void initialVariable(){
		chapters=new ArrayList<String>();
		dialogSearch=new ProgressDialog(this);
		
		
		
           	
	}
	
	private void initView(){
		  
		
    lv = (ListView)findViewById(R.id.listView_storeOne);
		
		adapter = new ItemsAdapterChapter(this, R.layout.cell_tech_white, chapters);
		
		lv.setDivider(null);
        lv.setAdapter(adapter);
       
 
        lv.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                int position, long id) {
            	
            	
            	Intent intent = new Intent(getApplicationContext(), RunRead.class);
           		
            	intent.putExtra("File_name", filename);
            	intent.putExtra("File_dir", filedir);
            	intent.putExtra("id_item", id_item);
            	intent.putExtra("readpercent", String.valueOf(0));
            	intent.putExtra("id_chapter", String.valueOf(position));
            	intent.putExtra("id_word", String.valueOf(0));
            	intent.putExtra("countWords", String.valueOf(0));
            	intent.putExtra("book_title", book_title);
              	 
            	
        		
        		startActivity(intent);
                finish();
           
            }
          });
	}
	
	   public void onClickBack(View v){
	    	finish();
	    }
	   
	   private void getContentBook(){
		    LoadContentBook task = new LoadContentBook();
			task.execute(new String[] {""});
	    }
		
		 public class LoadContentBook extends AsyncTask <String, String, String>{
	
		    	protected void onPreExecute() {
		    		super.onPreExecute();
		    		
		    		dialogSearch.setTitle("Получение данных");
		    		dialogSearch.setMessage("Подождите, чтение...");
		    		dialogSearch.setCancelable(true);
		    		dialogSearch.show();	
		        }
		    	@Override
		    	protected String doInBackground(String... urls) {
		    	
		    		getDataXmlToDb.getArrayChaptersItem(chapters, filename, filedir);
		    		    		return "";
		    	}
		    			    	
		    	@Override
		    	protected void onPostExecute(String result) {
		    		
		    		dialogSearch.dismiss();
		    				initView();
		    		
  		       
		    	}
		    	
		    }

}