package com.sreader.util;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import javax.xml.parsers.SAXParserFactory;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Base64;
import android.util.TypedValue;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

public abstract class images {

	
	public static Drawable getDrawable(Bitmap set_bmp){
		return new BitmapDrawable(set_bmp).mutate();
		
		
	}
	
	public static Bitmap getBitmapFromBinar(String base64) {

	    byte[] data = Base64.decode(base64, Base64.DEFAULT);
	    return BitmapFactory.decodeByteArray(data, 0, data.length);
	}
	
public static void getNameFileSave(String dir, String filename, Bitmap bmp){
	
	File dirs=new File(dir);
	if (!dirs.exists())
		dirs.mkdirs();
	
	try {
	       FileOutputStream out = new FileOutputStream(dir+"/"+filename);
	       Bitmap resizedbmp = Bitmap.createScaledBitmap(bmp, 100, 150, true);
	       resizedbmp.compress(Bitmap.CompressFormat.PNG, 90, out);
	} catch (Exception e) {
	       e.printStackTrace();
	}
	

}

public static void saveCoverToFileWithXml(String filename_xml, String filedir_xml, String filename_img, String filedir_img){
	ArrayList<String> arr_binar=new ArrayList<String>();
	File dir = new File(filedir_xml);
	File file = new File(dir,filename_xml); 
	SAXParserFactory factory = SAXParserFactory.newInstance();
  /*      try {
        SAXParser parser = factory.newSAXParser();
     //   getBinarySax saxp = new getBinarySax(arr_binar);

       
			parser.parse(file, saxp);
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
*/
        
        if (arr_binar.size()>0){
        	getNameFileSave(filedir_img, filename_img, getBitmapFromBinar(arr_binar.get(0)));
        }
}
public static Bitmap getDropShadow(Bitmap bitmap) {

    if (bitmap==null) return null;
    int think = 10;
    int w = bitmap.getWidth();
    int h = bitmap.getHeight();

    int newW = w - (think);
    int newH = h - (think);

    Bitmap.Config conf = Bitmap.Config.ARGB_8888;
    Bitmap bmp = Bitmap.createBitmap(w, h, conf);
    Bitmap sbmp = Bitmap.createScaledBitmap(bitmap, newW, newH, false);

    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Canvas c = new Canvas(bmp);

    // Right
    Shader rshader = new LinearGradient(0, 0, think, 0, Color.LTGRAY, Color.GRAY, Shader.TileMode.CLAMP);
    paint.setShader(rshader);
    c.drawRect(0, 0, newW, h, paint);

    // Bottom
   /* Shader bshader = new LinearGradient(0, newH, 0, h, Color.LTGRAY, Color.GRAY, Shader.TileMode.CLAMP);
    paint.setShader(bshader);
    c.drawRect(think, newH, newW  , h, paint);
*/
    //Corner
    Shader cchader = new LinearGradient(0, newH, 0, h, Color.LTGRAY, Color.LTGRAY, Shader.TileMode.CLAMP);
    paint.setShader(cchader);
    c.drawRect(newW, newH, w  , h, paint);


    c.drawBitmap(sbmp, think, think, null);

    return bmp;
}

public static void setImageFromFile(Context context, ImageView imageview, String imgurl ){
	
	
	if ((imgurl!=null) && (imgurl.length()>0)) {
    	try {
    	File file = new File(imgurl);
    	  
    	   if (file.exists()) {  
    		
    		   Bitmap bitmapimage=BitmapFactory.decodeFile(imgurl);	
			imageview.setImageBitmap(bitmapimage);
    	   }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
		}
    	   }
	
	int newHeight =(int)( ((WindowManager) context.getSystemService(context.WINDOW_SERVICE)).getDefaultDisplay().getHeight() / 4.5);
	int orgWidth = imageview.getDrawable().getIntrinsicWidth();
	int orgHeight = imageview.getDrawable().getIntrinsicHeight();

	int newWidth = ((int) Math.floor((orgWidth * newHeight) / orgHeight));
	LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(newWidth, newHeight);
	params.setMargins(getPixels(context, 7), getPixels(context, 5), getPixels(context, 4), getPixels(context, 0));
	imageview.setLayoutParams(params);
    imageview.setScaleType(ImageView.ScaleType.FIT_XY);

}

///////////преобразование dp в px для установки параметров///////////////

public static int getPixels(Context context, int dipValue){
Resources r = context.getResources();
int px = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, 
r.getDisplayMetrics());
return px;
}
	
}
