package sharkitter.util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class MapUtils {

    private static final int RED = 0;
    private static final int GREEN = 1;
    private static final int BLUE = 2;

    /**
     * Checks if the given location is in water using GoogleMaps API, compares 3 pixels for more accuracy
     * @param latitude  Latitude to test
     * @param longitude Longitude to test
     * @return  True if the location is in water, false otherwise
     * @throws IOException
     */
    public static boolean isInWater(double latitude, double longitude) throws IOException {
        URL url = new URL("https://maps.googleapis.com/maps/api/staticmap?center=" + latitude + "," + longitude + "&size=40x40&maptype=roadmap&sensor=false&zoom=16&key=AIzaSyB0AvSaindCKl-WPTV7qUHWvPbMDGkOCKU\n");
        BufferedImage image = ImageIO.read(url);

        int[] pixel1RGB = getPixelRGB(image,0,0);

        int[] pixel2RGB = getPixelRGB(image,0,1);

        int[] pixel3RGB = getPixelRGB(image,0,2);

        int sumRGB[] = new int[] {
                pixel1RGB[RED] + pixel2RGB[RED] + pixel3RGB[RED],
                pixel1RGB[GREEN] + pixel2RGB[GREEN] + pixel3RGB[GREEN],
                pixel1RGB[BLUE] + pixel2RGB[BLUE] + pixel3RGB[BLUE]};

        if(sumRGB[RED] == 537 && sumRGB[GREEN] == 627 && sumRGB[BLUE] == 765) { // Color of water
            return true;
        }
        return false;
    }

    /**
     * Gets the color of a given pixel from an image
     * @param image Image to test
     * @param x Pixel's x coordinate
     * @param y Pixel's y coordinate
     * @return  An array of red, green and blue values for the pixel
     */
    private static int[] getPixelRGB(BufferedImage image, int x, int y) {
        int pixel = image.getRGB(x,y);

        return new int[] {
                (pixel >> 16) & 0xff, //red
                (pixel >>  8) & 0xff, //green
                (pixel      ) & 0xff  //blue
        };
    }
}
