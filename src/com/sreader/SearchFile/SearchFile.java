package com.sreader.SearchFile;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ViewSwitcher;

import com.sreader.store.Item;
import com.trinetix.sreader.R;

public class SearchFile extends Activity {
	
	//new
	final ArrayList<String> listFiles=new ArrayList<String>();
	
	
	ArrayList<ItemFile> arrfile=new ArrayList<ItemFile>();
	
	private ViewSwitcher viewSwitcher_storeOne;
	private static final String TAG = "F_PATH";
	
	private File path = new File(Environment.getExternalStorageDirectory() + "/Books/MoonReader");
	ProgressDialog myPd_ring;
	public ProgressDialog progressdial;
	
		 
		

	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.store_one);
        viewSwitcher_storeOne=(ViewSwitcher)findViewById(R.id.viewSwitcher_storeOne);
        //  mainSwitcher = (ViewSwitcher)findViewById(R.id.viewSwitcher_storeOne);
        
        progressdial=new ProgressDialog(this);
        progressdial.setTitle("Импорт eBooks");
        progressdial.setMessage("Добавлен: ");
		progressdial.setCancelable(true);
		
		
	   /*   if (savedInstanceState == null) {
			    Bundle extras = getIntent().getExtras();
			    if(extras == null) {
			        headerText=null;
			    	subcatId= null;
			    } else {
			    	headerText= extras.getString("headerText");
			        subcatId = extras.getString("subcatId");
			        
			    }
			} else {
			    headerText = savedInstanceState.get.getString("headerText");
			    subcatId = savedInstanceState.getString("subcatId");
			}
*/		
     //   setSearch();
        viewSwitcher_storeOne.showNext();
          Intent intent = getIntent();
	    /*    
	      String DirSearch =intent.getStringExtra("DirSearch");
          path=new File (DirSearch);
          */
        loadItems("");
        /*int i=0;*/
	}
	
public void loadItems(String filters) {
		
	ListLoader task = new ListLoader();
		task.execute(new String[] {""});

	}

public void initView() {
	 
	 
	ItemsFileAdapter adapter;
	 DisplayMetrics metrics = new DisplayMetrics();
	 getWindowManager().getDefaultDisplay().getMetrics(metrics);

	 
	 		 
	 if (arrfile.size()>0)  viewSwitcher_storeOne.showNext();
       adapter = new ItemsFileAdapter(this, R.layout.cell_tech_white, arrfile, "Избранное");
   	
	 	ListView lv = (ListView)findViewById(R.id.listView_storeOne);
       lv.setAdapter(adapter);


       lv.setOnItemClickListener(new OnItemClickListener() {
           public void onItemClick(AdapterView<?> parent, View view,
               int position, long id) {
             // When clicked, show a toast with the TextView text
           	
        	   ItemFile item = arrfile.get(position);
        	   Item it=new Item(new String[]{"asd", "asd", "asd", "asd", "asd", "asd"});
           /*	
           	String itemTitle = item.getTitle();
           	String itemId = item.getId();
           	*/
           	//Toast.makeText(getApplicationContext(), itemTitle + ":" + itemId, Toast.LENGTH_SHORT).show();
           	
           	
       		Intent intent = new Intent(getApplicationContext(), FileDetailed.class);
       		
       		/*intent.putExtra("itemId", itemId);*/
       		intent.putExtra("itemFromList", item);
           	          	
       		startActivity(intent);
           }
         });
       
   //    adapter.notifyDataSetChanged();
	 
}

public class ListLoader extends AsyncTask <String, String, ArrayList<String>>{

    
	
	protected void onPreExecute() {
		super.onPreExecute();
	
	progressdial.show();	
    }
	@Override
	protected ArrayList<String> doInBackground(String... urls) {
		try {
			path.mkdirs();
		} catch (SecurityException e) {
			Log.e(TAG, "unable to write on the sd card ");
		}

		// Checks whether path exists
		if (path.exists()) {
			FilenameFilter filter = new FilenameFilter() {
				public boolean accept(File dir, String filename) {
					File sel = new File(dir, filename);
					// Filters based on whether the file is hidden or not
					return (sel.isFile() || sel.isDirectory())
							&& !sel.isHidden();

				}
			};

			String[] fList = path.list(filter);
			
			for (int i = 0; i < fList.length; i++) {
				File sel = new File(path, fList[i]);
				if (!sel.isDirectory()) {
					
				arrfile.add(new ItemFile(fList[i], R.drawable.file_icon));
				//	listFiles.add(fList[i]);
				}
				
			}
		}
		
		for(ItemFile cur: arrfile){
			publishProgress("Добавлен: "+cur.getFile());
			 try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return listFiles;
	}
	
	protected void onProgressUpdate(String... progress) {
       //String dt=progress[0];
		progressdial.setMessage(progress[0]);
		
    }
	
	@Override
	protected void onPostExecute(ArrayList<String> result) {
		progressdial.dismiss();
		initView();
	}
	
}



private void setSearch(){
	
	      
	myPd_ring=ProgressDialog.show(SearchFile.this, "Импорт eBooks", "", true);
    myPd_ring.setCancelable(true);
      		
    		
    
}

private void getListFiles(){

	try {
		path.mkdirs();
	} catch (SecurityException e) {
		Log.e(TAG, "unable to write on the sd card ");
	}

	// Checks whether path exists
	if (path.exists()) {
		FilenameFilter filter = new FilenameFilter() {
			public boolean accept(File dir, String filename) {
				File sel = new File(dir, filename);
				// Filters based on whether the file is hidden or not
				return (sel.isFile() || sel.isDirectory())
						&& !sel.isHidden();

			}
		};

		String[] fList = path.list(filter);
		
		for (int i = 0; i < fList.length; i++) {
			File sel = new File(path, fList[i]);
			if (!sel.isDirectory()) {
				listFiles.add(fList[i]);
			
			
			
			}
		}
	}
	
}





/*private void do_local_search()
{
  this.tabHost.setCurrentTab(0);
  LinearLayout localLinearLayout = (LinearLayout)LayoutInflater.from(this).inflate(2130903070, null);
  EditText localEditText1 = (EditText)localLinearLayout.findViewById(2131296407);
  EditText localEditText2 = (EditText)localLinearLayout.findViewById(2131296408);
  CheckBox localCheckBox1 = (CheckBox)localLinearLayout.findViewById(2131296409);
  CheckBox localCheckBox2 = (CheckBox)localLinearLayout.findViewById(2131296410);
  CheckBox localCheckBox3 = (CheckBox)localLinearLayout.findViewById(2131296411);
  localEditText1.setText(A.lastPath);
  localEditText2.setText(A.lastBookSearchKey);
  localCheckBox1.setChecked(A.searchOnlyBooks);
  localCheckBox2.setChecked(A.searchSubFolder);
  localCheckBox3.setChecked(A.searchHiddenFiles);
  new AlertDialog.Builder(this).setTitle(getString(2131034304)).setView(localLinearLayout).setPositiveButton(17039370, new DialogInterface.OnClickListener(localEditText1, localEditText2, localCheckBox1, localCheckBox2, localCheckBox3)
  {
    public void onClick(DialogInterface paramDialogInterface, int paramInt)
    {
      String str = this.val$pathEdit.getText().toString();
      A.lastBookSearchKey = this.val$keyEdit.getText().toString();
      A.searchOnlyBooks = this.val$bookOnlyCheck.isChecked();
      A.searchSubFolder = this.val$subFolderCheck.isChecked();
      A.searchHiddenFiles = this.val$subHiddenCheck.isChecked();
      if (str.equals("/"));
      while (true)
      {
        return;
        if (!T.isFolder(str))
        {
          T.showToastText(ActivityMain.this, str + " " + ActivityMain.this.getString(2131034350));
          continue;
        }
        ActivityMain.this.progressDlg = ProgressDialog.show(ActivityMain.this, "", ActivityMain.this.getString(2131034343) + " " + A.lastBookSearchKey + "...", true);
        new ActivityMain.20.1(this, str).start();
      }
    }
  }).setNegativeButton(17039360, null).show();
}
	
	*/
	
}
