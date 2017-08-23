import colorblindness;
import processing.glu.*;
import processing.glu.tessellator.*;

void setup(){
  //rgbContrast corrects the image using rgb contrast method
  
  //Parameters -
  //String - Input Source - Absolute Address of the Input File
  //String - Output Destination - Absolte Address of the output File
  
  //Optional Paramater - 
  //Float - Factor - Factor of contrast increase
  //On no input, function uses precalculated optimal factor of 0.28
  
  //Format - colorblindness.rgbcontrast(<inputString>,<outputString>,<optional factor>);
  n
  colorblindness.rgbContrast("/Users/mycomputer/Desktop/sample.png","/Users/mycomputer/Desktop/sampleOutput.png");
}

void draw(){
  //Draw anything on the board, save it using PImage
  //Open the Image using colorblindness library and process it
}