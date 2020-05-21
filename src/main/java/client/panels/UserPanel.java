package client.panels;

<<<<<<< HEAD
import client.components.table.ColourEditor;
import client.components.table.ColourRenderer;
import client.components.table.PictureEditor;
import client.components.table.PictureRenderer;
import client.components.table.DisplayableObjectTableModel;
import client.components.table.ObjectTableModel;
import client.services.BillboardService;
import client.services.PermissionsService;
import client.services.SessionService;
import common.models.*;
import common.swing.Notification;
import common.utils.HashingFactory;
import client.frames.CreateEditUserFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class UserPanel extends JPanel implements ActionListener {

    ObjectTableModel<Permissions> tableModel;
    JTable table;
    Container buttonContainer = new Container();
    JButton editButton, createButton, refreshButton, deleteButton;
    String selected;

    public UserPanel() {
        createButton = new JButton("Create New");
        editButton = new JButton("Edit Password");
        refreshButton = new JButton("Refresh");
        deleteButton = new JButton("Delete Selected");
        createButton.addActionListener(this::actionPerformed);
        editButton.addActionListener(this::actionPerformed);
        refreshButton.addActionListener(this::actionPerformed);
        deleteButton.addActionListener(this::actionPerformed);
        editButton.setEnabled(false);
        deleteButton.setEnabled(false);

        tableModel = new DisplayableObjectTableModel<>(Permissions.class, PermissionsService.getInstance());
        tableModel.setObjectRows(PermissionsService.getInstance().refresh());
        table = new JTable(tableModel);

        setupSelection();
        setupRenderersAndEditors();

        JScrollPane pane = new JScrollPane(table);

        buttonContainer.setLayout(new FlowLayout());
        buttonContainer.add(createButton);
        buttonContainer.add(editButton);
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

            if (selected != null) {
                editButton.setEnabled(true);
                deleteButton.setEnabled(true);

                System.out.println(selected);
            } else {
                editButton.setEnabled(false);
                deleteButton.setEnabled(false);
            }
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

            if (username != null) {
                String password = (String)JOptionPane.showInputDialog(
                    this,
                    "Input a password for user: " + username,
                    "Password",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    null,
                    ""
                );

                if (password != null) {
                    // TODO: Get Permissions from dialog or something

                    try {
                        User user = new User();
                        user.username = username;
                        user.password = HashingFactory.hashPassword(password);

                        Permissions permissions = Permissions.Random(0, username);

                        tableModel.setObjectRows(PermissionsService.getInstance().insert(new UserPermissions(user, permissions)));
                        tableModel.fireTableDataChanged();
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
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

            int id = tableModel.getObjectRows().stream().filter(x -> x.username.equals(selected)).findFirst().get().id;

            try {
                PermissionsService.getInstance().updatePassword(id, selected, result);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }

        if (e.getSource() == refreshButton) {
            tableModel.setObjectRows(PermissionsService.getInstance().refresh());
            tableModel.fireTableDataChanged();
        }

        if (e.getSource() == deleteButton) {
            Session session = SessionService.getInstance();
            var permissionList = tableModel.getObjectRows();

            if (session.username.equals(selected)) {
                Notification.display("Cannot delete yourself!");
            } else if (permissionList.size() == 1) {
                Notification.display("Cannot delete the only user!");
            } else {
                Permissions permissions = permissionList.stream().filter(x -> x.username.equals(selected)).findFirst().get();

                User u = new User();
                u.id = permissions.id;
                u.username = permissions.username;

                tableModel.setObjectRows(PermissionsService.getInstance().delete(u));
                tableModel.fireTableDataChanged();
            }
=======
        if(e.getSource() == addButton){
            new CreateEditUserFrame("CreateUser"); // Open create user frame
        }
        // Check if edit user button is pressed
        if(e.getSource() == editButton){
            new CreateEditUserFrame("EditUser"); // Open edit user frame
>>>>>>> e3e2f07bcc3f8ff7e6e903a768f76cec5e169f5f
        }
    }
}
