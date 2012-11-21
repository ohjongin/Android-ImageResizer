#In development...
--------

What is Android ImageResizer?
--------
Wrapper around `Bitmap.createScaledBitmap` with cropping and rotation.

Usage
--------

    ImageResizer.resize(bitmap, 500);
    ImageResizer.resize(bitmap, 640, 480);
        
    ImageResizer.crop(bitmap, 640, 480);
    ImageResizer.crop(bitmap, 50, 50, 640, 480);
        
    ImageResizer.rotate(bitmap, ImageRotation.CW_90);