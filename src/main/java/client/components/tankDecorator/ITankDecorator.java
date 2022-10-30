package client.components.tankDecorator;
import java.awt.Color;

public interface ITankDecorator {
  public void decorate(String text);
  public void decorate(double a, double b);
  public void decorate(double a, double b, Color color);
  public void decorate(double amount);
}