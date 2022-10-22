package client.panels;

import network.client.CEngine;

import javax.swing.*;

import java.awt.*;

public class DynamicPanel extends JPanel {
    public DynamicPanel() {
        setPreferredSize(new Dimension(1920, 1080));
        setBounds(0, 0, 1920, 1080); 
        setOpaque(false);
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        CEngine.getInstance().RenderDynamic(g2d);
    }
}