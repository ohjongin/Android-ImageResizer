package com.svenkapudija.imageresizer;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

public class ImageResizer {

	private static final String TAG = ImageResizer.class.getName();
	
	/*** RESIZE ***/
	public static Bitmap resize(Bitmap original, int size) {
		return resize(original, size, size, null);
	}
	
	public static Bitmap resize(Bitmap original, int size, ResizeMode mode) {
		return resize(original, size, size, mode, null);
	}
	
	public static Bitmap resize(Bitmap original, int size, DimensionUnit unit, Context ... context) {
		return resize(original, size, size, null, unit, context);
	}
	
	public static Bitmap resize(Bitmap original, int size, ResizeMode mode, DimensionUnit unit, Context ... context) {
		return resize(original, size, size, mode, unit, context);
	}
	
	public static Bitmap resize(Bitmap original, int width, int height) {
		return resize(original, width, height, null);
	}
	
	public static Bitmap resize(Bitmap original, int width, int height, ResizeMode mode) {
		return resize(original, width, height, mode, null);
	}
	
	public static Bitmap resize(Bitmap original, int width, int height, DimensionUnit unit, Context ... context) {
		return resize(original, width, height, null, unit, context);
	}
	
	public static Bitmap resize(Bitmap original, int width, int height, ResizeMode mode, DimensionUnit unit, Context ... context) {
		if(unit == null) {
			unit = DimensionUnit.PX;
		}
		
		// Convert all units to pixels
		try {
			width = DimensionUnit.convertToPixels(unit, width, context);
			height = DimensionUnit.convertToPixels(unit, height, context);
		} catch (ImageResizerException e) {
			Log.e(TAG, e.getMessage());
			return null;
		}
		
		if(mode == null) {
			mode = ResizeMode.AUTOMATIC;
		}
		
		// Calculate is it better to resize FIT_TO_WIDTH or
		// FIT_TO_HEIGHT
		if(mode == ResizeMode.AUTOMATIC) {
			if(getOrientation(original.getWidth(), original.getHeight()) == ImageOrientation.LANDSCAPE) {
				mode = ResizeMode.FIT_TO_WIDTH;
			} else {
				mode = ResizeMode.FIT_TO_HEIGHT;
			}
		}
		
		if(mode == ResizeMode.FIT_TO_WIDTH) {
			height = original.getHeight() / (original.getWidth()/width);
		} else if(mode == ResizeMode.FIT_TO_HEIGHT) {
			width = original.getWidth() / (original.getHeight()/height);
		}
		
		Bitmap resized = Bitmap.createScaledBitmap(original, width, height, true);
		original.recycle();
		return resized;
	}
	
	private static ImageOrientation getOrientation(int width, int height) {
		if(width >= height) {
			return ImageOrientation.LANDSCAPE;
		} else {
			return ImageOrientation.PORTRAIT;
		}
	}
	
	private enum ImageOrientation {
		
		PORTRAIT,
		LANDSCAPE
		
	}
	
	/*** CROP ***/
	public static Bitmap crop(Bitmap original, int width, int height) {
		return crop(original, -1, -1, width, height, null);
	}
	
	public static Bitmap crop(Bitmap original, int x, int y, int width, int height) {
		return crop(original, x, y, width, height, null);
	}
	
	public static Bitmap crop(Bitmap original, int width, int height, DimensionUnit unit, Context ... context) {
		return crop(original, -1, -1, width, height, unit, context);
	}
	
	public static Bitmap crop(Bitmap original, int x, int y, int width, int height, DimensionUnit unit, Context ... context) {
		try {
			width = DimensionUnit.convertToPixels(unit, width, context);
			height = DimensionUnit.convertToPixels(unit, height, context);
		} catch (ImageResizerException e) {
			Log.e(TAG, e.getMessage());
			return null;
		}
		
		if(original.getWidth() < width || original.getHeight() < height)
    		return original;
		
		int newWidth, newHeight;
		int originalAspectRatio = original.getWidth() / original.getHeight();
		int croppedAspectRatio = width / height;

		if (originalAspectRatio >= croppedAspectRatio) {
			// If the original is wider than cropped image
			newHeight = height;
			newWidth = original.getWidth() / (original.getHeight() / height);
		} else {
			// If the cropped image is wider than the original
			newWidth = width;
			newHeight = original.getHeight() / (original.getWidth() / width);
		}
		
        Bitmap result = Bitmap.createScaledBitmap(original, newWidth, newHeight, true);
        original.recycle();
        
        if(x < 0) {
        	x = (newWidth - width) / 2;
        }
        
        if(y < 0) {
        	y = (newHeight - height) / 2;
        }
        
    	return Bitmap.createBitmap(result, x, y, width, height);
	}
	
	/*** ROTATE ***/
	public static Bitmap rotate(Bitmap original, ImageRotation rotation) {
		return null;
	}
	
}
