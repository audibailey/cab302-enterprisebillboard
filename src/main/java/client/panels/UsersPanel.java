package client.panels;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class UsersPanel extends JPanel {
    JButton button;

    public UsersPanel() {
        button = new JButton("Users");
        add(button);

    }
}
