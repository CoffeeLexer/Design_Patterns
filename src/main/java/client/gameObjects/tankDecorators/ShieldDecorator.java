package client.gameObjects.tankDecorators;
import client.gameObjects.*;

public class ShieldDecorator extends TankDecorator {
  public ShieldDecorator(Tank decoratedTank)
  {
      super(decoratedTank);
  }

  private void setShield(double amount) {
    System.out.println("set shield to " + amount);
    wrappee.setShield(amount);
  }

  private void renderShield(double amount) {
    if (amount > 0) {
      System.out.println("render shield");
      wrappee.movementSpeed = 1.0f;
      // wrappee.setTexture("images/shielded-tank.png");
    } else {
      System.out.println("RESET shield");
      // wrappee.resetTexture();
    }
  }

  @Override
  public void decorate(double amount)
  {
    setShield(amount);
    renderShield(amount);
    wrappee.decorate(amount);
  }
}
