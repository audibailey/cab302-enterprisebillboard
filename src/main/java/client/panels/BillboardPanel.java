package client.panels;

import client.components.table.ColourEditor;
import client.components.table.ColourRenderer;
import client.components.table.PictureEditor;
import client.components.table.PictureRenderer;
import client.components.table.DisplayableObjectTableModel;
import client.components.table.ObjectTableModel;
import client.services.BillboardService;
import client.services.SessionService;
import viewer.Main;
import com.mysql.cj.log.Log;
import common.models.Billboard;
import common.models.Picture;
import common.models.Session;
import common.swing.Notification;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Base64;
import java.util.Optional;

public class BillboardPanel extends JPanel implements ActionListener {

    ObjectTableModel<Billboard> tableModel;
    JTable table;
    Container buttonContainer = new Container();
    JButton viewButton, createButton, refreshButton, deleteButton, importButton, exportButton;
    String selected;

    public BillboardPanel() {
        Session session = SessionService.getInstance();

        createButton = new JButton("Create Billboard");
        viewButton = new JButton("View Selected");
        refreshButton = new JButton("Refresh");
        deleteButton = new JButton("Delete Selected");
        importButton = new JButton("Import into Selected");
        exportButton = new JButton("Export Selected");

        createButton.addActionListener(this::actionPerformed);

        if (!session.permissions.canCreateBillboard) {
            createButton.setEnabled(false);
        }

        viewButton.addActionListener(this::actionPerformed);
        refreshButton.addActionListener(this::actionPerformed);
        deleteButton.addActionListener(this::actionPerformed);
        importButton.addActionListener(this::actionPerformed);
        exportButton.addActionListener(this::actionPerformed);
        viewButton.setEnabled(false);
        deleteButton.setEnabled(false);
        importButton.setEnabled(false);
        exportButton.setEnabled(false);

        tableModel = new DisplayableObjectTableModel(Billboard.class, BillboardService.getInstance());
        tableModel.setObjectRows(BillboardService.getInstance().refresh());
        table = new JTable(tableModel);

        setupSelection();
        setupRenderersAndEditors();

        JScrollPane pane = new JScrollPane(table);

        buttonContainer.setLayout(new FlowLayout());
        buttonContainer.add(createButton);
        buttonContainer.add(viewButton);
        buttonContainer.add(deleteButton);
        buttonContainer.add(importButton);
        buttonContainer.add(exportButton);
        buttonContainer.add(refreshButton);

        setLayout(new BorderLayout());
        add(buttonContainer, BorderLayout.NORTH);
        add(pane, BorderLayout.CENTER);
        setVisible(true);
    }

    public void setupSelection() {
        table.setAutoCreateRowSorter(true);
        table.setCellSelectionEnabled(true);
        ListSelectionModel cellSelectionModel = table.getSelectionModel();
        cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        cellSelectionModel.addListSelectionListener(e -> {
            selected = (String)table.getModel().getValueAt(table.getSelectedRow(), 1);
            Session session = SessionService.getInstance();

            Optional<Billboard> selectedValue = tableModel.getObjectRows().stream().filter(x -> x.name.equals(selected)).findFirst();

            if (selectedValue.isPresent() && (session.permissions.canEditBillboard || session.userId == selectedValue.get().userId)) {
                if (selected != null) {
                    viewButton.setEnabled(true);
                    deleteButton.setEnabled(true);
                    importButton.setEnabled(true);
                    exportButton.setEnabled(true);
                    System.out.println(selected);
                } else {
                    viewButton.setEnabled(false);
                    deleteButton.setEnabled(false);
                    importButton.setEnabled(false);
                    exportButton.setEnabled(false);
                }
            }
        });
    }

    public void setupRenderersAndEditors() {
        //Set up renderer and editor for the Favourite Colour column.
        table.setDefaultRenderer(Color.class, new ColourRenderer());
        table.setDefaultEditor(Color.class, new ColourEditor());
        table.setDefaultEditor(Picture.class, new PictureEditor());
        table.setDefaultRenderer(Picture.class, new PictureRenderer());
    }

    @Override
    // Adding listener events for the user panel buttons.
    public void actionPerformed(ActionEvent e) {
        // Check if new user button is pressed
        if(e.getSource() == createButton){
            String result = (String)JOptionPane.showInputDialog(
                this,
                "Input a new billboard name",
                "Create Billboard",
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                ""
            );

            if (result != null) {
                Billboard b = new Billboard();
                b.name = result;

                tableModel.setObjectRows(BillboardService.getInstance().insert(b));
                tableModel.fireTableDataChanged();
            }
        }
        // Check if edit user button is pressed
        if(e.getSource() == viewButton){
            SwingUtilities.invokeLater(() -> {
                try {
                   Main.createAndShowGUI(tableModel.getObjectRows().stream().filter(x -> x.name.equals(selected)).findFirst().get());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
        }

        if (e.getSource() == refreshButton) {
            tableModel.setObjectRows(BillboardService.getInstance().refresh());
            tableModel.fireTableDataChanged();
        }

        if (e.getSource() == deleteButton) {
            tableModel.setObjectRows(BillboardService.getInstance().delete(tableModel.getObjectRows().stream().filter(x -> x.name.equals(selected)).findFirst().get()));
            tableModel.fireTableDataChanged();
        }

        if (e.getSource() == importButton) {
            try {
                // open file picker for xml
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileFilter(new FileNameExtensionFilter("XML file", "xml"));
                int returnVal = fileChooser.showOpenDialog(new JDialog((Window) null));

                // get file
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();

                    DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory
                        .newInstance();
                    DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

                    Document document = documentBuilder.parse(file);

                    // parse variables
                    String billboardColour = getAttributeValue("billboard", "background", document);
                    String message = getTagValue("message", document);
                    String messageColor = getAttributeValue("message", "colour", document);

                    String pictureUrl = getAttributeValue("picture", "url", document);
                    String pictureData = getAttributeValue("picture", "data", document);

                    String information = getTagValue("information", document);
                    String informationColour = getAttributeValue("information", "colour", document);

                    // update selected billboard with variables
                    Optional<Billboard> selectedBillboard = tableModel.getObjectRows().stream().filter(x -> x.name.equals(selected)).findFirst();

                    if (selectedBillboard.isPresent()) {
                        Billboard billboard = selectedBillboard.get();

                        billboard.backgroundColor = billboardColour == null ? "#FFFFFF" : billboardColour;
                        billboard.message = message;
                        billboard.messageColor = messageColor == null ? "#000000" : messageColor;

                        billboard.information = information;
                        billboard.informationColor = informationColour == null ? "#FFFFFF" : informationColour;

                        if (pictureUrl != null && pictureData != null) {
                            throw new Exception("Picture cannot have both url and data");
                        } else if (pictureUrl != null) {
                            String encoded =  getByteArrayFromImageURL(pictureUrl);
                            byte[] base64 = Base64.getDecoder().decode(encoded);
                            BufferedImage pictureOutput = ImageIO.read(new ByteArrayInputStream(base64));

                            if (pictureOutput != null) {
                                billboard.picture = encoded;
                            }

                        } else if (pictureData != null) {
                            billboard.picture = pictureData;
                        } else {
                            billboard.picture = null;
                        }

                        Boolean success = BillboardService.getInstance().update(billboard);

                        if (success) {
                            tableModel.setObjectRows(BillboardService.getInstance().refresh());
                            tableModel.fireTableDataChanged();
                        }
                    }
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Failed to load the file: " + ex.getMessage());
                ex.printStackTrace();
            }
        }

        if (e.getSource() == exportButton) {
            try {
                Optional<Billboard> selectedBillboard = tableModel.getObjectRows().stream().filter(x -> x.name.equals(selected)).findFirst();

                if (selectedBillboard.isPresent()) {
                    Billboard billboard = selectedBillboard.get();

                    DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();

                    DocumentBuilder documentBuilder = null;

                    documentBuilder = documentFactory.newDocumentBuilder();

                    Document document = documentBuilder.newDocument();

                    // root element
                    Element root = document.createElement("billboard");
                    root.setAttribute("background", billboard.backgroundColor);
                    document.appendChild(root);

                    if (billboard.message != null && !billboard.message.isEmpty()) {
                        Element element = document.createElement("message");
                        element.setTextContent(billboard.message);
                        element.setAttribute("colour", billboard.messageColor);

                        root.appendChild(element);
                    }

                    if (billboard.picture != null && !billboard.picture.isEmpty()) {
                        Element element = document.createElement("picture");
                        element.setAttribute("data", billboard.picture);

                        root.appendChild(element);
                    }

                    if (billboard.information != null && !billboard.information.isBlank()) {
                        Element element = document.createElement("information");
                        element.setTextContent(billboard.information);
                        element.setAttribute("colour", billboard.informationColor);

                        root.appendChild(element);
                    }

                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setFileFilter(new FileNameExtensionFilter("XML file", "xml"));
                    int returnVal = fileChooser.showSaveDialog(new JDialog((Window) null));

                    if (returnVal == JFileChooser.APPROVE_OPTION) {
                        TransformerFactory transformerFactory = TransformerFactory.newInstance();
                        Transformer transformer = transformerFactory.newTransformer();
                        DOMSource domSource = new DOMSource(document);
                        StreamResult streamResult = new StreamResult(fileChooser.getSelectedFile());

                        transformer.transform(domSource, streamResult);

                        Notification.display("Successfully exported to " + fileChooser.getName(fileChooser.getSelectedFile()));
                    }
                }
            } catch (Exception ex) {
                Notification.display("Error exporting: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
    }

    private static String getTagValue(String tag, Document document) {
        try {
            NodeList nodeList = document.getElementsByTagName(tag).item(0).getChildNodes();
            Node node = nodeList.item(0);
            return node.getNodeValue();
        } catch (Exception e) {
            return null;
        }
    }

    private static String getAttributeValue(String tag, String attribute, Document document) {
        try {
            return document.getElementsByTagName(tag).item(0).getAttributes().getNamedItem(attribute).getNodeValue();
        } catch (Exception e) {
            return null;
        }
    }

    private String getByteArrayFromImageURL(String url) {

        try {
            URL imageUrl = new URL(url);
            URLConnection ucon = imageUrl.openConnection();
            InputStream is = ucon.getInputStream();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int read = 0;
            while ((read = is.read(buffer, 0, buffer.length)) != -1) {
                baos.write(buffer, 0, read);
            }
            baos.flush();
            return Base64.getEncoder().encodeToString(baos.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
