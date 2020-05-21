package client.panels;

import client.components.ColourEditor;
import client.components.ColourRenderer;
import client.components.PictureEditor;
import client.components.PictureRenderer;
import client.components.table.DisplayableObjectTableModel;
import client.components.table.ObjectTableModel;
import common.models.Billboard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class TestBillboardPanel extends JPanel implements ActionListener {

    JTable table;
    Container buttonContainer = new Container();
    JButton viewButton, createButton;
    String selected;

    public TestBillboardPanel() {
        createButton = new JButton("Create New");
        viewButton = new JButton("View Selected");
        viewButton.setEnabled(false);

        ObjectTableModel<Billboard> tableModel = new DisplayableObjectTableModel<>(Billboard.class);
        tableModel.setObjectRows(getBs());
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

    public static List<Billboard> getBs() {
        final List<Billboard> list = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            list.add(Billboard.Random(1));
        }
        return list;
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

        }
    }
}
