package com.svenkapudija.imageresizer.operations;

import java.io.File;

import com.svenkapudija.imageresizer.utils.ImageWriter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ImageRotate {

	public static Bitmap rotate(Bitmap original, ImageRotation rotation) {
		return ImageScalingRotating.rotate(original, original.getWidth(), original.getHeight(), rotation);
	}
	
	public static Bitmap rotate(File original, boolean overwrite, ImageRotation rotation) {
		Bitmap originalBitmap = BitmapFactory.decodeFile(original.getAbsolutePath());
		
		Bitmap rotatedBitmap = ImageScalingRotating.rotate(originalBitmap, originalBitmap.getWidth(), originalBitmap.getHeight(), rotation);
		originalBitmap.recycle();
		
		if(overwrite) {
			ImageWriter.writeToFile(rotatedBitmap, original);
		}
		
		return rotatedBitmap;
	}
	
}
