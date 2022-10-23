package client.components.weapon;

import java.util.concurrent.TimeUnit;

import client.components.GameComponent;
import client.components.Transform;
import client.gameObjects.Projectile;
import network.server.SEngine;

public class Weapon extends GameComponent {
    int parentSize;

    public Weapon(int size) {
        this.parentSize = size;
    }

    @Override
    public String key() {
        return Weapon.Key();
    }

    public static String Key() {
        return "Weapon";
    }

    public void shoot() {
        Transform transform = gameObject.getComponent(Transform.Key());

        // get the center coordinates of the parent object
        float xCoords = transform.position.x + this.parentSize / 2;
        float yCoords = transform.position.y + this.parentSize / 2;

        SEngine.GetInstance()
                .Add(new Projectile(xCoords, yCoords, transform.rotation,
                        "images/tank-projectile.png")
                        .setAlgorithm(new ShotgunAlgorithm(20, TimeUnit.MILLISECONDS, 500)));
    }
}