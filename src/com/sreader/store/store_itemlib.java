package com.sreader.store;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ViewSwitcher;

import com.trinetix.sreader.R;
import com.sreader.SreaderMain;
import com.sreader.Db.ItemsOpenHelper;


public class store_itemlib extends Activity {
//	private ViewSwitcher mainSwitcher;
	private ViewSwitcher viewSwitcher_storeOne;
	private ArrayList <Item> items;
	
	private String headerText;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.store_one);
        
        
        viewSwitcher_storeOne=(ViewSwitcher)findViewById(R.id.viewSwitcher_storeOne);
      //  mainSwitcher = (ViewSwitcher)findViewById(R.id.viewSwitcher_storeOne);
        viewSwitcher_storeOne.showNext();
        
        items=new ArrayList <Item>();
        
        
       
    }
	
	public void loadItems(String filters) {
		
		DBLoader task = new DBLoader();
		task.execute(new String[] {""});

	}
	
	 public void initView() {
		 
		 
		 ItemsAdapter adapter;
		 DisplayMetrics metrics = new DisplayMetrics();
		 getWindowManager().getDefaultDisplay().getMetrics(metrics);

		 
		 if (items.size()==0) viewSwitcher_storeOne.showPrevious();
		 
		 else  viewSwitcher_storeOne.showNext();
	        adapter = new ItemsAdapter(this, R.layout.cell_tech_white, items);
	    	
		 	ListView lv = (ListView)findViewById(R.id.listView_storeOne);
	        lv.setAdapter(adapter);

	 
	        lv.setOnItemClickListener(new OnItemClickListener() {
	            public void onItemClick(AdapterView<?> parent, View view,
	                int position, long id) {
	              // When clicked, show a toast with the TextView text
	            	
	            	Item item = items.get(position-1);
	            	
	            	String itemTitle = item.getTitle();
	            	String itemId = item.getId();
	            	
	            	//Toast.makeText(getApplicationContext(), itemTitle + ":" + itemId, Toast.LENGTH_SHORT).show();
	            	
	            	
            		/*Intent intent = new Intent(getApplicationContext(), store_itemdesc.class);
            		
            		intent.putExtra("itemId", itemId);
            		intent.putExtra("itemFromList", item);
	            	            	
            		startActivity(intent);*/
	            }
	          });
	       
	        
	        adapter.notifyDataSetChanged();
		 
	 }
	 
	 
	 
	
	private class DBLoader extends AsyncTask <String, Void, String>{

    	@Override
    	protected String doInBackground(String... urls) {
    		
    		ItemsOpenHelper dbHelper = new ItemsOpenHelper(getApplicationContext());
    		
    		items = dbHelper.getAllItems();
    		
			return "";
		}
    	
    	@Override
		protected void onPostExecute(String result) {
			initView();
		}
    	
    }
	public void onClickSearch(View v) {
		startSearch(null, false, null, false);
	}
	
	 ///////////////////формирование списка меню////////////////
    final int MENU_HOME = 1;
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
	// TODO Auto-generated method stub
	menu.add(0, MENU_HOME, 0, "Главная");
	
	
	return super.onCreateOptionsMenu(menu);
	}	    

@Override
public boolean onOptionsItemSelected(MenuItem item) {
//TODO Auto-generated method stub
switch (item.getItemId()) {
//пункты меню для tvColor
case MENU_HOME:
Intent intent = new Intent(this, SreaderMain.class);
intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
startActivity(intent);
break;
}
return true;
}
/////////////////////конец обработчика меню/////////////////////////////


@Override
public void onStop(){
    super.onStart();
    viewSwitcher_storeOne.showPrevious();   
}


@Override
protected void onResume() {
	// TODO Auto-generated method stub
	super.onResume();
	
	
	 loadItems("");
}	
}
