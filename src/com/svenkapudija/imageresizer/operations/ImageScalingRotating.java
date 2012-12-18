package com.svenkapudija.imageresizer.operations;

import java.io.ByteArrayOutputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Log;

import com.svenkapudija.imageresizer.utils.ImageOrientation;

public class ImageScalingRotating {

	private static Bitmap scaleOrRotate(Bitmap original, int width, int height, ImageRotation ... rotation) {
		// Retrieved from http://stackoverflow.com/questions/4231817
		
		int inSampleSize = 0;
		if(ImageOrientation.getOrientation(width, height) == ImageOrientation.LANDSCAPE) {
			inSampleSize = calculateInSampleSize(width, original.getWidth());
		} else {
			inSampleSize = calculateInSampleSize(height, original.getHeight());
		}

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
		sampledSrcBitmap = null;
		
		return resultBitmap;
	}
	
	public static Bitmap scale(Bitmap original, int width, int height) {
		return scaleOrRotate(original, width, height);
	}
	
	public static Bitmap rotate(Bitmap original, int width, int height, ImageRotation ... rotation) {
		return scaleOrRotate(original, width, height, rotation);
	}

	private static int calculateInSampleSize(int size, int srcSize) {
		int inSampleSize = 1;
		while(srcSize / 2 >= size){
		    inSampleSize *= 4;
		    srcSize /= 2;
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
		original = null;
		return byteArray;
	}
	
}
