package client.panels;

import client.services.SessionService;
import common.models.Session;

import javax.swing.*;

public class PanelHandler extends JTabbedPane {

    BillboardPanel billboardPanel = new BillboardPanel();
    SchedulePanel schedulePanel = new SchedulePanel();
    UserPanel userPanel = new UserPanel();

    public PanelHandler() {
        Session session = SessionService.getInstance();

        if (session == null) {
            add("Test Billboard", new TestBillboardPanel());
            add("Test User", new TestUserPanel());
            add("Schedule", schedulePanel);
        }
        else {
            if (session.permissions.canViewBillboard) {
                //add("Billboards", billboardPanel);
                add("Test Billboard", new TestBillboardPanel());
            }

            if (session.permissions.canScheduleBillboard) {
                add("Schedule", schedulePanel);
            }

            if (session.permissions.canEditUser) {
                //add("Users", userPanel);
                add("Test User", new TestUserPanel());
            }
        }
    }
}
