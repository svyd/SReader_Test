package com.sreader.Db;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.sreader.bookmark.ItemBookmark;
import com.sreader.store.Item;

public class ItemsOpenHelper extends SQLiteOpenHelper {
	
	
	public static final String ID = "id";
	public static final String TITLE = "title";
	public static final String AUTHOR = "author";
	public static final String GENRE = "genre";
	public static final String LANG = "lang";
	public static final String PUBLISHER = "publisher";
	public static final String ANOTATION = "anotation";
	public static final String TYPE = "type";
	public static final String READPERCENT = "readpercent";
	public static final String ID_CHAPTER = "id_chapter";
	public static final String ID_WORD = "id_word";
	public static final String COUNT_WOPRDS = "countWords";
	public static final String IMAGE_URL = "image_url";
	public static final String FILE_NAME = "file_name";
	public static final String FILE_DIR = "file_dir";
	
	public static final String ID_BOOKMARK = "id";
	public static final String ID_BOOK_BOOKMARK = "id_book";
	public static final String TITLE_BOOKMARK = "title";
	public static final String PHRASE_BOOKMARK = "phrase";
	public static final String READPERCENT_BOOKMARK = "readpercent";
	public static final String ID_CHAPTER_BOOKMARK = "id_chapter";
	public static final String ID_WORD_BOOKMARK = "id_word";
	public static final String COUNT_WOPRDS_BOOKMARK = "countWords";
	public static final String FILE_NAME_BOOKMARK = "file_name";
	public static final String FILE_DIR_BOOKMARK = "file_dir";
	
	
	private static final int DATABASE_VERSION = 2;
    public static final String ITEMS_TABLE_NAME = "items";
    private static final String ITEMS_TABLE_CREATE =
        "CREATE TABLE " + ITEMS_TABLE_NAME + " ("+
        ID + " TEXT, " +
        TITLE + " TEXT, " +
        AUTHOR + " TEXT, " +
        GENRE + " TEXT, " +
        LANG + " TEXT, " +
        PUBLISHER + " TEXT, " +
        ANOTATION + " TEXT, " +
        TYPE + " TEXT, " +
        READPERCENT + " INT, " +
        ID_CHAPTER + " TEXT, " +
        ID_WORD + " TEXT, " +
        COUNT_WOPRDS + " TEXT, " +
        IMAGE_URL + " TEXT, " +
        FILE_NAME + " TEXT, " +
        FILE_DIR + " TEXT);";
    
    public static final String ITEMS_TABLE_BOOKMARK = "Bookmark";
    private static final String ITEMS_TABLE_BOOKMARK_CREATE =
        "CREATE TABLE " + ITEMS_TABLE_BOOKMARK + " ("+
        ID_BOOKMARK + " TEXT, " +
        ID_BOOK_BOOKMARK + " TEXT, " +        		
        TITLE_BOOKMARK + " TEXT, " +
        PHRASE_BOOKMARK + " TEXT, " +
        READPERCENT_BOOKMARK + " INT, " +
        ID_CHAPTER_BOOKMARK + " TEXT, " +
        ID_WORD_BOOKMARK + " TEXT, " +
        COUNT_WOPRDS_BOOKMARK + " TEXT, " +
        FILE_NAME_BOOKMARK + " TEXT, " +
        FILE_DIR_BOOKMARK + " TEXT);";

    private static final String ITEMS_INDEX_CREATE =
        "CREATE UNIQUE INDEX IF NOT EXISTS ITEM_ID ON items(id);";
   /* private static final String ITEMS_INDEX_CREATE_BOOKMARK =
            "CREATE UNIQUE INDEX IF NOT EXISTS ITEM_ID ON items(id);";
    */
    public ItemsOpenHelper(Context context) {
        super(context, "itemsStorage", null, DATABASE_VERSION);
    }

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(ITEMS_TABLE_CREATE);
		db.execSQL(ITEMS_TABLE_BOOKMARK_CREATE);
		db.execSQL(ITEMS_INDEX_CREATE);
		

	}

	@Override
	public void onUpgrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1,
			int paramInt2) {
		// TODO Auto-generated method stub

	}
	
	
	public void deleteItem(String id) {
		SQLiteDatabase db = this.getWritableDatabase();
	    db.delete(ITEMS_TABLE_NAME, ID + " = ?",
	            new String[] { id });
	    db.close();
	    
	}
	
	public void updateItem(String id, ContentValues cv) {
				
		SQLiteDatabase db = this.getWritableDatabase();
		db.updateWithOnConflict(ITEMS_TABLE_NAME, cv, ID + " = ?", new String[] { id }, SQLiteDatabase.CONFLICT_REPLACE);
		
	    db.close();
	    
	}
	
	public ArrayList<ItemBookmark> getAllItemsBookmark() {
		ArrayList <ItemBookmark> items = new ArrayList<ItemBookmark>();
		
		String selectQuery = "SELECT  * FROM " + ITEMS_TABLE_BOOKMARK;
		SQLiteDatabase db = this.getReadableDatabase();
	    Cursor cursor = db.rawQuery(selectQuery, null);
	    
	    
	 
	    if (cursor.moveToFirst()) {
	        do {  	
	        	
	    		ContentValues values = new ContentValues();
	    		DatabaseUtils.cursorRowToContentValues(cursor, values);
	    		ItemBookmark item = new ItemBookmark(values);
	    		items.add(item);
	        
	        } while (cursor.moveToNext());
	    }
	    cursor.close();
	    db.close();
	    return items;				
	}
	
	public ArrayList<Item> getAllItems() {
		ArrayList <Item> items = new ArrayList<Item>();
		
		String selectQuery = "SELECT  * FROM " + ITEMS_TABLE_NAME;
		SQLiteDatabase db = this.getReadableDatabase();
	    Cursor cursor = db.rawQuery(selectQuery, null);
	    
	    
	 
	    if (cursor.moveToFirst()) {
	        do {  	
	        	
	    		ContentValues values = new ContentValues();
	    		DatabaseUtils.cursorRowToContentValues(cursor, values);
	    		Item item = new Item(values);
	    		items.add(item);
	        
	        } while (cursor.moveToNext());
	    }
	    cursor.close();
	    db.close();
	    return items;				
	}

    public ArrayList<Item> findItems(String mItemName) {
        ArrayList <Item> items = new ArrayList<Item>();

        String selectQuery = "SELECT  * FROM " + ITEMS_TABLE_NAME + " WHERE " + TITLE + " LIKE '" + mItemName + "%';" ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);



        if (cursor.moveToFirst()) {
            do {

                ContentValues values = new ContentValues();
                DatabaseUtils.cursorRowToContentValues(cursor, values);
                Item item = new Item(values);
                items.add(item);

            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return items;
    }
	
	public int getItemsCount() {
		String countQuery = "SELECT  * FROM " + ITEMS_TABLE_NAME;
		SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        db.close();
        return cursor.getCount();
	}
	
	public void addItem(Item item) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues cv=item.getContentValues();
		db.insertWithOnConflict(ITEMS_TABLE_NAME, null, item.getContentValues(), SQLiteDatabase.CONFLICT_REPLACE);
		db.close();
	}

	public void addItemBookmark(ItemBookmark item) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues cv=item.getContentValues();
		db.insertWithOnConflict(ITEMS_TABLE_BOOKMARK, null, item.getContentValues(), SQLiteDatabase.CONFLICT_REPLACE);
		db.close();
	}
	
	public Item getItem(String id) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(ITEMS_TABLE_NAME, new String[] {ID, TITLE, AUTHOR, GENRE, LANG, PUBLISHER, ANOTATION, TYPE, READPERCENT, ID_CHAPTER, ID_WORD,COUNT_WOPRDS, IMAGE_URL, FILE_NAME, FILE_DIR},
				ID + "=?",  new String[] {id}, null, null, null, null);
		
		if (cursor.getCount() != 0) cursor.moveToFirst();
		else  {
			cursor.close();
			db.close();
			return null;
		}
		
		ContentValues values = new ContentValues();
		DatabaseUtils.cursorRowToContentValues(cursor, values);
		
		Item retv = new Item(values);
		
		
		cursor.close();
		db.close();
		
		return retv;
	}
	
}
