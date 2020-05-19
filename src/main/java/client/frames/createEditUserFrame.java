package client.frames;

import common.models.Billboard;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class consists of the Create/Edit user frame.
 * All methods that manage and create this part of the GUI are present in this file.
 *
 * @author Trevor Waturuocha
 */

public class createEditUserFrame extends JFrame implements ActionListener {
    Container container = getContentPane(); // Get parent container

    JPanel panel = new JPanel(); // Panel to draw contents into

    JLabel nameLabel = new JLabel("Username:"); // Username label
    JTextField name = new JTextField(25); // Username label field

    JLabel perms = new JLabel("Select user permissions:"); // Select user permissions label
    JCheckBox bPerms = new JCheckBox("Create billboards");// Create billboards permission checkbox
    JCheckBox eBPerms = new JCheckBox("Create billboards");// Edit all billboards permission checkbox
    JCheckBox sBPerms = new JCheckBox("Create billboards");// Schedule billboards permission checkbox
    JCheckBox eUPerms = new JCheckBox("Create billboards");// Edit users permission checkbox



    JButton save = new JButton("Save");

    public createEditUserFrame(String choice) {
        // Check if create user button is selected
        if(choice == "CreateUser"){
            setTitle("Create User"); // Add create user title
        }
        // Check if edit user button is selected
        if(choice == "EditUser"){
            setTitle("Edit User"); // Add edit user title
        }
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayoutManager();
        addComponentsToContainer();
        setLocationAndSize();
        // Set the listener for the save button and make it the default button for enter press
        save.addActionListener(this);
        getRootPane().setDefaultButton(save);
        setResizable(false);
        container.add(new JScrollPane(panel));
        setVisible(true);
    }

    public void setLayoutManager() {
        panel.setLayout(new GridBagLayout());
    }

    public void addComponentsToContainer() {
        GridBagConstraints c = new GridBagConstraints();
        Insets i = new Insets(5, 5, 5, 5);
        c.insets = i;
        c.fill = GridBagConstraints.HORIZONTAL; // Setting grid layout constraints
        // Add username label to panel
        c.gridx = 0;
        c.gridy = 0;
        panel.add(nameLabel, c);
        // Add username text box to panel
        c.gridx = 1;
        c.gridy = 0;
        panel.add(name, c);
        // Add user permissions label to panel
        c.gridx = 0;
        c.gridy = 1;
        panel.add(perms, c);
        // Add create billboard permissions checkbox to panel
        c.gridx = 1;
        c.gridy = 1;
        panel.add(bPerms, c);
        // Add edit billboard permissions checkbox to panel
        c.gridx = 1;
        c.gridy = 2;
        panel.add(eBPerms, c);
        // Add schedule billboard permissions checkbox to panel
        c.gridx = 1;
        c.gridy = 3;
        panel.add(sBPerms, c);
        // Add edit user permissions checkbox to panel
        c.gridx = 1;
        c.gridy = 4;
        panel.add(eUPerms, c);
        // Add save button to panel
        c.gridx = 0;
        c.gridy = 5;
        c.gridwidth = 1;
        panel.add(save, c);
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
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Check if save button is pressed
        if (e.getSource() == save){
            setVisible(false);// Close frame (add save functionality next)
        }
    }
}
