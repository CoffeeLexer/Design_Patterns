package client.components.weaponFacade;
import org.apache.commons.lang3.NotImplementedException;
import client.components.GameComponent;
import client.components.Transform;
import client.gameObjects.Projectile;

public abstract class ProjectileAlgorithm extends GameComponent {
  public abstract void fly(Transform transform, float delta);

  public void setProjectile(Projectile projectile) {
      this.parent = projectile;
  }

  @Override
  public void update(float delta) {
    Transform transform = parent.getComponent(Transform.Key());
    fly(transform, delta);
  }
  @Override
  public ProjectileAlgorithm cloneShallow() {
    throw new NotImplementedException();
  }
  @Override
  public ProjectileAlgorithm cloneDeep() {
    throw new NotImplementedException();
  }
}
