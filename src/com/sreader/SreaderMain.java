package com.sreader;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.StringTokenizer;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.sreader.Db.ItemsOpenHelper;
import com.sreader.Preferences.Constants;
import com.sreader.Preferences.MyPreferences;
import com.sreader.parsers.getDataXmlToDb;
import com.sreader.store.Item;
import com.sreader.store.ItemsAdapter;
import com.sreader.util.FileManager;
import com.sreader.util.UnZip;
import com.sreader.util.Utils;
import com.trinetix.sreader.R;


public class SreaderMain extends Activity{

	private ViewSwitcher viewSwitcher_storeOne;
	private ArrayList<Item> items;
	private ArrayList<String> itemsFile;
	ItemsOpenHelper dbHelper;
	public ProgressDialog dialogSearch;
    ItemsAdapter adapter;
	 DisplayMetrics metrics;
	 ListView lv;
	 private FileManager filemanager;
	 boolean controlStop=false;
	 
	 EditText input;
	String DIR_MNT=Environment.getExternalStorageDirectory().toString();
	String DIR_BOOKS=Constants.DEFAULT_DIR_BOOKS;
	String DIR_SEARCH="";
	static final int ID_DIALOGNOTOKEN = 1;
	private boolean SWITCHER_VISIBLE=false;
    ArrayList<String> str_dir = null;
    final int REQUEST_CODE_DETAIL=0;
	

	
	private Boolean firstLvl = true;

	private static final String TAG = "F_PATH";

	private ItemSearch[] fileList;
	private File path = new File(Environment.getExternalStorageDirectory() + "");
	String set_title="Путь: "+path.getAbsolutePath();
	private String chosenFile;
	private static final int DIALOG_LOAD_FILE = 1000;
    int sort_type=0;
	ListAdapter adapter_search;
	private MyPreferences Preference;
	boolean checkNewFilesToAdd=false;
	boolean checkNewFiles=false;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.store_one);
  
        initialVariable();
        
   
    }
	
	private void initialVariable(){
		
		Preference=new MyPreferences(this);
		if (Preference.getSpeedText()==0) Preference.setDefaultPreferences();
		dbHelper = new ItemsOpenHelper(getApplicationContext());
		filemanager=new FileManager();
		metrics = new DisplayMetrics();
		 getWindowManager().getDefaultDisplay().getMetrics(metrics);
		 
		items=new ArrayList <Item>();
		itemsFile=new ArrayList <String>();
		
		path = new File(DIR_MNT+"/"+DIR_BOOKS);
		
		viewSwitcher_storeOne=(ViewSwitcher)findViewById(R.id.viewSwitcher_storeOne);
		
		
		
		
		dialogSearch=new ProgressDialog(this);
		dialogSearch.setCancelable(true);
		
		lv = (ListView)findViewById(R.id.listView_storeOne);
		
		adapter = new ItemsAdapter(this, R.layout.cell_tech_white, items);
		
		lv.setDivider(null);
        lv.setAdapter(adapter);
       
 
        lv.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                int position, long id) {
            	
            	Item item = items.get(position);
        
            	
       		Intent intent = new Intent(getApplicationContext(), BookDetailed.class);
       		intent.putExtra("id_item", item.getId());
        	   		startActivityForResult(intent, REQUEST_CODE_DETAIL);
        		
            }
          });
       viewSwitcher_storeOne.showNext();
		SWITCHER_VISIBLE=true;
		if (!checkFoundData()) copyAndAddData();
		else {
			 loadItems("");
			CountDownTimer dt = new CountDownTimer(2000, 1000) {
                public void onTick(long millisUntilFinished) {
            	
            	 int totalSeconds2 = (int) millisUntilFinished / 1000;
              }
             
             public void onFinish() {
            	viewSwitcher_storeOne.showPrevious();
            	SWITCHER_VISIBLE=false;
            	
             }
          }.start();
		}
      
    	
	}
	
	public void loadItems(String filters) {
		
		DBLoader task = new DBLoader();
		task.execute(new String[] {""});

	}
 	
	private class DBLoader extends AsyncTask <String, Void, String>{

    	@Override
    	protected String doInBackground(String... urls) {
    		try{
    		items.clear();
    		
     		ArrayList<Item> it = dbHelper.getAllItems();
     		for(Item cur: it) items.add(cur);
    		}
    		catch(Exception e){
    			
    		}
  		return "";
		}
      	@Override
		protected void onPostExecute(String result) {
      		adapter.notifyDataSetChanged();
      		if ((checkNewFiles) && (!checkNewFilesToAdd)) runDialogEmptySearch();
		}
      }
	

	public void onClickScanFiles(View v) {
		addBooksFromPath();
	}
	
	  
    
    private void runSearchFiles(){
    	ListLoader task = new ListLoader();
		task.execute(new String[] {""});
    }
    @Override
	protected Dialog onCreateDialog(int id) {
		Dialog dialog = null;
		AlertDialog.Builder builder = new Builder(this);

		if (fileList == null) {
			Log.e(TAG, "No files loaded");
			dialog = builder.create();
			return dialog;
		}

		switch (id) {
		case DIALOG_LOAD_FILE:
			set_title="Путь: "+path.getAbsolutePath();
			builder.setTitle(set_title);
			
			builder.setAdapter(adapter_search, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					chosenFile = fileList[which].file;
					File sel = new File(path + "/" + chosenFile);
					if (sel.isDirectory()) {
						firstLvl = false;

						// Adds chosen directory to list
						str_dir.add(chosenFile);
						fileList = null;
						path = new File(sel + "");

						loadFileList();

						removeDialog(DIALOG_LOAD_FILE);
						
						showDialog(DIALOG_LOAD_FILE);
						Log.d(TAG, path.getAbsolutePath());

					}

					else { 
						
						if (chosenFile.equalsIgnoreCase("Вверх") && !sel.exists()) {

				
						String s = str_dir.remove(str_dir.size() - 1);

						path = new File(path.toString().substring(0,
								path.toString().lastIndexOf(s)));
						fileList = null;

						if (str_dir.isEmpty()) {
							firstLvl = true;
						}
						loadFileList();

						removeDialog(DIALOG_LOAD_FILE);
						showDialog(DIALOG_LOAD_FILE);
						Log.d(TAG, path.getAbsolutePath());

					}
					else {
						removeDialog(DIALOG_LOAD_FILE);
						showDialog(DIALOG_LOAD_FILE);
					}

				}
				}
			})
			.setPositiveButton("Ok", new OnClickListener(){

				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
					DIR_BOOKS="";
					for(String cur:str_dir) DIR_BOOKS+=cur+"/";
					input.setText(DIR_BOOKS);
					
				}
				
			})
			.setNegativeButton("Отмена", new OnClickListener(){

				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
					
				}
				
			});
			break;
		}
		dialog = builder.show();
		return dialog;
	}
    
    private void loadFileList() {
		try {
			path.mkdirs();
		} catch (SecurityException e) {
			Log.e(TAG, "unable to write on the sd card ");
		}

		if (path.exists()) {
			FilenameFilter filter = new FilenameFilter() {
				public boolean accept(File dir, String filename) {
					File sel = new File(dir, filename);
					return (sel.isFile() || sel.isDirectory())
							&& !sel.isHidden();

				}
			};

			String[] fList = path.list(filter);
			fileList = new ItemSearch[fList.length];
			for (int i = 0; i < fList.length; i++) {
				fileList[i] = new ItemSearch(fList[i], R.drawable.file_icon);

				File sel = new File(path, fList[i]);

				if (sel.isDirectory()) {
					fileList[i].icon = R.drawable.directory_icon;
					Log.d("DIRECTORY", fileList[i].file);
				} else {
					Log.d("FILE", fileList[i].file);
				}
			}

			if (!firstLvl) {
				ItemSearch temp[] = new ItemSearch[fileList.length + 1];
				for (int i = 0; i < fileList.length; i++) {
					temp[i + 1] = fileList[i];
				}
				temp[0] = new ItemSearch("Вверх", R.drawable.directory_up);
				fileList = temp;
			}
		} else {
			Log.e(TAG, "path does not exist");
		}

		adapter_search = new ArrayAdapter<ItemSearch>(this,
				android.R.layout.select_dialog_item, android.R.id.text1,
				fileList) {
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				View view = super.getView(position, convertView, parent);
				TextView textView = (TextView) view
						.findViewById(android.R.id.text1);

				textView.setCompoundDrawablesWithIntrinsicBounds(
						fileList[position].icon, 0, 0, 0);

				int dp5 = (int) (5 * getResources().getDisplayMetrics().density + 0.5f);
				textView.setCompoundDrawablePadding(dp5);

				return view;
			}
		};

	}

	private class ItemSearch {
		public String file;
		public int icon;

		public ItemSearch(String file, Integer icon) {
			this.file = file;
			this.icon = icon;
		}

		@Override
		public String toString() {
			return file;
		}
	}
    
    public class ListLoader extends AsyncTask <String, String, ArrayList<String>>{

        
    	
    	protected void onPreExecute() {
    		super.onPreExecute();
    		checkNewFiles=true;
    		checkNewFilesToAdd=false;
    		if (!SWITCHER_VISIBLE){
    		dialogSearch.setTitle("Импорт eBooks");
    		dialogSearch.setMessage("Выполняется поиск...");
    		dialogSearch.show();	
    		}
        }
    	@Override
    	protected ArrayList<String> doInBackground(String... urls) {
    		try {
    			path.mkdirs();
    		} catch (SecurityException e) {
    			
    		}

    
    		if (path.exists()) {
    			FilenameFilter filter = new FilenameFilter() {
    				public boolean accept(File dir, String filename) {
    					File sel = new File(dir, filename);
    					
    					return (sel.isFile() || sel.isDirectory())
    							&& !sel.isHidden();

    				}
    			};

    			String[] fList = path.list(filter);
    			
    			for (int i = 0; i < fList.length; i++) {
    				File sel = new File(path, fList[i]);
    				if (!sel.isDirectory()) {
    					
    					itemsFile.add(fList[i]);
    				
    				}
    				
    			}
    		}
    		
    	
    		for(String cur: itemsFile){
    			if (!ifFileExists(cur)){
    				if ((cur.endsWith(".txt")) || (cur.endsWith(".fb2")) || (cur.endsWith(".html")) || (cur.endsWith(".fb2.zip")) || (cur.endsWith(".htm"))){
    				checkNewFilesToAdd=true;	
    				if (cur.endsWith(".fb2")) {
    					
    					addInfoFb2FileToDb(cur, DIR_MNT+"/"+DIR_BOOKS, DIR_MNT+"/"+DIR_BOOKS+"/Images");
    					publishProgress("Добавлен: "+cur);
    					
    				}
    				else if (cur.endsWith(".fb2.zip")){ 
    					publishProgress("Распаковка: "+cur);
    					ArrayList<String> fileList=new ArrayList<String>(); 
    					
    					UnZip  UnZip=new  UnZip(DIR_MNT+"/"+DIR_BOOKS+"/"+cur, DIR_MNT+"/"+Constants.DEFAULT_DIR_BOOKS+"/", fileList);
    					UnZip.unzip();
    					File fileZipToDel=new File(DIR_MNT+"/"+DIR_BOOKS+"/"+cur);
    					if (fileZipToDel.exists()) fileZipToDel.delete();
    					
    					for(String curstr:fileList) {
    						addInfoFb2FileToDb(curstr, DIR_MNT+"/"+Constants.DEFAULT_DIR_BOOKS+"/", DIR_MNT+"/"+DIR_BOOKS+"/Images");
        					publishProgress("Добавлен: "+curstr);
    						Log.v("fileList", curstr);
    					}
    				}
    				else
    					if (cur.endsWith(".txt")) {
    					addInfoTXTFileToDb(cur, DIR_MNT+"/"+DIR_BOOKS, "");
    					publishProgress("Добавлен: "+cur);
    					}
    					
    					else  if (cur.endsWith(".html") || cur.endsWith(".htm")) {
    					addInfoHTMLFileToDb(cur, DIR_MNT+"/"+DIR_BOOKS, "");
    					publishProgress("Добавлен: "+cur);
    					}
       				}
    			
    			}
    			 try {
    				Thread.sleep(1000);
    			} catch (InterruptedException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
    		}
    		
    		return itemsFile;
    	}
    	
    	private void addInfoHTMLFileToDb(String cur, String filedir, String imagesdir){
    		Item item=new Item();
    		getDataXmlToDb.getInfoHtml(item, cur, filedir);
			
			
			String title="";
			String set_author="";
			String set_genre="";
			String set_annotation="";
			String set_lang="";
			String set_publish="";
			
			try {
				title=item.getTitle();
			}
			catch(Exception e){}
			if ((title==null) || (title.length()==0)) title=cur;
		
			dbHelper.addItem(new Item(new String[]{Utils.getCurrentDate(), title,set_author,set_genre, set_lang, set_publish, set_annotation,cur.substring(cur.lastIndexOf(".")+1),"0","0","0","0",item.getImage_url(),cur,filedir}));
    	}
    	private void addInfoTXTFileToDb(String cur, String filedir, String imagesdir){
    		dbHelper.addItem(new Item(new String[]{Utils.getCurrentDate(), cur,"","","","","",cur.substring(cur.lastIndexOf(".")+1),"0","0","0","0","",cur,filedir}));	
    	}
    	private void addInfoFb2FileToDb(String cur, String filedir, String imagesdir){
    		Item item=new Item();
    		getDataXmlToDb.getInfoItem(item, cur, filedir, cur+".png", imagesdir);
			
			
			String title="";
			String set_author="";
			String set_genre="";
			String set_annotation="";
			String set_lang="";
			String set_publish="";
			
			try {
				title=item.getTitle();
			}
			catch(Exception e){}
			if ((title==null) || (title.length()==0)) title=cur;
			if ((item.getAuthor()!=null)) set_author=item.getAuthor();
			if ((item.getGenre()!=null)) set_genre=item.getGenre();
			if ((item.getAnotation()!=null)) set_annotation=item.getAnotation();
			if ((item.getLang()!=null)) set_lang=item.getLang();
			if ((item.getPublisher()!=null)) set_publish=item.getPublisher();
			dbHelper.addItem(new Item(new String[]{Utils.getCurrentDate(), title,set_author,set_genre, set_lang, set_publish, set_annotation,cur.substring(cur.lastIndexOf(".")+1),"0","0","0","0",item.getImage_url(),cur,filedir}));	
    	}
    	
    	protected void onProgressUpdate(String... progress) {
           //String dt=progress[0];
    		if (!SWITCHER_VISIBLE) dialogSearch.setMessage(progress[0]);
    		
        }
    	
    	@Override
    	protected void onPostExecute(ArrayList<String> result) {
    		if (!SWITCHER_VISIBLE) dialogSearch.dismiss();
    		else {
    			viewSwitcher_storeOne.showPrevious();
    			SWITCHER_VISIBLE=false;
    		}
    			loadItems("");
    	 
    	
    	}
    	
    }




@Override
public void onStop(){
    super.onStart();
   controlStop=true;   
}


@Override
protected void onResume() {
	// TODO Auto-generated method stub
	super.onResume();

	
	
}	
private void addBooksFromPath(){
 // TODO Auto-generated method stub
	 AlertDialog.Builder builder = new AlertDialog.Builder(this);
     builder.setTitle("Поиск книг в каталоге:");
  
        input = new EditText(this);
		input.setId(ID_DIALOGNOTOKEN);
	    input.setText(DIR_BOOKS);
	    input.setFocusable(true);
	    input.setFocusableInTouchMode(false);
	   
	    input.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				str_dir=new ArrayList<String>();
				setItemsDir(str_dir);
				if (str_dir.size()>0)firstLvl=false;
				loadFileList();
				showDialog(DIALOG_LOAD_FILE);
			}
		});
      
     
      builder.setView(input);

     builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

         public void onClick(DialogInterface dialog, int whichButton) {
        	 DIR_SEARCH=DIR_MNT+"/"+input.getText().toString();
        	 path = new File(DIR_SEARCH);
        	runSearchFiles();
            return;
         }
     });

     builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

         public void onClick(DialogInterface dialog, int which) {
             return;
         }
     });

     builder.create().show();

}

//TODO Search actions
public void onClickSearchDb(View v) {
		Toast.makeText(this.getApplicationContext(), "Need do realisation of search actions", Toast.LENGTH_SHORT).show();
		
}	




public void onButtonAdd(View view) {
	showDialog(ID_DIALOGNOTOKEN);
	
}

public void onClickSort(View v){
	
		AlertDialog.Builder dialogFont = new AlertDialog.Builder(this);
		dialogFont.setTitle(R.string.dialog_sort_title)
			
		
		.setSingleChoiceItems(R.array.name_sort, sort_type, new DialogInterface.OnClickListener(){

			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
				sort_type=which;
			}
			
		})
				
		.setPositiveButton("Применить", new OnClickListener(){

			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
			
				sortArrBooks(sort_type);
				dialog.cancel();
			}
			
		});
		
		dialogFont.create().show();
}

private boolean checkFoundData(){
	boolean check=false;
	if (dbHelper.getAllItems().size()==0) check=false;
		else check=true;
	
	return check;
	}
private void setItemsDir(ArrayList<String> arr_str){
	StringTokenizer str_tok =new StringTokenizer(DIR_BOOKS, "/"); 
	while(str_tok.hasMoreTokens()) arr_str.add(str_tok.nextToken());
}

private void copyAndAddData(){
	filemanager.CopyAssetsToSDCard(getApplicationContext(), "SReader");
	CountDownTimer dt = new CountDownTimer(2000, 1000) {
        public void onTick(long millisUntilFinished) {
    	
    	 int totalSeconds2 = (int) millisUntilFinished / 1000;
      }
     
     public void onFinish() {
    	 runSearchFiles();
     }
  }.start();
	
	
}

private boolean ifFileExists(String filename){
	boolean result=false;
	for(Item curitems: items){
		if (filename.equals(curitems.getFile_name())) result=true; 
	}
	return result;
	
}

  private void sortArrBooks(int sort){
	  final int i=sort;
	  Utils.sortarray(items, sort);
	  adapter.notifyDataSetChanged();
  }
  
  public void runDialogEmptySearch(){
		
		AlertDialog.Builder dialogFont = new AlertDialog.Builder(this);
		dialogFont.setTitle("Внимание!")
		.setMessage("Новые книги не найдены. Добавьте книги на карту памяти и повторите сканирование")
			
				
		.setPositiveButton("Ok", new OnClickListener(){

			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
							
				dialog.cancel();
				
			}
			
		});
		
		dialogFont.create().show();
}
  
  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    
    if (resultCode == RESULT_OK) {
      switch (requestCode) {
      case REQUEST_CODE_DETAIL:
    	
    	  loadItems("");
    	  
        break;
    
      }
        }
  }

}