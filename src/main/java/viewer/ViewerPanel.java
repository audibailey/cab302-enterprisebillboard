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
    Billboard billboard = Billboard.Random(1); // Creating new random Billboard object for testing
    //Method to sort all billboard display components to display viewer. Pane parameter is used to add components to box layout
    public ViewerPanel(Container pane) throws IOException {
        //billboard.message = null;
        //billboard.picture = null;
        //billboard.information = null;
        new Picture(billboard, pane);
        new Message(billboard, pane);
        new Information(billboard, pane);
    }
}
