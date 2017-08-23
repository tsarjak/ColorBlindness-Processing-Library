package colorblindness;

import processing.core.PApplet;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import static colorblindness.matrixOperators.multiplyMatrices;

import static colorblindness.labHelper.*;

public class colorblindness {

    PApplet myParent;

    int myVariable = 0;
    int colors[][][];

    public final static String VERSION = "##library.prettyVersion##";


    public colorblindness(PApplet theParent) {
        myParent = theParent;
        welcome();

        colors = new int[256][256][256];

    }

    public static void sayhello(){

        System.out.print("Hello");

    }

    public static void labMethod(String imageName, String savePath){

        labMethod(imageName, savePath, 0.28);

    }
    public static void labMethod(String imageName, String savePath, double factor) {

        BufferedImage img = open(imageName);
        int width = img.getWidth();
        int height = img.getHeight();
        double lab[] = new double[3];


        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Color myColor = new Color(img.getRGB(j, i));

                lab = rgbToLab(myColor.getRed(), myColor.getGreen(), myColor.getBlue());

                if(lab[1] < 0){
                    lab[1] = lab[1] * -1;
                    lab[1] = ((1 - (lab[1]/128)) * factor) + lab[1]/128;
                    lab[1] = lab[1] * -1;
                    //lab[1] = (lab[1]/128) - ((1+lab[1]/128) * factor);
                } else if(lab[1] > 0){
                    lab[1] = ((1 - (lab[1]/128)) * factor) + lab[1]/128;
                }

                if(lab[2] < 0){

                    lab[2] = lab[2] * -1;
                    lab[2] = ((1 - (lab[2]/128)) * factor) + lab[2]/128;
                    lab[2] = lab[2] * -1;
                    //lab[2] = (-lab[2]/128) - ((1+lab[2]/128) * factor);
                } else if(lab[2] > 0){
                    lab[2] = ((1 - (lab[2]/128)) * factor) + lab[2]/128;
                }

                int rgb[] = new int[3];
                rgb = labToRgb(lab[0],lab[1] * 128 ,lab[2] * 128);

                System.out.println(rgb[0] + "," + rgb[1] + "," + rgb[2]);

                if (rgb[0] < 0){
                    rgb[0] = 0;
                } else if(rgb[0]>252){
                    rgb[0] = 252;
                }

                if (rgb[1] < 0){
                    rgb[1] = 0;
                } else if(rgb[1]>252){
                    rgb[1] = 252;
                }

                if (rgb[2] < 0){
                    rgb[2] = 0;
                } else if(rgb[2]>252){
                    rgb[2] = 252;
                }


                Color newColor = new Color((int) (rgb[0]),(int) (rgb[1]), (int) (rgb[2]));

                int rgbColor = newColor.getRGB();

                img.setRGB(j,i,rgbColor);

            }
        }

        saveImage(savePath, img);

    }


    public static BufferedImage open(String imageName){
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(imageName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return img;
    }

    public static void saveImage(String savePath, BufferedImage img){
        File outputFile = new File(savePath);
        try {
            ImageIO.write(img, "png", outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void hContrast(String imageName, String savePath, int factor){
        double f = (259 * (factor + 252)) / (252 * (259-factor));

        BufferedImage img = open(imageName);
        int width = img.getWidth();
        int height = img.getHeight();
        double temp;


        for(int i=0;i<height;i++) {
            for (int j = 0; j < width; j++) {
                Color myColor = new Color(img.getRGB(j, i));
                temp = myColor.getRed();
                double newRed = (f * (temp - 128)) + 128;
                temp = myColor.getGreen();
                double newGreen = (f * (temp - 128)) + 128;
                temp = myColor.getBlue();
                double newBlue = (f * (temp - 128)) + 128;


                if (newRed < 0){
                    newRed = 0;
                } else if(newRed>252){
                    newRed = 252;
                }

                if (newGreen < 0){
                    newGreen = 0;
                } else if(newGreen>252){
                    newGreen = 252;
                }

                if (newBlue < 0){
                    newBlue = 0;
                } else if(newBlue>252){
                    newBlue = 252;
                }

                System.out.println(newRed + "," + newGreen + "," + newBlue);

                Color newColor = new Color((int) (newRed),(int) (newGreen), (int) (newBlue));

                int rgb = newColor.getRGB();

                img.setRGB(j,i,rgb);
            }

        }

        saveImage(savePath, img);

    }

    public static void correctImage(String imageName,BufferedImage simImg, String savePath){
        //Correction using colordifference method!

        BufferedImage originalImg = open(imageName);
        int width = simImg.getWidth();
        int height = simImg.getHeight();

        double current[][] = new double[3][1];
        double errCorrect[][] = {{0, 0, 0}, {0.7, 1, 0}, {0.7, 0, 1}};

        double temp;

        for(int i=0;i<height;i++) {
            for (int j = 0; j < width; j++) {
                Color originalColor = new Color(originalImg.getRGB(j,i));
                Color simColor = new Color(simImg.getRGB(j,i));

                int originalRed = originalColor.getRed();
                int sim = simColor.getRed();
                current[0][0] = originalRed-sim;

                int originalGreen = originalColor.getGreen();
                sim = simColor.getGreen();
                current[1][0] = originalGreen-sim;

                int originalBlue = originalColor.getBlue();
                sim = simColor.getBlue();
                current[2][0] = originalBlue-sim;


                current = multiplyMatrices(errCorrect,current);

                current[0][0] = originalRed + current[0][0];
                current[1][0] = originalGreen + current[1][0];
                current[2][0] = originalBlue + current[2][0];

                for(int k=0;k<3;k++){
                    if(current[k][0] < 0){
                        current[k][0] = 0;
                    } else if(current[k][0] > 252){
                        current[k][0] = 252;
                    }
                }

                Color newColor = new Color((int) current[0][0],(int) current[1][0], (int) current[2][0]);

                int rgb = newColor.getRGB();

                simImg.setRGB(j,i,rgb);

            }
        }

        saveImage(savePath, simImg);

    }

    public static void rgbContrast(String imageName, String savePath, double factor){

        BufferedImage img = open(imageName);
        int width = img.getWidth();
        int height = img.getHeight();
        double temp;


        for(int i=0;i<height;i++) {
            for (int j = 0; j < width; j++) {
                Color myColor = new Color(img.getRGB(j,i));
                temp = myColor.getRed();
                double newRed = ((1 - (temp/252)) * factor) + temp/252;
                temp = myColor.getGreen();
                double newGreen = ((1 - (temp/252)) * factor) + temp/252;

                double newBlue;

                temp = myColor.getBlue();

                if(newRed > newGreen){
                    newBlue = temp/252 - (temp/252 * factor);
                }else{
                    newBlue = ((1 - (temp/252)) * factor) + temp/252;
                }

                Color newColor = new Color((int) (newRed * 252),(int) (newGreen * 252), (int) (newBlue*252));

                int rgb = newColor.getRGB();

                img.setRGB(j,i,rgb);


            }
        }

        saveImage(savePath, img);

        System.out.print("Saved Successfully");

    }

    public static void rgbContrast(String imageName, String savePath){

        rgbContrast(imageName, savePath, 0.28);

    }

    public static void daltonize(int type, String imageName, String savePath, boolean correct){
        BufferedImage img = open(imageName);
        int width = img.getWidth();
        int height = img.getHeight();
        double current[][] = new double[3][1];
        double toLMS[][] = {{17.8824, 43.5161, 4.11935}, {3.45565, 27.1554, 3.86714}, {0.0299566, 0.184309, 1.46709}};
        double protanope[][] = {{0, 2.02344, -2.52581}, {0, 1, 0}, {0, 0, 1}};
        double deutranope[][] = {{1, 0, 0}, {0.494207, 0, 1.24827}, {0, 0, 1}};
        double tritanope[][] = {{1, 0, 0}, {0, 1, 0}, {-0.395913, 0.801109, 0}};
        double toRGB[][] = {{0.08094444, -0.1305044, 0.116721066}, {-0.010248533514, 0.05401932663599884, -0.11361470821404349}, {-0.0003652969378610491, -0.004121614685876285, 0.6935114048608589}};

        for(int i=1;i<height;i++) {
            for (int j = 0; j < width; j++) {
                Color myColor = new Color(img.getRGB(j, i));

                double temp = myColor.getRed();
                current[0][0] = temp;
                temp = myColor.getGreen();
                current[1][0] = temp;
                temp = myColor.getBlue();
                current[2][0] = temp;
                //if(i==height-1 && j==width-1){
                System.out.print(current[0][0]+ "," + current[1][0] + "," + current[2][0]);

                current = multiplyMatrices(toLMS, current);

                if(type == 1){
                    current = multiplyMatrices(protanope,current);
                } else if(type == 2){
                    current = multiplyMatrices(deutranope,current);
                } else if(type==3){
                    current = multiplyMatrices(tritanope,current);
                } else {
                    System.out.print("Invalid Colorblindness Parameter");
                }

                current = multiplyMatrices(toRGB,current);


                if (current[0][0] < 0){
                    current[0][0] = 0;
                } else if(current[0][0]>252){
                    current[0][0] = 252;
                }

                if (current[1][0] < 0){
                    current[1][0] = 0;
                } else if(current[1][0]>252){
                    current[1][0] = 252;
                }

                if (current[2][0] < 0){
                    current[2][0] = 0;
                } else if(current[2][0]>252){
                    current[2][0] = 252;
                }

                Color newColor = new Color((int) (current[0][0]),(int) (current[1][0] ), (int) (current[2][0]));

                int rgb = newColor.getRGB();

                img.setRGB(j,i,rgb);
            }
        }

        if(!correct) {
            saveImage(savePath, img);
        } else{
            correctImage(imageName,img,savePath);
            }
        }



    private void welcome() {
        System.out.println("##library.name## ##library.prettyVersion## by ##author##");
    }


    public String sayHello() {
        return "Colorblindness 1.0";
    }

    public static String version() {
        return VERSION;
    }

}