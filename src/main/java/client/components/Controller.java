package client.components;

import client.controls.ControllerListener;
import client.gameObjects.GameComponent;
import client.gameObjects.Projectile;
import client.panels.MainPanel;

import java.awt.geom.Point2D;

public class Controller extends GameComponent implements ControllerListener {
    private float movementSpeed = 5f;
    private float rotationSpeed = 3f;
    private static int tankSize = 60;
    public static int TankSize() {return tankSize;}
    public Point2D.Float movement = new Point2D.Float(0, 0);
    public void drive(float direction) {
        Transform transform = gameObject.getComponent(Transform.Key());
        float x = -direction * movementSpeed * (float) Math.sin(Math.toRadians(transform.rotation));
        float y = direction * movementSpeed * (float) Math.cos(Math.toRadians(transform.rotation));
        transform.position.setLocation(transform.position.getX() + x, transform.position.getY() + y);
    }

    public void rotate(float direction) {
        Transform transform = gameObject.getComponent(Transform.Key());
        transform.rotation += (direction * rotationSpeed);
        transform.rotation %= 360;
    }
    @Override
    public void sync() {

    }

    @Override
    public void update() {
        drive(movement.y);
        rotate(movement.x);
    }

    @Override
    public String key() {
        return "Controller";
    }
    public static String Key() {
        return "Controller";
    }

    @Override
    public void onMove(Point2D.Float input) {
        movement = input;
    }

    @Override
    public void onFire() {
        Transform transform = gameObject.getComponent(Transform.Key());
        Point2D.Float currentPosition = transform.getPosition();
        String projectileImage = "images/tank-projectile.png";

        // projectile spawns on the center of the tank
        float xCoords = currentPosition.x + (float)Controller.TankSize() / 2;
        float yCoords = currentPosition.y + (float)Controller.TankSize() / 2;

        MainPanel.addObject(new Projectile(xCoords, yCoords, transform.rotation, projectileImage));
    }
}
