package network.data;

import java.io.Serializable;

public abstract class Handshake implements Serializable {
    public enum Method {
        empty,
        login,
        info,
        keyPressed,
        keyReleased,
        keyTyped,
        joinGame,
        setGameObject,
        syncEngine,
        removeGameObject,
        tagPlayer,
        interpreter,
    }
    public Method method = Method.empty;
}
