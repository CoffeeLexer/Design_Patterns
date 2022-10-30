package client.components.tankDecorator;
import client.components.GameComponent;
import client.gameObjects.*;
import java.awt.Color;

import org.apache.commons.lang3.NotImplementedException;

public abstract class TankDecorator extends GameComponent implements ITankDecorator {
  protected Tank wrappee;

  public TankDecorator(Tank decoratedTank) {
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

  public void decorate(double a, double b, Color color) {
    wrappee.decorate(a, b, color);
  }

  @Override
  public String key() {
      return TankDecorator.Key();
  }

  public static String Key() {
      return "TankDecorator";
  }
  @Override
  public TankDecorator clone() {
    throw new NotImplementedException();
  }
}
