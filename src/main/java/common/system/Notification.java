package common.system;

import java.awt.*;
import java.io.IOException;

/**
 * This class consists of the notification engine for Java windows/tray to handle errors.
 *
 * @author Jamie Martin
 */
public class Notification {

    public static void display(String caption, String text, TrayIcon.MessageType type) throws AWTException {
        if (SystemTray.isSupported()) {
            //Obtain only one instance of the SystemTray object
            SystemTray tray = SystemTray.getSystemTray();

            //If the icon is a file
            Image image = Toolkit.getDefaultToolkit().createImage("icon.png");

            TrayIcon trayIcon = new TrayIcon(image, "Java AWT Tray Demo");
            //Let the system resize the image if needed
            trayIcon.setImageAutoSize(true);
            //Set tooltip text for the tray icon
            trayIcon.setToolTip("System tray icon demo");

            tray.add(trayIcon);

            trayIcon.displayMessage(caption, text, type);

        }
    }
}

