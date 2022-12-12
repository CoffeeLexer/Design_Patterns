package client.components.weaponFacade;
import java.util.concurrent.TimeUnit;
import client.components.GameComponent;
import client.components.Specs;
import client.components.Transform;
import client.gameObjects.GameObject;
import client.gameObjects.Projectile;
import network.client.PlayerClient;
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

    public void shoot(PlayerClient player, int keyCode) {
        Transform transform = parent.getComponent(Transform.Key());

        // get the center (with an offset) coordinates of the parent object
        float xCoords = transform.position.x + this.parentSize / 2;
        float yCoords = transform.position.y + this.parentSize / 4;

        Projectile projectile = new Projectile(xCoords, yCoords, transform.rotation, "images/tank-projectile.png");
        projectile.owner = (GameObject)parent;

        int ammo = 0;
        if(player == null) ammo = 999;
        else ammo = ((Specs)parent.getComponent(Specs.Key())).ammo;
        boolean enoughAmmo = false;

        switch (keyCode) {
            case KeyEvent.VK_N -> {
                if(ammo > 0) {
                    projectile.setAlgorithm(new StraightFlyAlgorithm(30, TimeUnit.MILLISECONDS, 300));
                    ((Specs)parent.getComponent(Specs.Key())).ammo = ammo - 1;
                    enoughAmmo = true;
                }
                else {
                    player.receiveMessage("<strong style=\"color: red;\">Straight projectile needs 1 ammo point</strong>");
                }
            }
            case KeyEvent.VK_M -> {
                if(ammo > 7) {
                    projectile.setAlgorithm(new FragmentingAlgorithm(30, TimeUnit.MILLISECONDS, 300));
                    ((Specs)parent.getComponent(Specs.Key())).ammo = ammo - 8;
                    enoughAmmo = true;
                }
                else {
                    player.receiveMessage("<strong style=\"color: red;\">Fragment projectile needs 8 ammo points</strong>");
                }
            }
            case KeyEvent.VK_J -> {
                if(ammo > 2) {
                    projectile.setAlgorithm(new ShotgunAlgorithm(20, TimeUnit.MILLISECONDS, 300));
                    ((Specs)parent.getComponent(Specs.Key())).ammo = ammo - 3;
                    enoughAmmo = true;
                }
                    else {
                    player.receiveMessage("<strong style=\"color: red;\">Fragment projectile needs 3 ammo points</strong>");
                }
            }
            case KeyEvent.VK_K -> {
                if(ammo > 1) {
                    projectile.setAlgorithm(new HoverAlgorithm(15, TimeUnit.MILLISECONDS, 300));
                    ((Specs)parent.getComponent(Specs.Key())).ammo = ammo - 2;
                    enoughAmmo = true;
                }
                else {
                    player.receiveMessage("<strong style=\"color: red;\">Fragment projectile needs 2 ammo points</strong>");
                }
            }
        }

        if(enoughAmmo)
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