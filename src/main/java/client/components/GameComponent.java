package client.components;

import client.Primitive;
import client.gameObjects.GameObject;

import java.awt.*;
import java.io.Serializable;

public abstract class GameComponent extends Primitive implements Serializable, Prototype {
    public abstract String key();
    public void update(float delta) {}
    public void render(Graphics2D g2d) {}
    public void destroy() {}
    public <T extends Primitive> void addComponent(T component) {
        throw new RuntimeException("Leaf can not have this method!");
    }
    public <T extends Primitive> void removeComponent(String key) {
        throw new RuntimeException("Leaf can not have this method!");
    }
    public <T extends Primitive> T getComponent(String key) {
        throw new RuntimeException("Leaf can not have this method!");
    }
}
