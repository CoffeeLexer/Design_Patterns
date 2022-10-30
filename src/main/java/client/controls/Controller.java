package client.controls;

import network.client.Client;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Controller implements KeyListener {
    @Override
    public void keyTyped(KeyEvent e) {
        Client.GetInstance().KeyTyped(e.getKeyCode());
    }
    @Override
    public void keyPressed(KeyEvent e) {
        Client.GetInstance().KeyPressed(e.getKeyCode());
    }
    @Override
    public void keyReleased(KeyEvent e) {
        Client.GetInstance().KeyReleased(e.getKeyCode());
    }
}
