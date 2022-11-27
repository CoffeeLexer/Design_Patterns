package client.components.tankMemento;
import client.components.Transform;

// this object is Memento
public class TankState {
  float x;
  float y;
  float angle;

  public TankState(Transform tankPosition) {
    // we have to create copies of numbers, otherwise if we saved the whole tankPosition,
    // it would be saved by reference so it wouldn't keep the old values on reset
    this.x = tankPosition.getPosition().x;
    this.y = tankPosition.getPosition().y;
    this.angle = tankPosition.getAngle();
  }

  public Transform getPosition() {
    return new Transform().setPosition(x, y).setRotation(angle);
  }
}
