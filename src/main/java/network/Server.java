package network;

import client.gameObjects.GameComponent;
import client.gameObjects.GameObject;
import network.data.Handshake;
import network.data.Linker;
import network.data.Message;
import network.data.Payload;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;

public class Server {

    private Selector selector = null;
    private ServerSocketChannel serverSocket = null;
    private Map<Handshake.Method, Function<Object, Function<SocketChannel, Consumer<Payload>>>> functions = null;

    public Server() {
        try {
            functions = new TreeMap<>();
            //  Create Selector
            selector = Selector.open();

            //  Open socket server
            serverSocket = ServerSocketChannel.open();
            serverSocket.bind(new InetSocketAddress("localhost", 8080));
            serverSocket.configureBlocking(false);

            //  Binding master channel (Client binder)
            serverSocket.register(selector, SelectionKey.OP_ACCEPT, 0);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void On(Handshake.Method invoker, Function<Object, Function<SocketChannel, Consumer<Payload>>> function) throws Exception {
        functions.put(invoker, function);
    }
    public void Notify(Payload payload) {
        try {
            Set<SelectionKey> sK = selector.keys();
            for (SelectionKey k : sK) {
                if (!k.isReadable() | !k.isValid()) continue;
                SocketChannel c = (SocketChannel) k.channel();
                Communicator.Write(c, payload);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int availableID() {
        Set<SelectionKey> sK = selector.keys();
        Set<Integer> ids = new HashSet<>();
        for (SelectionKey k : sK) {
            ids.add((Integer) k.attachment());
        }
        for(int i = 0;; i++)
            if(!ids.contains(i))
                return i;
    }

    void Listen() throws Exception {
        boolean active = true;
        Set<SelectionKey> selectionKeys = null;
        Iterator<SelectionKey> iterator = null;

        while(active) {
            //  Wait for channel activity
            selector.select();
            selectionKeys = selector.selectedKeys();
            iterator = selectionKeys.iterator();

            //  Go through all channels waiting for servers attention
            while(iterator.hasNext()) {
                SelectionKey key = iterator.next();
                iterator.remove();

                //  Guard for invalid requests
                if(!key.isValid()) continue;

                // Client is trying to connect to server
                if(key.isAcceptable()) {
                    System.out.println("Acceptable");
                    SocketChannel client = serverSocket.accept();
                    client.configureBlocking(false);
                    // TODO: Centralize ATTACHMENT parameter
                    int id = availableID();
                    System.out.printf("\t%s: Client Joined\n", id);
                    client.register(selector, SelectionKey.OP_READ, id);
                }

                //  Client sending data to server
                if(key.isValid() && key.isReadable()) {
                    System.out.printf("Readable: %s\n", key.attachment());
                    SocketChannel client = (SocketChannel) key.channel();
                    Payload payload = null;
                    try {
                        payload = Communicator.Read(client);
                    }
                    catch (SocketException | InterruptedException e) {
                        if(e.getMessage().equalsIgnoreCase("connection reset")) {
                            System.out.printf("\t%s: Client Disconnected!\n", key.attachment());
                            client.close();
                            continue;
                        }
                        e.printStackTrace();
                    }
                    System.out.printf("\t%s: %s\n", key.attachment(), payload.method);
                    if(functions.containsKey(payload.method))
                        functions.get(payload.method).apply(key.attachment()).apply(client).accept(payload);
                }

                //  Should not be reached. If is used, create issue.
                if(key.isValid() && key.isConnectable()) {
                    System.out.println("Connectable");
                    throw new Exception("Not Implemented");
                }
                if(key.isValid() && key.isWritable()) {
                    System.out.printf("Writable (%s)\n", (int)key.attachment());
                    throw new Exception("Not Implemented");
                }
            }
        }
    }
    public static int availableID(Map<Integer, GameObject> map) {
        for(int i = 0;; i++)
            if(!map.containsKey(i))
                return i;
    }
    public static void main(String[] args) throws Exception {
        //  Server Instance Possible singleton
        Server server = new Server();
        //  All objects
        Map<Integer, GameObject> gameObjects = new TreeMap<>();

        server.On(Handshake.Method.login, clientID -> client -> payload -> {
            Payload payload1 = new Payload(Handshake.Method.login);
            try {
                Communicator.Write(client, payload1);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        server.On(Handshake.Method.updateComponent, clientID -> client -> payload -> {
            GameComponent gameComponent = payload.GetData();
            gameObjects.get(gameComponent.gameObject.uniqueID).updateComponent(gameComponent);
            server.Notify(payload);
        });
        server.On(Handshake.Method.createGameObject, clientID -> client -> payload -> {
            GameObject o = payload.GetData();
            o.uniqueID = Server.availableID(gameObjects);
            gameObjects.put(o.uniqueID, o);
            Payload payload1 = new Payload(Handshake.Method.createGameObjectConfirm, o);
            try {
                Communicator.Write(client, payload1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Payload payload2 = new Payload(Handshake.Method.createGameObject, o);
            server.Notify(payload2);
        });
        server.Listen();
    }
}