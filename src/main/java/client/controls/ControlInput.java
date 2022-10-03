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

    private HashSet<Integer> pressedKeys = new HashSet<>();

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

        // System.out.println(e.getKeyChar());
        float px = x, py = y;
        switch (transformKey(e)) {
            case 16: // shift
                if (!pressedKeys.contains(16)) {
                    invokeAbility(null);
                }
                break;
            case 32:
                if (!pressedKeys.contains(32)) {
                    invokeFire();
                }
                break;
            case (int)'W':
                y = -1;
                break;
            case (int)'A':
                x = -1;
                break;
            case (int)'S':
                y = 1;
                break;
            case (int)'D':
                x = 1;
                break;
        }

        pressedKeys.add(transformKey(e));

        if (px != x || py != y) {
            invokeMovement();
        }
    }
    private int transformKey(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if(keyCode >= (int)'a' && keyCode <= (int)'z') {
            keyCode += ((int)'A' - (int)'a');
        }
        return keyCode;
    }
    @Override
    public void keyReleased(KeyEvent e) {
        switch (transformKey(e)) {
            case (int)'W':
                y = pressedKeys.contains((int)'S') ? 1 : 0;
                break;
            case (int)'S':
                y = pressedKeys.contains((int)'W') ? -1 : 0;
                break;
            case (int)'A':
                x = pressedKeys.contains((int)'D') ? 1 : 0;
                break;
            case (int)'D':
                x = pressedKeys.contains((int)'A') ? -1 : 0;
                break;
        }

        pressedKeys.remove(transformKey(e));

        invokeMovement();
    }
}
