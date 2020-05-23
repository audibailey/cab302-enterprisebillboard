package client.panels;

import client.components.table.*;
import client.services.ScheduleService;
import client.services.SessionService;
import common.models.Picture;
import common.models.Schedule;
import common.models.Session;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SchedulePanel extends JPanel implements ActionListener {

    ObjectTableModel<Schedule> tableModel;
    JTable table;
    Container buttonContainer = new Container();
    JButton createButton, refreshButton, deleteButton;
    String selected;


    public SchedulePanel() {
        // Get session
        Session session = SessionService.getInstance();
        // Adding button labels
        createButton = new JButton("Create Schedule");
        refreshButton = new JButton("Refresh");
        deleteButton = new JButton("Delete Selected");
        // Disable schedule button if user is not permitted
        if (!session.permissions.canScheduleBillboard) {
            createButton.setEnabled(false);
        }

        refreshButton.addActionListener(this::actionPerformed);
        deleteButton.addActionListener(this::actionPerformed);
        deleteButton.setEnabled(false);

        // Getting table data and configuring table
        tableModel = new DisplayableObjectTableModel(Schedule.class, null);
        tableModel.setObjectRows(ScheduleService.getInstance().refresh());
        table = new JTable(tableModel);
        setupSelection();
        setupRenderersAndEditors();
        JScrollPane pane = new JScrollPane(table);

        // Add buttons to container
        buttonContainer.setLayout(new FlowLayout());
        buttonContainer.add(createButton);
        buttonContainer.add(deleteButton);
        buttonContainer.add(refreshButton);

        // Add components to frame
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

            if (selected != null && session.permissions.canScheduleBillboard) {
                deleteButton.setEnabled(true);
                System.out.println(selected);
            } else {
                deleteButton.setEnabled(false);
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
    }
}
