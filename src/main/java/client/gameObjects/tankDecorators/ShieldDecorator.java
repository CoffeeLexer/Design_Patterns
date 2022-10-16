package client.gameObjects.tankDecorators;
import client.gameObjects.*;

public class ShieldDecorator extends TankDecorator {
  public ShieldDecorator(Tank decoratedTank)
  {
      super(decoratedTank);
  }

  public void setShield(double amount) {
    System.out.println("setting shield to " + amount);
    System.out.println("shield before " + wrappee.getShieldAmount());
    wrappee.setShield(amount);
    System.out.println("shield after " + wrappee.getShieldAmount());
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
