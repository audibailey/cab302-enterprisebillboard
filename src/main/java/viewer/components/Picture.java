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
    /**
     * Picture class constructor. Takes billboard object and container to draw in as parameters.
     * @param billboard
     * @param wFactor
     * @param hFactor
     * @throws IOException
     */
    public Picture(Billboard billboard, int wFactor, int hFactor) throws IOException {
        byte[] base64 = Base64.getDecoder().decode(billboard.picture);
        BufferedImage pictureOutput = ImageIO.read(new ByteArrayInputStream(base64));

        // Converting image byte array to a buffered image to calculation new size
        int labelWidth = (Toolkit.getDefaultToolkit().getScreenSize().width / wFactor);
        int labelHeight = Toolkit.getDefaultToolkit().getScreenSize().height * 2 / hFactor;

        setIcon(scaleImage(new ImageIcon(pictureOutput), labelWidth, labelHeight));

        setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    /**
     * Method to calculate and resize image appropriately inside panel. Takes buffered image and label boundaries as parameters.
     * @param pic
     * @param picBoundsWidth
     * @param picBoundsHeight
     * @return ByteArray
     * @throws IOException
     */
    public byte[] calcPicSize(BufferedImage pic, int picBoundsWidth, int picBoundsHeight) throws IOException {
        int pic_width = pic.getWidth();
        int pic_height = pic.getHeight();
        int bound_width = picBoundsWidth;
        int bound_height = picBoundsHeight;
        int new_width = pic_width;
        int new_height = pic_height;

        // Checking if scaling the width is needed
        if (pic_width > bound_width) {
            new_width = bound_width;
            new_height = (new_width * pic_height) / pic_width;
        }

        // Checking if scaling the height is needed
        if (new_height > bound_height) {
            new_height = bound_height;
            new_width = (new_height * pic_width) / pic_height;
        }
        // Formatting image with new dimensions
        BufferedImage resizedPic = new BufferedImage(new_width, new_height, pic.getType());
        Graphics g = resizedPic.getGraphics();
        g.drawImage(pic, 0, 0, resizedPic.getWidth(), resizedPic.getHeight(), null);
        g.dispose();
        ByteArrayOutputStream finalPicture = new ByteArrayOutputStream();
        ImageIO.write(resizedPic, "jpg", finalPicture);
        return finalPicture.toByteArray();
    }

    /**
     * Method to calculate and resize an ImageIcon appropriately inside panel. Takes the ImageIcon and label boundaries as parameters.
     * @param icon
     * @param w
     * @param h
     * @return ImageIcon
     */
    public ImageIcon scaleImage(ImageIcon icon, int w, int h)
    {
        int nw = icon.getIconWidth();
        int nh = icon.getIconHeight();
        // Checking if scaling the width is needed
        if(icon.getIconWidth() > w)
        {
            nw = w;
            nh = (nw * icon.getIconHeight()) / icon.getIconWidth();
        }
        // Checking if scaling the height is needed
        if(nh > h)
        {
            nh = h;
            nw = (icon.getIconWidth() * nh) / icon.getIconHeight();
        }

        return new ImageIcon(icon.getImage().getScaledInstance(nw, nh, Image.SCALE_DEFAULT));
    }
}
