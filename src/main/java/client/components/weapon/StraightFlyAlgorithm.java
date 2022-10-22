package client.components.weapon;
import java.awt.geom.*;
import java.util.concurrent.TimeUnit;

import client.components.Transform;
import client.gameObjects.Projectile;
import network.server.SEngine;

public class StraightFlyAlgorithm extends ProjectileAlgorithm {
  private float speed;
  private long end;

  public StraightFlyAlgorithm(float speed, TimeUnit unit, long duration) {
    end = System.currentTimeMillis() + unit.toMillis(duration);
    this.speed = speed;
  }

  @Override
  public void fly(Transform transform, float delta) {
    if (System.currentTimeMillis() > end) {
      SEngine.GetInstance().Destroy(gameObject);
    }

    transform.moveForward(speed * delta);
  }

  @Override
  public String key() {
    return getClass().getName();
  }
}
