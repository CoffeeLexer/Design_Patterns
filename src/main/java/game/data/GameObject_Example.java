package game.data;

public class GameObject_Example extends GameObject {
    @Override
    public void update(float delta) {
        super.update(delta);
        rotation += delta / 3.14;
    }
}
