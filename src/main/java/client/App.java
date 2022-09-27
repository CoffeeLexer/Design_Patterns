package client;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.nio.ReadOnlyBufferException;

public class App {

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new GameEngine().startGame();
            }
        });
    }
}
