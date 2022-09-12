package network;

import network.data.Handshake;
import network.data.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.ClassNotFoundException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * This class implements java Socket server
 * @author pankaj
 *
 */
public class Server {
    private static final int defaultPort = 8080;

    ServerSocket server = null;
    Socket socket = null;
    ObjectOutputStream oos = null;
    ObjectInputStream ois = null;

    private void Connect() {
        try {
            socket = server.accept();
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
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

    public Server() {
        try {
            server = new ServerSocket(defaultPort);
        } catch (Exception ignored) {}

        boolean active = true;
        try {
            while(active) {
                System.out.println("Waiting");
                Connect();

                Handshake handshake = (Handshake) ois.readObject();
                System.out.println("Method: " + handshake.method);

                switch(handshake.method) {
                    case ping -> {
                        oos.writeObject("Pong!");
                    }
                    case shutdown -> {
                        active = false;
                    }
                    case message -> {
                        Message m = (Message)ois.readObject();
                        System.out.printf("%s: %s\n", m.author, m.content);
                    }
                    default -> {
                        System.out.printf("Undefined method: %s\n", handshake.method);
                    }
                }
                Disconnect();
            }
        } catch (IOException | ClassNotFoundException ignored) {}

        System.out.println("Shutting down Socket server!!");
        Shutdown();
    }

    private void Shutdown() {
        try {
            server.close();
        } catch (IOException ignored) {}
    }

    public static void main(String[] args) {
        Server server = new Server();
    }
}