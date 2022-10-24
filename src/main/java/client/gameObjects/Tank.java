package client.gameObjects;

import client.components.*;
import client.components.weapon.Weapon;

public class Tank extends GameObject {
    int tankSize = 50;
    
    public Tank(String imagePath) {
        this(100, 100, 15, imagePath);
    }
    public Tank(float x, float y, float angle, String imagePath) {
        addComponent(new Transform().setPosition(x, y).setRotation(angle));
        addComponent(new Renderer(imagePath, this.tankSize, true));
        addComponent(new ConstantSpeed(0.0f));
        addComponent(new ConstantRotation(0.0f));
        addComponent(new Weapon(this.tankSize));
        tag = Tag.Dynamic;
    }
}
