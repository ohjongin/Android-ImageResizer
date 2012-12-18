package com.svenkapudija.imageresizer.operations;

import java.io.File;

import android.graphics.Bitmap;
import android.graphics.Matrix;

import com.svenkapudija.imageresizer.utils.ImageDecoder;
import com.svenkapudija.imageresizer.utils.ImageOrientation;

public class ImageResize {
	
	private static final String TAG = ImageResize.class.getName();
	
	private File original;
	private int width;
	private int height;
	private ResizeMode mode;
	
	public ImageResize(File original, int width, int height, ResizeMode mode) {
		this.original = original;
		this.width = width;
		this.height = height;
		this.mode = mode;
	}

	public Bitmap resize() {
		Bitmap sampledSrcBitmap = ImageDecoder.decodeFile(original, width, height);
		if(sampledSrcBitmap == null) {
			return null;
		}
		
		int sourceWidth = sampledSrcBitmap.getWidth();
		int sourceHeight = sampledSrcBitmap.getHeight();
		
		if(mode == null || mode == ResizeMode.AUTOMATIC) {
			mode = calculateResizeMode(sourceWidth, sourceHeight);
		}
		
		if(mode == ResizeMode.FIT_TO_WIDTH) {
			height = calculateHeight(sourceWidth, sourceHeight, width);
		} else if(mode == ResizeMode.FIT_TO_HEIGHT) {
			width = calculateWidth(sourceWidth, sourceHeight, height);
		}
		
		float desiredScale = calculateDesiredScale(width, sourceWidth);
		Matrix matrix = new Matrix();
		matrix.postScale(desiredScale, desiredScale);
		
		return Bitmap.createBitmap(sampledSrcBitmap, 0, 0, width, height, matrix, true);
	}
	
	private float calculateDesiredScale(int width, int srcWidth) {
		while(srcWidth / 2 > width){
		    srcWidth /= 2;
		}
		
		return (float) width / srcWidth;
	}

	
	private ResizeMode calculateResizeMode(int width, int height) {
		if(ImageOrientation.getOrientation(width, height) == ImageOrientation.LANDSCAPE) {
			return ResizeMode.FIT_TO_WIDTH;
		} else {
			return ResizeMode.FIT_TO_HEIGHT;
		}
	}
	
	private int calculateWidth(int originalWidth, int originalHeight, int height) {
		return (int) (originalWidth / ((double) originalHeight/height));
	}

	private int calculateHeight(int originalWidth, int originalHeight, int width) {
		return (int) (originalHeight / ((double) originalWidth/width));
	}
	
}
