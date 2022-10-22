package client.components.weapon;

import java.util.concurrent.TimeUnit;

import client.components.GameComponent;
import client.components.Transform;
import client.gameObjects.Projectile;
import network.server.SEngine;

public class Weapon extends GameComponent {
    @Override
    public String key() {
        return Weapon.Key();
    }

    public static String Key() {
        return "Weapon";
    }

    public void shoot() {
        Transform transform = gameObject.getComponent(Transform.Key());
        SEngine.GetInstance()
                .Add(new Projectile(transform.position.x, transform.position.y, transform.rotation,
                        "images/tank-projectile.png")
                        .setAlgorithm(new FragmentingAlgorithm(30, TimeUnit.MILLISECONDS, 500)));
    }
    @Override
    public Weapon clone() {
        return new Weapon();
    }
}