package client.gameObjects;

import client.components.*;
import network.server.SEngine;

import java.util.concurrent.TimeUnit;

public class Projectile extends GameObject {
    public Projectile(float x, float y, float angle, String imagePath) {
        addComponent(new Renderer(imagePath, 30, true));
        addComponent(new Transform().setPosition(x, y).setRotation(angle));
        addComponent(new ConstantSpeed(15.0f));
        addComponent(new Lifetime(TimeUnit.SECONDS, 1));
        addComponent(Collider.fromTexture(getComponent(Renderer.Key()), getComponent(Transform.Key())));
        ((Collider)getComponent(Collider.Key())).setFunction(gameObject -> {

        });
        tag = Tag.Dynamic;
    }
}
