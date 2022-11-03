package client.components.weaponFacade;
import java.util.concurrent.TimeUnit;
import client.components.GameComponent;
import client.components.Transform;
import client.gameObjects.Projectile;
import network.server.SEngine;
import java.awt.event.KeyEvent;

public class WeaponFacade extends GameComponent {
    int parentSize;

    public WeaponFacade(int size) {
        this.parentSize = size;
    }

    @Override
    public String key() {
        return WeaponFacade.Key();
    }

    public static String Key() {
        return "WeaponFacade";
    }

    public void shoot(int keyCode) {
        Transform transform = gameObject.getComponent(Transform.Key());

        // get the center (with an offset) coordinates of the parent object
        float xCoords = transform.position.x + this.parentSize / 2;
        float yCoords = transform.position.y + this.parentSize / 4;

        Projectile projectile = new Projectile(xCoords, yCoords, transform.rotation, "images/tank-projectile.png");

        switch (keyCode) {
            case KeyEvent.VK_N -> {
                StraightFlyAlgorithm algorithm = new StraightFlyAlgorithm(30, TimeUnit.MILLISECONDS, 300);
                projectile.setAlgorithm(algorithm);
            }
            case KeyEvent.VK_M -> {
                FragmentingAlgorithm algorithm = new FragmentingAlgorithm(30, TimeUnit.MILLISECONDS, 300);
                projectile.setAlgorithm(algorithm);
            }
            case KeyEvent.VK_J -> {
                ShotgunAlgorithm algorithm = new ShotgunAlgorithm(20, TimeUnit.MILLISECONDS, 300);
                projectile.setAlgorithm(algorithm);
            }
            case KeyEvent.VK_K -> {
                HoverAlgorithm algorithm = new HoverAlgorithm(15, TimeUnit.MILLISECONDS, 300);
                projectile.setAlgorithm(algorithm);
            }
        }

        SEngine.GetInstance().Add(projectile);
    }
    @Override
    public WeaponFacade cloneShallow() {
        return new WeaponFacade(this.parentSize);
    }
    @Override
    public WeaponFacade cloneDeep() {
        return new WeaponFacade(Integer.valueOf(this.parentSize));
    }
}