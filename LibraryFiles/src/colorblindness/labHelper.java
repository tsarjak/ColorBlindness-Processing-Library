package colorblindness;

/**
 * Created by mycomputer on 8/17/17.
 */


// This class converts rgb to lab and vice versa
public class labHelper {

    public static double[] rgbToLab(int R, int G, int B) {

        double r, g, b, X, Y, Z, xr, yr, zr;

        // D65/2°
        double Xr = 95.047;
        double Yr = 100.0;
        double Zr = 108.883;


        // --------- RGB to XYZ ---------//

        r = R/255.0;
        g = G/255.0;
        b = B/255.0;

        if (r > 0.04045)
            r = Math.pow((r+0.055)/1.055,2.4);
        else
            r = r/12.92;

        if (g > 0.04045)
            g = Math.pow((g+0.055)/1.055,2.4);
        else
            g = g/12.92;

        if (b > 0.04045)
            b = Math.pow((b+0.055)/1.055,2.4);
        else
            b = b/12.92 ;

        r*=100;
        g*=100;
        b*=100;

        X =  0.4124*r + 0.3576*g + 0.1805*b;
        Y =  0.2126*r + 0.7152*g + 0.0722*b;
        Z =  0.0193*r + 0.1192*g + 0.9505*b;


        // --------- XYZ to Lab --------- //

        xr = X/Xr;
        yr = Y/Yr;
        zr = Z/Zr;

        if ( xr > 0.008856 )
            xr =  (float) Math.pow(xr, 1/3.);
        else
            xr = (float) ((7.787 * xr) + 16 / 116.0);

        if ( yr > 0.008856 )
            yr =  (float) Math.pow(yr, 1/3.);
        else
            yr = (float) ((7.787 * yr) + 16 / 116.0);

        if ( zr > 0.008856 )
            zr =  (float) Math.pow(zr, 1/3.);
        else
            zr = (float) ((7.787 * zr) + 16 / 116.0);


        double[] lab = new double[3];

        lab[0] = (116*yr)-16;
        lab[1] = 500*(xr-yr);
        lab[2] = 200*(yr-zr);

        return lab;

    }

    public static int[] labToRgb(double l, double a, double b){
        int rgb[] = new int[3];

        double x,y,z, X, Y, Z;

        y = (l + 16) / 116;
        x = a/500 + y;
        z = y - b/200;

        if ( Math.pow(y,3)  > 0.008856 ) {
            y = Math.pow(y, 3);
        } else {
            y = ( y - 16 / 116 ) / 7.787;
        }


        if ( Math.pow(x,3)  > 0.008856 ) {
            x = Math.pow(x,3);
        } else {
            x = ( x - 16 / 116 ) / 7.787;
        }
        if ( Math.pow(z,3)  > 0.008856 ) {
            z = Math.pow(z,3);
        } else{
            z = ( z - 16 / 116 ) / 7.787;
        }


        x = x * 95.047;
        y = y * 100;
        z = z * 108.883;

        double var_R, var_G, var_B;

       // System.out.println(x + "," + y + "," + z);

        x = x/100;
        y = y/100;
        z = z/100;

        var_R = x *  3.2406 + y * -1.5372 + z * -0.4986;
        var_G = x * -0.9689 + y *  1.8758 + z *  0.0415;
        var_B = x *  0.0557 + y * -0.2040 + z *  1.0570;


        if ( var_R > 0.0031308 ) {
            var_R = 1.055 * ( Math.pow(var_R,1/2.4) ) - 0.055;
        } else {
            var_R = 12.92 * var_R;
        }

        if ( var_G > 0.0031308 ){
            var_G = 1.055 * (Math.pow(var_G,1/2.4) ) - 0.055;
        } else{
            var_G = 12.92 * var_G;
        }

        if ( var_B > 0.0031308 ) {
            var_B = 1.055 * ( Math.pow(var_B,1/2.4)) - 0.055;
        } else{
            var_B = 12.92 * var_B;
        }

        rgb[0] = (int) (var_R * 255);
        rgb[1] = (int) (var_G * 255);
        rgb[2] = (int) (var_B * 255);

        return rgb;
    }
}
