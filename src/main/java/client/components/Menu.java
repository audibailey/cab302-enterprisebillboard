package client.components;

import javax.swing.*;
import java.awt.*;

public class Menu extends JMenuBar {
    Color bgColor = Color.WHITE;

    private JMenuItem logout = new JMenuItem("Logout") {
        @Override
        public Dimension getMaximumSize() {
            setBackground(bgColor);
            Dimension d1 = super.getPreferredSize();
            Dimension d2 = super.getMaximumSize();
            d2.width = d1.width;
            return d2;
        }
    };

    private JMenuItem updatePassword = new JMenuItem("Update Password") {
        @Override
        public Dimension getMaximumSize() {
            setBackground(bgColor);
            Dimension d1 = super.getPreferredSize();
            Dimension d2 = super.getMaximumSize();
            d2.width = d1.width;
            return d2;
        }
    };

    public Menu() {
        add(Box.createHorizontalGlue());
        add(updatePassword);
        add(logout);
    }

    public JMenuItem getLogout() {
        return logout;
    }

    public JMenuItem getUpdatePassword() { return updatePassword; }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(bgColor);
        g2d.fillRect(0, 0, getWidth() - 1, getHeight() - 1);
    }
}
