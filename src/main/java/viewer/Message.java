package viewer;

import common.models.Billboard;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilderFactory;
import java.awt.*;

public class Message extends JPanel {
    JLabel msgString;
    // Message class constructor. Takes billboard object and container to draw in as parameters.
    public Message(Billboard billboard, Container container){
        // Check if message string is not empty and information and picture are empty
        if (billboard.message != null && billboard.information == null && billboard.picture == null){
            JLabel msg = new JLabel(billboard.message);
            CalcMsgWidth(msg); // Formatting message for billboard width
            msg.setAlignmentX(Component.CENTER_ALIGNMENT); // Horizontally centering message text
            container.add(Box.createVerticalGlue()); // To add padding to top of message
            container.add(msg, BoxLayout.Y_AXIS); // Adding message to centre of container
            container.add(Box.createVerticalGlue()); // To add padding to bottom of message
        }
        // Check if message string and picture is not empty

        // Check if message string and information are not empty and picture is empty
        if (billboard.message != null && billboard.information != null && billboard.picture == null){
            JLabel msg = new JLabel(billboard.message);
            CalcMsgWidth(msg); // Formatting message for billboard width
            msg.setAlignmentX(Component.CENTER_ALIGNMENT); // Horizontally centering message text
            msg.setAlignmentY(Component.CENTER_ALIGNMENT); // Vertically centering message text
            JLabel info = new JLabel(billboard.information); // Creating new centered label to store information text
            new Information(billboard, container).FormatInfo(info); // Formatting information text
            info.setAlignmentY(Component.CENTER_ALIGNMENT); // Vertically centering information text
            container.add(msg); // Adding message to container
            container.add(info); // Adding information to container
        }
        // Otherwise do nothing
    }

    // Method to calculate the largest possible message to fit the screen in one line
    public void CalcMsgWidth(JLabel label){
        Font labelFont = label.getFont(); // Get the font used for the label
        String labelText = label.getText(); // Get the string used for the label
        int stringWidth = label.getFontMetrics(labelFont).stringWidth(labelText); // Getting the width of the string
        int labelWidth = Toolkit.getDefaultToolkit().getScreenSize().width; // Getting the width of the label

        // Checking to see how much the font can grow in width.
        double largestWidth = (double)labelWidth / (double)stringWidth; // Calculate largest width by taking label width/string width
        int finalSize = (int)(labelFont.getSize() * largestWidth); // Set final font size by multiplying font size with scale factor

        // Set the final plain font size to use for the message label
        label.setFont(new Font(labelFont.getName(), Font.PLAIN, finalSize));
    }
}
