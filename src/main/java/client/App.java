package client;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.nio.ReadOnlyBufferException;

public class App {
    private static void createAndShowGUI() {
        MainFrame frame = new MainFrame();
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
