package common.swing;

import javax.swing.*;

public class Notification {
    public static void display(String message) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                final JDialog dialog = new JDialog();
                dialog.setAlwaysOnTop(true);
                JOptionPane.showMessageDialog(dialog, message);
            }
        });
    }
}
