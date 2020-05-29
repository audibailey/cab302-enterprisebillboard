package client.panels;

import client.components.table.ColourEditor;
import client.components.table.ColourRenderer;
import client.components.table.PictureEditor;
import client.components.table.PictureRenderer;
import client.components.table.ObjectTableModel;
import client.components.table.IObjectTableModel;
import client.services.BillboardService;
import client.services.SessionService;
import common.utils.XML;
import viewer.Main;
import common.models.Billboard;
import common.utils.Picture;
import common.utils.session.Session;
import common.swing.Notification;
import org.w3c.dom.Document;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Optional;

/**
 * This class renders the Java Swing billboard panel for the client.
 *
 * @author Jamie Martin
 */
public class BillboardPanel extends JPanel implements ActionListener {

    IObjectTableModel<Billboard> tableModel;
    JTable table;
    Container buttonContainer = new Container();
    JButton viewButton, createButton, refreshButton, deleteButton, importButton, exportButton;
    String selected;

    public BillboardPanel() {
        Session session = SessionService.getInstance();

        createButton = new JButton("Create Billboard");
        viewButton = new JButton("View Selected");
        refreshButton = new JButton("Refresh");
        deleteButton = new JButton("Delete Selected");
        importButton = new JButton("Import into Selected");
        exportButton = new JButton("Export Selected");

        createButton.addActionListener(this::actionPerformed);

        if (!session.permissions.canCreateBillboard) {
            createButton.setEnabled(false);
        }

        viewButton.addActionListener(this::actionPerformed);
        refreshButton.addActionListener(this::actionPerformed);
        deleteButton.addActionListener(this::actionPerformed);
        importButton.addActionListener(this::actionPerformed);
        exportButton.addActionListener(this::actionPerformed);
        viewButton.setEnabled(false);
        deleteButton.setEnabled(false);
        importButton.setEnabled(false);
        exportButton.setEnabled(false);

        tableModel = new ObjectTableModel(Billboard.class, BillboardService.getInstance());
        tableModel.setObjectRows(BillboardService.getInstance().refresh());
        table = new JTable(tableModel);

        setupSelection();
        setupRenderersAndEditors();

        JScrollPane pane = new JScrollPane(table);

        buttonContainer.setLayout(new FlowLayout());
        buttonContainer.add(createButton);
        buttonContainer.add(viewButton);
        buttonContainer.add(deleteButton);
        buttonContainer.add(importButton);
        buttonContainer.add(exportButton);
        buttonContainer.add(refreshButton);

        setLayout(new BorderLayout());
        add(buttonContainer, BorderLayout.NORTH);
        add(pane, BorderLayout.CENTER);
        setVisible(true);
    }

    public void setupSelection() {
        table.setAutoCreateRowSorter(true);
        table.setCellSelectionEnabled(true);
        ListSelectionModel cellSelectionModel = table.getSelectionModel();
        cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        cellSelectionModel.addListSelectionListener(e -> {
            selected = (String)table.getModel().getValueAt(table.getSelectedRow(), 1);
            Session session = SessionService.getInstance();

            Optional<Billboard> selectedValue = tableModel.getObjectRows().stream().filter(x -> x.name.equals(selected)).findFirst();

            if (selected != null) {
                viewButton.setEnabled(true);
            } else {
                viewButton.setEnabled(false);
            }

            if (selectedValue.isPresent() && (session.permissions.canEditBillboard || session.userId == selectedValue.get().userId)) {
                if (selected != null) {
                    deleteButton.setEnabled(true);
                    importButton.setEnabled(true);
                    exportButton.setEnabled(true);
                } else {
                    deleteButton.setEnabled(false);
                    importButton.setEnabled(false);
                    exportButton.setEnabled(false);
                }
            }
        });
    }

    public void setupRenderersAndEditors() {
        //Set up renderer and editor for the Favourite Colour column.
        table.setDefaultRenderer(Color.class, new ColourRenderer());
        table.setDefaultEditor(Color.class, new ColourEditor());
        table.setDefaultEditor(Picture.class, new PictureEditor());
        table.setDefaultRenderer(Picture.class, new PictureRenderer());
    }

    @Override
    // Adding listener events for the user panel buttons.
    public void actionPerformed(ActionEvent e) {
        // Check if new user button is pressed
        if(e.getSource() == createButton){
            String result = (String)JOptionPane.showInputDialog(
                this,
                "Input a new billboard name",
                "Create Billboard",
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                ""
            );

            if (result != null) {
                Billboard b = new Billboard();
                b.name = result;

                tableModel.setObjectRows(BillboardService.getInstance().insert(b));
                tableModel.fireTableDataChanged();
            }
        }
        // Check if edit user button is pressed
        if(e.getSource() == viewButton){
            SwingUtilities.invokeLater(() -> {
                try {
                   Main.createAndShowGUI(tableModel.getObjectRows().stream().filter(x -> x.name.equals(selected)).findFirst().get());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
        }

        if (e.getSource() == refreshButton) {
            tableModel.setObjectRows(BillboardService.getInstance().refresh());
            tableModel.fireTableDataChanged();
        }

        if (e.getSource() == deleteButton) {
            tableModel.setObjectRows(BillboardService.getInstance().delete(tableModel.getObjectRows().stream().filter(x -> x.name.equals(selected)).findFirst().get()));
            tableModel.fireTableDataChanged();
        }

        if (e.getSource() == importButton) {
            try {
                // open file picker for xml
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileFilter(new FileNameExtensionFilter("XML file", "xml"));
                int returnVal = fileChooser.showOpenDialog(new JDialog((Window) null));

                // get file
                if (returnVal == JFileChooser.APPROVE_OPTION) {

                    String xml = XML.readFile(fileChooser.getSelectedFile().getPath());

                    // update selected billboard with variables
                    Optional<Billboard> selectedBillboard = tableModel.getObjectRows().stream().filter(x -> x.name.equals(selected)).findFirst();

                    if (selectedBillboard.isPresent()) {

                        Billboard billboard = XML.fromXML(xml, selectedBillboard.get());

                        Boolean success = BillboardService.getInstance().update(billboard);

                        if (success) {
                            tableModel.setObjectRows(BillboardService.getInstance().refresh());
                            tableModel.fireTableDataChanged();
                        }
                    }
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Failed to load the file: " + ex.getMessage());
                ex.printStackTrace();
            }
        }

        if (e.getSource() == exportButton) {
            try {
                Optional<Billboard> selectedBillboard = tableModel.getObjectRows().stream().filter(x -> x.name.equals(selected)).findFirst();

                if (selectedBillboard.isPresent()) {
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setFileFilter(new FileNameExtensionFilter("XML file", "xml"));
                    int returnVal = fileChooser.showSaveDialog(new JDialog((Window) null));

                    if (returnVal == JFileChooser.APPROVE_OPTION) {
                        Billboard billboard = selectedBillboard.get();

                        String xml = XML.toXML(billboard);
                        Document document = XML.toDocument(xml);
                        File file = fileChooser.getSelectedFile();

                        XML.saveDocument(document, file);

                        Notification.display("Successfully exported " + billboard.name + " to " + fileChooser.getName(fileChooser.getSelectedFile()));
                    }
                }
            } catch (Exception ex) {
                Notification.display("Error exporting: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
    }
}
