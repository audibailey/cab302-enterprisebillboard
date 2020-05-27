package client.panels;

import client.services.SessionService;
import common.models.Session;

import javax.swing.*;

/**
 * This class handles the different types of panels available for the client.
 *
 * @author Jamie Martin
 */
public class PanelHandler extends JTabbedPane {

    public PanelHandler() {
        Session session = SessionService.getInstance();

        add("Billboards", new BillboardPanel());

        if (session.permissions.canScheduleBillboard) {
            add("Schedule", new SchedulePanel());
        }

        if (session.permissions.canEditUser) {
            add("Users", new UserPanel());
        }
    }
}
