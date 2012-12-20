What is Android ImageResizer?
--------
Simple library which provides `Bitmap` (in form of `File`, `byte[]` and `Resource`) resizing, cropping, rotation, flipping on Android. Used tips from
[developer.android.com](http://developer.android.com/training/building-graphics.html).

Usage
--------

Copy-paste `.jar` file to your `/libs` folder and then in Eclipse right click on it and `Add to build path`.
> [Download .jar - commit 8012d6b981bcbb0f6d35d6aa9195dacbb9542291](http://www.svenkapudija.com/projects/imageresizer-8012d6b981bcbb0f6d35d6aa9195dacbb9542291.jar)

### Resize
        
    // Different width and height
    ImageResizer.resize(myImageFile, 640, 480);
        
    // Choose ResizeMode - FIT_TO_WIDTH, FIT_TO_HEIGHT, FIT_EXACT or AUTOMATIC
    ImageResizer.resize(myImageFile, 640, 480, ResizeMode.FIT_TO_WIDTH);
        
### Crop

    // Basic croping
    ImageResizer.crop(myImageFile, 500, 500);

    // Croping with specified starting X and Y point
    ImageResizer.crop(myImageFile, 50, 50, 640, 480);

### Rotation and flipping
Very memory-intensive for high-resolution images (for ex. `2560*1600`), better scale it down and then rotate.

    // Rotate an image for 90, 180 or 270 degrees
    ImageResizer.rotate(myImageFile, ImageRotation.CW_90);

    // Flip an image horizontally or vertically
    ImageResizer.rotate(myImageFile, ImageRotation.FLIP_HORIZONTAL);


Developed by
------------
* Sven Kapuđija

License
-------

    Copyright 2012 Sven Kapuđija
    
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
    
    http://www.apache.org/licenses/LICENSE-2.0
    
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
