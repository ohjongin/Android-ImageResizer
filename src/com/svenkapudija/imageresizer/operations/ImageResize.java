package com.svenkapudija.imageresizer.operations;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.svenkapudija.imageresizer.ImageResizerException;
import com.svenkapudija.imageresizer.utils.ImageOrientation;
import com.svenkapudija.imageresizer.utils.ImageWriter;

public class ImageResize {
	
	private static final String TAG = ImageResize.class.getName();

	public static Bitmap resize(Bitmap original, int width, int height, ResizeMode mode, DimensionUnit unit, Context ... context) {
		try {
			width = DimensionUnit.convertToPixels(unit, width, context);
			height = DimensionUnit.convertToPixels(unit, height, context);
		} catch (ImageResizerException e) {
			Log.e(TAG, e.getMessage());
			return null;
		}
		
		if(mode == null || mode == ResizeMode.AUTOMATIC) {
			mode = calculateResizeMode(original.getWidth(), original.getHeight());
		}
		
		if(mode == ResizeMode.FIT_TO_WIDTH) {
			height = calculateHeight(original.getWidth(), original.getHeight(), width);
		} else if(mode == ResizeMode.FIT_TO_HEIGHT) {
			width = calculateWidth(original.getWidth(), original.getHeight(), height);
		}
		
		return ImageScalingRotating.scale(original, width, height);
	}
	
	public static Bitmap resize(File original, boolean overwrite, int width, int height, ResizeMode mode, DimensionUnit unit, Context ... context) {
		Bitmap originalBitmap = decodeFile(original, Math.max(width, height));
		if(originalBitmap == null) {
			return null;
		}
		
		Bitmap scaledBitmap = resize(originalBitmap, width, height, mode, unit, context);
		originalBitmap.recycle();
		
		if(overwrite) {
			ImageWriter.writeToFile(scaledBitmap, original);
		}
		
		return scaledBitmap;
	}
	
	private static Bitmap decodeFile(File f, int size){
	    try {
	        BitmapFactory.Options o = new BitmapFactory.Options();
	        o.inJustDecodeBounds = true;
	        BitmapFactory.decodeStream(new FileInputStream(f),null,o);

	        int scale=1;
	        while(o.outWidth/scale/2 >= size && o.outHeight/scale/2 >= size) {
	        	scale*=2;
	        }

	        BitmapFactory.Options o2 = new BitmapFactory.Options();
	        o2.inSampleSize=scale;
	        return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
	    } catch (FileNotFoundException ignorable) {}
	    
	    return null;
	}
	
	private static ResizeMode calculateResizeMode(int width, int height) {
		if(ImageOrientation.getOrientation(width, height) == ImageOrientation.LANDSCAPE)
			return ResizeMode.FIT_TO_WIDTH;
		else
			return ResizeMode.FIT_TO_HEIGHT;
	}
	
	private static int calculateWidth(int originalWidth, int originalHeight, int height) {
		return (int) (originalWidth / ((double) originalHeight/height));
	}

	private static int calculateHeight(int originalWidth, int originalHeight, int width) {
		return (int) (originalHeight / ((double) originalWidth/width));
	}
	
}
