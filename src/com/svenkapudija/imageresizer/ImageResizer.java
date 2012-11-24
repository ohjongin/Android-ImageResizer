package com.svenkapudija.imageresizer;

import java.io.ByteArrayOutputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Log;

public class ImageResizer {

	private static final String TAG = ImageResizer.class.getName();
	
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
		
		return createBitmap(original, width, height);
	}

	private static ResizeMode calculateResizeMode(int width, int height) {
		if(getOrientation(width, height) == ImageOrientation.LANDSCAPE)
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

	private static Bitmap createBitmap(Bitmap original, int width, int height, ImageRotation ... rotation) {
		// Retrieved from http://stackoverflow.com/questions/4231817
		
		int inSampleSize = calculateInSampleSize(width, original.getWidth());

		byte[] originalByteArray = convertBitmapToByteArray(original);
		Bitmap sampledSrcBitmap = decodeBitmapWithInSampleSize(originalByteArray, inSampleSize);
		
		Bitmap resultBitmap = null;
		if(rotation != null && rotation.length > 0 && rotation[0] != null) {
			resultBitmap = rotateBitmap(sampledSrcBitmap, width, height, rotation);
		} else {
			float desiredScale = calculateDesiredScale(width, original.getWidth());
			resultBitmap = scaleBitmap(sampledSrcBitmap, desiredScale);
		}
		
		sampledSrcBitmap.recycle();

		return resultBitmap;
	}

	private static int calculateInSampleSize(int width, int srcWidth) {
		int inSampleSize = 1;
		while(srcWidth / 2 > width){
		    inSampleSize *= 2;
		}
		
		return inSampleSize;
	}
	
	private static float calculateDesiredScale(int width, int srcWidth) {
		while(srcWidth / 2 > width){
		    srcWidth /= 2;
		}
		
		return (float) width / srcWidth;
	}

	private static Bitmap scaleBitmap(Bitmap sampledSrcBitmap, float desiredScale) {
		Matrix matrix = new Matrix();
		matrix.postScale(desiredScale, desiredScale);
		
		return Bitmap.createBitmap(sampledSrcBitmap, 0, 0, sampledSrcBitmap.getWidth(), sampledSrcBitmap.getHeight(), matrix, true);
	}

	private static Bitmap rotateBitmap(Bitmap sampledSrcBitmap, int width, int height, ImageRotation... rotation) {
		Matrix matrix = new Matrix();
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
		
		return Bitmap.createBitmap(sampledSrcBitmap, 0, 0, sampledSrcBitmap.getWidth(), sampledSrcBitmap.getHeight(), matrix, true);
	}

	private static Bitmap decodeBitmapWithInSampleSize(byte[] originalByteArray, int inSampleSize) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = false;
		options.inDither = false;
		options.inSampleSize = inSampleSize;
		options.inScaled = false;
		options.inPreferredConfig = Bitmap.Config.ARGB_8888;
		Bitmap sampledSrcBitmap = BitmapFactory.decodeByteArray(originalByteArray, 0, originalByteArray.length, options);
		originalByteArray = null;
		
		return sampledSrcBitmap;
	}

	private static byte[] convertBitmapToByteArray(Bitmap original) {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		original.compress(Bitmap.CompressFormat.PNG, 100, stream);
		byte[] byteArray = stream.toByteArray();
		original.recycle();
		return byteArray;
	}
	
	private static ImageOrientation getOrientation(int width, int height) {
		if(originalIsWiderThanCroppedImage(width, height)) {
			return ImageOrientation.LANDSCAPE;
		} else {
			return ImageOrientation.PORTRAIT;
		}
	}
	
	private enum ImageOrientation {
		
		PORTRAIT,
		LANDSCAPE
		
	}
	
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
		
        Bitmap resizedBitmap = createBitmap(original, newWidth, newHeight);
        
        x = calculateX(x, newWidth, width);
		y = calculateY(y, newHeight, height);
        Bitmap croppedBitmap = Bitmap.createBitmap(resizedBitmap, x, y, width, height);
        resizedBitmap.recycle();
        
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

	public static Bitmap rotate(Bitmap original, ImageRotation rotation) {
		return createBitmap(original, original.getWidth(), original.getHeight(), rotation);
	}
	
}
