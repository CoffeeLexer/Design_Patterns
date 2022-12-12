package client.panels;

import java.awt.*;
import java.awt.geom.AffineTransform;
import javax.swing.*;

import network.client.CEngine;

public class StaticPanel extends JPanel {

    private double translateX = 0, translateY = 0;
    private double centerX =  800, centerY = 450;

    public void setTranslate(double x, double y) {
        translateX = centerX - x;
        translateY = centerY - y;
    }
    public void setCenter(double x, double y) {
        centerX = x;
        centerY = y;
    }


    public StaticPanel() {
//        setPreferredSize(new Dimension(1920, 1080));
        setBounds(0, 0, 1920, 1080);
        translateX = 0;
        translateY = 0;
        setOpaque(false);
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        var transform = new AffineTransform();
        transform.translate(translateX, translateY);
        g2d.setTransform(transform);
        CEngine.getInstance().RenderStatic(g2d);
    }
}
