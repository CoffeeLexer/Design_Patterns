package client.components.weapon;

import java.awt.geom.*;
import java.awt.geom.Point2D.Float;

import client.components.GameComponent;
import client.components.Transform;
import client.gameObjects.Projectile;

public abstract class ProjectileAlgorithm extends GameComponent {

  public abstract void fly(Transform transform, float delta);

  public void setProjectile(Projectile projectile) {
      this.gameObject = projectile;
  }

  @Override
  public void update(float delta) {
    Transform transform = gameObject.getComponent(Transform.Key());
    fly(transform, delta);
  }
  @Override
  public ProjectileAlgorithm clone() {
    return null;
  }
}
