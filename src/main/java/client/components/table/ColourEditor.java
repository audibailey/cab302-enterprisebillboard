package client.components.table;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class makes the Java Swing colour editor for the client.
 *
 * @author Jamie Martin
 */
public class ColourEditor extends AbstractCellEditor implements TableCellEditor, ActionListener {

    Color currentColour;
    JButton button;
    JColorChooser colourPicker;
    JDialog dialog;
    protected static final String EDIT = "edit";

    public ColourEditor() {
        // Prep the editor, a button.
        button = new JButton();
        button.setActionCommand(EDIT);
        button.addActionListener(this);
        button.setBorderPainted(false);

        // Prep the colour picker
        colourPicker = new JColorChooser();
        dialog = JColorChooser.createDialog(button,"Pick a Color",true, colourPicker, this, null);
    }

    /**
     * Handles events from the editor button and from the dialog's OK button.
     */
    public void actionPerformed(ActionEvent e) {
        if (EDIT.equals(e.getActionCommand())) {
            // The user has clicked the cell, so spawn the colour picker.
            button.setBackground(currentColour);
            colourPicker.setColor(currentColour);
            dialog.setVisible(true);

            // prompts rerender
            fireEditingStopped();

        } else {
            // user pressed OK button
            currentColour = colourPicker.getColor();
        }
    }

    /**
     * Method for AbstractCellEditor extension
     * @return
     */
    public Object getCellEditorValue() {
        return currentColour;
    }

    /**
     * Method for TableCellEditor implementation
     * @param table
     * @param value
     * @param isSelected
     * @param row
     * @param column
     * @return
     */
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        currentColour = (Color)value;
        return button;
    }
}
