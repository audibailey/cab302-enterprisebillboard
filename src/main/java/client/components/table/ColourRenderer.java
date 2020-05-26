package client.components.table;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

/**
 * This class renders the Java Swing colour renderer for the client.
 *
 * @author Jamie Martin
 */
public class ColourRenderer extends JLabel
    implements TableCellRenderer {

    public ColourRenderer() {
        setOpaque(true);
    }

    public Component getTableCellRendererComponent(
        JTable table, Object colour,
        boolean isSelected, boolean hasFocus,
        int row, int column) {

        if (colour != null) {
            Color newColour = (Color)colour;
            setBackground(newColour);

            setToolTipText("RGB value: " + newColour.getRed() + ", "
                + newColour.getGreen() + ", "
                + newColour.getBlue());
        }

        return this;
    }
}
