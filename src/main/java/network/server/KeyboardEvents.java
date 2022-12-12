package network.server;

import client.components.ConstantRotation;
import client.components.ConstantSpeed;
import client.components.Lifetime;
import client.components.weaponFacade.WeaponFacade;
import client.gameObjects.GameObject;
import client.gameObjects.Tank;
import network.client.PlayerClient;

import java.awt.event.KeyEvent;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class KeyboardEvents {
    public static class Constants {
        public static final float drivingSpeed = 3f;
        public static final float rotationSpeed = 2.25f;
    }

    public static void Rotate(Tank tank, Set<Integer> keysPressed) {
        if(tank == null) return;
        ConstantRotation cr = tank.getComponent(ConstantRotation.Key());
        float speed = 0;
        if(keysPressed.contains(KeyEvent.VK_A)) speed -= Constants.rotationSpeed;
        if(keysPressed.contains(KeyEvent.VK_LEFT)) speed -= Constants.rotationSpeed;
        if(keysPressed.contains(KeyEvent.VK_D)) speed += Constants.rotationSpeed;
        if(keysPressed.contains(KeyEvent.VK_RIGHT)) speed += Constants.rotationSpeed;
        cr.speed = Math.min(Math.max(speed, -Constants.rotationSpeed), Constants.rotationSpeed);
    }

    public static void Drive(Tank tank, Set<Integer> keysPressed) {
        if(tank == null) return;
        ConstantSpeed cs = tank.getComponent(ConstantSpeed.Key());
        float speed = 0;
        if(keysPressed.contains(KeyEvent.VK_W)) speed += Constants.drivingSpeed;
        if(keysPressed.contains(KeyEvent.VK_UP)) speed += Constants.drivingSpeed;
        if(keysPressed.contains(KeyEvent.VK_S)) speed -= Constants.drivingSpeed;
        if(keysPressed.contains(KeyEvent.VK_DOWN)) speed -= Constants.drivingSpeed;
        cs.speed = Math.min(Math.max(speed, -Constants.drivingSpeed), Constants.drivingSpeed);
    }

    public static void Shoot(PlayerClient player, Tank tank, int keyCode) {
        WeaponFacade weaponFacade = tank.getComponent(WeaponFacade.Key());
        weaponFacade.shoot(player, keyCode);
    }

    public static void InvokeShield(Tank tank) {
        tank.toggleShield();
    }

    public static void TimeTravel(Tank tank) {
        tank.timeTravel();
    }

    public static void Clone(Tank tank) {
        // do not allow to clone if tank is time travelling
        if (!tank.isTimeTravelling()) {
            GameObject clone = tank.cloneShallow();
            ConstantSpeed cs = clone.getComponent(ConstantSpeed.Key());
            cs.speed = Constants.drivingSpeed;
            ConstantRotation cr = clone.getComponent(ConstantRotation.Key());
            cr.speed = 0;
            clone.addComponent(new Lifetime(TimeUnit.SECONDS, 1, true));
            SEngine.GetInstance().Add(clone);
        }
    }
}
