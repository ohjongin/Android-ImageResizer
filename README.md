#In development...
--------

What is Android ImageResizer?
--------
Library which provides simple image resizing, cropping and rotation.

Usage
--------

    ImageResizer.resize(bitmap, 500);
    ImageResizer.resize(bitmap, 640, 480);
        
    ImageResizer.crop(bitmap, 640, 480);
    ImageResizer.crop(bitmap, 50, 50, 640, 480);
        
    ImageResizer.rotate(bitmap, ImageRotation.CW_90);