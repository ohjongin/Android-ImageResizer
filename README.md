#In development...
--------

What is Android ImageResizer?
--------
Wrapper around `Bitmap.createScaledBitmap` with cropping, rotation and support for multiple dimension units.

Usage
--------

### Resize

        // The simplest case - width and height are the same size
        ImageResizer.resize(myImage, 500);
        
        // Different width and height
        ImageResizer.resize(myImage, 640, 480);
        
        // Choose ResizeMode - FIT_TO_WIDTH, FIT_TO_HEIGHT, FIT_EXACT or AUTOMATIC
        ImageResizer.resize(myImage, 640, 480, ResizeMode.FIT_TO_WIDTH);
        
        // Resize size based on DP units (library will convert those into pixels
        // based on device screen density)
        ImageResizer.resize(myImage, 200, 200, DimensionUnit.DP, this);
        
### Crop

        // Basic croping
        ImageResizer.crop(myImage, 500, 500);

        // Croping with specified starting X and Y point
        ImageResizer.crop(myImage, 50, 50, 640, 480);

### Rotate

        // Rotate an image for 90, 180 or 270 degrees
        ImageResizer.rotate(myImage, ImageRotation.CW_90);