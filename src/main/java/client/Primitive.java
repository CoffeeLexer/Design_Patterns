package client;

import client.components.GameComponent;

import java.awt.*;

public abstract class Primitive {
    public abstract  <T extends GameComponent> void addComponent(T component);
    public abstract  <T extends GameComponent> void removeComponent(String key);
    public abstract  <T extends GameComponent> T getComponent(String key);
    public abstract void update(float delta);
    public abstract void render(Graphics2D g2d);
    public abstract void destroy();
}
