package game.client;

import game.data.MouseEventData;
import game.data.Payload;
import game.protocol.TCP;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

// Send a message to server on Mouse or Keyboard event
public class Controller {
    public static class Keyboard implements KeyListener {
        private final TCP.Client tcp;

        public Keyboard(TCP.Client client) {
            tcp = client;
        }

        @Override
        public void keyTyped(KeyEvent e) {
            tcp.send(new Payload(Payload.Method.keyTyped, e.getKeyCode()));
        }

        @Override
        public void keyPressed(KeyEvent e) {
            tcp.send(new Payload(Payload.Method.keyPressed, e.getKeyCode()));
        }

        @Override
        public void keyReleased(KeyEvent e) {
            tcp.send(new Payload(Payload.Method.keyReleased, e.getKeyCode()));
        }
    }

    public static class Mouse implements MouseListener {
        private final TCP.Client tcp;

        public Mouse(TCP.Client client) {
            tcp = client;
        }

        @Override
        public void mouseClicked(MouseEvent mouseEvent) {
            tcp.send(new Payload(Payload.Method.mouseClicked, new MouseEventData(mouseEvent)));
        }

        @Override
        public void mousePressed(MouseEvent mouseEvent) {
            tcp.send(new Payload(Payload.Method.mousePressed, new MouseEventData(mouseEvent)));
        }

        @Override
        public void mouseReleased(MouseEvent mouseEvent) {
            tcp.send(new Payload(Payload.Method.mouseReleased, new MouseEventData(mouseEvent)));
        }

        @Override
        public void mouseEntered(MouseEvent mouseEvent) {
            tcp.send(new Payload(Payload.Method.mouseEntered, new MouseEventData(mouseEvent)));
        }

        @Override
        public void mouseExited(MouseEvent mouseEvent) {
            tcp.send(new Payload(Payload.Method.mouseExited, new MouseEventData(mouseEvent)));
        }
    }
}
