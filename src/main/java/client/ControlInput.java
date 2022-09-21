package com.swingapp;

import java.awt.event.*;

public class ControlInput implements KeyListener {

    public float y = 0;
    public float x = 0;

    private boolean wPresed = false;
    private boolean sPresed = false;
    private boolean aPresed = false;
    private boolean dPresed = false;

    private ControlInput() {
    }

    private static ControlInput _instance;

    public static ControlInput getInstance() {
        if(_instance == null){
            _instance = new ControlInput();
        }

        return _instance;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyChar()) {
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
    }
}
