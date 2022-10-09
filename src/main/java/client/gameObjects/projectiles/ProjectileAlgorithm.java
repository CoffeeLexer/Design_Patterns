package client.gameObjects.projectiles;
import java.awt.geom.*;

public interface ProjectileAlgorithm {
  public void fly(Point2D.Float position, float flyingXSpeed, float flyingYSpeed, int projectileSize, boolean collided);
}
