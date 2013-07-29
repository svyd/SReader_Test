package com.sreader.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import android.util.Log;

public class UnZip{
	 private String _zipFile; 
	  private String _location; 
	  private ZipFile zf;
	  private ArrayList<String> fileNamesFromZip;

    public UnZip(String zipFile, String location, ArrayList<String> set_fileNamesFromZip) {
    	_zipFile = zipFile; 
        _location = location; 
        fileNamesFromZip=set_fileNamesFromZip; 
       
        _dirChecker(""); 
    }
    public void unzip() { 
        try  { 
          FileInputStream fin = new FileInputStream(_zipFile); 
          ZipInputStream zin = new ZipInputStream(fin); 
          ZipEntry ze = null; 
          while ((ze = zin.getNextEntry()) != null) { 
        	  fileNamesFromZip.add(ze.getName());
        	  Log.v("Decompress", "Unzipping " + ze.getName()); 
     
            if(ze.isDirectory()) { 
              _dirChecker(ze.getName()); 
            } else { 
              FileOutputStream fout = new FileOutputStream(_location + ze.getName()); 
              for (int c = zin.read(); c != -1; c = zin.read()) { 
                fout.write(c); 
              } 
     
              zin.closeEntry(); 
              fout.close(); 
            } 
             
          } 
          zin.close(); 
        } catch(Exception e) { 
          Log.e("Decompress", "unzip", e); 
        } 
     
      } 

    private void _dirChecker(String dir) { 
        File f = new File(_location + dir); 
     
        if(!f.isDirectory()) { 
          f.mkdirs(); 
        } 
      }
    
  /*  public ArrayList<String> getFileListOfZip()
    {
      ArrayList<String> localArrayList = new ArrayList<String>();
      if (this.zf != null){
        Enumeration<? extends ZipEntry> localEnumeration = this.zf.entries();
        while (localEnumeration.hasMoreElements())
          localArrayList.add(((ZipEntry)localEnumeration.nextElement()).getName());
      }
      return localArrayList;
    }*/
}
