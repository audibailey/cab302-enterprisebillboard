package client.frames;

import common.models.Billboard;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BillboardFrame extends JFrame implements ActionListener {

    Container container = getContentPane();

    JPanel panel = new JPanel();

    JLabel nameLabel = new JLabel("NAME");
    JTextField name = new JTextField(25);

    JLabel messageLabel = new JLabel("MESSAGE");
    JTextArea message = new JTextArea(3, 25);
    JColorChooser messageColor = new JColorChooser(Color.BLACK);

    JLabel pictureLabel = new JLabel("PICTURE");
    JFileChooser picture = new JFileChooser();
    JColorChooser backgroundColor = new JColorChooser(Color.WHITE);

    JLabel informationLabel = new JLabel("INFORMATION");
    JTextArea information = new JTextArea(3,25);
    JColorChooser informationColor = new JColorChooser(Color.BLACK);

    JButton save = new JButton("Save");

    public BillboardFrame(Billboard billboard) {
        setTitle("Edit Billboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayoutManager();
        addComponentsToContainer();
        setLocationAndSize();
        setDefaultValues(billboard);
        // Set the listener for the save button and make it the default button for enter press
        save.addActionListener(this);
        getRootPane().setDefaultButton(save);
        setResizable(false);
        container.add(new JScrollPane(panel));

        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        jfc.setDialogTitle("Select an image");
        jfc.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("PNG and GIF images", "png", "gif");
        jfc.addChoosableFileFilter(filter);

        int returnValue = jfc.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            System.out.println(jfc.getSelectedFile().getPath());
        }


        setVisible(true);
    }

    public void setLayoutManager() {
        panel.setLayout(new GridBagLayout());
    }

    public void addComponentsToContainer() {
        GridBagConstraints c = new GridBagConstraints();
        Insets i = new Insets(5, 5, 5, 5);
        c.insets = i;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        panel.add(nameLabel, c);
        c.gridx = 1;
        c.gridy = 0;
        panel.add(name, c);

        c.gridx = 0;
        c.gridy = 1;
        panel.add(messageLabel, c);
        c.gridx = 1;
        c.gridy = 1;
        panel.add(message, c);
        c.gridx = 1;
        c.gridy = 2;
        panel.add(messageColor, c);

        c.gridx = 0;
        c.gridy = 3;
        panel.add(pictureLabel, c);
        c.gridx = 1;
        c.gridy = 3;
        panel.add(picture, c);
        c.gridx = 1;
        c.gridy = 4;
        panel.add(backgroundColor, c);

        c.gridx = 0;
        c.gridy = 5;
        panel.add(informationLabel, c);
        c.gridx = 1;
        c.gridy = 5;
        panel.add(information, c);
        c.gridx = 1;
        c.gridy = 6;
        panel.add(informationColor, c);

        c.gridx = 1;
        c.gridy = 2;
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

    public void setDefaultValues(Billboard b) {
        name.setText(b.name);
        message.setText(b.message);
        messageColor.setColor(Color.decode(b.messageColor));

        backgroundColor.setColor(Color.decode(b.backgroundColor));

        information.setText(b.information);
        informationColor.setColor(Color.decode(b.informationColor));
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
