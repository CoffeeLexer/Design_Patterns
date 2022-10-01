package network;

import network.data.Handshake;
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
    private Map<Handshake.Method, Function<SocketChannel, Consumer<Payload>>> functions = null;

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
            serverSocket.register(selector, SelectionKey.OP_ACCEPT);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void On(Handshake.Method invoker, Function<SocketChannel, Consumer<Payload>> function) throws Exception {
        functions.put(invoker, function);
    }
    public void Notify(Payload payload) {
        try {
            Set<SelectionKey> sK = selector.keys();
            for (SelectionKey k : sK) {
                if (!k.isReadable() | !k.isValid()) continue;
                System.out.printf("\tPinging: %s\n", k.attachment());
                SocketChannel c = (SocketChannel) k.channel();
                Communicator.Write(c, payload);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
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
                    int rng = new Random().nextInt();
                    System.out.printf("\t%s: Client Joined\n", rng);
                    client.register(selector, SelectionKey.OP_READ, rng);
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
                        functions.get(payload.method).apply(client).accept(payload);
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
    public static void main(String[] args) throws Exception {
        Server server = new Server();
        server.On(Handshake.Method.login, client -> payload -> {
            Payload payload1 = new Payload(Handshake.Method.login);
            try {
                Communicator.Write(client, payload1);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        server.On(Handshake.Method.message, client -> payload -> {
            Message m = payload.GetData();
            System.out.printf("\t%s: %s\n", m.author, m.content);
        });
        server.On(Handshake.Method.ping, client -> payload -> {
            Payload payload1 = new Payload(Handshake.Method.ping);
            server.Notify(payload1);
        });
        server.Listen();
    }
}