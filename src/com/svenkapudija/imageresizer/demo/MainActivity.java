package com.svenkapudija.imageresizer.demo;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;

import com.svenkapudija.imageresizer.DimensionUnit;
import com.svenkapudija.imageresizer.ImageResizer;
import com.svenkapudija.imageresizer.ImageRotation;
import com.svenkapudija.imageresizer.R;
import com.svenkapudija.imageresizer.ResizeMode;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        Bitmap myImage = null;
        
        // The simplest case - width and height are the same size
        ImageResizer.resize(myImage, 500);
        
        // Different width and height
        ImageResizer.resize(myImage, 640, 480);
        
        // Choose ResizeMode - FIT_TO_WIDTH, FIT_TO_HEIGHT, FIT_EXACT or AUTOMATIC
        ImageResizer.resize(myImage, 640, 480, ResizeMode.FIT_TO_WIDTH);
        
        // Resize according to DP's (library will convert those into pixels
        // based on device screen density)
        ImageResizer.resize(myImage, 200, 200, DimensionUnit.DP, this);
        
        // Basic croping
        ImageResizer.crop(myImage, 500, 500);

        // Croping with specified starting X and Y point
        ImageResizer.crop(myImage, 50, 50, 640, 480);
        
        // Rotate an image for every 90 degrees
        ImageResizer.rotate(myImage, ImageRotation.CW_90);
        
    }
}
