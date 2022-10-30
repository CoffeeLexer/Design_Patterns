package client.components.tankDecorator;
import client.gameObjects.*;
import network.server.SEngine;
import client.components.Transform;
import java.awt.geom.Point2D;
import java.awt.Color;

public class LabelDecorator extends TankDecorator {
  private int yOffset = -15;

  public LabelDecorator(Tank decoratedTank){
      super(decoratedTank);
  }

  private void displayLabel(String text) {
    // get tank coordinates
    Point2D.Float position = ((Transform)this.wrappee.getComponent(Transform.Key())).position;
    int x = (int) Math.round(position.getX());
    int y = (int) Math.round(position.getY());

    // render label on tank
    Label label = new Label(x, y, text, this.wrappee, Color.GREEN, yOffset);
    SEngine.GetInstance().Add(label);
    wrappee.addChildID(label.uniqueID);
  }

  @Override
  public void decorate(String text)
  {
    displayLabel(text);
    wrappee.decorate(text);
  }
  @Override
  public LabelDecorator clone() {
    return new LabelDecorator(this.wrappee);
  }
}