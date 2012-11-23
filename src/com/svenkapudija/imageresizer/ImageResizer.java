package com.svenkapudija.imageresizer;

import java.io.ByteArrayOutputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
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
			height = (int) (original.getHeight() / ((double) original.getWidth()/width));
		} else if(mode == ResizeMode.FIT_TO_HEIGHT) {
			width = (int) (original.getWidth() / ((double) original.getHeight()/height));
		}
		
		return createBitmap(original, width, height);
	}
	
	private static Bitmap createBitmap(Bitmap original, int width, int height, ImageRotation ... rotation) {
		// Retrieved from http://stackoverflow.com/questions/4231817
		
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		original.compress(Bitmap.CompressFormat.PNG, 100, stream);
		byte[] byteArray = stream.toByteArray();
		
		int srcWidth = original.getWidth();
		//int srcHeight = original.getHeight();
		
		original.recycle();
		
		int inSampleSize = 1;
		while(srcWidth / 2 > width){
		    srcWidth /= 2;
		    //srcHeight /= 2;
		    inSampleSize *= 2;
		}

		float desiredScale = (float) width / srcWidth;

		// Decode with inSampleSize
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = false;
		options.inDither = false;
		options.inSampleSize = inSampleSize;
		options.inScaled = false;
		options.inPreferredConfig = Bitmap.Config.ARGB_8888;
		Bitmap sampledSrcBitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length, options);
		
		// Resize
		Matrix matrix = new Matrix();
		
		// Rotate if necessary
		if(rotation != null && rotation.length > 0 && rotation[0] != null) {
			ImageRotation rotationType = rotation[0];
			if(rotationType == ImageRotation.FLIP_HORIZONTAL) {
				matrix.setScale(-1, 1);
				matrix.postTranslate(width, 0);
			} else if(rotationType == ImageRotation.FLIP_VERTICAL) {
				matrix.setScale(1, -1);
				matrix.postTranslate(0, height);
			} else {
				matrix.setRotate(ImageRotation.inDegrees(rotation[0]), width/2, height/2);
			}
		} else {
			matrix.postScale(desiredScale, desiredScale);
		}
		
		Bitmap scaledBitmap = Bitmap.createBitmap(sampledSrcBitmap, 0, 0, sampledSrcBitmap.getWidth(), sampledSrcBitmap.getHeight(), matrix, true);
		sampledSrcBitmap.recycle();

		return scaledBitmap;
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
		if(unit == null) {
			unit = DimensionUnit.PX;
		}
		
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
		
        if(x < 0) {
        	x = (newWidth - width) / 2;
        }
        
        if(y < 0) {
        	y = (newHeight - height) / 2;
        }
        
        // Resize
        Bitmap scaledBitmap = createBitmap(original, newWidth, newHeight);
        
        // Crop
        Bitmap croppedBitmap = Bitmap.createBitmap(scaledBitmap, x, y, width, height);
        scaledBitmap.recycle();
        
    	return croppedBitmap;
	}
	
	/*** ROTATE ***/
	public static Bitmap rotate(Bitmap original, ImageRotation rotation) {
		return createBitmap(original, original.getWidth(), original.getHeight(), rotation);
	}
	
}
