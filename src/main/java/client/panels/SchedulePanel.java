package client.panels;

import client.components.table.*;
import client.services.BillboardService;
import client.services.PermissionsService;
import client.services.ScheduleService;
import client.services.SessionService;
import common.models.*;
import common.swing.Notification;
import common.utils.RandomFactory;
import common.utils.Time;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class renders the Java Swing schedule panel for the client.
 *
 * @author Jamie Martin
 */
public class SchedulePanel extends JPanel implements ActionListener {

    ObjectTableModel<Schedule> tableModel;
    JTable table;
    Container buttonContainer = new Container();
    JButton createButton, refreshButton, deleteButton;
    String selected;


    public SchedulePanel() {
        // Get session
        Session session = SessionService.getInstance();
        // Adding button labels
        createButton = new JButton("Create Schedule");
        refreshButton = new JButton("Refresh");
        deleteButton = new JButton("Delete Selected");
        // Disable schedule button if user is not permitted
        if (!session.permissions.canScheduleBillboard) {
            createButton.setEnabled(false);
        }

        createButton.addActionListener(this::actionPerformed);
        refreshButton.addActionListener(this::actionPerformed);
        deleteButton.addActionListener(this::actionPerformed);
        deleteButton.setEnabled(false);

        // Getting table data and configuring table
        tableModel = new DisplayableObjectTableModel(Schedule.class, null);
        tableModel.setObjectRows(ScheduleService.getInstance().refresh());
        table = new JTable(tableModel);
        setupSelection();
        setupRenderersAndEditors();
        JScrollPane pane = new JScrollPane(table);

        // Add buttons to container
        buttonContainer.setLayout(new FlowLayout());
        buttonContainer.add(createButton);
        buttonContainer.add(deleteButton);
        buttonContainer.add(refreshButton);

        // Add components to frame
        setLayout(new BorderLayout());
        add(buttonContainer, BorderLayout.NORTH);
        add(pane, BorderLayout.CENTER);
        setVisible(true);

        // TODO: Hookup to Schedule view on button press (call this within the spawned dialog/frame to get data)
        var res = ScheduleService.getInstance().getSchedule();
        System.out.println("Hello Trevor");
    }

    public void setupSelection() {
        table.setAutoCreateRowSorter(true);
        table.setCellSelectionEnabled(true);
        ListSelectionModel cellSelectionModel = table.getSelectionModel();
        cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        cellSelectionModel.addListSelectionListener(e -> {
            selected = (String)table.getModel().getValueAt(table.getSelectedRow(), 1);
            Session session = SessionService.getInstance();

            if (selected != null && session.permissions.canScheduleBillboard) {
                deleteButton.setEnabled(true);
            } else {
                deleteButton.setEnabled(false);
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
        if(e.getSource() == createButton) {
            try {
                // Setting up billboard dropdown menu
                List<Billboard> billboardList = BillboardService.getInstance().billboards;
                List<String> billboardNames = billboardList.stream().map(b -> b.name).collect(Collectors.toList());

                JComboBox billboards = new JComboBox(new DefaultComboBoxModel(billboardNames.toArray()));

                JComboBox days = new JComboBox(new DefaultComboBoxModel(getNames(DayOfWeek.class)));

                // Setting up start time spinner
                SpinnerDateModel startModel = new SpinnerDateModel();
                JSpinner startTime = new JSpinner(startModel);
                startTime.setEditor(new JSpinner.DateEditor(startTime,"H:mm"));
                // Setting up duration spinner
                SpinnerNumberModel durModel = new SpinnerNumberModel(1, 1, 1440, 1);
                JSpinner duration = new JSpinner(durModel);
                // Setting up interval spinner
                SpinnerNumberModel intModel = new SpinnerNumberModel(1, 0, 60, 1);
                JSpinner interval = new JSpinner(intModel);
                // Setting up components for Schedule dialog box
                final JComponent[] components = new JComponent[]{
                    new JLabel("Select a billboard:"),
                    billboards,
                    new JLabel("Select day to show"),
                    days,
                    new JLabel("Billboard start time:"),
                    startTime,
                    new JLabel("Billboard duration:"),
                    duration,
                    new JLabel("Billboard interval:"),
                    interval
                };
                int result = JOptionPane.showConfirmDialog(this, components, "Schedule a billboard", JOptionPane.PLAIN_MESSAGE);
                // If OK button is clicked, update schedule table
                if (result == JOptionPane.OK_OPTION) {
                    // If billboard is not selected, display warning message
                    if (billboards.getSelectedItem() == null) {
                        Notification.display("Billboard was not selected. Please try again");
                        // If billboard is not selected, display warning message
                    } else if (startTime.getValue() == null || duration.getValue() == null || interval.getValue() == null ) {
                        Notification.display("One of the schedule values are empty. Please try again");
                        // Else populate table
                    } else {
                        Schedule schedule = new Schedule();
                        schedule.billboardName = ((String)billboards.getSelectedItem());
                        schedule.dayOfWeek = days.getSelectedIndex();
                        schedule.start = Time.timeToMinute((Date) startTime.getValue());
                        schedule.duration = (Integer) duration.getValue();
                        schedule.interval = (Integer) interval.getValue();

                        tableModel.setObjectRows(ScheduleService.getInstance().insert(schedule));
                        tableModel.fireTableDataChanged();
                    }
                }
            }
            catch (Exception ex) {
                Notification.display(ex.getMessage());
            }
        }
        
        if (e.getSource() == deleteButton) {
                var scheduleList = tableModel.getObjectRows();
                Schedule s = scheduleList.stream().filter(x -> x.billboardName.equals(selected)).findFirst().get();
                tableModel.setObjectRows(ScheduleService.getInstance().delete(s));
                tableModel.fireTableDataChanged();
        }

        if (e.getSource() == refreshButton) {
            tableModel.setObjectRows(ScheduleService.getInstance().refresh());
            tableModel.fireTableDataChanged();
        }
    }

    public static String[] getNames(Class<? extends Enum<?>> e) {
        return Arrays.stream(e.getEnumConstants()).map(Enum::name).toArray(String[]::new);
    }
}
