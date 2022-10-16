package client.gameObjects.tankDecorators;
import client.gameObjects.*;

public class ShieldDecorator extends TankDecorator {
  public ShieldDecorator(Tank decoratedTank)
  {
      super(decoratedTank);
  }

  public void setShield(double amount) {
    System.out.println("setting shield to " + amount);
    wrappee.setShield(amount);
  }

  public void renderShield() {
    System.out.println("render shield");
  }

  @Override
  public void decorate(double amount)
  {
    setShield(amount);
    renderShield();
  }
}
