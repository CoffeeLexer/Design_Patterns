package network.server;

import client.components.ConstantRotation;
import client.components.ConstantSpeed;
import client.components.GameComponent;
import client.components.Lifetime;
import client.components.Transform;
import client.components.weapon.Weapon;
import client.gameObjects.GameObject;
import client.gameObjects.Tank;

import java.awt.event.KeyEvent;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class KeyboardEvents {
    public static class Constants {
        public static final float drivingSpeed = 3f;
        public static final float rotationSpeed = 2.25f;
    }

    public static void Rotate(Tank tank, Set<Integer> keysPressed) {
        ConstantRotation cr = tank.getComponent(ConstantRotation.Key());
        float speed = 0;
        if(keysPressed.contains(KeyEvent.VK_A)) speed -= Constants.rotationSpeed;
        if(keysPressed.contains(KeyEvent.VK_LEFT)) speed -= Constants.rotationSpeed;
        if(keysPressed.contains(KeyEvent.VK_D)) speed += Constants.rotationSpeed;
        if(keysPressed.contains(KeyEvent.VK_RIGHT)) speed += Constants.rotationSpeed;
        cr.speed = Math.min(Math.max(speed, -Constants.rotationSpeed), Constants.rotationSpeed);
    }

    public static void Drive(Tank tank, Set<Integer> keysPressed) {
        ConstantSpeed cs = tank.getComponent(ConstantSpeed.Key());
        float speed = 0;
        if(keysPressed.contains(KeyEvent.VK_W)) speed += Constants.drivingSpeed;
        if(keysPressed.contains(KeyEvent.VK_UP)) speed += Constants.drivingSpeed;
        if(keysPressed.contains(KeyEvent.VK_S)) speed -= Constants.drivingSpeed;
        if(keysPressed.contains(KeyEvent.VK_DOWN)) speed -= Constants.drivingSpeed;
        cs.speed = Math.min(Math.max(speed, -Constants.drivingSpeed), Constants.drivingSpeed);
    }

    public static void Shoot(Tank tank, int keyCode) {
        Weapon weapon = tank.getComponent(Weapon.Key());
        weapon.shoot(keyCode);
    }

    public static void InvokeShield(Tank tank) {
        tank.toggleShield();
    }
    public static void Clone(Tank tank) {
        GameObject clone = tank.cloneDeep();
        ConstantSpeed cs = clone.getComponent(ConstantSpeed.Key());
        cs.speed = Constants.drivingSpeed;
        ConstantRotation cr = clone.getComponent(ConstantRotation.Key());
        cr.speed = 0;
        clone.addComponent(new Lifetime(TimeUnit.SECONDS, 1));
        SEngine.GetInstance().Add(clone);
    }
}
