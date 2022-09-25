package client.controls;

import java.awt.event.*;
import java.util.ArrayList;

import java.awt.geom.*;

public class ControlInput implements KeyListener {

    private ArrayList<ControlListener> listeners;

    private static ControlInput _instance = new ControlInput();

    public float y = 0;
    public float x = 0;

    private boolean wPresed = false;
    private boolean sPresed = false;
    private boolean aPresed = false;
    private boolean dPresed = false;

    public static ControlInput getInstance() {
        return _instance;
    }

    private ControlInput() {
        listeners = new ArrayList<>();
    }

    public static void addControlListener(ControlListener listener) {
        if (!_instance.listeners.contains(listener)) {
            _instance.listeners.add(listener);
        }
    }

    private void invokeMovement() {
        for (ControlListener controlListener : listeners) {
            controlListener.onMove(new Point2D.Float(x, y));
        }
    }

    private void invokeFire() {
        for (ControlListener controlListener : listeners) {
            controlListener.onFire();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        float px = x, py = y;
        switch (e.getKeyChar()) {
            case ' ':
                invokeFire();
                break;
            case 'w':
                wPresed = true;
                y = -1;
                break;
            case 'a':
                aPresed = true;
                x = -1;
                break;
            case 's':
                sPresed = true;
                y = 1;
                break;
            case 'd':
                dPresed = true;
                x = 1;
                break;
        }

        if (px != x || py != y) {
            invokeMovement();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyChar()) {
            case 'w':
                wPresed = false;
                y = sPresed ? 1 : 0;
                break;
            case 's':
                sPresed = false;
                y = wPresed ? -1 : 0;
                break;
            case 'a':
                aPresed = false;
                x = dPresed ? 1 : 0;
                break;
            case 'd':
                dPresed = false;
                x = aPresed ? -1 : 0;
                break;
        }

        invokeMovement();
    }
}
