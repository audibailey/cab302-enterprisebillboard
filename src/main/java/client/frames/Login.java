package client.frames;

import client.Main;
import client.services.SessionService;
import common.models.Billboard;
import common.models.Session;
import common.router.IActionResult;
import common.router.Status;
import common.utils.ClientSocketFactory;
import common.utils.HashingFactory;
import common.utils.RandomFactory;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

public class Login extends JFrame implements ActionListener {

    Container container = getContentPane();
    JLabel usernameLabel = new JLabel("USERNAME");
    JLabel passwordLabel = new JLabel("PASSWORD");
    JTextField username = new JTextField(15);
    JPasswordField password = new JPasswordField(15);
    JButton login = new JButton("LOGIN");

    public Login() {
        setTitle("Billboard Control Panel: Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayoutManager();
        addComponentsToContainer();
        setLocationAndSize();
        // Set the listener for the login button and make it the default button for enter press
        login.addActionListener(this);
        getRootPane().setDefaultButton(login);
        setResizable(false);
        setVisible(true);
    }

    public void setLayoutManager() {
        container.setLayout(new GridBagLayout());
    }

    public void addComponentsToContainer() {
        GridBagConstraints c = new GridBagConstraints();
        Insets i = new Insets(5, 5, 5, 5);
        c.insets = i;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        container.add(usernameLabel, c);
        c.gridx = 1;
        c.gridy = 0;
        container.add(username, c);
        c.gridx = 0;
        c.gridy = 1;
        container.add(passwordLabel, c);
        c.gridx = 1;
        c.gridy = 1;
        container.add(password, c);
        c.gridx = 1;
        c.gridy = 2;
        c.gridwidth = 1;
        container.add(login, c);
    }

    public void setLocationAndSize() {
        // Get the screen dimensions
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenSize.width;
        int height = screenSize.height;

        // Display the window.
        // Get the screen size
        setSize(width / 2, height / 2);
        // Get the frame size for centering
        int x = (width - getWidth()) / 2;
        int y = (height - getHeight()) / 2;
        // Set the new frame location and show GUI
        setLocation(x, y);

        usernameLabel.setSize(100, 30);
        passwordLabel.setSize(100, 30);
        username.setSize(150, 30);
        password.setSize(150, 30);
        login.setSize(100, 30);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            // Use this to generate both the salt and password to store in the database manually
            String u = username.getText();

            HashMap<String, String> params = new HashMap<>();
            params.put("username", u);
            params.put("password", HashingFactory.hashPassword(String.valueOf(password.getPassword())));
            IActionResult res = new ClientSocketFactory("/login", null, params, null).Connect();

            if (res != null && res.status == Status.SUCCESS && res.body instanceof Session) {
                SessionService.setInstance((Session) res.body);
                System.out.println("Successfully logged in!");
                System.out.println("Your token is: " + SessionService.getInstance().token);
                dispose();
                Main.createAndShowClient();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
