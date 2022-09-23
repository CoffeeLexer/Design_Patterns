package network;

import network.data.Handshake;
import network.data.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    private static final int defaultPort = 8080;

    Socket socket = null;
    ObjectOutputStream oos = null;
    ObjectInputStream ois = null;

    String host = null;
    int port = -1;

    public Client() {
        try {
            this.host = InetAddress.getLocalHost().getHostName();
            this.port = defaultPort;
        } catch (UnknownHostException ignored) {}
    }

    public Client(int port) {
        try {
            this.host = InetAddress.getLocalHost().getHostName();
            this.port = port;
        } catch (UnknownHostException ignored) {}
    }

    public Client(String host, int port) {
        this.host = host;
        this.port = defaultPort;
    }

    private void Connect(Handshake.Method type) {
        try {
            socket = new Socket(host, port);
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());

            Handshake h = new Handshake();
            h.method = type;

            oos.writeObject(h);
        } catch (IOException ignored) {}
    }

    private void Disconnect() {
        try {
            socket.close();
            socket = null;

            ois.close();
            ois = null;

            oos.close();
            oos = null;
        } catch (IOException ignored) {}
    }
    private String username = "unknown";
    public void Message(String content) {
        Connect(Handshake.Method.message);

        network.data.Message m = new network.data.Message();
        m.author = username;
        m.content = content;
        try {
            oos.writeObject(m);
        } catch (IOException ignored) {}

        Disconnect();
    }

    public static void main(String[] args) {
        //  Testing purposes only
        Client client = new Client();
        client.username = "big tiddy";
        client.Message("White people cringe");
    }
}