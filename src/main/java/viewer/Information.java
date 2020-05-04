package viewer;

import common.models.Billboard;

import javax.swing.*;
import java.awt.*;

public class Information extends JLabel {
    // Information class constructor. Takes billboard object and container to draw in as parameters.
    public Information(Billboard billboard, Container container){
        // Check if information string is not empty and message and picture are empty
        if (billboard.information != null && billboard.message == null && billboard.picture == null){
            JLabel info = new JLabel(billboard.information); // Creating new centered label to store information text
            FormatInfo(info); // Formatting information text
            container.add(Box.createVerticalGlue()); // To add padding to top of message
            container.add(info); // Adding message to container
            container.add(Box.createVerticalGlue()); // To add padding to bottom of message
        }
        // Otherwise do nothing
    }
    // Method to format the information text
    public void FormatInfo(JLabel label){
        int labelWidth = (Toolkit.getDefaultToolkit().getScreenSize().width / 4) * 3; // Storing the width of the label as 75% of screen width
        int labelHeight = Toolkit.getDefaultToolkit().getScreenSize().height / 2; // Storing the height of the label as 50% of screen height
        label.setMinimumSize(new Dimension(labelWidth,labelHeight)); // Setting the minimum label dimensions
        label.setMaximumSize(new Dimension(labelWidth,labelHeight)); // Setting the maximum label dimensions
        label.setAlignmentX(Component.CENTER_ALIGNMENT); // Aligning label to the centre of the window
        label.setHorizontalAlignment(CENTER); // Aligning label text to the centre of the label
        String labelText = label.getText(); // Get the string used for the label
        // Formatting information with font size, div centering and text wrapping using HTML string formatting
        label.setText("<html><div style=\"text-align:center; font-size:40;\">" + labelText + "</div></html>"); // Updating label with formatted information text
    }
}
