package client.panels;

import java.awt.*;
import javax.swing.*;
import java.awt.geom.*;

import client.components.Renderer;
import client.components.Transform;
import client.gameObjects.GameObject;

public class StaticPanel extends JPanel {
    private GameObject[][] grid;

    private int size;

    public StaticPanel(int size, int width, int height) {
        setPreferredSize(new Dimension(1920, 1080));
        setBounds(0, 0, 1920, 1080);
        setOpaque(false);
        this.size = size;
        grid = new GameObject[height][width];
    }

    public void addGameObject(GameObject gameObject, int x, int y) {
        gameObject.addComponent(new Transform());
        Transform transform = gameObject.getComponent(Transform.Key());
        transform.position = new Point2D.Float(x * size, y * size);
        grid[y][x] = gameObject;
    }

    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[y].length; x++) {
                GameObject go = grid[y][x];
                if (go != null) {
                    client.components.Renderer renderer = go.getComponent(Renderer.Key());
                    renderer.renderOn(g2d);
                }
            }
        }
    }

}
