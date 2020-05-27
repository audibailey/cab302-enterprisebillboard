package viewer;

import common.models.Billboard;
import common.router.IActionResult;
import common.router.Status;
import common.utils.ClientSocketFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.Instant;

/**
 * This class consists of the Billboard Viewer handler.
 * All methods that manage and create the GUI are present in this file.
 *
 * @author Trevor Waturuocha
 * @author Jamie Martin
 */
public class Main {
    /**
     * Create the Billboard Viewer GUI and show it.
     */
    public static void createAndShowGUI(Billboard b) throws Exception {
        if (b != null) {
            new Frame(new Panel(b), false);
        } else {
            new Thread(new Runnable() {
                Frame frame = new Frame(new Panel(Main.getCurrent()), false);

                @Override
                public void run() {
                    while(true) {
                        try {
                            Thread.sleep(15000);
                            Frame temp = new Frame(new Panel(Main.getCurrent()), true);
                            Thread.sleep(500);
                            frame.dispose();
                            frame = temp;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        }
    }

    public static Billboard getCurrent() {
        Billboard billboard = new Billboard();

        IActionResult res = new ClientSocketFactory("/schedule/get/current", null, null).Connect();

        if (res != null && res.status == Status.SUCCESS && res.body instanceof Billboard) {
            billboard = (Billboard)res.body;
            billboard.information = billboard.information + " " + new SimpleDateFormat("K:mm:ss a z").format(Date.from(Instant.now()));
        } else {
            billboard.message = "No billboard scheduled";
            billboard.information = new SimpleDateFormat("K:mm:ss a z").format(Date.from(Instant.now()));
        }

        return billboard;
    }

    /**
     * Main class to run GUI Application and socket interface
     */
    public static void main(String[] args) throws Exception {
        // Schedule a job for the event-dispatching thread:
        // creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    createAndShowGUI(null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
