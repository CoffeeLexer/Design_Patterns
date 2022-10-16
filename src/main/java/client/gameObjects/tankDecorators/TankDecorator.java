package client.gameObjects.tankDecorators;
import client.gameObjects.*;

public abstract class TankDecorator extends Tank {
  protected Tank wrappee;

  public TankDecorator(Tank decoratedTank)
  {
    super(decoratedTank);
    this.wrappee = decoratedTank;
  }

  public void decorate() {
    wrappee.decorate();
  }
}
