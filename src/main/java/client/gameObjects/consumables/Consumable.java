package client.gameObjects.consumables;

import client.components.Renderer;
import client.components.Transform;
import client.gameObjects.GameObject;
import client.gameObjects.Tag;
import network.server.SEngine;

public abstract class Consumable extends GameObject {

    Transform transform;

    public Consumable(String imagePath, int width) {
        transform = new Transform();
        addComponent(transform);
        addComponent(new Renderer(imagePath, width, true));
        tag = Tag.Dynamic;
    }

    public Consumable setPosition(float x, float y) {
        transform.setPosition(x, y);
        return this;
    }
}
