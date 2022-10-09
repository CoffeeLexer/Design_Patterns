package client.gameObjects.projectiles;
import client.gameObjects.GameObject;

public class Fragment extends GameObject {
  private StraightFlyAlgorithm algorithm = new StraightFlyAlgorithm();
  private float movementSpeed = 7;
  private float flyingXSpeed = 0;
  private float flyingYSpeed = 0;
  private static int fragmentSize = 12;

  public Fragment(float x, float y, float angle, String imagePath) {
    super(imagePath, fragmentSize, true);
    setPosition(x, y);
    this.rotation = angle;
    this.flyingXSpeed = movementSpeed * (float) Math.sin(Math.toRadians(angle % 360));
    this.flyingYSpeed = -movementSpeed * (float) Math.cos(Math.toRadians(angle % 360));
  }

  @Override
  public void update() {
    algorithm.fly(position, flyingXSpeed, flyingYSpeed, fragmentSize, false);
  }

  @Override
  public void beforeCollide() {
    algorithm.fly(position, flyingXSpeed, flyingYSpeed, fragmentSize, true);
  }
}
