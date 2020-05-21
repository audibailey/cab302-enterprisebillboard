package client.panels;

import client.components.SelectableTable;
import client.components.SelectableTableModel;
import client.frames.createEditUserFrame;
import client.services.BillboardService;
import client.services.SessionService;
import common.models.Billboard;
import common.models.Session;
import common.router.IActionResult;
import common.utils.ClientSocketFactory;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class BillboardPanel extends JPanel implements ActionListener {

    JButton createButton = new JButton("Create Billboard");
    JButton editButton = new JButton("Edit Billboard");

    String selected;

    private String[] columnNames = {"Name", "Message" };

    SelectableTable table = new SelectableTable(new SelectableTableModel(new Object[][] {}, columnNames), new ListSelectionListener() {
        public void valueChanged(ListSelectionEvent e) {
            selected = (String) table.getModel().getValueAt(table.getSelectedRow(), 0);
            System.out.println(selected);
        }
    });

    JScrollPane scrollPane = new JScrollPane(table);

    public BillboardPanel() {
        setLayout(new GridBagLayout());
        createButton.addActionListener(this::actionPerformed);
        editButton.addActionListener(this::actionPerformed);
        addComponentsToContainer();

        Session session = SessionService.getInstance();

        if (session != null) {
            setModel(Billboard.objectify(BillboardService.getInstance().billboards));
        }
    }

    public void addComponentsToContainer() {
        GridBagConstraints gbc = new GridBagConstraints();
        Insets i = new Insets(5, 5, 5, 5);
        gbc.insets = i;
        gbc.fill = GridBagConstraints.CENTER;
        gbc.gridwidth = 1;

        Session session = SessionService.getInstance();

        if (session == null) {
            gbc.gridx = 0;
            gbc.gridy = 0;
            add(createButton, gbc);

            gbc.gridx = 1;
            gbc.gridy = 0;
            add(editButton, gbc);
        } else {
            if (session.permissions.canCreateBillboard) {
                gbc.gridx = 0;
                gbc.gridy = 0;
                add(createButton, gbc);
            }

            if (session.permissions.canEditBillboard) {
                gbc.gridx = 1;
                gbc.gridy = 0;
                add(editButton, gbc);
            }
        }

        GridBagConstraints table_gbc = new GridBagConstraints();

        table_gbc.weightx = 1.0;
        table_gbc.weighty = 1.0;
        table_gbc.gridx = 0;
        table_gbc.gridy = 1;
        table_gbc.gridwidth = 6;
        table_gbc.fill = GridBagConstraints.BOTH;
        add(scrollPane, table_gbc);
        table.setFillsViewportHeight(true);
    }

    public void setModel(Object[][] objects) {
        SwingUtilities.invokeLater(() -> table.setModel(new SelectableTableModel(objects, columnNames)));
    }

    @Override
    // Adding listener events for the user panel buttons.
    public void actionPerformed(ActionEvent e) {
        // Check if new user button is pressed
        if(e.getSource() == createButton){
            setModel(new Object[][] { { "Billboard", "Hello!"} });
        }
        // Check if edit user button is pressed
        if(e.getSource() == editButton){
            //new createEditUserFrame("EditUser"); // Open edit user frame
        }
    }
}
