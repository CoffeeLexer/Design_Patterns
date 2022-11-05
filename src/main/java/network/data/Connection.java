package network.data;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.locks.ReentrantLock;

public class Connection {
    protected ObjectInputStream input = null;
    protected ObjectOutputStream output = null;
    protected ReentrantLock lock = null;
    protected Integer udpPort = null;
    protected Socket socket = null;
    public Connection(Socket clientSocket) {
        construct(clientSocket);
    }
    protected void construct(Socket clientSocket) {
        try {
            output = new ObjectOutputStream(clientSocket.getOutputStream());
            input = new ObjectInputStream(clientSocket.getInputStream());
            lock = new ReentrantLock();

            socket = clientSocket;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void writeObject(Object obj) throws IOException {
        lock.lock();
        output.writeObject(obj);
        lock.unlock();
    }

    public Object readObject() throws IOException, ClassNotFoundException {
        return input.readObject();
    }
    public void setUDPPort(int port) {
        udpPort = port;
    }
    public int getUDPPort() {
        return udpPort;
    }
    public InetAddress getAddress() {
        return socket.getInetAddress();
    }
}
