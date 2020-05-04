package viewer;

import common.models.Billboard;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.*;
import java.io.*;

/**
 * This class consists of the picture methods for the Billboard Viewer GUI.
 * It sets up all picture related contents to be displayed in the Viewer.
 *
 * @author Trevor Waturuocha
 */

public class Picture extends JLabel {
    // Picture class constructor. Takes billboard object and container to draw in as parameters.
    public Picture(Billboard billboard, Container container) throws IOException {
        // Check if picture byte array is not empty
        if (billboard.picture != null && billboard.information == null && billboard.message == null){
            DrawPicture(billboard, container, 2, 4);
        }
        // Check if picture byte array, information and message are not empty
        if (billboard.information != null && billboard.message != null && billboard.picture != null){
            new Message(billboard, container).DrawMessage(billboard, container); // Drawing message
            DrawPicture(billboard, container, 2, 6); // Drawing picture
            new Information(billboard, container).DrawInformation(billboard, container, 4, 1); // Drawing information
        }
        // Otherwise do nothing
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
    // Method to draw picture. Takes billboard object, container and label resize factors as parameters.
    public void DrawPicture(Billboard billboard, Container container, int wFactor, int hFactor) throws IOException {
        // Test billboard image
        BufferedImage image = ImageIO.read(new File("src/main/java/viewer/test.jpg")); // Reading image file from path
        ByteArrayOutputStream finalPicture = new ByteArrayOutputStream(); // Opening output stream
        ImageIO.write(image, "jpg", finalPicture); // Writing image file into output stream
        billboard.picture = finalPicture.toByteArray(); // Converting output stream to a byte array and storing into picture

        // Converting image byte array to a buffered image to calculation new size
        ByteArrayInputStream pictureInput = new ByteArrayInputStream(billboard.picture); // Opening input stream
        BufferedImage pictureOutput = ImageIO.read(pictureInput); // Reading image for input stream
        int labelWidth = (Toolkit.getDefaultToolkit().getScreenSize().width / wFactor); // Storing the width of the label with resize factor
        int labelHeight = Toolkit.getDefaultToolkit().getScreenSize().height * 2 / hFactor; // Storing the height of the label with resize factor

        // Check if picture output data is not null before resizing image
        if (pictureOutput != null){
            billboard.picture = calcPicSize(pictureOutput, labelWidth, labelHeight); // Resizing the picture
        }
        ImageIcon pic = new ImageIcon(billboard.picture); // Storing image byte array in image icon
        JLabel picture = new JLabel(pic);
        picture.setAlignmentX(Component.CENTER_ALIGNMENT); // Horizontally centering message text
        container.add(Box.createVerticalGlue()); // To add padding to top of picture
        container.add(picture); // Adding message to centre of container
        container.add(Box.createVerticalGlue()); // To add padding to bottom of picture
    }
}
