package game.client;

import game.data.GameObject;

import javax.swing.*;

import java.awt.*;

public class DynamicPanel extends JPanel {
    private Engine engine;
    public DynamicPanel() {
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
        engine.render(g2d, GameObject.Tag.Dynamic);
        revalidate();
    }
}