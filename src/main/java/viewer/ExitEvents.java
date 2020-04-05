/*
 * Created on 24/04/2006
 */
package viewer;

import javax.swing.*;
import java.awt.event.*;

// Class for event listening
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
