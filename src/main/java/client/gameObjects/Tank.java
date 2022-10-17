package client.gameObjects;

import client.components.*;

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
        tag = Tag.Dynamic;
    }
}
