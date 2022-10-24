package client.gameObjects;

import client.components.*;
import network.server.SEngine;

public class Tank extends GameObject {
    public Tank(String imagePath) {
        this(100, 100, 15, imagePath);
    }
    public Tank(float x, float y, float angle, String imagePath) {
        addComponent(new Transform().setPosition(x, y).setRotation(angle));
        addComponent(new Renderer(imagePath, 50, true));
        addComponent(new ConstantSpeed(0.0f));
        addComponent(new ConstantRotation(0.0f));
        addComponent(new Weapon());
        addComponent(Collider.fromTexture(getComponent(Renderer.Key()), getComponent(Transform.Key())));
        ((Collider)getComponent(Collider.Key())).setFunction(gameObject -> {
            SEngine.GetInstance().Destroy(gameObject);
        });
        tag = Tag.Dynamic;
    }
}
