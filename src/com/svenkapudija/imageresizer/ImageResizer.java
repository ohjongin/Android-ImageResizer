package com.svenkapudija.imageresizer;

import java.io.File;

import android.content.Context;
import android.graphics.Bitmap;

import com.svenkapudija.imageresizer.operations.DimensionUnit;
import com.svenkapudija.imageresizer.operations.ImageCrop;
import com.svenkapudija.imageresizer.operations.ImageResize;
import com.svenkapudija.imageresizer.operations.ImageRotate;
import com.svenkapudija.imageresizer.operations.ImageRotation;
import com.svenkapudija.imageresizer.operations.ResizeMode;

public class ImageResizer {

	// Resize - Bitmaps
	
	public static Bitmap resize(Bitmap original, int width, int height) {
		return ImageResize.resize(original, width, height, null, null);
	}
	
	public static Bitmap resize(Bitmap original, int width, int height, ResizeMode mode) {
		return ImageResize.resize(original, width, height, mode, null);
	}
	
	public static Bitmap resize(Bitmap original, int width, int height, DimensionUnit unit, Context ... context) {
		return ImageResize.resize(original, width, height, null, unit, context);
	}
	
	public static Bitmap resize(Bitmap original, int width, int height, ResizeMode mode, DimensionUnit unit, Context ... context) {
		return ImageResize.resize(original, width, height, mode, unit, context);
	}
	
	// Resize - Files
	
	public static Bitmap resize(File original, boolean overwrite, int width, int height) {
		return ImageResize.resize(original, overwrite, width, height, null, null);
	}
	
	public static Bitmap resize(File original, boolean overwrite, int width, int height, ResizeMode mode) {
		return ImageResize.resize(original, overwrite, width, height, mode, null);
	}
	
	public static Bitmap resize(File original, boolean overwrite, int width, int height, DimensionUnit unit, Context ... context) {
		return ImageResize.resize(original, overwrite, width, height, null, unit, context);
	}
	
	public static Bitmap resize(File original, boolean overwrite, int width, int height, ResizeMode mode, DimensionUnit unit, Context ... context) {
		return ImageResize.resize(original, overwrite, width, height, mode, unit, context);
	}
	
	// Crop - Bitmaps
	
	public static Bitmap crop(Bitmap original, int width, int height) {
		return ImageCrop.crop(original, -1, -1, width, height, null);
	}
	
	public static Bitmap crop(Bitmap original, int x, int y, int width, int height) {
		return ImageCrop.crop(original, x, y, width, height, null);
	}
	
	public static Bitmap crop(Bitmap original, int width, int height, DimensionUnit unit, Context ... context) {
		return ImageCrop.crop(original, -1, -1, width, height, unit, context);
	}
	
	public static Bitmap crop(Bitmap original, int x, int y, int width, int height, DimensionUnit unit, Context ... context) {
		return ImageCrop.crop(original, x, y, width, height, unit, context);
	}
	
	// Crop - Files
	
	public static Bitmap crop(File original, boolean overwrite, int width, int height) {
		return ImageCrop.crop(original, overwrite, -1, -1, width, height, null);
	}
	
	public static Bitmap crop(File original, boolean overwrite, int x, int y, int width, int height) {
		return ImageCrop.crop(original, overwrite, x, y, width, height, null);
	}
	
	public static Bitmap crop(File original, boolean overwrite, int width, int height, DimensionUnit unit, Context ... context) {
		return ImageCrop.crop(original, overwrite, -1, -1, width, height, unit, context);
	}
	
	public static Bitmap crop(File original, boolean overwrite, int x, int y, int width, int height, DimensionUnit unit, Context ... context) {
		return ImageCrop.crop(original, overwrite, x, y, width, height, unit, context);
	}
	
	// Rotate - Bitmaps
	
	public static Bitmap rotate(Bitmap original, ImageRotation rotation) {
		return ImageRotate.rotate(original, rotation);
	}
	
	// Rotate - Files
	
	public static Bitmap rotate(File original, boolean overwrite, ImageRotation rotation) {
		return ImageRotate.rotate(original, overwrite, rotation);
	}
	
}
