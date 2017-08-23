import colorblindness;
import processing.glu.*;
import processing.glu.tessellator.*;

void setup(){
  //colorblindness.simulate - 4 parameters
  //INT - Type of colorblindness: 1-Protanopia, 2-Deutranopia, 3-Tritanopia
  //String - Input Source - Absolute address of the input file
  //String - Output destination - Absolute address of the output file
  //Boolean - correct: True is Correct, False is only simulate

  colorblindness.daltonize(3,"/Users/mycomputer/Desktop/4.png","/Users/mycomputer/Desktop/sampleOutput.png",false);
}

void draw(){
  //Draw anything on the board, save it using PImage
  //Open the Image using colorblindness library and process it
}