package client.components.table;

import common.models.Picture;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;

public class PictureEditor extends AbstractCellEditor
    implements TableCellEditor,
    ActionListener {
    Picture currentPicture;
    JButton button;
    JFileChooser fileChooser;
    JDialog dialog;
    protected static final String EDIT = "edit";

    public PictureEditor() {
        button = new JButton();
        button.setActionCommand(EDIT);
        button.addActionListener(this);
        button.setBorderPainted(false);

        fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Image files", "BMP", "JPEG", "PNG"));
        dialog = new JDialog((Window) null);
    }

    public void actionPerformed(ActionEvent e) {
        if (EDIT.equals(e.getActionCommand())) {

            int returnVal = fileChooser.showOpenDialog(dialog);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try {
                    System.out.println(file.toPath());
                    byte[] fileContent = Files.readAllBytes(file.toPath());
                    currentPicture.data = Base64.getEncoder().encodeToString(fileContent);
                    System.out.println(currentPicture.data);
                } catch (IOException e1) {
                    JOptionPane.showMessageDialog(null, "Failed to load the file: " + e1.getMessage());
                    e1.printStackTrace();
                }
            }
            //Make the renderer reappear.
            fireEditingStopped();
        }
    }

    //Implement the one CellEditor method that AbstractCellEditor doesn't.
    public Object getCellEditorValue() {
        return currentPicture;
    }

    //Implement the one method defined by TableCellEditor.
    public Component getTableCellEditorComponent(JTable table,
                                                 Object value,
                                                 boolean isSelected,
                                                 int row,
                                                 int column) {
        currentPicture = (Picture) value;
        return button;
    }
}
