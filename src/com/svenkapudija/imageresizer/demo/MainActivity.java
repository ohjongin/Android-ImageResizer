package com.svenkapudija.imageresizer.demo;

import java.io.File;

import android.app.Activity;
import android.os.Bundle;

import com.svenkapudija.imageresizer.ImageResizer;
import com.svenkapudija.imageresizer.R;
import com.svenkapudija.imageresizer.operations.ImageRotation;
import com.svenkapudija.imageresizer.operations.ResizeMode;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        File myImage = null;
        
        // The simplest case
        ImageResizer.resize(myImage, 640, 480);
        
        // Choose ResizeMode - FIT_TO_WIDTH, FIT_TO_HEIGHT, FIT_EXACT or AUTOMATIC
        ImageResizer.resize(myImage, 640, 480, ResizeMode.FIT_TO_WIDTH);
        
        // Basic croping
        ImageResizer.crop(myImage, 500, 500);

        // Croping with specified starting X and Y point
        ImageResizer.crop(myImage, 50, 50, 640, 480);
        
        // Rotate an image for every 90 degrees
        ImageResizer.rotate(myImage, ImageRotation.CW_90);
        
    }
}
