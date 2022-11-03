package network.server;

import network.client.ClientWorker;
import network.data.Connection;
import network.data.Handshake;
import network.data.Payload;
import network.factories.LevelFactory;
import network.factories.StrongholdFactory;
import network.factories.CityFactory;
import network.factories.DungeonFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

public class Server {
    private ServerSocket serverSocket = null;
    private final Integer port = 8080;
    private ThreadGroup workers = null;
    private ReentrantLock clientLock = null;
    private List<Connection> connections = null;

    private LevelFactory levelFactory;

    private Server() {
        try {
            connections = new ArrayList<>();
            workers = new ThreadGroup("ClientListeners");
            serverSocket = new ServerSocket(port);
            clientLock = new ReentrantLock();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static final Server instance = new Server();

    public static Server GetInstance() {
        return instance;
    }

    public void CloseConnection(Connection connection) {
        clientLock.lock();
        connections.remove(connection);
        clientLock.unlock();
    }

    public void Listen() {
        try {
            boolean active = true;
            while (active) {
                Socket clientSocket = serverSocket.accept();
                clientLock.lock();

                Connection connection = new Connection();
                connection.socket = clientSocket;

                connection.output = new ObjectOutputStream(clientSocket.getOutputStream());
                connection.input = new ObjectInputStream(clientSocket.getInputStream());
                connection.lock = new ReentrantLock();

                Payload payload = (Payload) connection.input.readObject();
                if (payload.method == Handshake.Method.login) {
                    connection.udpPort = payload.GetData();
                } else {
                    System.out.println("First Invoking must be Method login!");
                }
                connections.add(connection);
                ServerWorker worker = new ServerWorker(connection);
                new Thread(workers, worker).start();

                clientLock.unlock();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void Info() {
        System.out.printf("Current client count: %s\n", workers.activeCount());
        SEngine.GetInstance().Info();
    }

    public void NotifyTCP(Payload payload) {
        clientLock.lock();
        var i = connections.iterator();

        while (i.hasNext()) {
            var connection = i.next();

            connection.lock.lock();
            try {
                connection.output.writeObject(payload);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                connection.lock.unlock();
            }
        }
        clientLock.unlock();
    }

    public void NotifyUDP(Payload payload) {
        clientLock.lock();
        var i = connections.iterator();

        while (i.hasNext()) {
            var connection = i.next();
            UDPSender.getInstance().Send(connection.socket.getInetAddress(), connection.udpPort, payload);
        }
        clientLock.unlock();
    }

    private void initializeGame() {
        levelFactory = new StrongholdFactory(32, 18, 60);
        levelFactory.buildLevel();
    }

    public static void main(String[] args) {
        Server.GetInstance().initializeGame();
        Server.GetInstance().Listen();
    }
}
