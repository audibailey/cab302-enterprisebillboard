package client.components.table;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

/**
 * This class renders the Java Swing colour renderer for the client.
 *
 * @author Jamie Martin
 */
public class ColourRenderer extends JLabel implements TableCellRenderer {

    public ColourRenderer() {
        setOpaque(true);
    }

    /**
     * Method called by the Table to render the component
     * @param table
     * @param colour
     * @param isSelected
     * @param hasFocus
     * @param row
     * @param column
     * @return
     */
    public Component getTableCellRendererComponent(JTable table, Object colour, boolean isSelected, boolean hasFocus, int row, int column) {
        if (colour != null) setBackground((Color)colour);

        return this;
    }
}
