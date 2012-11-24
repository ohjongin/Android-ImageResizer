package com.svenkapudija.imageresizer;

import com.svenkapudija.imageresizer.operations.DimensionUnit;
import com.svenkapudija.imageresizer.operations.ImageCrop;
import com.svenkapudija.imageresizer.operations.ImageResize;
import com.svenkapudija.imageresizer.operations.ImageRotation;
import com.svenkapudija.imageresizer.operations.ImageScalingRotating;
import com.svenkapudija.imageresizer.operations.ResizeMode;

import android.content.Context;
import android.graphics.Bitmap;

public class ImageResizer {

	public static Bitmap resize(Bitmap original, int size) {
		return ImageResize.resize(original, size, size, null, null);
	}
	
	public static Bitmap resize(Bitmap original, int size, ResizeMode mode) {
		return ImageResize.resize(original, size, size, mode, null);
	}
	
	public static Bitmap resize(Bitmap original, int size, DimensionUnit unit, Context ... context) {
		return ImageResize.resize(original, size, size, null, unit, context);
	}
	
	public static Bitmap resize(Bitmap original, int size, ResizeMode mode, DimensionUnit unit, Context ... context) {
		return ImageResize.resize(original, size, size, mode, unit, context);
	}
	
	public static Bitmap resize(Bitmap original, int width, int height) {
		return ImageResize.resize(original, width, height, null, null);
	}
	
	public static Bitmap resize(Bitmap original, int width, int height, ResizeMode mode) {
		return ImageResize.resize(original, width, height, mode, null);
	}
	
	public static Bitmap resize(Bitmap original, int width, int height, DimensionUnit unit, Context ... context) {
		return ImageResize.resize(original, width, height, null, unit, context);
	}
	
	
	public static Bitmap crop(Bitmap original, int width, int height) {
		return ImageCrop.crop(original, -1, -1, width, height, null);
	}
	
	public static Bitmap crop(Bitmap original, int x, int y, int width, int height) {
		return ImageCrop.crop(original, x, y, width, height, null);
	}
	
	public static Bitmap crop(Bitmap original, int width, int height, DimensionUnit unit, Context ... context) {
		return ImageCrop.crop(original, -1, -1, width, height, unit, context);
	}
	
	
	public static Bitmap rotate(Bitmap original, ImageRotation rotation) {
		return ImageScalingRotating.rotate(original, original.getWidth(), original.getHeight(), rotation);
	}
	
}
