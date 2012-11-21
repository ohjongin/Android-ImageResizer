package com.svenkapudija.imageresizer;

import java.io.File;

import android.graphics.Bitmap;

public interface ImageResizer {

	public Bitmap resize(Bitmap original, int size);
	public Bitmap resize(Bitmap original, int size, ResizeMode mode);
	
	public Bitmap resize(Bitmap original, int width, int height);
	public Bitmap resize(Bitmap original, int width, int height, ResizeMode mode);
	
	public Bitmap crop(Bitmap original, int width, int height);
	public Bitmap crop(Bitmap original, int x, int y, int width, int height);
	
	public Bitmap rotate(Bitmap original, ImageRotation rotation);
	
	public void save(File saveLocation);
	
}
