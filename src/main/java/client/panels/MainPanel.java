package client.panels;

import javax.imageio.ImageIO;
import javax.swing.*;

import client.gameObjects.*;
import client.gameObjects.tankDecorators.HealthDecorator;
import client.gameObjects.tankDecorators.LabelDecorator;
import client.gameObjects.tankDecorators.ShieldDecorator;
import client.gameObjects.tankDecorators.TankDecorator;

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
        setBounds(0, 0, 1920, 1080); 
        setOpaque(false);

        gameObjects = new ArrayList<GameObject>();
        Tank decoratedTank = new LabelDecorator(new HealthDecorator(new ShieldDecorator(new Tank("images/tank-yellow.png")))).listensToInput();
        decoratedTank.decorate();

        gameObjects.add(decoratedTank);
        timer.start();
    }

    private void updateObjects(Graphics2D g2d) {
        gameObjects.forEach((gameObject) -> {
            if (gameObject.enabled) {
                gameObject.update();
            }
            gameObject.renderOn(g2d);
        });
    }

    public static void addObject(GameObject obj) {
        gameObjects.add(obj);
        imitateCollideAfterDelay(obj);
    }

    public static void deleteObject(GameObject obj) {
        gameObjects.remove(obj);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        updateObjects(g2d);
        getToolkit().sync();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    // FOR TESTING
    private static void imitateCollideAfterDelay(GameObject obj) {
        Timer collideTimer = new Timer(600, new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
                obj.beforeCollide();
                deleteObject(obj);
            }
        });
        collideTimer.setRepeats(false);
        collideTimer.start();
      }
}