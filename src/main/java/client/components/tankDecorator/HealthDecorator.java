package client.components.tankDecorator;
import client.gameObjects.*;
import network.server.SEngine;
import client.components.Transform;
import java.awt.geom.Point2D;
import java.awt.Color;

public class HealthDecorator extends TankDecorator {
  private Label label;

  public HealthDecorator(Tank decoratedTank)
  {
      super(decoratedTank);

      Point2D.Float position = ((Transform)this.wrappee.getComponent(Transform.Key())).position;
      int x = (int) Math.round(position.getX());
      int y = (int) Math.round(position.getY());

      // render initial HP on tank
      String text = decoratedTank.getCurrentHP() + " / " + decoratedTank.getMaxHP();
      Label label = new Label(x, y, text, this.wrappee, Color.RED);
      this.label = label;
      SEngine.GetInstance().Add(label);
      wrappee.addChildID(label.uniqueID);
  }

  private void displayHealth(double current, double max) {
    this.label.setText("HP: " + current + " / " + max);
    this.label.setColor(Color.RED);
  }

  private void displayHealth(double current, double max, Color color) {
    this.label.setText("HP: " + current + " / " + max);
    this.label.setColor(color);
  }

  @Override
  public void decorate(double a, double b)
  {
    displayHealth(a, b);
    wrappee.decorate(a, b);
  }

  @Override
  public void decorate(double a, double b, Color color)
  {
    displayHealth(a, b, color);
    wrappee.decorate(a, b, color);
  }
  @Override
  public HealthDecorator cloneShallow() {
    return new HealthDecorator(this.wrappee);
  }
  public HealthDecorator cloneDeep() {
    return new HealthDecorator(this.wrappee);
  }
}