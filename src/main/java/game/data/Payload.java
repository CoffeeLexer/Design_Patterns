package game.data;

import java.io.Serializable;

public class Payload implements Serializable {
    Object object = null;
    public Method method = Method.empty;

    public <T extends Serializable> T getData() {return (T)object;}
    public <T extends Serializable> void setData(T data) {
        object = data;
    }

    // No data is needed for some methods
    public Payload(Method m) {
        method = m;
    }

    // Some methods require data
    public Payload(Method m, Object data) {
        this(m);
        object = data;
    }

    // By default initialise as "empty" method
    public Payload() {
        this(Method.empty);
    }

    public enum Method {
        empty,
        bindUDP,

        // Create/Set objects
        setObject,

        // Remove object
        removeObject,

        // Client need to synchronize static objects
        syncEngine,

        // Keyboard event methods
        keyTyped,
        keyPressed,
        keyReleased,

        // Mouse event methods
        mousePressed,
        mouseReleased,
        mouseClicked,
        mouseEntered,
        mouseExited,
    }
}
