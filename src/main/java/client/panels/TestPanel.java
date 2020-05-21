package client.panels;

import client.components.ColourEditor;
import client.components.ColourRenderer;
import client.components.PictureEditor;
import client.components.PictureRenderer;
import client.components.table.DisplayableObjectTableModel;
import client.components.table.ObjectTableModel;
import common.models.Billboard;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class TestPanel extends JPanel {

    public TestPanel() {

        ObjectTableModel<Billboard> tableModel = new DisplayableObjectTableModel<>(Billboard.class);
        tableModel.setObjectRows(getBs());
        JTable table = new JTable(tableModel);

        //Set up renderer and editor for the Favorite Color column.
        table.setDefaultRenderer(Color.class,
            new ColourRenderer());
        table.setDefaultEditor(Color.class,
            new ColourEditor());
        table.setDefaultEditor(BufferedImage.class, new PictureEditor());
        table.setDefaultRenderer(BufferedImage.class, new PictureRenderer());

        JScrollPane pane = new JScrollPane(table);

        setLayout(new BorderLayout());
        add(pane, BorderLayout.CENTER);
        setVisible(true);
    }

    public static List<Billboard> getBs() {
        final List<Billboard> list = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            list.add(Billboard.Random(1));
        }
        return list;
    }
}
