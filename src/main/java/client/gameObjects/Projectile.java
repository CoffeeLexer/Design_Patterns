package client.gameObjects;

import client.components.Renderer;
import client.components.Transform;

public class Projectile extends GameObject {
    private float movementSpeed = 15;
    private float flyingXSpeed = 0;
    private float flyingYSpeed = 0;

    public Projectile(float x, float y, float angle, String imagePath) {
        addComponent(new Renderer(imagePath, 30, true));
        addComponent(new Transform().setPosition(x, y).setRotation(angle - 90));
        // image is horizontal so rotate it by 90 degrees

        this.flyingXSpeed = movementSpeed * (float) Math.sin(Math.toRadians(angle % 360));
        this.flyingYSpeed = -movementSpeed * (float) Math.cos(Math.toRadians(angle % 360));
        tag = "Dynamic";
    }

    private void fly() {
        Transform transform = getComponent(Transform.Key());
        transform.position.setLocation(transform.position.getX() + flyingXSpeed, transform.position.getY() + flyingYSpeed);
    }

    @Override
    public void update() {
      fly();
    }
}
