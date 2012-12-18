package com.svenkapudija.imageresizer;

import java.io.File;

import android.graphics.Bitmap;

import com.svenkapudija.imageresizer.operations.ImageCrop;
import com.svenkapudija.imageresizer.operations.ImageResize;
import com.svenkapudija.imageresizer.operations.ImageRotate;
import com.svenkapudija.imageresizer.operations.ImageRotation;
import com.svenkapudija.imageresizer.operations.ResizeMode;

public class ImageResizer {

	// Resize - Files
	public static Bitmap resize(File original, int width, int height) {
		return new ImageResize(original, width, height, null).resize();
	}
	
	public static Bitmap resize(File original, int width, int height, ResizeMode mode) {
		return new ImageResize(original, width, height, mode).resize();
	}
	
	// Crop - Files
	public static Bitmap crop(File original, int width, int height) {
		return new ImageCrop(original, -1, -1, width, height).crop();
	}
	
	public static Bitmap crop(File original, int x, int y, int width, int height) {
		return new ImageCrop(original, x, y, width, height).crop();
	}
	
	// Rotate - Files
	public static Bitmap rotate(File original, ImageRotation rotation) {
		return new ImageRotate(original, rotation).rotate();
	}
	
}
