package viewer;

import javax.swing.*;
import java.awt.*;

/**
 * This class consists of the Viewer Panel for the Billboard Viewer GUI.
 * It creates the Panel and ...
 *
 * @author Trevor Waturuocha
 */
public class ViewerPanel extends JPanel {
    JLabel title; // Title label field

    public ViewerPanel() {
        super(new BorderLayout());
        // Adding title to panel
        title = new JLabel("Billboard Viewer"); // Constructing title label object
        add(title, BorderLayout.NORTH); // Adding title to top of the frame
        title.setHorizontalAlignment(JLabel.CENTER); // Centering title text
    }
}
