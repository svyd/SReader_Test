package com.sreader.bookmark;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.sreader.RunRead;
import com.sreader.Db.ItemsOpenHelper;
import com.trinetix.sreader.R;


public class BookmarksFromBd extends Activity {
    private String id_item="";
    ListView lv;
    ItemsAdapterBookmark adapter;
    ItemsOpenHelper dbHelper; 
    public ArrayList<ItemBookmark> items;
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.store_bookmarks);
        
        Bundle extras = getIntent().getExtras();
        
        
        id_item = extras.getString("id_item");
    	  
        
        initialVariable();// инициализация переменных
     
    }
	//инициализация переменных
	private void initialVariable(){
		dbHelper = new ItemsOpenHelper(getApplicationContext());
		items=new ArrayList<ItemBookmark>();
		
		lv = (ListView)findViewById(R.id.listView_storeOne);
		
		loadItems("");
		
           	
	}
	
	private void initView(){
      
		adapter = new ItemsAdapterBookmark(this, R.layout.cell_tech_white, items);
		
		lv.setDivider(null);
        lv.setAdapter(adapter);
       
 
        lv.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                int position, long id) {
            	
            	ItemBookmark item=items.get(position);
            	
            	Intent intent = new Intent(getApplicationContext(), RunRead.class);
            	
            	intent.putExtra("File_name", item.getFile_name());
            	intent.putExtra("File_dir", item.getFile_dir());
            	intent.putExtra("id_item", id_item);
            	intent.putExtra("readpercent", item.getReadpercent());
            	intent.putExtra("id_chapter", item.getId_chapter());
            	intent.putExtra("id_word", item.getId_word());
            	intent.putExtra("countWords", item.getCountWords());
            	intent.putExtra("book_title", item.getTitle());
              	                
               
            	startActivity(intent);
                finish();
           
            }
          });
	}
	
	   public void onClickBack(View v){
	    	finish();
	    }
	   
	   public void loadItems(String filters) {
			
			DBLoader task = new DBLoader();
			task.execute(new String[] {""});

		}
	   
	   private class DBLoader extends AsyncTask <String, Void, String>{

	    	@Override
	    	protected String doInBackground(String... urls) {
	    		try{
	    			ArrayList<ItemBookmark> bm_list=new ArrayList<ItemBookmark>();
	    		
	    			bm_list = dbHelper.getAllItemsBookmark();
	     		
	    			for(ItemBookmark cur: bm_list){
	    				if (cur.getId_book().equals(id_item)) items.add(cur);
	    			}
	    		}
	    		catch(Exception e){
	    			
	    		}
	  		return "";
			}
	      	@Override
			protected void onPostExecute(String result) {
	      		if (items.size()==0) runDialogEmpty(); 
	      		else initView();
			}
	      }

	   public void runDialogEmpty(){
			
			AlertDialog.Builder dialogFont = new AlertDialog.Builder(this);
			dialogFont.setTitle("Информация")
			.setMessage("Закладки отсутствуют")
				
					
			.setPositiveButton("Ok", new OnClickListener(){

				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
								
					dialog.cancel();
					finish();
				}
				
			});
			
			dialogFont.create().show();
	}

}