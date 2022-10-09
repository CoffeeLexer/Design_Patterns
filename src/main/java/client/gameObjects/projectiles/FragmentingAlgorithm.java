package client.gameObjects.projectiles;
import java.awt.geom.*;
import client.panels.MainPanel;

public class FragmentingAlgorithm implements ProjectileAlgorithm {
  public void createFragments(double x, double y, float rotation, int projectileSize) {
    String fragmentImage = "images/fragment.png";

    // fragment spawns at the center of the projectile
    float xCoords = (float) x + projectileSize / 2;
    float yCoords = (float) y + projectileSize / 2;

    MainPanel.addObject(new Fragment(xCoords, yCoords, rotation, fragmentImage));
  }

  @Override
  public void fly(Point2D.Float position, float flyingXSpeed, float flyingYSpeed, int projectileSize, boolean collided) {
    if (!collided) {
      position.setLocation(position.getX() + flyingXSpeed, position.getY() + flyingYSpeed);
    } else {
      for (int i = 0; i < 8; i++) {
        float rotation = i * 45;
        createFragments(position.getX(), position.getY(), rotation, projectileSize);
      }
    }
  }
}
