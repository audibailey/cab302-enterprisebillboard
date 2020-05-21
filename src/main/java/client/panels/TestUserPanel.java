package client.panels;

import client.components.ColourEditor;
import client.components.ColourRenderer;
import client.components.PictureEditor;
import client.components.PictureRenderer;
import client.components.table.DisplayableObjectTableModel;
import client.components.table.ObjectTableModel;
import client.services.BillboardService;
import client.services.PermissionsService;
import common.models.Permissions;
import common.models.User;
import common.models.UserPermissions;
import common.utils.HashingFactory;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TestUserPanel extends JPanel implements ActionListener {

    JTable table;
    Container buttonContainer = new Container();
    JButton editButton, createButton;
    String selected;

    public TestUserPanel() {
        createButton = new JButton("Create New");
        editButton = new JButton("Edit Password");
        createButton.addActionListener(this::actionPerformed);
        editButton.addActionListener(this::actionPerformed);
        editButton.setEnabled(false);

        ObjectTableModel<Permissions> tableModel = new DisplayableObjectTableModel<>(Permissions.class, PermissionsService.getInstance());
        tableModel.setObjectRows(PermissionsService.getInstance().refresh());
        table = new JTable(tableModel);

        setupSelection();
        setupRenderersAndEditors();

        JScrollPane pane = new JScrollPane(table);

        buttonContainer.setLayout(new FlowLayout());
        buttonContainer.add(createButton);
        buttonContainer.add(editButton);

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
            editButton.setEnabled(true);
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
            String username = (String)JOptionPane.showInputDialog(
                this,
                "Input a username",
                "Username",
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                ""
            );

            String password = (String)JOptionPane.showInputDialog(
                this,
                "Input a password for user: " + username,
                "Password",
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                ""
            );

            try {
                User user = new User();
                user.username = username;
                user.password = HashingFactory.hashPassword(password);

                // PermissionsService.getInstance().insert();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        // Check if edit user button is pressed
        if(e.getSource() == editButton){
            String result = (String)JOptionPane.showInputDialog(
                this,
                "Input a new password for user: " + selected,
                "Edit Password",
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                ""
            );

            try {
                PermissionsService.updatePassword(selected, result);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }
}
