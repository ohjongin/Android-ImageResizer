package com.svenkapudija.imageresizer.operations;

import com.svenkapudija.imageresizer.ImageResizerException;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

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
	
}
