package viewer;

import javax.swing.*;
import java.awt.*;

// Class for main viewer panel
public class ViewerPanel extends JPanel {
    JLabel title; // Title label field
    public ViewerPanel () {
        super(new BorderLayout());
        // Adding title to panel
        title = new JLabel("BillboardViewer"); // Constructing title label object
        add(title, BorderLayout.NORTH); // Adding title to top of the frame
        title.setHorizontalAlignment(JLabel.CENTER); // Centering title text
    }
}
