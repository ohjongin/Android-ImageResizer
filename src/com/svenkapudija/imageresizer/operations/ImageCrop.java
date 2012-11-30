package com.svenkapudija.imageresizer.operations;

import java.io.File;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.svenkapudija.imageresizer.ImageResizerException;
import com.svenkapudija.imageresizer.utils.ImageWriter;

public class ImageCrop {

	private static final String TAG = ImageCrop.class.getName();
	
	public static Bitmap crop(Bitmap original, int x, int y, int width, int height, DimensionUnit unit, Context ... context) {
		try {
			width = DimensionUnit.convertToPixels(unit, width, context);
			height = DimensionUnit.convertToPixels(unit, height, context);
		} catch (ImageResizerException e) {
			Log.e(TAG, e.getMessage());
			return null;
		}
		
		if(originalIsSmallerThanResult(original.getWidth(), original.getHeight(), width, height))
    		return original;
		
		int newWidth, newHeight;
		int originalAspectRatio = original.getWidth() / original.getHeight();
		int croppedAspectRatio = width / height;

		if (originalIsWiderThanCroppedImage(originalAspectRatio, croppedAspectRatio)) {
			newHeight = height;
			newWidth = original.getWidth() / (original.getHeight() / height);
		} else {
			newWidth = width;
			newHeight = original.getHeight() / (original.getWidth() / width);
		}
		
        Bitmap resizedBitmap = ImageScalingRotating.scale(original, newWidth, newHeight);
        
        x = calculateX(x, resizedBitmap.getWidth(), width);
		y = calculateY(y, resizedBitmap.getHeight(), height);
		
        Bitmap croppedBitmap = Bitmap.createBitmap(resizedBitmap, x, y, width, height);
        resizedBitmap.recycle();
        
    	return croppedBitmap;
	}
	
	public static Bitmap crop(File original, boolean overwrite, int x, int y, int width, int height, DimensionUnit unit, Context ... context) {
		Bitmap originalBitmap = BitmapFactory.decodeFile(original.getAbsolutePath());
		if(originalBitmap == null) {
			return null;
		}
		
		Bitmap croppedBitmap = crop(originalBitmap, x, y, width, height, unit, context);
		originalBitmap.recycle();
		
		if(overwrite) {
			ImageWriter.writeToFile(croppedBitmap, original);
		}
		
		return croppedBitmap;
	}

	private static boolean originalIsWiderThanCroppedImage(int originalAspectRatio, int croppedAspectRatio) {
		return originalAspectRatio >= croppedAspectRatio;
	}

	private static boolean originalIsSmallerThanResult(int originalWidth, int originalHeight, int width, int height) {
		return originalWidth < width || originalHeight < height;
	}
	
	private static int calculateX(int x, int newWidth, int width) {
        if(x < 0) {
        	x = (newWidth - width) / 2;
        }
        
        return x;
	}
	
	private static int calculateY(int y, int newHeight, int height) {
        if(y < 0) {
        	y = (newHeight - height) / 2;
        }
        
        return y;
	}

}
