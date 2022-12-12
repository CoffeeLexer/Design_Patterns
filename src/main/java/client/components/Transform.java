package client.components;

import client.gameObjects.GameObject;

import java.awt.geom.Point2D;

public class Transform extends GameComponent {

    public Transform(){
        position = new Point2D.Float(0, 0);
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
        updateStateChange(true);
        this.position = new Point2D.Float(x, y);
        return this;
    }
    public Transform setPosition(Point2D.Float position) {
        updateStateChange(true);
        this.position = position;
        return this;
    }
    public Transform setRotation(float rotation) {
        updateStateChange(true);
        this.rotation = rotation;
        return this;
    }
    public Transform moveForward(float distance) {
        updateStateChange(true);
        position.x += distance * Math.cos(Math.toRadians(rotation));
        position.y += distance * Math.sin(Math.toRadians(rotation));
        return this;
    }
    public void updateStateChange(boolean value) {
        if(gameObject != null)
            gameObject.newState = value;
    }
    public Transform rotate(float rotation) {
        updateStateChange(true);
        this.rotation += rotation;
        this.rotation %= 360.0f;
        return this;
    }
    public Transform setParent(GameObject parent) {
        gameObject = parent;
        return this;
    }
    @Override
    public String key() {
        return Transform.Key();
    }
    public static String Key() {
        return "Transform";
    }

    @Override
    public Transform cloneShallow() {
        return new Transform().setPosition(position.x, position.y).rotate(rotation);
    }
    @Override
    public Transform cloneDeep() {
        return new Transform().setPosition(Float.valueOf(position.x), Float.valueOf(position.y)).rotate(Float.valueOf(rotation));
    }
}
