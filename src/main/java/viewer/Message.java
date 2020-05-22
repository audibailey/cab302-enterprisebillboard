package viewer;

import common.models.Billboard;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilderFactory;
import java.awt.*;
import java.io.IOException;

/**
 * This class consists of the message methods for the Billboard Viewer GUI.
 * It sets up all message related contents to be displayed in the Viewer.
 *
 * @author Trevor Waturuocha
 */

public class Message extends JLabel {
    JLabel msgString;
    // Message class constructor. Takes billboard object and container to draw in as parameters.
    public Message(Billboard billboard, Container container) throws IOException {
        // Check if message string is not empty
        if (billboard.message != null && billboard.information == null && billboard.picture == null){
            DrawMessage(billboard, container); // Drawing message
        }
        // Check if message string and picture byte array is not empty
        if (billboard.message != null && billboard.information == null && billboard.picture != null){
            DrawMessage(billboard, container); // Drawing message
            new Picture(billboard, container).DrawPicture(billboard, container, 2, 3); // Drawing picture
        }
        // Check if message string and information are not empty
        if (billboard.message != null && billboard.information != null && billboard.picture == null){
            DrawMessage(billboard, container); // Drawing message
            new Information(billboard, container).DrawInformation(billboard, container, 4, 2); // Drawing information
        }

        // Otherwise do nothing
    }

    /**
     * Method to calculate the largest possible message to fit the screen in one line
     * @param label
     */
    public void CalcMsgWidth(JLabel label, Billboard billboard){
        Font labelFont = label.getFont(); // Get the font used for the label
        String labelText = label.getText(); // Get the string used for the label
        int stringWidth = label.getFontMetrics(labelFont).stringWidth(labelText); // Getting the width of the string
        int labelWidth = Toolkit.getDefaultToolkit().getScreenSize().width; // Getting the width of the label
        int maxHeight = Toolkit.getDefaultToolkit().getScreenSize().height / 3; // Maximum label height
        // Checking to see how much the font can grow in width.
        double largestWidth = (double)labelWidth / (double)stringWidth; // Calculate largest width by taking label width/string width
        int finalSize = (int)(labelFont.getSize() * largestWidth); // Set final font size by multiplying font size with scale factor

        // Set the final plain font size to use for the message label
        label.setFont(new Font(labelFont.getName(), Font.PLAIN, finalSize));
        // Check if font is larger than max height
        if (finalSize > maxHeight){
            label.setFont(new Font(labelFont.getName(), Font.PLAIN, maxHeight)); // Set font size to fit maximum label height
        }
    }

    /**
     * Method to draw message. Takes billboard object and container to draw in as parameters.
     * @param billboard
     * @param container
     */
    public void DrawMessage(Billboard billboard, Container container){
        JLabel msg = new JLabel(billboard.message); // Adding message string to label
        CalcMsgWidth(msg, billboard); // Formatting message for billboard width
        // Check if message string has a message colour attribute to add colour
        if(billboard.messageColor != null){
            msg.setForeground(Color.decode(billboard.messageColor));
        }
        msg.setAlignmentX(Component.CENTER_ALIGNMENT); // Horizontally centering message text
        container.add(Box.createVerticalGlue()); // To add padding to top of message
        container.add(msg); // Adding message to centre of container
        container.add(Box.createVerticalGlue()); // To add padding to bottom of message
    }
}
