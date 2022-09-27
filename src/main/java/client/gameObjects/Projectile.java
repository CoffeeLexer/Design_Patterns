package client.gameObjects;

public class Projectile extends GameObject {
    private float movementSpeed = 15;
    private float flyingXSpeed = 0;
    private float flyingYSpeed = 0;

    public Projectile(float x, float y, float angle, String imagePath) {
      super(imagePath, 30, true);
      setPosition(x, y);
      this.rotation = angle - 90; // image is horizontal so rotate it by 90 degrees

      this.flyingXSpeed = movementSpeed * (float) Math.sin(Math.toRadians(angle % 360));
      this.flyingYSpeed = -movementSpeed * (float) Math.cos(Math.toRadians(angle % 360));
    }

    private void fly() {
      position.setLocation(position.getX() + flyingXSpeed, position.getY() + flyingYSpeed);
    }

    @Override
    public void update() {
      fly();
    }
}
