package client.components.table;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class ColourRenderer extends JLabel
    implements TableCellRenderer {

    public ColourRenderer() {
        setOpaque(true); //MUST do this for background to show up.
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
