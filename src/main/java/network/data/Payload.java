package network.data;

import java.io.Serializable;

public class Payload extends Handshake implements Serializable {
    Object object = null;
    public <T extends Serializable> T GetData() {
        return (T)object;
    }
    public <T extends Serializable> void SetData(T data) {
        object = data;
    }
    public Payload(Method m) {
        method = m;
    }
    public <T extends Serializable> Payload(Method m, T data) {
        this(m);
        object = data;
    }
    public Payload() {
        this(Method.empty);
    }
}
