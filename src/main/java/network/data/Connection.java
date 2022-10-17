package network.data;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.locks.ReentrantLock;

public class Connection {
    public ObjectInputStream input = null;
    public ObjectOutputStream output = null;
    public ReentrantLock lock = null;
    public Integer udpPort = null;
    public Socket socket = null;
}
