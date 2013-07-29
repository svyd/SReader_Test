package com.sreader.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.util.ByteArrayBuffer;

import android.content.Context;
import android.content.res.AssetManager;

public class FileManager{

      public void DownloadFromUrl(String http, String path, String filedown, String filesave) { 
              try {
            	//you can write here any link
            	      URL url = new URL(http+filedown); 
                      File file = new File(path, filesave);
                     /* Open a connection to that URL. */
                      URLConnection ucon = url.openConnection();
                      ucon.setConnectTimeout(5000);
                      ucon.setReadTimeout(15000);
                      // Define InputStreams to read from the URLConnection.
                      InputStream is = ucon.getInputStream();
                      BufferedInputStream bis = new BufferedInputStream(is);
                       //Read bytes to the Buffer until there is nothing more to read(-1).
                       ByteArrayBuffer baf = new ByteArrayBuffer(50);
                      int current = 0;
                      while ((current = bis.read()) != -1) {
                              baf.append((byte) current);
                      }
                      /* Convert the Bytes read to a String. */
                      FileOutputStream fos = new FileOutputStream(file);
                      fos.write(baf.toByteArray());
                      fos.close();

              } catch (IOException e) {
                  //    Log.d("ImageManager", "Error: " + e);
              }

      }
	
     public void CopyAssets(Context c, String dir) {
   
    	    AssetManager assetManager = c.getResources().getAssets();
    	    String[] files = null;
    	    try {
    	        files = assetManager.list(dir);
    	    } catch (IOException e) {
    	       // Log.e("tag", e.getMessage());
    	    }
    	    for(String filename : files) {
    	        InputStream in = null;
    	        OutputStream out = null;
    	        try {
    	          in = assetManager.open(dir+"/"+filename);
    	          String fullPath = "/data/data/"+c.getPackageName()+"/" + dir;
  	            File dirpath = new File(fullPath);
  	            if (!dirpath.exists())
  	            	dirpath.mkdir();
     	        
    	          out = new FileOutputStream(fullPath+"/" + filename);
    	          copyFile(in, out);
    	          in.close();
    	          in = null;
    	          out.flush();
    	          out.close();
    	          out = null;
    	        } catch(Exception e) {
    	          //  Log.e("tag", e.getMessage());
    	        }       
    	    }
    	}
     
     public void CopyAssetsToSDCard(Context c, String dir) {
    	   
 	    AssetManager assetManager = c.getResources().getAssets();
 	    String[] files = null;
 	    try {
 	        files = assetManager.list(dir
 	        		);
 	    } catch (IOException e) {
 	       // Log.e("tag", e.getMessage());
 	    }
 	    for(String filename : files) {
 	        InputStream in = null;
 	        OutputStream out = null;
 	        try {
 	          in = assetManager.open(dir+"/"+filename);
 	         File creatdir=new File(android.os.Environment.getExternalStorageDirectory(), "Books");
 	        if (!creatdir.exists())
 	        	creatdir.mkdir();
 	         // String fullPath = "/data/data/"+c.getPackageName()+"/" + dir;
 	         String fullPath =android.os.Environment.getExternalStorageDirectory()+"/Books"+"/" + dir;
 	        //  File dirpath = new File(fullPath);
 	         File dirpath=new File(android.os.Environment.getExternalStorageDirectory()+"/Books", dir);
	            if (!dirpath.exists())
	            	dirpath.mkdir();
  	        
 	         out = new FileOutputStream(fullPath+"/" + filename);
 	          copyFile(in, out);
 	          in.close();
 	          in = null;
 	          out.flush();
 	          out.close();
 	          out = null;
 	        } catch(Exception e) {
 	           // Log.e("tag", e.getMessage());
 	        }       
 	    }
 	}
     
    	private void copyFile(InputStream in, OutputStream out) throws IOException {
    	    byte[] buffer = new byte[1024];
    	    int read;
    	    while((read = in.read(buffer)) != -1){
    	      out.write(buffer, 0, read);
    	    }
    	}

        public	void Delete(File fileOrDirectory) {
    	    if (fileOrDirectory.isDirectory())
    	        for (File child : fileOrDirectory.listFiles())
    	            Delete(child);

    	    fileOrDirectory.delete();
    	}

	
}
