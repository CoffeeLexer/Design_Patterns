package client.gameObjects.tankDecorators;
import client.gameObjects.*;

public class ShieldDecorator extends TankDecorator {
  public ShieldDecorator(Tank decoratedTank)
  {
      super(decoratedTank);
  }

  // For example if current HP is 3 but you have a shield of 1
  // and you get hit for 1 damage, your HP should stay at 3
  // because total HP = tank HP + shield amount
  // * if amount is 0, then it means the shield should be disabled
  public void setShield(double amount) {
    System.out.println("setting shield " + amount);
    wrappee.updateHealth(amount);
  }

  @Override public void decorate()
  {
    wrappee.decorate();
    setShield(1);
  }
}
