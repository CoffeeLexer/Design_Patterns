package client.gameObjects.tankDecorators;
import client.gameObjects.*;

public abstract class TankDecorator extends Tank {
  protected Tank wrappee;

  public TankDecorator(Tank decoratedTank)
  {
    super(decoratedTank);
    this.wrappee = decoratedTank;
  }

  public void decorate(String text) {
    wrappee.decorate(text);
  }

  public void decorate(double amount) {
    wrappee.decorate(amount);
  }

  public void decorate(double a, double b) {
    wrappee.decorate(a, b);
  }

  public void decorate(double a, double b, String color) {
    wrappee.decorate(a, b, color);
  }
}
