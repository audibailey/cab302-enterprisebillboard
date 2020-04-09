package client.panels;

import client.MenuBar;

import javax.swing.*;

public class MainPanel extends JTabbedPane {
    private MenuBar menuBar;

    public MainPanel(MenuBar menuBar) {
        this.menuBar = menuBar;
        add("Billboards", new BillboardsPanel());
        add("Schedule", new SchedulePanel());
        add("Users", new UsersPanel());
    }

    public MenuBar getMenuBar() {
        return menuBar;
    }
}
