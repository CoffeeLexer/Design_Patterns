package client.components.weaponFacade;
import java.util.concurrent.TimeUnit;
import client.components.Transform;
import client.gameObjects.GameObject;
import network.server.SEngine;

public class HoverAlgorithm extends ProjectileAlgorithm {
  private long firstFlyDuration;
  private long rotateDuration;
  private long secondFlyDuration;
  private float flySpeed;

  private int rotationSpeed = 5;
  private int rotateDurationInMs = 1450;

  public HoverAlgorithm(float speed, TimeUnit unit, long duration) {
    this.flySpeed = speed;

    long currentTime = System.currentTimeMillis();
    this.firstFlyDuration = currentTime + unit.toMillis(duration);
    this.rotateDuration = this.firstFlyDuration + unit.toMillis(rotateDurationInMs);
    this.secondFlyDuration = this.rotateDuration + unit.toMillis(duration);
  }

  @Override
  public void fly(Transform transform, float delta) {
    long currentTime = System.currentTimeMillis();
    if (currentTime <= firstFlyDuration) {
      transform.moveForward(flySpeed * delta);
    } else if (currentTime <= rotateDuration) {
      transform.rotate(rotationSpeed * delta);
    } else if (currentTime <= secondFlyDuration) {
      transform.moveForward((flySpeed * 2) * delta);
    } else {
      SEngine.GetInstance().Destroy((GameObject)parent);
    }
  }

  @Override
  public String key() {
    return getClass().getName();
  }
}
