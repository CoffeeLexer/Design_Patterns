package client.gameObjects;

import client.components.ConstantSpeed;
import client.components.Lifetime;
import client.components.Renderer;
import client.components.Transform;
import client.components.weapon.ProjectileAlgorithm;

import java.util.concurrent.TimeUnit;

public class Projectile extends GameObject {
    public Projectile(float x, float y, float angle, String imagePath) {
        this(x, y, angle, imagePath, 30);
    }

    public Projectile(float x, float y, float angle, String imagePath, int projectileSize) {
        addComponent(new Renderer(imagePath, projectileSize, true));
        addComponent(new Transform().setPosition(x, y).setRotation(angle));
        tag = Tag.Dynamic;
    }

    public Projectile setAlgorithm(ProjectileAlgorithm algorithm) {
        algorithm.setProjectile(this);
        addComponent(algorithm);
        return this;
    }
}