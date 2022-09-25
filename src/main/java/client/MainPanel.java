package client;

import javax.imageio.ImageIO;
import javax.swing.*;

import client.gameObjects.*;

import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.event.*;
import java.awt.geom.*;

public class MainPanel extends JPanel implements ActionListener {

    private static ArrayList<GameObject> gameObjects;

    Timer timer = new Timer(1000 / 60, this);

    public MainPanel() {
        setPreferredSize(new Dimension(1920, 1080));
        setOpaque(true);

        gameObjects = new ArrayList<GameObject>();
        addObject(new Wall("images/wall-blue.jpg"));
        addObject(new Tank("images/tank-blue.png").listensToInput());

        timer.start();
    }

    private void updateObjects(Graphics2D g2d) {
        for (GameObject gameObject : gameObjects) {
            if (gameObject.enabled) {
                gameObject.update();
            }
            BufferedImage resized = gameObject.getImage();
            Point2D.Float goPosition = gameObject.getPosition();
            g2d.drawImage(resized, null, (int) Math.round(goPosition.getX()), (int) Math.round(goPosition.getY()));
        }
    }

    public static void addObject(GameObject obj) {
        gameObjects.add(obj);
    }

    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setBackground(Color.WHITE);
        updateObjects(g2d);
        getToolkit().sync();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }
}