package game.data;

import java.awt.event.MouseEvent;
import java.io.Serializable;

public class MouseEventData implements Serializable {

    public int x, y;

    public MouseEventData(MouseEvent e) {
        x = e.getX();
        y = e.getY();
        /*
         * Implement data you wish to send to server
         * Example of data sending is implemented with keyboard
         * This is more or less wrapper, as MouseEvent is not serializable
         */
    }
}
