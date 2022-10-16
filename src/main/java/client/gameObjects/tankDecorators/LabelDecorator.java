package client.gameObjects.tankDecorators;
import client.gameObjects.*;

public class LabelDecorator extends TankDecorator {
  public LabelDecorator(Tank decoratedTank)
  {
      super(decoratedTank);
  }

  public void displayLabel(String text) {
    System.out.println("display label " + text);
  }

  @Override
  public void decorate(String text)
  {
    displayLabel(text);
  }
}
