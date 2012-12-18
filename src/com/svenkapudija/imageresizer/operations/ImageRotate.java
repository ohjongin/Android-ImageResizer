package com.svenkapudija.imageresizer.operations;

import java.io.File;

import android.graphics.Bitmap;
import android.graphics.Matrix;

import com.svenkapudija.imageresizer.utils.ImageDecoder;

public class ImageRotate {

	private File original;
	private ImageRotation rotation;
	
	public ImageRotate(File original, ImageRotation rotation) {
		this.original = original;
		this.rotation = rotation;
	}

	public Bitmap rotate() {
		Bitmap sampledSrcBitmap = ImageDecoder.decodeFile(original);
		if(sampledSrcBitmap == null) {
			return null;
		}
		
		int width = sampledSrcBitmap.getWidth();
		int height = sampledSrcBitmap.getHeight();
		
		Matrix matrix = new Matrix();
		if(rotation == ImageRotation.FLIP_HORIZONTAL) {
			matrix.setScale(-1, 1);
			matrix.postTranslate(width, 0);
		} else if(rotation == ImageRotation.FLIP_VERTICAL) {
			matrix.setScale(1, -1);
			matrix.postTranslate(0, height);
		} else {
			matrix.setRotate(ImageRotation.inDegrees(rotation), width/2, height/2);
		}
		
		return Bitmap.createBitmap(sampledSrcBitmap, 0, 0, sampledSrcBitmap.getWidth(), sampledSrcBitmap.getHeight(), matrix, true);
	}
	
}
