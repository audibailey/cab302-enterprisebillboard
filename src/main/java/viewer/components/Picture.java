package viewer.components;

import common.models.Billboard;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.*;
import java.io.*;
import java.util.Base64;

/**
 * This class consists of the picture methods for the Billboard Viewer GUI.
 * It sets up all picture related contents to be displayed in the Viewer.
 *
 * @author Trevor Waturuocha
 */
public class Picture extends JLabel {
    // Picture class constructor. Takes billboard object and container to draw in as parameters.
    public Picture(Billboard billboard, int wFactor, int hFactor) throws IOException {
        byte[] base64 = Base64.getDecoder().decode(billboard.picture);
        BufferedImage pictureOutput = ImageIO.read(new ByteArrayInputStream(base64));

        // Converting image byte array to a buffered image to calculation new size
        int labelWidth = (Toolkit.getDefaultToolkit().getScreenSize().width / wFactor); // Storing the width of the label with resize factor
        int labelHeight = Toolkit.getDefaultToolkit().getScreenSize().height * 2 / hFactor; // Storing the height of the label with resize factor

        setIcon(scaleImage(new ImageIcon(pictureOutput), labelWidth, labelHeight));

        setAlignmentX(Component.CENTER_ALIGNMENT); // Horizontally centering message text
    }

    // Method to calculate and resize image appropriately inside panel. Takes buffered image and label boundaries as parameters.
    public byte[] calcPicSize(BufferedImage pic, int picBoundsWidth, int picBoundsHeight) throws IOException {
        int pic_width = pic.getWidth(); // Getting picture width
        int pic_height = pic.getHeight(); // Getting picture height
        int bound_width = picBoundsWidth; // Getting picture width bounds
        int bound_height = picBoundsHeight; // Getting picture height bounds
        int new_width = pic_width; // Setting picture width in temp variable
        int new_height = pic_height; // Setting picture height in temp variable

        // Checking if scaling the width is needed
        if (pic_width > bound_width) {
            // Scaling width to fit the bounds
            new_width = bound_width;
            // Scaling height to maintain aspect ratio
            new_height = (new_width * pic_height) / pic_width;
        }

        // Checking if scaling the height is needed
        if (new_height > bound_height) {
            // Scale height to fit the bounds
            new_height = bound_height;
            // Scaling width to maintain aspect ratio
            new_width = (new_height * pic_width) / pic_height;
        }

        BufferedImage resizedPic = new BufferedImage(new_width, new_height, pic.getType()); // Creating buffered image with new dimensions
        Graphics g = resizedPic.getGraphics(); // Creating new graphics variable to edit resizedPic
        g.drawImage(pic, 0, 0, resizedPic.getWidth(), resizedPic.getHeight(), null); // Storing pic image data into resizedPic
        g.dispose(); // now using resizedPic instead of pic
        ByteArrayOutputStream finalPicture = new ByteArrayOutputStream(); // Opening output stream
        ImageIO.write(resizedPic, "jpg", finalPicture); // Writing image file into output stream
        return finalPicture.toByteArray(); // Returning final formatted image byte array
    }

    public ImageIcon scaleImage(ImageIcon icon, int w, int h)
    {
        int nw = icon.getIconWidth();
        int nh = icon.getIconHeight();

        if(icon.getIconWidth() > w)
        {
            nw = w;
            nh = (nw * icon.getIconHeight()) / icon.getIconWidth();
        }

        if(nh > h)
        {
            nh = h;
            nw = (icon.getIconWidth() * nh) / icon.getIconHeight();
        }

        return new ImageIcon(icon.getImage().getScaledInstance(nw, nh, Image.SCALE_DEFAULT));
    }
}
