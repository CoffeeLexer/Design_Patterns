package client.gameObjects.projectiles;
import java.awt.geom.*;

public class StraightFlyAlgorithm implements ProjectileAlgorithm {
  @Override
  public void fly(Point2D.Float position, float flyingXSpeed, float flyingYSpeed, int projectileSize, boolean collided) {
    if (!collided) {
      position.setLocation(position.getX() + flyingXSpeed, position.getY() + flyingYSpeed);
    }
  }
}
