package network.data;

import java.io.Serializable;

public abstract class Handshake implements Serializable {
    public enum Method {
        //  Reserved for server-client communication
        empty,  // Default method property
        login,  // Client login method

        //  Can be modified
        echo,
        ping,
        shutdown,
        message,
        notify,
        observer,
        test,

        createComponent,
        setComponent,
        destroyComponent,
        updateComponent,

        createGameObject,
        createGameObjectConfirm,
        setGameObject,
        destroyGameObject,
        updateGameObject,

        linkerUpdate,
    }
    public Method method = Method.empty;
}
