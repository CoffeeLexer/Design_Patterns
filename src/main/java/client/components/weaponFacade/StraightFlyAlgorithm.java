package client.components.weaponFacade;
import java.util.concurrent.TimeUnit;
import client.components.Transform;
import client.gameObjects.GameObject;
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
      SEngine.GetInstance().Destroy((GameObject)parent);
    }

    transform.moveForward(speed * delta);
  }

  @Override
  public String key() {
    return getClass().getName();
  }
}
