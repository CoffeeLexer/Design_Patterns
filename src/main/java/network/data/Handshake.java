package network.data;

import java.io.Serializable;

public class Handshake implements Serializable {
    public enum Method {
        empty,
        echo,
        ping,
        shutdown,
        message
    }
    public Method method = Method.empty;
}
