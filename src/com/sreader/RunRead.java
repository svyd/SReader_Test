package com.sreader;

import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.provider.Settings;
import android.util.Log;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.sreader.Db.ItemsOpenHelper;
import com.sreader.Preferences.Constants;
import com.sreader.Preferences.MyPreferences;
import com.sreader.bookmark.Bookmarks;
import com.sreader.bookmark.ItemBookmark;
import com.sreader.chapter.Chapters;
import com.sreader.leksem.Book;
import com.sreader.leksem.Chapter;
import com.sreader.store.SetChapter;
import com.sreader.util.Utils;
import com.sreader.visibleAllText.MyCustomAdapter;
import com.trinetix.sreader.R;

public class RunRead extends Activity{
   
	int time=180;
	int maxSpeed=700;
	int minSpeed=60;
	int progre=0;
	int b=0;
	int x=0;
	int SeekBarHdiskr=0;
	ArrayList<String> arrstr=new ArrayList<String>(); 
	String input="";
	String[] strtext=null;
	RelativeLayout linlaybase;
	LinearLayout linlayspeedtext, linlaytoolbar,linlayprogressbar_hor, linlaytouch, linlaybottolbar;
	TextView times, text_torun, text_readpersent, text_run_title, text_readpersent_alltext;
	VelocityTracker mVelocityTracker;
	int oldX = -1, oldY = -1;
    String strToPrint="";
	int dx = 0, dy = 0;
	StringTokenizer tokens=null;
	Book book;
	String sdcard;
	String book_title="";
	public ProgressDialog dialogSearch;
	Thread thread;
	String NAME_FORGET_FILE="";
	String DIR_FORGET_FILE="";
	String ID_ITEM="";
	Date currentDate;
	GestureDetector mGestureDetector;
	int id_chapter=0;
	int id_word=0;
	int progres_pb_hor=0;
	int countWords=0;
	double CoefPersent=0;
	double CoefPersentAllText=0;
	int pause=0;
	private Handler handler;
	private ProgressBar progressbar_hor;
	boolean pausedRead=false;
	final int ID_DIALOG_FONT=1;
	final int ID_DIALOG_NOTCONTENT=3;
	final int ID_DIALOG_BRIGHTNESS=2;
	final int REQUEST_CODE_CHAPTER=1;
	final int REQUEST_CODE_BOOKMARK=2;
	final int REQUEST_CODE_ALLTEXT=3;
	int seeksetsh_o_max=100;
	ArrayList<SetChapter> setChapters;
	int CURRENT_VISIBLE=0;
	ListView listView1;
	SeekBar seekbarAllText;
	
	public ArrayList<String> words;
	MyCustomAdapter mAdapter;
	ImageView butModeVisible;

	int brightness = 0;
	Window window; 
    ContentResolver cResolver; 
    private SensorManager mSensorManager;
    private PowerManager mPowerManager;
    private WindowManager mWindowManager;
    private WakeLock mWakeLock;
    private String phrase="";
    private boolean addBookmark=false;
    private Chapter curchapter;
    private ViewSwitcher viewSwitcherReadBook;
    private MyPreferences Preferences; 
    int DEFAULT_SH_V=5;
    boolean start_bookmark=false;
    boolean start_chapter=false;
        
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.readbook);
     
        
        
        
        
        	  Bundle extras = getIntent().getExtras();
              NAME_FORGET_FILE = extras.getString("File_name");
          	  DIR_FORGET_FILE = extras.getString("File_dir");
          	  ID_ITEM = extras.getString("id_item");
          	  progres_pb_hor=(Integer.valueOf(extras.getString("readpercent")));
          	  id_chapter=(Integer.valueOf(extras.getString("id_chapter")));
          	  id_word=(Integer.valueOf(extras.getString("id_word")));
          	  countWords=(Integer.valueOf(extras.getString("countWords")));
          	  book_title = extras.getString("book_title");
          	  initialVariable();
          	  getContentBook();
          	  
          	  

    }
  ///////////////////////////////////////////////////////инициализация переменных//////////////////////////////////////////////////////////////
  	private void initialVariable(){
  		
  		Preferences=new MyPreferences(this); 
  		seekbarAllText=(SeekBar)findViewById(R.id.seekbar_alltext);
  		time=Preferences.getSpeedText();
  		
  		dialogSearch=new ProgressDialog(this);
  	    handler = new Handler();
  	    viewSwitcherReadBook=(ViewSwitcher)findViewById(R.id.viewSwitcherReadBook); 
  	    words=new ArrayList<String>();
  	    words.add(book_title+"#");
  	    setChapters=new ArrayList<SetChapter>();
  	    setChapters.add(new SetChapter(id_chapter, id_word, countWords));

  	  text_readpersent_alltext = (TextView) findViewById(R.id.text_readpersent_alltext);
  	    
  	    mAdapter = new MyCustomAdapter(this, words);
  	    
  	  listView1=(ListView)findViewById(R.id.listView1);
      listView1.setDivider(null);
      listView1.setAdapter(mAdapter);
     
      listView1.setOnScrollListener(new android.widget.AbsListView.OnScrollListener() {
  		
  		public void onScrollStateChanged(AbsListView view, int scrollState) {
  			// TODO Auto-generated method stub
  			
  		}
  		
  		public void onScroll(AbsListView view, int firstVisibleItem,
  				int visibleItemCount, int totalItemCount) {
  			// TODO Auto-generated method stub
  			seekbarAllText.setProgress(firstVisibleItem);
  			int set_persent=(int)(Math.round(firstVisibleItem/CoefPersentAllText));
  			if (set_persent>100) set_persent=100;
  			text_readpersent_alltext.setText("Прочитано "+set_persent+" %");
 
  			
  		}
        });
      
   
        butModeVisible=(ImageView)findViewById(R.id.butModeVisible);  
      
        butModeVisible.setOnClickListener(new android.view.View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
			
				switch(CURRENT_VISIBLE){
				case Constants.VISIBLE_RUNTEXT:
					
					onVisibleAllText();
					butModeVisible.setImageResource(R.drawable.icon_butt_torun);
					break;
				case Constants.VISIBLE_ALLTEXT:
					gettingPositionListView();
					onVisibleRunText();
					butModeVisible.setImageResource(R.drawable.icon_butt_totext);
					break;
				}
			}
		});
  	    progressbar_hor=(ProgressBar)findViewById(R.id.progressbar_hor);
  	    progressbar_hor.setMax(100);
  	    
  	    linlayprogressbar_hor=(LinearLayout)findViewById(R.id.linlayprogressbar_hor);
  	    linlayprogressbar_hor.setVisibility(View.VISIBLE);
  	    
  	    linlaytouch=(LinearLayout)findViewById(R.id.linlaytouch);
  	    
  		linlayspeedtext=(LinearLayout)findViewById(R.id.linlayspeedtext);
  		
  		linlaybottolbar=(LinearLayout)findViewById(R.id.linlaybottolbar);
  		linlaybottolbar.setVisibility(View.INVISIBLE);
  		
  		linlaytoolbar=(LinearLayout)findViewById(R.id.linlaytoolbar);
  		linlaytoolbar.setVisibility(View.INVISIBLE);
  		
  	    linlaybase=(RelativeLayout)findViewById(R.id.linlaybase);
  	    linlaytouch.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return mGestureDetector.onTouchEvent(event);
            }
        });
  	    
  	  try{
	        // Get an instance of the SensorManager
	        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

	        // Get an instance of the PowerManager
	        mPowerManager = (PowerManager) getSystemService(POWER_SERVICE);

	        // Get an instance of the WindowManager
	        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
	        mWindowManager.getDefaultDisplay();

	        // Create a bright wake lock
	        mWakeLock = mPowerManager.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK, getClass()
	                .getName());

	      
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e("onCreate", e.getMessage());
		}
  	
  	mGestureDetector = new GestureDetector(new GestureDetector.SimpleOnGestureListener() {
        @Override
        public void onLongPress(MotionEvent e) {
        	setActiveDeactiveContext();
        }
        @Override
        public boolean onDoubleTap(MotionEvent e) {
             return true;
        }
        @Override
        public boolean onSingleTapUp(MotionEvent e){
        	setActiveDeactiveToolBar();
        	return true;
        }
          @Override
        public boolean onDown(MotionEvent e) {
        	  Preferences.setSpeedText(time);
        //	publ(time+"");
        	return true;
        }
        
        @Override
        public boolean  onScroll (MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        	int deltaY = (int) (distanceY);
			
			if ((-deltaY<0) && time>minSpeed){
				time=time-2;
			}
			if ((-deltaY>0) && time<maxSpeed){
				time=time+2;
				
			}
			Preferences.setSpeedText(time);
     		times.setText("Скорость "+String.valueOf(60*1000/time)+" слов/мин");
        	return true;
        }
    });
    mGestureDetector.setIsLongpressEnabled(true);
		
  		text_run_title = (TextView) findViewById(R.id.text_run_title);
  		
  		text_torun = (TextView) findViewById(R.id.text_torun);
  		if (Preferences.getFontSize()!=0) 
  			text_torun.setTextSize(TypedValue.COMPLEX_UNIT_SP, Preferences.getFontSize());
  		
  		setPreferSh_O();
  		
  		times=(TextView)findViewById(R.id.text_speed);
  		times.setText("Скорость "+String.valueOf(60*1000/time)+" слов/мин");
  		
  		
  		
  		text_readpersent = (TextView) findViewById(R.id.text_readpersent);
  	
  		
  		if (Preferences.getColorSheme()!=0) setColorSheme();
  		
  		if (NAME_FORGET_FILE.endsWith("html") || NAME_FORGET_FILE.endsWith("htm")
    			|| NAME_FORGET_FILE.endsWith("txt"))setHidenButtToolbar();
   	}
  	
  	
 
  	
    public void initView(){
    	text_run_title.setText(book_title);
    	
    	String strprint="";
    	try{
    	if(id_chapter<book.size()){
    		curchapter=book.get(id_chapter);
    		int size_chapters_w=curchapter.getWords().size();
    		if (size_chapters_w>id_word){
    			strprint=curchapter.getWord(id_word);
    			countWords++;
    			if ((strprint.endsWith(".")) || (strprint.endsWith(". ")) || (strprint.endsWith("\n")) || (strprint.endsWith("\n "))) pause=300;
    			addBookmark=false;
    			phrase=strprint;
    			if (strprint.length()>=DEFAULT_SH_V) { pause+=(time*(strprint.length()/DEFAULT_SH_V))-time;
    			publicText(strprint, "#654987");
    			
    			}
    			else{
    				boolean checkVisible=false;
    				
        			
    				while(!checkVisible) {
    					if (id_word+1<size_chapters_w) {
    				
    				if(!((strprint+curchapter.getWord(id_word+1)).length()>DEFAULT_SH_V) && (!strprint.endsWith("."))) {
    					id_word++;
    					strprint=strprint+" "+curchapter.getWord(id_word);
    					countWords++;
    					}
    				else {
    					checkVisible=true;
    					publicText(strprint,"#000000");
    			
    					}
    				}else {
    					checkVisible=true;
    					publicText(strprint, "#000000");
    					
    				}
    				}
    			}
    			
    		}
    		else {
    			id_word=0;
    			id_chapter++;
    		}
    	}
    	else {
    		handler.removeCallbacks(updateTimeTask);
			finish();
    	}
    	}
    	catch(Exception e){
        	
        }
    }
    
    private void publicText(String strprint, String color){
    	text_torun.setText(strprint);
    //	text_torun.setTextColor(Color.parseColor(color));
		progres_pb_hor=(int)Math.floor(countWords/CoefPersent);
		progressbar_hor.setProgress(progres_pb_hor);
		if (progres_pb_hor>=100) progres_pb_hor=100;
		text_readpersent.setText("Прочитано "+progres_pb_hor+" %");
   		id_word++;	
    }
    
 
    private Runnable updateTimeTask = new Runnable() {
		public void run() {
			initView();
			if (id_chapter<book.size()) {
				handler.postDelayed(this, time+pause);
				pause=0;
			}
			
			else finish();
		}
	};
	
	@Override
	public Dialog onCreateDialog(int id) {
	AlertDialog.Builder dialog = null;
		switch(id){
		case ID_DIALOG_FONT:
			LayoutInflater inflater = (LayoutInflater)this.getSystemService(LAYOUT_INFLATER_SERVICE);
			
			View layout = inflater.inflate(R.layout.dialog_setfont, (ViewGroup)findViewById(R.id.dialogfontroot));
			SeekBar seeksetsize=(SeekBar)layout.findViewById(R.id.seeksetsize);
			SeekBar seeksetsh_o=(SeekBar)layout.findViewById(R.id.seeksetsh_o);
			seeksetsh_o.setMax(seeksetsh_o_max);
			seeksetsh_o.setProgress(DEFAULT_SH_V);
			seeksetsize.setMax(Constants.DEFAULT_SIZE_TEXT*4);
			seeksetsize.setProgress(/*(int)(text_torun.getTextSize())*/Preferences.getFontSize());
			seeksetsh_o.setOnSeekBarChangeListener(Providers.getSeekBarChangeListener_SH_O(this));
            seeksetsize.setOnSeekBarChangeListener(Providers.getSeekBarChangeListenerTextSize(this, 10));
            
		AlertDialog.Builder dialogFont = new AlertDialog.Builder(this);
		dialogFont.setTitle(R.string.dialog_setfont_title)
			
		
		.setSingleChoiceItems(R.array.name_font, Preferences.getFontTypeface(), new DialogInterface.OnClickListener(){

			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				Preferences.setFontTypeface(which);
			}
			
		})
		.setView(layout)
		
		.setPositiveButton("Применить", new OnClickListener(){

			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				setFont();
				dialog.cancel();
			}
			
		});
		dialog=dialogFont;
		break;
			case ID_DIALOG_NOTCONTENT:
				AlertDialog.Builder dialogContent = new AlertDialog.Builder(this);
				dialogContent.setTitle("Ошибка!")
					.setMessage("Структура документа повреждена")
					.setPositiveButton("Ok", new OnClickListener(){

					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						
						dialog.cancel();
						finish();
					}
					
				});
				dialog=dialogContent;
			break;

            case ID_DIALOG_BRIGHTNESS:
                LayoutInflater inflaterDialog = (LayoutInflater)this.getSystemService(LAYOUT_INFLATER_SERVICE);

                final View layoutDialog = inflaterDialog.inflate(R.layout.dialog_setbrightness, (ViewGroup)findViewById(R.id.dialogfontroot));
                SeekBar seeksbrightness=(SeekBar)layoutDialog.findViewById(R.id.seeksetbrightness);
                seeksbrightness.setMax(9);
                seeksbrightness.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                        ((CheckBox)layoutDialog.findViewById(R.id.checkboxautobrightness)).setChecked(false);
                        Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
                        WindowManager.LayoutParams lp = getWindow().getAttributes();
                        lp.screenBrightness = .1f * i;

                        getWindow().setAttributes(lp);
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });

                AlertDialog.Builder dialogBrightness = new AlertDialog.Builder(this);
                dialogBrightness.setTitle("Brightness control")
                .setView(layoutDialog)
                .setPositiveButton("Применить", new OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        CheckBox mAutoBrightnessChkBx = (CheckBox)layoutDialog.findViewById(R.id.checkboxautobrightness);
                        if (mAutoBrightnessChkBx.isChecked()) {
                            Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC);
                            WindowManager.LayoutParams lp = getWindow().getAttributes();
                            lp.screenBrightness = WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_NONE;
                            getWindow().setAttributes(lp);
                        }
                        dialog.cancel();
                    }

                });
                dialog = dialogBrightness;
            break;
		
		}
		
		
		return dialog.create();
		
		
	}
	
	public void onClickBack(View v){
		finish();
	}
	
	public void printTxt(String str){
		Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
	}

	public void createDialogColorSheme(View v){
		            
		AlertDialog.Builder dialogSheme = new AlertDialog.Builder(this);
		dialogSheme.setTitle(R.string.dialog_set_colorsheme)
			
		
		.setSingleChoiceItems(R.array.color_sheme, Preferences.getColorSheme(), new DialogInterface.OnClickListener(){

			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				Preferences.setColorSheme(which);
			}
			
		})
			
		.setPositiveButton("Применить", new OnClickListener(){

			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				setColorSheme();
				dialog.cancel();
			}
			
		});
		dialogSheme.create().show();
	}
	
public void createViewDialog(View v){
	
	switch(v.getId()){
	case R.id.butt_set_font:
		this.showDialog(ID_DIALOG_FONT);
		break;
	
	case R.id.butsetbright:
	//TODO Create Brightness dialog
        this.showDialog(ID_DIALOG_BRIGHTNESS);
		//Toast.makeText(this, "Create Brightness dialog", Toast.LENGTH_SHORT).show();
		break;
	
	}
	
}



private void setFont(){
	DEFAULT_SH_V=Preferences.getSh_O();
	text_torun.setTextSize(TypedValue.COMPLEX_UNIT_SP, Preferences.getFontSize());
	
	
	switch(Preferences.getFontTypeface()){
	case 0:
	text_torun.setTypeface(Typeface.SERIF);	
	break;
	case 1:
	text_torun.setTypeface(Typeface.SANS_SERIF);	
	break;
	case 2:
	text_torun.setTypeface(Typeface.MONOSPACE);	
	break;
	}
	
}

public void publishDialogNotCont(){
	this.showDialog(ID_DIALOG_NOTCONTENT);
}

public void onClickListChapter(View v){
	ArrayList<String> chapters=new ArrayList<String>(); 
	for(Chapter cut_ch:book) chapters.add(cut_ch.getName());
		
	start_chapter=true;
	
	Intent intent = new Intent(this, Chapters.class);
	intent.putStringArrayListExtra("chapters", chapters);
    startActivityForResult(intent, REQUEST_CODE_CHAPTER);
    
    
    
}
	
public void onClickListBookmark(View v){
	
	start_bookmark=true;
	Intent intent = new Intent(this, Bookmarks.class);
	intent.putExtra("id_item", ID_ITEM);
	
    startActivityForResult(intent, REQUEST_CODE_BOOKMARK);
}

private void onVisibleAllText(){
	CURRENT_VISIBLE=Constants.VISIBLE_ALLTEXT;
    mAdapter.notifyDataSetChanged(); 
    int pos_scroll=getPositionToScroll();
    listView1.setSelection(pos_scroll);
    initialseekbarAllText(pos_scroll);
    viewSwitcherReadBook.showNext();
    linlaybottolbar.setVisibility(View.INVISIBLE);
    
 
}
private void onVisibleRunText(){
	CURRENT_VISIBLE=Constants.VISIBLE_RUNTEXT;
	viewSwitcherReadBook.showPrevious();
}

    @Override
    protected void onResume() {
        super.onResume();
        setFont();
        try {
			mWakeLock.acquire();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e("onResume", e.getMessage());
		}
    }
    
	@Override
	protected void onDestroy() {
		super.onDestroy();
		handler.removeCallbacks(updateTimeTask);
		book=null;
		
		
	}
    
	@Override
    protected void onPause() {
        super.onPause();
        addCurrentInfoToDb();
        if ((!start_bookmark) && (!start_chapter))
        setActiveDeactiveToolBar();
        // and release our wake-lock
        try {
			mWakeLock.release();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e("onPause",e.getMessage());
		}
    }
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	  
		switch(keyCode){
		case KeyEvent.KEYCODE_MENU:
			if (CURRENT_VISIBLE==Constants.VISIBLE_RUNTEXT){
			setActiveDeactiveContext();
			}else if (CURRENT_VISIBLE==Constants.VISIBLE_ALLTEXT) {
				setActiveDeactiveBotToolBar();
			}
	        return true;
		case KeyEvent.KEYCODE_BACK:
			if (CURRENT_VISIBLE==Constants.VISIBLE_RUNTEXT){
		if (linlaytoolbar.isShown()) setActiveDeactiveToolBar();
		else if (linlaybottolbar.isShown())  setActiveDeactiveContext();
		else finish();
			}
			  else if (CURRENT_VISIBLE==Constants.VISIBLE_ALLTEXT) {
				  gettingPositionListView();
					onVisibleRunText();
					butModeVisible.setImageResource(R.drawable.icon_butt_totext);

			  }
			
	        return true;	
		}
	  
		
		    
	    return super.onKeyDown(keyCode, event);
	}
    
	private void addCurrentInfoToDb(){
		ItemsOpenHelper dbHelper = new ItemsOpenHelper(getApplicationContext());
		ContentValues cv = new ContentValues();
		cv.put("readpercent", progres_pb_hor+"");
		cv.put("id_chapter", id_chapter+"");
		cv.put("id_word", id_word+"");
		cv.put("countWords", countWords+"");
		dbHelper.updateItem(ID_ITEM, cv);
	}
   
public void addBookmark(View v){
	if (!addBookmark){
	ItemsOpenHelper dbBMHelper = new ItemsOpenHelper(getApplicationContext());

	dbBMHelper.addItemBookmark(new ItemBookmark(new String[]{Utils.getCurrentDate(),ID_ITEM,curchapter.getName(),phrase, String.valueOf(progres_pb_hor),String.valueOf(id_chapter),
		String.valueOf(id_word), String.valueOf(countWords), NAME_FORGET_FILE, DIR_FORGET_FILE	}));
	printAddBookmarkDialog(phrase);
	}
  
}

private void setActiveDeactiveContext(){
	
  	if (!linlaytoolbar.isShown()) if ((!pausedRead)){
    	linlaybottolbar.setVisibility(View.VISIBLE);
    	linlayprogressbar_hor.setVisibility(View.INVISIBLE);
		linlayspeedtext.setVisibility(View.INVISIBLE);
		handler.removeCallbacks(updateTimeTask);
		publ("Пауза");
		pausedRead=true;
    	}
    	else{
    		linlaybottolbar.setVisibility(View.INVISIBLE);
        	linlayprogressbar_hor.setVisibility(View.VISIBLE);
			linlayspeedtext.setVisibility(View.VISIBLE);
			handler.removeCallbacks(updateTimeTask);
			handler.postDelayed(updateTimeTask, time);
			pausedRead=false;
    	}
}

private void setActiveDeactiveToolBar(){
	if (!pausedRead){
    	linlaytoolbar.setVisibility(View.VISIBLE);
    	//linlaybottolbar.setVisibility(View.VISIBLE);
    	//linlayprogressbar_hor.setVisibility(View.INVISIBLE);
		linlayspeedtext.setVisibility(View.INVISIBLE);
		handler.removeCallbacks(updateTimeTask);
		publ("Пауза");
		pausedRead=true;
    	}
    	else{
    		linlaytoolbar.setVisibility(View.INVISIBLE);
    		linlaybottolbar.setVisibility(View.INVISIBLE);
        	linlayprogressbar_hor.setVisibility(View.VISIBLE);
			linlayspeedtext.setVisibility(View.VISIBLE);
			handler.removeCallbacks(updateTimeTask);
			handler.postDelayed(updateTimeTask, time);
			pausedRead=false;
    	}
}
private void setActiveDeactiveBotToolBar(){
	if (!linlaybottolbar.isShown())	linlaybottolbar.setVisibility(View.VISIBLE);
		else 
    		linlaybottolbar.setVisibility(View.INVISIBLE);
        	
}


private void setColorSheme(){
	switch(Preferences.getColorSheme()){
	case Constants.COLOR_SHEME_SEPIA:
		this.setTheme(android.R.style.Theme_Black_NoTitleBar);
		text_torun.setTextColor(Color.BLACK);
		times.setTextColor(Color.parseColor("#555555"));
		text_run_title.setTextColor(Color.parseColor("#5D3E2C"));
		text_readpersent.setTextColor(Color.parseColor("#333333"));
		text_readpersent_alltext.setTextColor(Color.parseColor("#333333"));
		linlaytoolbar.setBackgroundResource(R.drawable.bg_topbar);
		linlayspeedtext.setBackgroundResource(R.drawable.bg_speed);
		linlaybase.setBackgroundResource(R.drawable.bg_ligth_fill2);
		listView1.setBackgroundResource(R.drawable.bg_ligth_fill2);
		((LinearLayout)findViewById(R.id.linlayseekbaralltext)).setBackgroundResource(R.drawable.bg_ligth_fill2);
		mAdapter.color=R.drawable.bg_ligth_fill2;
		((ImageView)findViewById(R.id.buttBack)).setImageResource(R.drawable.icon_butt_back);
		((ImageView)findViewById(R.id.buttBookmarks)).setImageResource(R.drawable.icon_butt_bookmarkstolb);
		((ImageView)findViewById(R.id.buttlistchapter)).setImageResource(R.drawable.icon_butt_content);
		break;
	case Constants.COLOR_SHEME_BLACK:
		this.setTheme(R.style.MyThemeBlack);
		text_torun.setTextColor(Color.WHITE);
		times.setTextColor(Color.parseColor("#E7E7E7"));
		text_run_title.setTextColor(Color.WHITE);
		text_readpersent.setTextColor(Color.parseColor("#E7E7E7"));
		text_readpersent_alltext.setTextColor(Color.parseColor("#E7E7E7"));
		linlaytoolbar.setBackgroundResource(R.drawable.bg_topbar_black);
		linlayspeedtext.setBackgroundResource(R.drawable.bg_speed_b);
		linlaybase.setBackgroundResource(R.drawable.bg_black_fill);
		listView1.setBackgroundResource(R.drawable.bg_black_fill);
		((LinearLayout)findViewById(R.id.linlayseekbaralltext)).setBackgroundResource(R.drawable.bg_black_fill);
		mAdapter.color=R.drawable.bg_black_fill;
		((ImageView)findViewById(R.id.buttBack)).setImageResource(R.drawable.icon_butt_back_black);
		((ImageView)findViewById(R.id.buttBookmarks)).setImageResource(R.drawable.icon_butt_bookmarkstolb_black);
		((ImageView)findViewById(R.id.buttlistchapter)).setImageResource(R.drawable.icon_butt_content_black);
				break;
	case Constants.COLOR_SHEME_WHITE:
		this.setTheme(android.R.style.Theme_Black_NoTitleBar);
		text_torun.setTextColor(Color.BLACK);
		times.setTextColor(Color.parseColor("#555555"));
		text_readpersent.setTextColor(Color.parseColor("#333333"));
		text_readpersent_alltext.setTextColor(Color.parseColor("#333333"));
		text_run_title.setTextColor(Color.parseColor("#333333"));
		linlaytoolbar.setBackgroundResource(R.drawable.bg_topbar_white);
		linlayspeedtext.setBackgroundResource(R.drawable.bg_speed_w);
		linlaybase.setBackgroundResource(R.drawable.bg_white_fill);
		listView1.setBackgroundResource(R.drawable.bg_white_fill);
		((LinearLayout)findViewById(R.id.linlayseekbaralltext)).setBackgroundResource(R.drawable.bg_white_fill);
		mAdapter.color=R.drawable.bg_white_fill;
		((ImageView)findViewById(R.id.buttBack)).setImageResource(R.drawable.icon_butt_back_white);
		((ImageView)findViewById(R.id.buttBookmarks)).setImageResource(R.drawable.icon_butt_bookmarkstolb_white);
		((ImageView)findViewById(R.id.buttlistchapter)).setImageResource(R.drawable.icon_butt_content_white);
		break;
		
	
	}
}

	void publ(String toaststr){
		Toast.makeText(this, toaststr, Toast.LENGTH_SHORT).show();
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


		    		    book=new Book(NAME_FORGET_FILE, DIR_FORGET_FILE);
		    	
		    		    Log.v("dir", NAME_FORGET_FILE+" "+DIR_FORGET_FILE);
		    		    		return "";


		    	}
		    			    	
		    	@Override
		    	protected void onPostExecute(String result) {
		    		printAllText();
		    		
   		       
		    	}
		    	
		    }
		 
		 
		 private void printAllText(){
			 addTextToArr task = new addTextToArr(this);
				task.execute(new String[] {""});
		    }
			
			 public class addTextToArr extends AsyncTask <String, String, String>{
	 	           Context context;
	 	           public addTextToArr(Context c){
	 	        	   this.context=c;
	 	           }
		
			    	@Override
			    	protected String doInBackground(String... urls) {

			    		TextView textview=mAdapter.getTextView();

			    		   
			        	String strToAdd="";
			        	boolean checkAdd=false;
			        	
			        	int set_id_chapter=0;
			        	int set_id_word=0;
			        	int set_col_word=0;
			        	try{
                            int widthDisplay = Utils.getWidthDisplay(context)
                                    -textview.getPaddingLeft()-textview.getPaddingLeft();
			        		for(Chapter curentchapter: book){
			        			words.add(curentchapter.getName()+"#");
			        			setChapters.add(new SetChapter(set_id_chapter, set_id_word, set_col_word));
			        			int count_words=curentchapter.getWords().size();
			        			for(int i=0; i<count_words; i++){
			        				strToAdd=curentchapter.getWord(i);
			        				set_id_word=i;
			        				
			        				checkAdd=false;
			        				
			        			
			        				while(!checkAdd) {
			        					if (i+1<count_words) {
			        				
			        				if(!(Utils.getTextWidth(textview, strToAdd+curentchapter.getWord(i+1), widthDisplay))) {
			        					i++;
			        					set_col_word++;
			        					strToAdd=strToAdd+" "+curentchapter.getWord(i);
			        					}
			        				else {
			        					checkAdd=true;
			        					words.add(strToAdd);
			        					setChapters.add(new SetChapter(set_id_chapter, set_id_word, set_col_word));
			        					strToAdd="";
			        				}
			        				}else {
			        					checkAdd=true;
			        					words.add(strToAdd);
			        					setChapters.add(new SetChapter(set_id_chapter, set_id_word, set_col_word));
			        					strToAdd="";
			        				}
			        				}
			        				set_col_word++;
			        			}
			        			set_id_chapter++;
			        		}
			        	
			        	}
			        	catch(Exception e){   	}
			        
			    		    		return "";
			    	}
			    			    	
			    	@Override
			    	protected void onPostExecute(String result) {
			    		CURRENT_VISIBLE=Constants.VISIBLE_RUNTEXT;
			    		CoefPersent=getCoefPersent(book);
			    		dialogSearch.dismiss();
			    		if (book.size()==0) publishDialogNotCont();
			    			else {
			    				initView();
			    				handler.removeCallbacks(updateTimeTask);
					    		handler.postDelayed(updateTimeTask, time);
			    			}
			    	}
			    	
			    } 
		 
		 
		 @Override
		  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		    
		    if (resultCode == RESULT_OK) {
		      switch (requestCode) {
		      case REQUEST_CODE_CHAPTER:
		    	  start_chapter=false;
		    	  id_chapter= data.getIntExtra("id_chapter", 0);
		    	  id_word=0; 
		    	  CoefPersent=getCoefPersent(book);
		        break;
		      case REQUEST_CODE_BOOKMARK:
		    	  start_bookmark=false;
		    	  id_chapter=(Integer.valueOf(data.getStringExtra("id_chapter")));
              	  id_word=(Integer.valueOf(data.getStringExtra("id_word")));
              	  countWords=(Integer.valueOf(data.getStringExtra("countWords")));
              	  progres_pb_hor=(Integer.valueOf(data.getStringExtra("progres_pb_hor")));
		    	  break;
		    	  
		     
		      }
		    // если вернулось не ОК
		   /* if (pausedRead)  {
		      linlaytoolbar.setVisibility(View.INVISIBLE);
      		linlaybottolbar.setVisibility(View.INVISIBLE);
          	linlayprogressbar_hor.setVisibility(View.VISIBLE);
  			linlayspeedtext.setVisibility(View.VISIBLE);
  			handler.removeCallbacks(updateTimeTask);
  			handler.postDelayed(updateTimeTask, time);
  			pausedRead=false;
		    }*/
		    } else {
		    //  Toast.makeText(this, "Wrong result", Toast.LENGTH_SHORT).show();
		    }
		  }

public void printAddBookmarkDialog(String bm_add){
				
				AlertDialog.Builder dialogFont = new AlertDialog.Builder(this);
				dialogFont.setTitle("Информация")
			.setMessage("Закладка \""+bm_add+"\" добавлена")		
				
									
				.setPositiveButton("Ok", new OnClickListener(){

					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						addBookmark=true;
						dialog.cancel();
					}
					
				});
				
				dialogFont.create().show();
		}	


private double getCoefPersent(Book book){
	 int col_words=0;
	 int id_cur_chapter=0;
	 for (Chapter cur_ch:book ){
		col_words+=cur_ch.getWords().size();
		if ((id_chapter==0) && (id_word==0)) countWords=0;
		else 
		if ((id_chapter==id_cur_chapter) && (id_chapter>0) && (id_word==0) ) countWords=col_words+id_word;
		id_cur_chapter++;
	 }
	 
		progressbar_hor.setMax((int)Math.floor(col_words/((double)col_words/(double)100)));
	 
	return (double)col_words/(double)100;
}

private void setPreferSh_O(){
	String sh_o="a";
		
	while(!(Utils.getTextWidth(text_torun, sh_o, Utils.getWidthDisplay(this)))) {
		sh_o+="a";
	}
	DEFAULT_SH_V=sh_o.length()-1;
	if (Preferences.getSh_O()==0) Preferences.setSh_O(DEFAULT_SH_V); 
	seeksetsh_o_max=DEFAULT_SH_V*2;
}

private void gettingPositionListView(){
	
	int id_fist_pos=listView1.getFirstVisiblePosition();
	Log.v("id_fist_pos", id_fist_pos+"");

	int id_last_pos=listView1.getLastVisiblePosition();
	Log.v("id_last_pos", id_last_pos+"");

	int result_pos=id_fist_pos+Math.round((id_last_pos-id_fist_pos)/2);
	Log.v("result_pos", result_pos+"");
	SetChapter SetChapter=setChapters.get(result_pos);
	id_chapter=SetChapter.getId_chapter();
	id_word=SetChapter.getId_word();
	id_word=SetChapter.getId_word();
	countWords=SetChapter.getcol_words();
	//getCoefPersent(book);
	
}
private int getPositionToScroll(){
	for(int i=0; i<setChapters.size(); i++){
		if (setChapters.get(i).getId_chapter()==id_chapter)
			if ((setChapters.get(i).getId_word()<=id_word) && (setChapters.get(i+1).getId_word()>=id_word))
				return i;
	}
	return 0;
}

private void setHidenButtToolbar(){
	((ImageView)findViewById(R.id.buttlistchapter)).setVisibility(View.GONE);
	((ImageView)findViewById(R.id.buttlistchhr)).setVisibility(View.GONE);
}
private void initialseekbarAllText(int pos_scroll){
	CoefPersentAllText=words.size()/100;
	seekbarAllText.setMax(words.size());
	seekbarAllText.setProgress(pos_scroll);
	text_readpersent_alltext.setText("Прочитано "+Math.round(pos_scroll/CoefPersentAllText)+" %");
	seekbarAllText.setOnSeekBarChangeListener(new android.widget.SeekBar.OnSeekBarChangeListener(){

		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			// TODO Auto-generated method stub
			listView1.setSelection(progress);
		}

		public void onStartTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub
			
		}

		public void onStopTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub
			
		}
		
	});
	
	
	
}

}