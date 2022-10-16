package client.gameObjects.tankDecorators;

public interface ITankDecorator {
  public void decorate(String text);
  public void decorate(double a, double b);
  public void decorate(double a, double b, String color);
  public void decorate(double amount);
}
