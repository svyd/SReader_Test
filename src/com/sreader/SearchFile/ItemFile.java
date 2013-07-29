package com.sreader.SearchFile;

import com.sreader.store.Item;

import android.os.Parcel;
import android.os.Parcelable;

public class ItemFile implements Parcelable {
	public String file;
	public String icon;
	
	public ItemFile (String file, Integer icon) {
		// TODO Auto-generated constructor stub
		this.file = file;
		this.icon = String.valueOf(icon);
	}
	
	public ItemFile(Parcel parcel) {
		this.setFile(parcel.readString());
		this.setIcon(parcel.readString());
		
		
	}
	@Override
	public String toString() {
		return file;
	}
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(file);
		dest.writeString(icon);
	}
	public static final Parcelable.Creator<ItemFile> CREATOR
    = new Parcelable.Creator<ItemFile>() {
	      public ItemFile createFromParcel(Parcel source) {
	            return new ItemFile(source);
	      }
	      public ItemFile[] newArray(int size) {
	            return new ItemFile[size];
	      }
	};

}
