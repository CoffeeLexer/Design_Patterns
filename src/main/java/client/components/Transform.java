package client.components;

import client.gameObjects.GameComponent;
import client.gameObjects.GameObject;

import java.awt.geom.Point2D;

public class Transform extends GameComponent {
    public static String Key() {
        return "Transform";
    }
    public Point2D.Float position;
    public float rotation;
    public float getAngle() {
        return rotation;
    }
    public Point2D.Float getPosition() {
        if (position == null) {
            position = new Point2D.Float(0, 0);
        }
        return new Point2D.Float((float) position.getX(), (float) position.getY());
    }
    public Transform setPosition(float x, float y) {
        this.position = new Point2D.Float(x, y);
        return this;
    }
    public Transform setRotation(float rotation) {
        this.rotation = rotation;
        return this;
    }

    @Override
    public String key() {
        return "Transform";
    }
}
