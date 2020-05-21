package client.panels;

import client.components.ColourEditor;
import client.components.ColourRenderer;
import client.components.PictureEditor;
import client.components.PictureRenderer;
import client.components.table.DisplayableObjectTableModel;
import client.components.table.ObjectTableModel;
import client.services.BillboardService;
import common.models.Billboard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class TestBillboardPanel extends JPanel implements ActionListener {

    JTable table;
    Container buttonContainer = new Container();
    JButton viewButton, createButton;
    String selected;

    public TestBillboardPanel() {
        createButton = new JButton("Create New");
        viewButton = new JButton("View Selected");
        createButton.addActionListener(this::actionPerformed);
        viewButton.addActionListener(this::actionPerformed);
        viewButton.setEnabled(false);

        ObjectTableModel<Billboard> tableModel = new DisplayableObjectTableModel<>(Billboard.class);
        tableModel.setObjectRows(BillboardService.getInstance());
        table = new JTable(tableModel);

        setupSelection();
        setupRenderersAndEditors();

        JScrollPane pane = new JScrollPane(table);

        buttonContainer.setLayout(new FlowLayout());
        buttonContainer.add(createButton);
        buttonContainer.add(viewButton);

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
            viewButton.setEnabled(true);
            System.out.println(selected);
        });
    }

    public void setupRenderersAndEditors() {
        //Set up renderer and editor for the Favorite Color column.
        table.setDefaultRenderer(Color.class,
            new ColourRenderer());
        table.setDefaultEditor(Color.class,
            new ColourEditor());
        table.setDefaultEditor(BufferedImage.class, new PictureEditor());
        table.setDefaultRenderer(BufferedImage.class, new PictureRenderer());
    }


    @Override
    // Adding listener events for the user panel buttons.
    public void actionPerformed(ActionEvent e) {
        // Check if new user button is pressed
        if(e.getSource() == createButton){
            // TODO : Spawn make billboard
        }
        // Check if edit user button is pressed
        if(e.getSource() == viewButton){
            SwingUtilities.invokeLater(() -> {
                try {
                    viewer.Main.createAndShowGUI(BillboardService.getInstance().stream().filter(x -> x.name.equals(selected)).findFirst().get());
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            });
        }
    }
}
