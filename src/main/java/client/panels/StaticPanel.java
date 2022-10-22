package client.panels;

import java.awt.*;
import javax.swing.*;

import network.client.CEngine;

public class StaticPanel extends JPanel {
    public StaticPanel() {
        setPreferredSize(new Dimension(1920, 1080));
        setBounds(0, 0, 1920, 1080);
        setOpaque(false);
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        CEngine.getInstance().RenderStatic(g2d);
    }
}
