package viewer;

import common.models.Billboard;

import javax.imageio.*;
import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.io.*;

/**
 * This class consists of the Viewer Panel for the Billboard Viewer GUI.
 * It creates the Panel and Displays the relevant Billboard contents
 *
 * @author Trevor Waturuocha
 */
public class ViewerPanel {
    //Method to sort all billboard display components to display viewer. Pane parameter is used to add components to box layout
    public ViewerPanel(Container pane, Billboard billboard) throws IOException {
        new Picture(billboard, pane); // Display picture
        new Message(billboard, pane); // Display message
        new Information(billboard, pane); // Display information
    }
}
