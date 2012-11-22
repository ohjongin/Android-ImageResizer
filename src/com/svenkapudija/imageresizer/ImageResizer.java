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
		
		// Resize based on mode
		if(mode == ResizeMode.AUTOMATIC) {
			Bitmap resized = Bitmap.createScaledBitmap(original, width, height, true);
			original.recycle();
			return resized;
		} else if(mode == ResizeMode.FIT_TO_WIDTH) {
			
		} else if(mode == ResizeMode.FIT_TO_HEIGHT) {
			
		} else if(mode == ResizeMode.FIT_EXACT) {
			
		}
		
		return null;
	}
	
	/*** CROP ***/
	public static Bitmap crop(Bitmap original, int width, int height) {
		return null;
	}
	
	public static Bitmap crop(Bitmap original, int x, int y, int width, int height) {
		return null;
	}
	
	public static Bitmap crop(Bitmap original, int width, int height, DimensionUnit unit, Context ... context) {
		return null;
	}
	
	public static Bitmap crop(Bitmap original, int x, int y, int width, int height, DimensionUnit unit, Context ... context) {
		try {
			width = DimensionUnit.convertToPixels(unit, width, context);
			height = DimensionUnit.convertToPixels(unit, height, context);
		} catch (ImageResizerException e) {
			Log.e(TAG, e.getMessage());
			return null;
		}
		
		return null;
	}
	
	/*** ROTATE ***/
	public static Bitmap rotate(Bitmap original, ImageRotation rotation) {
		return null;
	}
	
}
