package viewer;

import common.models.Billboard;

import javax.swing.*;
import java.awt.*;

public class Panel extends JPanel {
    public Panel(Billboard billboard) throws Exception {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Check if billboard has a background colour attribute to add background colour
        if(billboard.backgroundColor != null){
            setBackground(Color.decode(billboard.backgroundColor)); // Setting background colour
        }

        // Check if information string is not empty and message and picture are empty
        if (billboard.information != null && billboard.message == null && billboard.picture == null) {
            add(new Information(billboard, 4, 2)); // Drawing information
        }
        // Check if information string and picture byte array are not empty
        if (billboard.information != null && billboard.message == null && billboard.picture != null) {
            add(new Picture(billboard,2, 3));
            add(Box.createVerticalGlue());
            add(new Information(billboard, 4, 3));
            add(Box.createVerticalGlue());
        }

        // Check if picture byte array is not empty
        if (billboard.picture != null && billboard.information == null && billboard.message == null) {
            add(new Picture(billboard, 2, 4));
        }
        // Check if picture byte array, information and message are not empty
        if (billboard.information != null && billboard.message != null && billboard.picture != null) {
            add(Box.createVerticalGlue());
            add(new Message(billboard));
            add(Box.createVerticalGlue());
            add(new Picture(billboard, 2, 6));
            add(Box.createVerticalGlue());
            add(new Information(billboard, 4, 1));
            add(Box.createVerticalGlue());
        }

        // Check if message string is not empty
        if (billboard.message != null && billboard.information == null && billboard.picture == null) {
            add(Box.createVerticalGlue());
            add(new Message(billboard));
            add(Box.createVerticalGlue());
        }

        // Check if message string and picture byte array is not empty
        if (billboard.message != null && billboard.information == null && billboard.picture != null) {
            add(Box.createVerticalGlue());
            add(new Message(billboard));
            add(Box.createVerticalGlue());

            add(new Picture(billboard, 2, 3));
        }

        // Check if message string and information are not empty
        if (billboard.message != null && billboard.information != null && billboard.picture == null) {
            add(Box.createVerticalGlue());
            add(new Message(billboard));
            add(Box.createVerticalGlue());
            add(new Information(billboard, 4, 2));
            add(Box.createVerticalGlue());
        }
    }

}
