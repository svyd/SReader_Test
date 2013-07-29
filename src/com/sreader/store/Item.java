package com.sreader.store;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;

import com.sreader.Db.ItemsOpenHelper;

public class Item implements Parcelable {
	private String id;
	private String title;
	private String author;
	private String genre;
	private String lang;
	private String publisher;
	private String annotation;
	private String type;
	

	private String readpercent;
	private String id_chapter;
	private String id_word;
	private String countWords;
	private String image_url;
	private String file_name;
	private String file_dir;
	

	
	public ContentValues getContentValues() {
		ContentValues val = new ContentValues();
		
		val.put(ItemsOpenHelper.ID, 			getId());
		val.put(ItemsOpenHelper.TITLE, 			getTitle());
		val.put(ItemsOpenHelper.AUTHOR, 		getAuthor());
		val.put(ItemsOpenHelper.GENRE, 		getGenre());
		val.put(ItemsOpenHelper.LANG, 		this.getLang());
		val.put(ItemsOpenHelper.PUBLISHER, 		this.getPublisher());
		val.put(ItemsOpenHelper.ANOTATION, 		getAnotation());
		val.put(ItemsOpenHelper.TYPE, 			getType());
		val.put(ItemsOpenHelper.READPERCENT, 	getReadpercent());
		val.put(ItemsOpenHelper.ID_CHAPTER, 	getId_chapter());
		val.put(ItemsOpenHelper.ID_WORD, 	    getId_word());
		val.put(ItemsOpenHelper.COUNT_WOPRDS, 	getCountWords());
		val.put(ItemsOpenHelper.IMAGE_URL, 		getImage_url());
		val.put(ItemsOpenHelper.FILE_NAME, 		getFile_name());
		val.put(ItemsOpenHelper.FILE_DIR, 		getFile_dir());
		
		
		
		return val;
	}
	
	
	public String getCountWords() {
		return countWords;
	}


	public void setCountWords(String countWords) {
		this.countWords = countWords;
	}


	public Item(ContentValues values) {
		this.setId(values.getAsString(ItemsOpenHelper.ID));
		this.setTitle(values.getAsString(ItemsOpenHelper.TITLE));
		this.setAuthor(values.getAsString(ItemsOpenHelper.AUTHOR));	
		this.setGenre(values.getAsString(ItemsOpenHelper.GENRE));
		this.setLang(values.getAsString(ItemsOpenHelper.LANG));
		this.setPublisher(values.getAsString(ItemsOpenHelper.PUBLISHER));
		this.setAnotation(values.getAsString(ItemsOpenHelper.ANOTATION));
		this.setType(values.getAsString(ItemsOpenHelper.TYPE));
		this.setReadpercent(values.getAsString(ItemsOpenHelper.READPERCENT));
		this.setId_chapter(values.getAsString(ItemsOpenHelper.ID_CHAPTER));
		this.setId_word(values.getAsString(ItemsOpenHelper.ID_WORD));
		this.setCountWords(values.getAsString(ItemsOpenHelper.COUNT_WOPRDS));
		this.setImage_url(values.getAsString(ItemsOpenHelper.IMAGE_URL));
		this.setFile_name(values.getAsString(ItemsOpenHelper.FILE_NAME));
		this.setFile_dir(values.getAsString(ItemsOpenHelper.FILE_DIR));
		
	}
	
	public Item(String[] arg)  {

			this.setId(arg[0]);
			this.setTitle(arg[1]);	
			this.setAuthor(arg[2]);
			this.setGenre(arg[3]);
			this.setLang(arg[4]);
			this.setPublisher(arg[5]);
			this.setAnotation(arg[6]);
			this.setType(arg[7]);
			this.setReadpercent(arg[8]);
			this.setId_chapter(arg[9]);
			this.setId_word(arg[10]);
			this.setCountWords(arg[11]);
			this.setImage_url(arg[12]);
			this.setFile_name(arg[13]);
			this.setFile_dir(arg[14]);

	}
	public Item()  {
		
	}
	



	private void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}


	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}

	public String getImage_url() {
		return image_url;
	}

	


	public String getAuthor() {
		return author;
	}


	public void setAuthor(String author) {
		this.author = author;
	}

	public String getGenre() {
		return genre;
	}


	public void setGenre(String genre) {
		this.genre = genre;
	}
	
	public String getLang() {
		return lang;
	}


	public void setLang(String lang) {
		this.lang = lang;
	}


	public String getPublisher() {
		return publisher;
	}


	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	
	
	
	public String getAnotation() {
		return annotation;
	}


	public void setAnotation(String anotation) {
		this.annotation = anotation;
	}

	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
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


	private void setFile_name(String file_name) {
		this.file_name = file_name;
	}

	public String getFile_name() {
		return file_name;
	}
	
	private void setFile_dir(String file_dir) {
		this.file_dir = file_dir;
	}

	public String getFile_dir() {
		return file_dir;
	}
	
	
	
	

	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(id);
		dest.writeString(title);
		dest.writeString(author);
		dest.writeString(genre);
		dest.writeString(lang);
		dest.writeString(publisher);
		dest.writeString(annotation);
		dest.writeString(type);
		dest.writeString(readpercent);
		dest.writeString(id_chapter);
		dest.writeString(id_word);
		dest.writeString(countWords);
		dest.writeString(image_url);
		dest.writeString(file_name);
		dest.writeString(file_dir);

	}
	
	public Item(Parcel parcel) {
		this.setId(parcel.readString());
		this.setTitle(parcel.readString());
		this.setAuthor(parcel.readString());
		this.setGenre(parcel.readString());
		this.setLang(parcel.readString());
		this.setPublisher(parcel.readString());
		this.setAnotation(parcel.readString());
		this.setType(parcel.readString());
		this.setReadpercent(parcel.readString());
		this.setId_chapter(parcel.readString());
		this.setId_word(parcel.readString());
		this.setCountWords(parcel.readString());
		this.setImage_url(parcel.readString());
		this.setFile_name(parcel.readString());
		this.setFile_dir(parcel.readString());
		
	}
	
	public static final Parcelable.Creator<Item> CREATOR
    = new Parcelable.Creator<Item>() {
	      public Item createFromParcel(Parcel source) {
	            return new Item(source);
	      }
	      public Item[] newArray(int size) {
	            return new Item[size];
	      }
	};
	
	
	
}
