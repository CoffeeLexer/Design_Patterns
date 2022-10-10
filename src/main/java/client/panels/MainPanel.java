package client.panels;

import javax.swing.*;

import client.components.Renderer;
import client.gameObjects.GameObject;
import client.gameObjects.Tank;
import network.Client;

import java.awt.*;
import java.awt.event.*;

public class MainPanel extends JPanel implements ActionListener {
    Timer timer = new Timer(1000 / 60, this);

    public MainPanel() {
        setPreferredSize(new Dimension(1920, 1080));
        setBounds(0, 0, 1920, 1080); 
        setOpaque(false);
        Tank gameObject = (Tank)Client.getInstance().createGameObject(new Tank("images/tank-yellow.png"));
        gameObject.listensToInput();
        Client.getInstance().setGameObject(gameObject);
        timer.start();
    }

    private void updateObjects(Graphics2D g2d) {
        Client.getInstance().foreach((gameObject -> {
            if(gameObject.tag.equals("Dynamic")) {
                gameObject.update();
                client.components.Renderer renderer = gameObject.getComponent(Renderer.Key());
                renderer.renderOn(g2d);
            }
        }));
    }

    public static void addObject(GameObject obj) {
        Client.getInstance().createGameObject(obj);
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
}