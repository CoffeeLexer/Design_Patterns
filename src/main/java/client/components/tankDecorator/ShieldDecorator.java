package client.components.tankDecorator;
import client.gameObjects.*;

public class ShieldDecorator extends TankDecorator {
  public ShieldDecorator(Tank decoratedTank)
  {
      super(decoratedTank);
  }

  private void setShield(double amount) {
    wrappee.setShield(amount);
  }

  private void renderShield(double amount) {
    if (amount > 0) {
      wrappee.setImagePath("images/shielded-tank.png");
    } else {
      wrappee.setImagePath(null);
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