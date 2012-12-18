package com.svenkapudija.imageresizer.operations;

import java.io.File;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ImageCrop {

	private static final String TAG = ImageCrop.class.getName();
	
	private File original;
	private int x;
	private int y;
	private int width;
	private int height;
	
	public ImageCrop(File original, int x, int y, int width, int height) {
		this.original = original;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public Bitmap crop() {
		BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Bitmap sampledSrcBitmap = BitmapFactory.decodeFile(original.getAbsolutePath(), options);
		if(sampledSrcBitmap == null) {
			return null;
		}
		
		int sourceWidth = options.outWidth;
		int sourceHeight = options.outHeight;
		
		if(originalIsSmallerThanResult(sourceWidth, sourceHeight, width, height))
    		return sampledSrcBitmap;
		
		int newWidth, newHeight;
		int originalAspectRatio = sourceWidth / sourceHeight;
		int croppedAspectRatio = width / height;

		if (originalIsWiderThanCroppedImage(originalAspectRatio, croppedAspectRatio)) {
			newHeight = height;
			newWidth = sourceWidth / (sourceHeight / height);
		} else {
			newWidth = width;
			newHeight = sourceHeight / (sourceWidth / width);
		}
		
        Bitmap resizedBitmap = new ImageResize(original, newWidth, newHeight, null).resize();
        
        x = calculateX(x, resizedBitmap.getWidth(), width);
		y = calculateY(y, resizedBitmap.getHeight(), height);
		
        Bitmap croppedBitmap = Bitmap.createBitmap(resizedBitmap, x, y, width, height);
        resizedBitmap.recycle();
        
    	return croppedBitmap;
	}
	
	private boolean originalIsWiderThanCroppedImage(int originalAspectRatio, int croppedAspectRatio) {
		return originalAspectRatio >= croppedAspectRatio;
	}

	private boolean originalIsSmallerThanResult(int originalWidth, int originalHeight, int width, int height) {
		return originalWidth < width || originalHeight < height;
	}
	
	private int calculateX(int x, int newWidth, int width) {
        if(x < 0) {
        	x = (newWidth - width) / 2;
        }
        
        return x;
	}
	
	private int calculateY(int y, int newHeight, int height) {
        if(y < 0) {
        	y = (newHeight - height) / 2;
        }
        
        return y;
	}

}
