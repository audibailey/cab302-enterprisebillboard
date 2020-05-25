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
    // Message class constructor. Takes billboard object and container to draw in as parameters.
    public Message(Billboard billboard) {
        setText(billboard.message);
        CalcMsgWidth(billboard); // Formatting message for billboard width
        // Check if message string has a message colour attribute to add colour
        if(billboard.messageColor != null){
            setForeground(Color.decode(billboard.messageColor));
        }
        setAlignmentX(Component.CENTER_ALIGNMENT); // Horizontally centering message text
    }

    /**
     * Method to calculate the largest possible message to fit the screen in one line
     */
    public void CalcMsgWidth(Billboard billboard){
        Font labelFont = getFont(); // Get the font used for the label
        String labelText = getText(); // Get the string used for the label
        int stringWidth = getFontMetrics(labelFont).stringWidth(labelText); // Getting the width of the string
        int labelWidth = Toolkit.getDefaultToolkit().getScreenSize().width; // Getting the width of the label
        int maxHeight = Toolkit.getDefaultToolkit().getScreenSize().height / 3; // Maximum label height
        // Checking to see how much the font can grow in width.
        double largestWidth = (double)labelWidth / (double)stringWidth; // Calculate largest width by taking label width/string width
        int finalSize = (int)(labelFont.getSize() * largestWidth); // Set final font size by multiplying font size with scale factor

        // Set the final plain font size to use for the message label
        setFont(new Font(labelFont.getName(), Font.PLAIN, finalSize));
        // Check if font is larger than max height
        if (finalSize > maxHeight){
            setFont(new Font(labelFont.getName(), Font.PLAIN, maxHeight)); // Set font size to fit maximum label height
        }
    }
}
