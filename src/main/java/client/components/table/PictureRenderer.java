package client.components.table;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

/**
 * This class renders the Java Swing pictures for the client.
 *
 * @author Jamie Martin
 */
public class PictureRenderer extends JLabel
    implements TableCellRenderer {

    public PictureRenderer() {
        setHorizontalAlignment(SwingConstants.CENTER);
        setText("Edit Picture");
    }

    public Component getTableCellRendererComponent(
        JTable table, Object image,
        boolean isSelected, boolean hasFocus,
        int row, int column) {

        return this;
    }
}
