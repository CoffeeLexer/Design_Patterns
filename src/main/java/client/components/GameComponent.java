package client.components;

import client.gameObjects.GameObject;

import java.awt.*;
import java.io.Serializable;

public abstract class GameComponent implements Serializable, Prototype {
    public abstract String key();
    public GameObject gameObject;
    public void update(float delta) {}
    public void render(Graphics2D g2d) {}
    public void destroy() {}
}
