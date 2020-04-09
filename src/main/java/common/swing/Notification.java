package common.swing;

import javax.swing.*;

public class Notification {
    public static void display(String title, String message, int type) {
        JOptionPane.showMessageDialog(null, message, title, type);
    }
}
