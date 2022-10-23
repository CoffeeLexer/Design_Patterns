package client.components.weapon;

import java.awt.geom.*;
import java.util.concurrent.TimeUnit;

import client.components.Transform;
import client.gameObjects.Projectile;
import network.server.SEngine;

public class ShotgunAlgorithm extends ProjectileAlgorithm {

  private long duration;
  private float speed;

  public void createShell(float rotation) {
    String fragmentImage = "images/shell.png";
    Transform transform = gameObject.getComponent(Transform.Key());

    SEngine.GetInstance()
        .Add(new Projectile(transform.position.x, transform.position.y, rotation, fragmentImage, 9)
            .setAlgorithm(new StraightFlyAlgorithm(speed, TimeUnit.MILLISECONDS, duration)));
  }

  public ShotgunAlgorithm(float speed, TimeUnit unit, long duration) {
    this.duration = System.currentTimeMillis() + unit.toMillis(duration);
    this.speed = speed;
  }

  @Override
  public void fly(Transform transform, float delta) {
    SEngine.GetInstance().Destroy(gameObject);
    createShell(transform.rotation);
    createShell(transform.rotation - 10);
    createShell(transform.rotation + 10);
    return;
  }

  @Override
  public String key() {
    return getClass().getName();
  }
}
