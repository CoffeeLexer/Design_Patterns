package client.controls;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashSet;

public class Controller implements KeyListener {
    private ArrayList<ControllerListener> listeners = null;
    private HashSet<Integer> pressedKeys = null;

    public void listen(ControllerListener listener) {
        if (!_instance.listeners.contains(listener)) {
            _instance.listeners.add(listener);
        }
    }
    @Override
    public void keyTyped(KeyEvent e) {
    }
    public void invokeOnMove(Point2D.Float input) {
        for (ControllerListener listener : listeners) {
            listener.onMove(input);
        }
    }
    private Point2D.Float parseMovement() {
        float x = 0, y = 0;
        if(pressedKeys.contains(KeyEvent.VK_A)) x--;
        if(pressedKeys.contains(KeyEvent.VK_D)) x++;
        if(pressedKeys.contains(KeyEvent.VK_W)) y--;
        if(pressedKeys.contains(KeyEvent.VK_S)) y++;
        return new Point2D.Float(x, y);
    }
    @Override
    public void keyPressed(KeyEvent e) {
        pressedKeys.add(e.getKeyCode());
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_D -> {
                invokeOnMove(parseMovement());
            }
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {
        pressedKeys.remove(e.getKeyCode());
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_D -> {
                invokeOnMove(parseMovement());
            }
        }
    }
    private Controller() {
        listeners = new ArrayList<>();
        pressedKeys = new HashSet<>();
    }
    private final static Controller _instance = new Controller();
    public static Controller getInstance() {
        return _instance;
    }
}
