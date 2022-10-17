package client.panels;

import javax.swing.*;

import client.controls.Controller;
import network.client.CEngine;
import network.client.Client;
import network.client.UDPReceiver;

public class GameFrame extends JFrame {
    JLayeredPane layeredPane;
    public GameFrame(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, 1920, 1080);
        setSize(1600, 900);

        layeredPane.add(CEngine.getInstance().staticPanel, 1);
        layeredPane.add(CEngine.getInstance().dynamicPanel, 0);

        Client.GetInstance().JoinGame();

        Controller controller = new Controller();
        addKeyListener(controller);

        add(layeredPane);
        setVisible(true);
        setLocationRelativeTo(null);

    }

}
