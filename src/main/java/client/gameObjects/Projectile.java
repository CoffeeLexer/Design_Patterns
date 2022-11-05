package client.gameObjects;
import client.components.Collider;
import client.components.Colliders;
import client.components.Renderer;
import client.components.Transform;
import client.components.weaponFacade.ProjectileAlgorithm;

public class Projectile extends GameObject {
    public Projectile(float x, float y, float angle, String imagePath) {
        this(x, y, angle, imagePath, 25);
    }

    public Projectile(float x, float y, float angle, String imagePath, int projectileSize) {
        addComponent(new Renderer(imagePath, projectileSize, true));
        addComponent(new Transform().setPosition(x, y).setRotation(angle));
        addComponent(Collider.fromTexture(getComponent(Renderer.Key())));
        ((Collider)getComponent(Collider.Key())).setFunction(Colliders.projectile);
        tag = Tag.Dynamic;
    }

    public Projectile setAlgorithm(ProjectileAlgorithm algorithm) {
        algorithm.setProjectile(this);
        addComponent(algorithm);
        return this;
    }
}