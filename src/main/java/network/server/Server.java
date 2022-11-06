package network.server;

import network.client.ClientWorker;
import network.data.Connection;
import network.data.Handshake;
import network.data.Payload;
import network.factories.LevelFactory;
import network.factories.StrongholdFactory;
import network.levelManagement.LevelManager;
import network.factories.CityFactory;
import network.factories.DungeonFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;

import client.gameObjects.consumables.AmmunitionConsumable;

public class Server {
    private ServerSocket serverSocket = null;
    private final Integer port = 8080;
    private ThreadGroup workers = null;
    private ReentrantLock clientLock = null;
    public List<Connection> connections = null;

    private LevelManager levelManager;

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

                Connection connection = new Connection(clientSocket);



                Payload payload = (Payload) connection.readObject();
                if (payload.method == Handshake.Method.login) {
                    connection.setUDPPort(payload.GetData());
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


            try {
                connection.writeObject(payload);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        clientLock.unlock();
    }

    public void NotifyUDP(Payload payload) {
        clientLock.lock();
        var i = connections.iterator();

        while (i.hasNext()) {
            Connection connection = i.next();
            var udp = new UDPConnectionAdapter(connection);
            udp.writeObject(payload);
        }
        clientLock.unlock();
    }

    public void notifyConnections(Consumer<Connection> consumer){
        for (Connection connection : connections) {
            consumer.accept(connection);
        }
    }

    private void initializeGame() {
        levelManager = LevelManager.getInstance();
        levelManager.buildNextLevel();
    }

    public static void main(String[] args) {
        Server.GetInstance().initializeGame();
        Server.GetInstance().Listen();
    }
}
