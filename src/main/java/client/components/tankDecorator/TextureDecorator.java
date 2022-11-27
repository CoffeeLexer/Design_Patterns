package client.components.tankDecorator;
import client.gameObjects.*;

public class TextureDecorator extends TankDecorator {
  public TextureDecorator(Tank decoratedTank)
  {
      super(decoratedTank);
  }


  private void setTexture(String path) {
    wrappee.renderer.setTexture(path);
  }

  @Override
  public void decorate(String path)
  {
    setTexture(path);
    wrappee.decorate(path);
  }

  @Override
  public TextureDecorator cloneShallow() {
    return new TextureDecorator(this.wrappee);
  }

  @Override
  public TextureDecorator cloneDeep() {
    return new TextureDecorator(this.wrappee);
  }
}