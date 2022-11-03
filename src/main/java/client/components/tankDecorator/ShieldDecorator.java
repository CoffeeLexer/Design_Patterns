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
      wrappee.renderer.setTexture("images/shielded-tank.png");
    } else {
      wrappee.renderer.setTexture(wrappee.originalImagePath);
    }
  }

  @Override
  public void decorate(double amount)
  {
    setShield(amount);
    renderShield(amount);
    wrappee.decorate(amount);
  }
  @Override
  public ShieldDecorator cloneShallow() {
    return new ShieldDecorator(this.wrappee);
  }
  @Override
  public ShieldDecorator cloneDeep() {
    return new ShieldDecorator(this.wrappee);
  }
}