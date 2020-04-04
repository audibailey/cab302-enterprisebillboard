package client;

import javax.swing.*;
import java.awt.*;

public class MenuBar extends JMenuBar {
    Color bgColor=Color.WHITE;

    public void setColor(Color color) {
        bgColor=color;
    }

    public MenuBar() {
        add(new JMenu("Billboards"));
        add(new JMenu("Schedules"));
        add(new JMenu("Users"));
        add(Box.createHorizontalGlue());
        add(new JMenu("Logout"));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(bgColor);
        g2d.fillRect(0, 0, getWidth() - 1, getHeight() - 1);
    }
}
