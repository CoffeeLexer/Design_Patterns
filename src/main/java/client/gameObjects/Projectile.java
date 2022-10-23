package client.gameObjects;
import client.components.Renderer;
import client.components.Transform;
import client.components.weapon.ProjectileAlgorithm;

public class Projectile extends GameObject {
    public Projectile(float x, float y, float angle, String imagePath) {
        this(x, y, angle, imagePath, 25);
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