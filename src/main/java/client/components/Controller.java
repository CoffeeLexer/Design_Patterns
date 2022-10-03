package client.components;

import client.gameObjects.GameComponent;

import java.awt.geom.Point2D;

public class Controller extends GameComponent {
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
}
