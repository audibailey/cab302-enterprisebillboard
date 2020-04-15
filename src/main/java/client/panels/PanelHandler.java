package client.panels;

import javax.swing.*;

public class PanelHandler extends JTabbedPane {
    public PanelHandler() {
        add("Billboards", new BillboardPanel());
        add("Schedule", new SchedulePanel());
        add("Users", new UserPanel());
    }
}
