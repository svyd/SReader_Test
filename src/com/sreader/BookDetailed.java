package com.sreader;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sreader.Db.ItemsOpenHelper;
import com.sreader.bookmark.BookmarksFromBd;
import com.sreader.chapter.ChaptersFromBd;
import com.sreader.store.Item;
import com.sreader.util.images;
import com.trinetix.sreader.R;

public class BookDetailed extends Activity {
    /** Called when the activity is first created. */
	
	String item_id="";
	private Item itemFromList;
	ItemsOpenHelper dbHelper;
	private LayoutInflater inflater;
	LinearLayout linlaydetailinfl;
	boolean controll_ifl=false;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_detailed);
         
        dbHelper = new ItemsOpenHelper(getApplicationContext());
		    Bundle extras = getIntent().getExtras();
		     
		    	item_id=extras.getString("id_item");

 	}
    
    public void initView(){
    	inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    	linlaydetailinfl=(LinearLayout)findViewById(R.id.linlaydetailinfl);
    	if (itemFromList.getFile_name().endsWith("html") || itemFromList.getFile_name().endsWith("htm")
    			|| itemFromList.getFile_name().endsWith("txt"))setHidenButtToolbar();
    	
    ImageView imageview = (ImageView)findViewById(R.id.imageitemtech);
    images.setImageFromFile(this, imageview, itemFromList.getImage_url());
       
        
	item_id=itemFromList.getId();
	TextView item_title = (TextView)findViewById(R.id.item_title);
	item_title.setText(itemFromList.getTitle());
	TextView item_format = (TextView)findViewById(R.id.item_format);
	item_format.setText("Формат: "+itemFromList.getType());
	TextView item_persent = (TextView)findViewById(R.id.item_persent);
	item_persent.setText("Прочитано: "+itemFromList.getReadpercent()+" %");
	
	if (!controll_ifl) {
	if ((itemFromList.getLang()!=null) && (itemFromList.getLang().length()>0)){
		View v = inflater.inflate(R.layout.book_detailed_inf, null);
		((TextView)v.findViewById(R.id.textinfltitle)).setText("Языки:");
		((TextView)v.findViewById(R.id.textinfltoset)).setText(itemFromList.getLang());
		linlaydetailinfl.addView(v);
		
	}
	
	if ((itemFromList.getGenre()!=null) && (itemFromList.getGenre().length()>0)){
		View v = inflater.inflate(R.layout.book_detailed_inf, null);
		((TextView)v.findViewById(R.id.textinfltitle)).setText("Жанр:");
		((TextView)v.findViewById(R.id.textinfltoset)).setText(itemFromList.getGenre());
		linlaydetailinfl.addView(v);
		
	}
	
	if ((itemFromList.getPublisher()!=null) && (itemFromList.getPublisher().length()>0)){
		View v = inflater.inflate(R.layout.book_detailed_inf, null);
		((TextView)v.findViewById(R.id.textinfltitle)).setText("Издательство:");
		((TextView)v.findViewById(R.id.textinfltoset)).setText(itemFromList.getPublisher());
		linlaydetailinfl.addView(v);
		
	}
	controll_ifl=true;
	}
		
	if ((itemFromList.getAnotation()!=null) && (itemFromList.getAnotation().length()>0)) {
	TextView text_anotation = (TextView)findViewById(R.id.text_anotation);
	text_anotation.setText(itemFromList.getAnotation());
	
	}
	else {
		TextView text_headanotat = (TextView)findViewById(R.id.text_headanotat);
		text_headanotat.setVisibility(View.GONE);
	}
	
	
	

	TextView item_author=(TextView)findViewById(R.id.item_author);
	item_author.setText(itemFromList.getAuthor());
        
        
        
        Button button_read=(Button)findViewById(R.id.button_read);
        button_read.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent intent = new Intent(getApplicationContext(), RunRead.class);
        		
        		intent.putExtra("File_name", itemFromList.getFile_name());
        		intent.putExtra("File_dir", itemFromList.getFile_dir());
        		intent.putExtra("id_item", itemFromList.getId());
        		intent.putExtra("readpercent", itemFromList.getReadpercent());
        		intent.putExtra("id_chapter", itemFromList.getId_chapter());
        		intent.putExtra("id_word", itemFromList.getId_word());
        		intent.putExtra("countWords", itemFromList.getCountWords());
        		intent.putExtra("book_title", itemFromList.getTitle());
            	            	
        		startActivity(intent);
				
			}
        	
        });	
    }
    
public void loadItems(String filters) {
		
		DBLoader task = new DBLoader();
		task.execute(new String[] {""});

	}
 	
	private class DBLoader extends AsyncTask <String, Void, String>{

    	@Override
    	protected String doInBackground(String... urls) {
    		itemFromList=dbHelper.getItem(item_id);
  		return "";
		}
      	@Override
		protected void onPostExecute(String result) {
      		initView();
		}
      }
	
public void runDialogDellBook(View v){
		
		AlertDialog.Builder dialogFont = new AlertDialog.Builder(this);
		dialogFont.setTitle("Внимание!")
		.setMessage("Вы действительно хотите удалить книгу?")
			
				
		.setPositiveButton("Да", new android.content.DialogInterface.OnClickListener(){

			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
							
				dialog.cancel();
				removeBookFromDb();
				
			}
			
		})
		.setNegativeButton("Нет",  new android.content.DialogInterface.OnClickListener(){

			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
							
				dialog.cancel();
				
				
			}
			
		});
		
		dialogFont.create().show();
}

    public void removeBookFromDb(){
    	
    	dbHelper.deleteItem(item_id);
    	
    	Intent intent = new Intent();
      
      setResult(RESULT_OK, intent);
      finish();
   
    }
    
    public void onClickBack(View v){
    	finish();
    }
 
    public void onClickRunChapter(View v){
    	Intent intent = new Intent(getApplicationContext(), ChaptersFromBd.class);
   		intent.putExtra("id_item", item_id);
   		intent.putExtra("filename", itemFromList.getFile_name());
   		intent.putExtra("filedir", itemFromList.getFile_dir());
   		intent.putExtra("book_title", itemFromList.getTitle());
		
		startActivity(intent);
    }

    public void onClickRunBookmark(View v){
    	Intent intent = new Intent(getApplicationContext(), BookmarksFromBd.class);
   		intent.putExtra("id_item", item_id);
  		
		startActivity(intent);
    }
    
    @Override
    protected void onResume() {
        
    	loadItems("");
        super.onResume();
    }
    private void setHidenButtToolbar(){
    	((ImageView)findViewById(R.id.ImgViewChHrDetail)).setVisibility(View.GONE);
    	((ImageView)findViewById(R.id.ImgViewCgaptDetail)).setVisibility(View.GONE);
    }
    

   
}