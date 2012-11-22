package com.svenkapudija.imageresizer;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

public class ImageResizer {

	private static final String TAG = ImageResizer.class.getName();
	
	/*** RESIZE ***/
	public static Bitmap resize(Bitmap original, int size) {
		return null;
	}
	
	public static Bitmap resize(Bitmap original, int size, ResizeMode mode) {
		return null;
	}
	
	public static Bitmap resize(Bitmap original, int size, DimensionUnit unit, Context ... context) {
		try {
			size = DimensionUnit.convertToPixels(unit, size, context);
		} catch (ImageResizerException e) {
			Log.e(TAG, e.getMessage());
			return null;
		}
		
		return null;
	}
	
	public static Bitmap resize(Bitmap original, int size, ResizeMode mode, DimensionUnit unit, Context ... context) {
		try {
			size = DimensionUnit.convertToPixels(unit, size, context);
		} catch (ImageResizerException e) {
			Log.e(TAG, e.getMessage());
			return null;
		}
		
		return null;
	}
	
	public static Bitmap resize(Bitmap original, int width, int height) {
		return null;
	}
	
	public static Bitmap resize(Bitmap original, int width, int height, ResizeMode mode) {
		return null;
	}
	
	public static Bitmap resize(Bitmap original, int width, int height, DimensionUnit unit, Context ... context) {
		try {
			width = DimensionUnit.convertToPixels(unit, width, context);
			height = DimensionUnit.convertToPixels(unit, height, context);
		} catch (ImageResizerException e) {
			Log.e(TAG, e.getMessage());
			return null;
		}
		
		return null;
	}
	
	public static Bitmap resize(Bitmap original, int width, int height, ResizeMode mode, DimensionUnit unit, Context ... context) {
		try {
			width = DimensionUnit.convertToPixels(unit, width, context);
			height = DimensionUnit.convertToPixels(unit, height, context);
		} catch (ImageResizerException e) {
			Log.e(TAG, e.getMessage());
			return null;
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
		try {
			width = DimensionUnit.convertToPixels(unit, width, context);
			height = DimensionUnit.convertToPixels(unit, height, context);
		} catch (ImageResizerException e) {
			Log.e(TAG, e.getMessage());
			return null;
		}
		
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
