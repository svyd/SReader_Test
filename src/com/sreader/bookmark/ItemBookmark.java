package com.sreader.bookmark;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;

import com.sreader.Db.ItemsOpenHelper;

public class ItemBookmark implements Parcelable {
	private String id;
	private String id_book;
	private String title;
	private String phrase;
	private String readpercent;
	private String id_chapter;
	private String id_word;
	private String countWords;
	private String file_name;
	private String file_dir;
	

	
	public ContentValues getContentValues() {
		ContentValues val = new ContentValues();
		
		val.put(ItemsOpenHelper.ID_BOOKMARK, 			this.getId());
		val.put(ItemsOpenHelper.ID_BOOK_BOOKMARK, 			this.getId_book());
		val.put(ItemsOpenHelper.TITLE_BOOKMARK, 			this.getTitle());
		val.put(ItemsOpenHelper.PHRASE_BOOKMARK, 			this.getPhrase());
		val.put(ItemsOpenHelper.READPERCENT_BOOKMARK, 			this.getReadpercent());
		val.put(ItemsOpenHelper.ID_CHAPTER_BOOKMARK, 			this.getId_chapter());
		val.put(ItemsOpenHelper.ID_WORD_BOOKMARK, 			this.getId_word());
		val.put(ItemsOpenHelper.COUNT_WOPRDS_BOOKMARK, 			this.getCountWords());
		val.put(ItemsOpenHelper.FILE_NAME_BOOKMARK, 			this.getFile_name());
		val.put(ItemsOpenHelper.FILE_DIR_BOOKMARK, 			this.getFile_dir());
		
		return val;
	}
	
	
	public String getCountWords() {
		return countWords;
	}


	public void setCountWords(String countWords) {
		this.countWords = countWords;
	}


	public ItemBookmark(ContentValues values) {
		this.setId(values.getAsString(ItemsOpenHelper.ID_BOOKMARK));
		this.setId_book(values.getAsString(ItemsOpenHelper.ID_BOOK_BOOKMARK));
		this.setTitle(values.getAsString(ItemsOpenHelper.TITLE_BOOKMARK));
		this.setPhrase(values.getAsString(ItemsOpenHelper.PHRASE_BOOKMARK));
		this.setReadpercent(values.getAsString(ItemsOpenHelper.READPERCENT_BOOKMARK));
		this.setId_chapter(values.getAsString(ItemsOpenHelper.ID_CHAPTER_BOOKMARK));
		this.setId_word(values.getAsString(ItemsOpenHelper.ID_WORD_BOOKMARK));
		this.setCountWords(values.getAsString(ItemsOpenHelper.COUNT_WOPRDS_BOOKMARK));
		this.setFile_name(values.getAsString(ItemsOpenHelper.FILE_NAME_BOOKMARK));
		this.setFile_dir(values.getAsString(ItemsOpenHelper.FILE_DIR_BOOKMARK));
		
	}
	
	public ItemBookmark(String[] arg)  {

			this.setId(arg[0]);
			this.setId_book(arg[1]);
			this.setTitle(arg[2]);
			this.setPhrase(arg[3]);
			this.setReadpercent(arg[4]);
			this.setId_chapter(arg[5]);
			this.setId_word(arg[6]);
			this.setCountWords(arg[7]);
			this.setFile_name(arg[8]);
			this.setFile_dir(arg[9]);

	}
	public ItemBookmark()  {
		
	}
	


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getId_book() {
		return id_book;
	}


	public void setId_book(String id_book) {
		this.id_book = id_book;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getPhrase() {
		return phrase;
	}


	public void setPhrase(String phrase) {
		this.phrase = phrase;
	}


	public String getReadpercent() {
		return readpercent;
	}


	public void setReadpercent(String readpercent) {
		this.readpercent = readpercent;
	}


	public String getId_chapter() {
		return id_chapter;
	}


	public void setId_chapter(String id_chapter) {
		this.id_chapter = id_chapter;
	}


	public String getId_word() {
		return id_word;
	}


	public void setId_word(String id_word) {
		this.id_word = id_word;
	}


	public String getFile_name() {
		return file_name;
	}


	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}


	public String getFile_dir() {
		return file_dir;
	}


	public void setFile_dir(String file_dir) {
		this.file_dir = file_dir;
	}


	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(id);
		dest.writeString(id_book);
		dest.writeString(title);
		dest.writeString(phrase);
		dest.writeString(readpercent);
		dest.writeString(id_chapter);
		dest.writeString(id_word);
		dest.writeString(countWords);
		dest.writeString(file_name);
		dest.writeString(file_dir);

	}
	
	public ItemBookmark(Parcel parcel) {
		this.setId(parcel.readString());
		this.setId_book(parcel.readString());
		this.setTitle(parcel.readString());
		this.setPhrase(parcel.readString());
		this.setReadpercent(parcel.readString());
		this.setId_chapter(parcel.readString());
		this.setId_word(parcel.readString());
		this.setCountWords(parcel.readString());
		this.setFile_name(parcel.readString());
		this.setFile_dir(parcel.readString());
	}
	
	public static final Parcelable.Creator<ItemBookmark> CREATOR
    = new Parcelable.Creator<ItemBookmark>() {
	      public ItemBookmark createFromParcel(Parcel source) {
	            return new ItemBookmark(source);
	      }
	      public ItemBookmark[] newArray(int size) {
	            return new ItemBookmark[size];
	      }
	};
	
	
	
}
