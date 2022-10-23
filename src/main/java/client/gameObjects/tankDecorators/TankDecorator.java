package client.gameObjects.tankDecorators;
import client.gameObjects.*;
import client.controls.ControlInput;

public abstract class TankDecorator extends GameObject implements ITankDecorator {
  protected Tank wrappee;

  public TankDecorator(Tank decoratedTank)
  {
    super(decoratedTank.imagePath, decoratedTank.tankSize, true);
    setPosition(decoratedTank.getPosition().x, decoratedTank.getPosition().y);
    this.wrappee = decoratedTank;
    ControlInput.addControlListener(decoratedTank);
  }

  // public Tank listensToInput() {
  //   ControlInput.addControlListener(wrappee);
  //   return wrappee;
  // }

  public void decorate(String text) {
    wrappee.decorate(text);
  }

  public void decorate(double amount) {
    wrappee.decorate(amount);
  }

  public void decorate(double a, double b) {
    wrappee.decorate(a, b);
  }

  public void decorate(double a, double b, String modifier) {
    wrappee.decorate(a, b, modifier);
  }
}
