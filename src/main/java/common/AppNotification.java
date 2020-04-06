package common;

import javax.swing.*;

public class AppNotification {
    public static void display(String title, String message, int type) {
        JOptionPane.showMessageDialog(null, message, title, type);
    }
}
