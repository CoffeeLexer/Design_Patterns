package client.gameObjects;

import client.components.Collider;
import client.components.Renderer;
import client.components.Transform;

import java.io.Serializable;

public class Wall extends GameObject implements Serializable {

    public Wall(String imagePath, float x, float y) {
        this(imagePath, x, y, 100);
    }

    public Wall(String imagePath) {
        this(imagePath, 0, 0, 100);
    }
    
    public Wall(String imagePath, float x, float y, int size) {
        addComponent(new Renderer(imagePath, size, size));
        addComponent(new Transform().setPosition(x, y));
        addComponent(Collider.fromTexture(getComponent(Renderer.Key())));
        tag = Tag.Static;
    }
}