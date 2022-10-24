package client.gameObjects;

import client.components.Collider;
import client.components.Renderer;
import client.components.Transform;

import java.io.Serializable;

public class Wall extends GameObject implements Serializable {
    public Wall(String imagePath, float x, float y) {
        addComponent(new Transform().setPosition(x, y));
        addComponent(new Renderer(imagePath, 50, 50));
        addComponent(Collider.fromTexture(getComponent(Renderer.Key()), getComponent(Transform.Key())));
        tag = Tag.Static;
    }
    public Wall(String imagePath) {
        addComponent(new Transform().setPosition(0, 0));
        addComponent(new Renderer(imagePath, 50, 50));
        addComponent(Collider.fromTexture(getComponent(Renderer.Key()), getComponent(Transform.Key())));
        tag = Tag.Static;
    }
}
