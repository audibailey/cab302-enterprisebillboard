package client;

import javax.swing.*;
import java.awt.*;

public class MenuBar extends JMenuBar {
    private JMenu logout;

    Color bgColor=Color.WHITE;

    public void setColor(Color color) {
        bgColor=color;
    }

    public MenuBar() {
        add(Box.createHorizontalGlue());
        logout = new JMenu("Logout");
        add(logout);
    }

    public JMenu getLogout() {
        return logout;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(bgColor);
        g2d.fillRect(0, 0, getWidth() - 1, getHeight() - 1);
    }
}
