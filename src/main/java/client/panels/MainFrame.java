package client.panels;

import javax.swing.*;
import javax.swing.text.AbstractDocument.LeafElement;

import client.controls.ControlInput;
import client.gameObjects.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.ArrayList;

public class MainFrame extends JFrame {

    JLayeredPane layeredPane;



    public MainFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, 1280, 1024);
        MainPanel panel = new MainPanel();
        StaticPanel staticPanel = new StaticPanel(100, 16, 9);

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 16; j++) {
                GameObject wall = new Wall("images/wall-blue.jpg", 100);
                staticPanel.addGameObject(wall, j, i);
            }
        }

        layeredPane.add(staticPanel, 1);
        staticPanel.repaint();
        layeredPane.add(panel, 0);

        addKeyListener(ControlInput.getInstance());
        add(layeredPane);
        pack();
        setSize(1280, 1024);
        setVisible(true);
        setLocationRelativeTo(null);
    }



}
