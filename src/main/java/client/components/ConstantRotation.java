package client.components;

public class ConstantRotation extends GameComponent {
    public float speed = 0.0f;
    public ConstantRotation(float SpeedDegrees) {
        this.speed = SpeedDegrees;
    }
    @Override
    public String key() {
        return ConstantRotation.Key();
    }
    public static String Key() {
        return "ConstantRotation";
    }
    @Override
    public void update(float delta) {
        Transform transform = gameObject.getComponent(Transform.Key());
        transform.rotate(speed * delta);
    }
    @Override
    public ConstantRotation cloneShallow() {
        return new ConstantRotation(speed);
    }
    @Override
    public ConstantRotation cloneDeep() {
        return new ConstantRotation(Float.valueOf(speed));
    }
}
