package client.panels;

import javax.imageio.ImageIO;
import javax.swing.*;

import client.gameObjects.*;
import client.gameObjects.tankDecorators.HealthDecorator;
import client.gameObjects.tankDecorators.ITankDecorator;
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
        setPreferredSize(new Dimension(1280, 1024));
        setBounds(0, 0, 1280, 1024); 
        setOpaque(false);
        setSize(1280, 1024);

        gameObjects = new ArrayList<GameObject>();
        // ShieldDecorator tanka = new ShieldDecorator(new HealthDecorator(new LabelDecorator(new Tank("images/tank-yellow.png")));
        TankDecorator tank = new ShieldDecorator(new Tank("images/tank-yellow.png"));
        // Tank tank = new ShieldDecorator(new Tank("images/tank-yellow.png").listensToInput());
        //tank.decorate(tank.getCurrentHP(), tank.getMaxHP());
        //tank.decorate("Test tank name");

        // JLabel label = new JLabel("HelloWorld");  
        // // label.setHorizontalAlignment(SwingConstants.CENTER); // set the horizontal alignement on the x axis !
        // label.setVerticalAlignment(SwingConstants.CENTER); // set the verticalalignement on the y axis !
        // // label.setLocation(800, 900);
        // label.setBounds(800, 300, 100, 100);
        // label.setForeground(Color.RED);
        // add(label);

        // gameObjects.add(tank);
        gameObjects.add(tank);
        timer.start();
    }

    private void updateObjects(Graphics2D g2d) {
        gameObjects.forEach((gameObject) -> {
            if (gameObject.enabled) {
                gameObject.update();
            }
            gameObject.renderOn(g2d);
        });
        g2d.drawString("Hello World", 10, 10);
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