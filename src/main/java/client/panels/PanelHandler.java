package client.panels;

import client.services.SessionService;
import common.models.Session;

import javax.swing.*;

public class PanelHandler extends JTabbedPane {

    public PanelHandler() {
        Session session = SessionService.getInstance();

        if (session.permissions.canViewBillboard) {
            add("Billboards", new BillboardPanel());
        }

        if (session.permissions.canScheduleBillboard) {
            add("Schedule", new SchedulePanel());
        }

        if (session.permissions.canEditUser) {
            add("Users", new UserPanel());
        }
    }
}
