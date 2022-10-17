package client.components;

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
        SEngine.GetInstance().Add(new Projectile(transform.position.x, transform.position.y, transform.rotation, "images/tank-projectile.png"));
    }
}