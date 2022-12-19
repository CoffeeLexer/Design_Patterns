package game.data;

import java.io.Serializable;

public class Vector implements Serializable {
    public float x;
    public float y;
    public Vector(float x, float y) {
        this.x = x;
        this.y = y;
    }
}
