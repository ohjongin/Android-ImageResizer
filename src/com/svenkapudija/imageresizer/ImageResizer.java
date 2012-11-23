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
	
	/**
	 * <p>Resize your bitmap image to provided <code>size</code> (width and height are the same).</p>
	 * 
	 * @param original		Your original image.
	 * @param size			Desired width and height.
	 * @return				Resized image.
	 */
	public static Bitmap resize(Bitmap original, int size) {
		return resize(original, size, size, null);
	}
	
	/**
	 * <p>Resize your bitmap image to provided <code>size</code> (width and height are the same).</p>
	 * 
	 * @param original		Your original image.
	 * @param size			Desired width and height.
	 * @param mode			<code>AUTOMATIC</code> resize mode chooses <code>FIT_TO_WIDTH</code> or <code>FIT_TO_HEIGHT</code> based
	 * 						on original image orientation (landscape - <code>FIT_TO_WIDTH</code>, portrait - <code>FIT_TO_HEIGHT</code>).
	 * @return				Resized image.
	 */
	public static Bitmap resize(Bitmap original, int size, ResizeMode mode) {
		return resize(original, size, size, mode, null);
	}
	
	/**
	 * <p>Resize your bitmap image to provided <code>size</code> (width and height are the same).</p>
	 * 
	 * @param original		Your original image.
	 * @param size			Desired width and height.
	 * @param unit			If you intend to use <code>DP dimension unit</code> be sure to provide valid <code>context</code>,
	 * 						otherwise it's not needed.
	 * @param context		Required if using DP dimension unit.
	 * @return				Resized image.
	 */
	public static Bitmap resize(Bitmap original, int size, DimensionUnit unit, Context ... context) {
		return resize(original, size, size, null, unit, context);
	}
	
	/**
	 * <p>Resize your bitmap image to provided <code>size</code> (width and height are the same).</p>
	 * 
	 * @param original		Your original image.
	 * @param width			Desired width.
	 * @param height		Desired height.
	 * @param mode			<code>AUTOMATIC</code> resize mode chooses <code>FIT_TO_WIDTH</code> or <code>FIT_TO_HEIGHT</code> based
	 * 						on original image orientation (landscape - <code>FIT_TO_WIDTH</code>, portrait - <code>FIT_TO_HEIGHT</code>).
	 * @param unit			If you intend to use <code>DP dimension unit</code> be sure to provide valid <code>context</code>,
	 * 						otherwise it's not needed.
	 * @param context		Required if using DP dimension unit.
	 * @return				Resized image.
	 */
	public static Bitmap resize(Bitmap original, int size, ResizeMode mode, DimensionUnit unit, Context ... context) {
		return resize(original, size, size, mode, unit, context);
	}
	
	/**
	 * <p>Resize your bitmap image to provided <code>width</code> and <code>height</code>.</p>
	 * 
	 * @param original		Your original image.
	 * @param width			Desired width.
	 * @param height		Desired height.
	 * @return				Resized image.
	 */
	public static Bitmap resize(Bitmap original, int width, int height) {
		return resize(original, width, height, null);
	}
	
	/**
	 * <p>Resize your bitmap image to provided <code>width</code> and <code>height</code>.</p>
	 * 
	 * @param original		Your original image.
	 * @param width			Desired width.
	 * @param height		Desired height.
	 * @param mode			<code>AUTOMATIC</code> resize mode chooses <code>FIT_TO_WIDTH</code> or <code>FIT_TO_HEIGHT</code> based
	 * 						on original image orientation (landscape - <code>FIT_TO_WIDTH</code>, portrait - <code>FIT_TO_HEIGHT</code>).
	 * @return				Resized image.
	 */
	public static Bitmap resize(Bitmap original, int width, int height, ResizeMode mode) {
		return resize(original, width, height, mode, null);
	}
	
	/**
	 * <p>Resize your bitmap image to provided <code>width</code> and <code>height</code>.</p>
	 * 
	 * @param original		Your original image.
	 * @param width			Desired width.
	 * @param height		Desired height.
	 * @param unit			If you intend to use <code>DP dimension unit</code> be sure to provide valid <code>context</code>,
	 * 						otherwise it's not needed.
	 * @param context		Required if using DP dimension unit.
	 * @return				Resized image.
	 */
	public static Bitmap resize(Bitmap original, int width, int height, DimensionUnit unit, Context ... context) {
		return resize(original, width, height, null, unit, context);
	}
	
	/**
	 * <p>Resize your bitmap image to provided <code>width</code> and <code>height</code>.</p>
	 * 
	 * @param original		Your original image.
	 * @param width			Desired width.
	 * @param height		Desired height.
	 * @param mode			<code>AUTOMATIC</code> resize mode chooses <code>FIT_TO_WIDTH</code> or <code>FIT_TO_HEIGHT</code> based
	 * 						on original image orientation (landscape - <code>FIT_TO_WIDTH</code>, portrait - <code>FIT_TO_HEIGHT</code>).
	 * @param unit			If you intend to use <code>DP dimension unit</code> be sure to provide valid <code>context</code>,
	 * 						otherwise it's not needed.
	 * @param context		Required if using DP dimension unit.
	 * @return				Resized image.
	 */
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
	
	/**
	 * Scales or rotates a bitmap using custom scaling because <code>Bitmap.createScaledImage</code> provides
	 * poor resulting image quality.
	 * 
	 * @param original
	 * @param width
	 * @param height
	 * @param rotation
	 * @return
	 */
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
		byteArray = null;
		
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
	
	/**
	 * Crops the image to specific <code>width</code> and <code>height</code>.
	 * 
	 * @param original		Original image.
	 * @param width			Desired width
	 * @param height		Desired height
	 * @return				Cropped image. If any of the original dimensions are smaller than desired ones,
	 * 						original image is returned.
	 */
	public static Bitmap crop(Bitmap original, int width, int height) {
		return crop(original, -1, -1, width, height, null);
	}
	
	/**
	 * Crops the image to specific <code>width</code> and <code>height</code>.
	 * 
	 * @param original		Original image.
	 * @param x				The X coordinate of the first pixel in source
	 * @param y				The Y coordinate of the first pixel in source
	 * @param width			Desired width
	 * @param height		Desired height
	 * @return				Cropped image. If any of the original dimensions are smaller than desired ones,
	 * 						original image is returned.
	 */
	public static Bitmap crop(Bitmap original, int x, int y, int width, int height) {
		return crop(original, x, y, width, height, null);
	}
	
	/**
	 * Crops the image to specific <code>width</code> and <code>height</code>.
	 * 
	 * @param original		Original image.
	 * @param width			Desired width
	 * @param height		Desired height
	 * @param unit			If you intend to use <code>DP dimension unit</code> be sure to provide valid <code>context</code>,
	 * 						otherwise it's not needed.
	 * @param context		Required if using DP dimension unit.
	 * @return				Cropped image. If any of the original dimensions are smaller than desired ones,
	 * 						original image is returned.
	 */
	public static Bitmap crop(Bitmap original, int width, int height, DimensionUnit unit, Context ... context) {
		return crop(original, -1, -1, width, height, unit, context);
	}
	
	/**
	 * Crops the image to specific <code>width</code> and <code>height</code>.
	 * 
	 * @param original		Original image.
	 * @param x				The X coordinate of the first pixel in source
	 * @param y				The Y coordinate of the first pixel in source
	 * @param width			Desired width
	 * @param height		Desired height
	 * @param unit			If you intend to use <code>DP dimension unit</code> be sure to provide valid <code>context</code>,
	 * 						otherwise it's not needed.
	 * @param context		Required if using DP dimension unit.
	 * @return				Cropped image. If any of the original dimensions are smaller than desired ones,
	 * 						original image is returned.
	 */
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
	
	/**
	 * Rotates the image by 90, 180 or 270 degrees. Also can flip the image horizontally
	 * or vertically.
	 * 
	 * @param original		Original image.
	 * @param rotation		Rotation type.
	 * @return				Rotated/flipped image.
	 */
	public static Bitmap rotate(Bitmap original, ImageRotation rotation) {
		return createBitmap(original, original.getWidth(), original.getHeight(), rotation);
	}
	
}
