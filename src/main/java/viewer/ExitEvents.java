/*
 * Created on 24/04/2006
 */
package viewer;

import javax.swing.*;
import java.awt.event.*;

/**
 * This class consists of the Exit Events for the Viewer.
 * It listens for mouse click and escape actions to close the application
 *
 * @author Trevor Waturuocha
 */
public class ExitEvents extends JFrame {
    public static class MouseListener extends MouseAdapter {
        public void mouseClicked(MouseEvent e) {
            System.exit(0);
        }
    }
    public static class KeyListener extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
            if(e.getKeyCode()==KeyEvent.VK_ESCAPE) {
                System.exit(0);
            }
        }
    }

}
