package client.gameObjects.projectiles;
import client.gameObjects.GameObject;

public class Projectile extends GameObject {
  private ProjectileAlgorithm algorithm;
  private float movementSpeed = 20;
  private float flyingXSpeed = 0;
  private float flyingYSpeed = 0;
  private static int projectileSize = 30;

  public Projectile(float x, float y, float angle, String imagePath, String algo) {
    super(imagePath, projectileSize, true);

    if (algo == "straight") {
      this.algorithm = new StraightFlyAlgorithm();
    } else if (algo == "fragmenting") {
      this.algorithm = new FragmentingAlgorithm();
    }

    this.rotation = angle;
    setPosition(x, y);

    this.flyingXSpeed = movementSpeed * (float) Math.sin(Math.toRadians(angle % 360));
    this.flyingYSpeed = -movementSpeed * (float) Math.cos(Math.toRadians(angle % 360));
  }

  @Override
  public void update() {
    if (algorithm != null) {
      algorithm.fly(position, flyingXSpeed, flyingYSpeed, projectileSize, false);
    }
  }

  @Override
  public void beforeCollide() {
    if (algorithm != null) {
      algorithm.fly(position, flyingXSpeed, flyingYSpeed, projectileSize, true);
    }
  }
}
