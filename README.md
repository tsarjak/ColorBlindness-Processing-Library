# ColorBlindness-Processing-Library
This library simulates and corrects images to ease differentiation of colors for colorblind people - developed for Processing Foundation as a part of Google Summer of Code 2017

You can find the full source for the library [here](https://github.com/tsarjak/color-blindness-imageprocessing)!


## Importing the library
Start with importing the library to your code
```Java
import colorblindness;
import processing.glu.*;
import processing.glu.tessellator.*;
```

## Simulating/Correcting using color difference method (Daltonization)

The "colorblindness.daltonize() takes 4 parameters
1) Type of colorblindness
2) Absolute address of input file
3) Absolute Address of output file
4) Simulate or Correct the image

```Java
colorblindness.daltonize(int type, string inputAddress, string outputAddress, boolean correct);

//For example, the following line simulates the image at /Desktop/input.jpg 
//as per protanopia and stores the output at /Desktop/output.jpg

colorblindness.daltonize(1,"/Desktop/input.jpg","/Desktop/output.jpg",false);
```

Options for type - 
1 -> Protanopia
2 -> Deutranopia
3 -> Tritanopia

## Correcting the image using RGB Color Contrast method

There are two possible function calls for this

```Java
colorblindness.rgbContrast(string inputFile, string outputFile, float factor);
//The factor range from 0 to 1

colorblindness.rgbContrast(string inputFile, string outputFile);


colorblindness.rgbContrast("/Desktop/input.jpg","/Desktop/output.jpg",0.40);
```

When no factor is defined, the library uses predetermined optimum factor of 0.28


## Correcting the image using HSV Color Contrast method

```Java
colorblindness.hContrast(string inputFile, string outputFile, int factor);
//The factor range from 1 to 128

colorblindness.hContrast("/Desktop/input.jpg","/Desktop/output.jpg", 50);
```

## Correcting the image using CIE*LAB Color Contrast Method

```Java
colorblindness.labMethod(string inputFile, string outputFile, float factor);
//The factor range from 0 to 1

colorblindness.labMethod(string inputFile, string outputFile);


colorblindness.rgbContrast("/Desktop/input.jpg","/Desktop/output.jpg",0.40);
```

When no factor is defined, the library uses predetermined optimum factor of 0.35


#### For more details about the project, go [here](https://gsocsarjak.wordpress.com) 
