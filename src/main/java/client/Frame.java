package client;

import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame {
    JLabel label;

    public Frame(String s) {
        super(s);
        label=new JLabel();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Get the screen size
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();

        setSize(1200, 600);

        int x = (screenSize.width - getWidth()) / 2;
        int y = (screenSize.height - getHeight()) / 2;

        //Set the new frame location
        setLocation(x, y);
        setVisible(true);
    }
}

