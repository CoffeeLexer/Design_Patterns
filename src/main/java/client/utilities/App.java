package client.utilities;

import client.panels.GameFrame;

public class App {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(GameFrame::new);
    }
}
