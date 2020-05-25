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

    // to draw information. Takes billboard object, container and label resize factors as parameters.
    // Information class constructor. Takes billboard object and container to draw in as parameters.
    public Information(Billboard billboard, int wFactor, int hFactor) throws IOException {
        setText(billboard.information);
        FormatInfo(wFactor, hFactor); // Formatting information text
        // Check if information string has an information colour attribute to add colour
        if(billboard.informationColor != null){
            setForeground(Color.decode(billboard.informationColor));
        }
    }
    // Method to format the information text
    public void FormatInfo(int wFactor, int hFactor){
        int labelWidth = Toolkit.getDefaultToolkit().getScreenSize().width * 3 / wFactor; // Storing the width of the label with resize factor
        int labelHeight = Toolkit.getDefaultToolkit().getScreenSize().height / hFactor; // Storing the height of the label with resize factor
        setMinimumSize(new Dimension(labelWidth,labelHeight)); // Setting the minimum label dimensions
        setMaximumSize(new Dimension(labelWidth,labelHeight)); // Setting the maximum label dimensions
        setAlignmentX(Component.CENTER_ALIGNMENT); // Aligning label to the centre of the window
        setHorizontalAlignment(CENTER); // Aligning label text to the centre of the label
        String labelText = getText(); // Get the string used for the label
        // Formatting information with font size, div centering and text wrapping using HTML string formatting
        setText("<html><div style=\"text-align:center; font-size:40;\">" + labelText + "</div></html>"); // Updating label with formatted information text
    }
}
