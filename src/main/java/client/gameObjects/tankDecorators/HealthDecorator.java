package client.gameObjects.tankDecorators;
import client.gameObjects.*;

public class HealthDecorator extends TankDecorator {
  public HealthDecorator(Tank decoratedTank)
  {
      super(decoratedTank);
  }

  public void displayHealth(double current, double max) {
    System.out.println("display health " + current + " / " + max);
  }

  @Override
  public void decorate(double a, double b)
  {
    displayHealth(a, b);
  }
}
