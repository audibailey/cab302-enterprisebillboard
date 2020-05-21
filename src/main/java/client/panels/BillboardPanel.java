package client.panels;

import client.components.table.ColourEditor;
import client.components.table.ColourRenderer;
import client.components.table.PictureEditor;
import client.components.table.PictureRenderer;
import client.components.table.DisplayableObjectTableModel;
import client.components.table.ObjectTableModel;
import client.frames.CreateEditUserFrame;
import client.services.BillboardService;
import client.services.SessionService;
import common.models.Billboard;
import common.models.Picture;
import common.models.Session;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Optional;

public class BillboardPanel extends JPanel implements ActionListener {

    ObjectTableModel<Billboard> tableModel;
    JTable table;
    Container buttonContainer = new Container();
    JButton viewButton, createButton, refreshButton, deleteButton;
    String selected;

    public BillboardPanel() {
        Session session = SessionService.getInstance();

        createButton = new JButton("Create New");
        viewButton = new JButton("View Selected");
        refreshButton = new JButton("Refresh");
        deleteButton = new JButton("Delete Selected");
        createButton.addActionListener(this::actionPerformed);

        if (!session.permissions.canCreateBillboard) {
            createButton.setEnabled(false);
        }

        viewButton.addActionListener(this::actionPerformed);
        refreshButton.addActionListener(this::actionPerformed);
        deleteButton.addActionListener(this::actionPerformed);
        viewButton.setEnabled(false);
        deleteButton.setEnabled(false);

        tableModel = new DisplayableObjectTableModel<>(Billboard.class, BillboardService.getInstance());
        tableModel.setObjectRows(BillboardService.getInstance().refresh());
        table = new JTable(tableModel);

        setupSelection();
        setupRenderersAndEditors();

        JScrollPane pane = new JScrollPane(table);

        buttonContainer.setLayout(new FlowLayout());
        buttonContainer.add(createButton);
        buttonContainer.add(viewButton);
        buttonContainer.add(refreshButton);
        buttonContainer.add(deleteButton);

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

            if (selectedValue.isPresent() && (session.permissions.canEditBillboard || session.userId == selectedValue.get().userId)) {
                if (selected != null) {
                    viewButton.setEnabled(true);
                    deleteButton.setEnabled(true);

                    System.out.println(selected);
                } else {
                    viewButton.setEnabled(false);
                    deleteButton.setEnabled(false);
                }
            }
        });
    }

    public void setupRenderersAndEditors() {
        //Set up renderer and editor for the Favourite Colour column.
        table.setDefaultRenderer(Color.class,
            new ColourRenderer());
        table.setDefaultEditor(Color.class,
            new ColourEditor());
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
                    viewer.Main.createAndShowGUI(tableModel.getObjectRows().stream().filter(x -> x.name.equals(selected)).findFirst().get());
                } catch (IOException ioException) {
                    ioException.printStackTrace();
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
    }
}
