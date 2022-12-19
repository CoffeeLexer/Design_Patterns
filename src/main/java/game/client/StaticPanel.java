package game.client;

import game.data.GameObject;

import java.awt.*;
import javax.swing.*;


public class StaticPanel extends JPanel {
    public Engine engine;

    public StaticPanel() {
        setBounds(0, 0, 1920, 1080);
        setOpaque(false);
    }
    public void bind(Engine engine) {
        this.engine = engine;
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        engine.render(g2d, GameObject.Tag.Static);
    }
}
