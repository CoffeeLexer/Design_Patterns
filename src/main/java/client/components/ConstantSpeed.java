package client.components;

public class ConstantSpeed extends GameComponent{
    public float speed = 0.0f;
    public ConstantSpeed(float Speed) {
        this.speed = Speed;
    }
    @Override
    public String key() {
        return ConstantSpeed.Key();
    }
    public static String Key() {
        return "ConstantSpeed";
    }
    @Override
    public void update(float delta) {
        Transform transform = gameObject.getComponent(Transform.Key());
        transform.moveForward(speed * delta);
    }
}
