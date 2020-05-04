package viewer;

import common.models.Billboard;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * This class consists of the information methods for the Billboard Viewer GUI.
 * It sets up all information related contents to be displayed in the Viewer.
 *
 * @author Trevor Waturuocha
 */

public class Information extends JLabel {
    // Information class constructor. Takes billboard object and container to draw in as parameters.
    public Information(Billboard billboard, Container container) throws IOException {
        // Check if information string is not empty and message and picture are empty
        if (billboard.information != null && billboard.message == null && billboard.picture == null){
            DrawInformation(billboard, container, 4, 2); // Drawing information
        }
        // Check if information string and picture byte array are not empty
        if (billboard.information != null && billboard.message == null && billboard.picture != null){
            new Picture(billboard, container).DrawPicture(billboard, container, 2, 3); // Drawing picture
            DrawInformation(billboard, container, 4, 3); // Drawing information
        }
        // Otherwise do nothing
    }
    // Method to format the information text
    public void FormatInfo(JLabel label, int wFactor, int hFactor){
        int labelWidth = Toolkit.getDefaultToolkit().getScreenSize().width * 3 / wFactor; // Storing the width of the label with resize factor
        int labelHeight = Toolkit.getDefaultToolkit().getScreenSize().height / hFactor; // Storing the height of the label with resize factor
        label.setMinimumSize(new Dimension(labelWidth,labelHeight)); // Setting the minimum label dimensions
        label.setMaximumSize(new Dimension(labelWidth,labelHeight)); // Setting the maximum label dimensions
        label.setAlignmentX(Component.CENTER_ALIGNMENT); // Aligning label to the centre of the window
        label.setHorizontalAlignment(CENTER); // Aligning label text to the centre of the label
        String labelText = label.getText(); // Get the string used for the label
        // Formatting information with font size, div centering and text wrapping using HTML string formatting
        label.setText("<html><div style=\"text-align:center; font-size:40;\">" + labelText + "</div></html>"); // Updating label with formatted information text
    }
    // Method to draw information. Takes billboard object, container and label resize factors as parameters.
    public void DrawInformation(Billboard billboard, Container container, int wFactor, int hFactor){
        JLabel info = new JLabel(billboard.information); // Creating new centered label to store information text
        FormatInfo(info, wFactor, hFactor); // Formatting information text
        container.add(Box.createVerticalGlue()); // To add padding to top of information
        container.add(info); // Adding message to container
        container.add(Box.createVerticalGlue()); // To add padding to bottom of information
    }
}
