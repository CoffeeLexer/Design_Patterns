package client.controls;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.awt.geom.*;
import java.util.HashMap;

//  key codes:
//  w:      87
//  s:      83
//  d:      68
//  a:      65
//  space:  32
//  shift:  16

public class ControlInput implements KeyListener {

    private ArrayList<ControlListener> listeners;

    private static ControlInput _instance = new ControlInput();

    public float y = 0;
    public float x = 0;

    private HashSet<Character> pressedKeys = new HashSet<>();

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

    private void invokeAbility(Object ability) {
        // for later
        System.out.println("ability");
    }

    private void invokeFire(char keyName) {
        for (ControlListener controlListener : listeners) {
            controlListener.onFire(keyName);
        }
    }

    private void invokeClone() {
        for (ControlListener controlListener : listeners) {
            controlListener.onClone();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        // System.out.println(e.getKeyChar());
        float px = x, py = y;
        switch (e.getKeyChar()) {
            case '￿': // shift
                if (!pressedKeys.contains('￿')) {
                    invokeAbility(null);
                }
                break;
            case 'n':
            case 'm':
                invokeFire(e.getKeyChar());
                break;
            case 'w':
                y = -1;
                break;
            case 'a':
                x = -1;
                break;
            case 's':
                y = 1;
                break;
            case 'd':
                x = 1;
                break;
            case 'c':
                invokeClone();
                break;
        }

        pressedKeys.add(e.getKeyChar());

        if (px != x || py != y) {
            invokeMovement();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyChar()) {
            case 'w':
                y = pressedKeys.contains('s') ? 1 : 0;
                break;
            case 's':
                y = pressedKeys.contains('w') ? -1 : 0;
                break;
            case 'a':
                x = pressedKeys.contains('d') ? 1 : 0;
                break;
            case 'd':
                x = pressedKeys.contains('a') ? -1 : 0;
                break;
        }

        pressedKeys.remove(e.getKeyChar());

        invokeMovement();
    }
}
