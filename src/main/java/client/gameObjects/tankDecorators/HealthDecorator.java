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

  public void displayHealth(double current, double max, String color) {
    System.out.println("display health " + current + " / " + max + " -- " + color);
  }

  @Override
  public void decorate(double a, double b)
  {
    displayHealth(a, b);
  }

  @Override
  public void decorate(double a, double b, String color)
  {
    displayHealth(a, b, color);
  }
}
