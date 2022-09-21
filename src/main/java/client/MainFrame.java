package com.swingapp;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

public class MainFrame extends JFrame {

    public MainPanel panel;

    public MainFrame(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel = new MainPanel();
        addKeyListener(ControlInput.getInstance());
        add(panel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

}
