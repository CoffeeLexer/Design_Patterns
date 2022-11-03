package client.components.weapon;
import java.util.concurrent.TimeUnit;
import client.components.GameComponent;
import client.components.Transform;
import client.gameObjects.Projectile;
import network.server.SEngine;
import java.awt.event.KeyEvent;

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

    public void shoot(int keyCode) {
        Transform transform = gameObject.getComponent(Transform.Key());

        // get the center coordinates of the parent object
        float xCoords = transform.position.x + this.parentSize / 2;
        float yCoords = transform.position.y + this.parentSize / 4;

        Projectile projectile = new Projectile(xCoords, yCoords, transform.rotation, "images/tank-projectile.png");

        switch (keyCode) {
            case KeyEvent.VK_N -> {
                projectile.setAlgorithm(new StraightFlyAlgorithm(30, TimeUnit.MILLISECONDS, 300));
            }
            case KeyEvent.VK_M -> {
                projectile.setAlgorithm(new FragmentingAlgorithm(30, TimeUnit.MILLISECONDS, 300));
            }
            case KeyEvent.VK_J -> {
                projectile.setAlgorithm(new ShotgunAlgorithm(20, TimeUnit.MILLISECONDS, 300));
            }
            case KeyEvent.VK_K -> {
                projectile.setAlgorithm(new HoverAlgorithm(15, TimeUnit.MILLISECONDS, 300));
            }
        }

        SEngine.GetInstance().Add(projectile);
    }
    @Override
    public Weapon cloneShallow() {
        return new Weapon(this.parentSize);
    }
    @Override
    public Weapon cloneDeep() {
        return new Weapon(Integer.valueOf(this.parentSize));
    }
}