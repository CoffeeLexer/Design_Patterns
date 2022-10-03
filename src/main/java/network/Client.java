package network;

import client.gameObjects.GameComponent;
import client.gameObjects.GameObject;
import network.data.Handshake;
import network.data.Linker;
import network.data.Payload;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class Client {
    private SocketChannel client = null;
    private Map<Handshake.Method, Consumer<Payload>> functions = null;
    private boolean listening = false;

    public void Connect() {
        try {
            client = SocketChannel.open(new InetSocketAddress("localhost", 8080));
            client.configureBlocking(true);
            functions = new TreeMap<>();
            Login();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void On(Handshake.Method invoker, Consumer<Payload> function) throws Exception {
        if(!listening) functions.put(invoker, function);
        else throw new Exception("Adding listener after Listen() was invoked!");
    }

    private void _listen() {
        System.out.println("Listening");
        while (true) {
            try {
                Payload payload = Communicator.Read(client);
                if(functions.containsKey(payload.method))
                    functions.get(payload.method).accept(payload);
            }
            catch (Exception e) {
                if(e.getMessage().equalsIgnoreCase("connection reset")) {
                    System.out.println(e.getMessage());
                    return;
                }
                e.printStackTrace();
            }
        }
    }
    Thread listener = null;
    public void Listen() {
        listening = true;
        listener = new Thread(this::_listen);
        listener.start();
    }
    private void Login() {
        Payload payload = new Payload(Handshake.Method.login, null);
        try {
            Communicator.Write(client, payload);
            client.finishConnect();
            Communicator.Read(client);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    Map<Integer, GameObject> gameObjects = null;

    private static Client instance = new Client();
    private Client() {
        Connect();
        gameObjects = new TreeMap<>();
        blockingQueue = new LinkedBlockingQueue<>();
        try {
            On(Handshake.Method.updateGameObject, payload -> {
                GameObject o = payload.GetData();
                gameObjects.put(o.uniqueID, o);
            });
            On(Handshake.Method.createGameObject, payload -> {
                GameObject gameObject = payload.GetData();
                gameObjects.put(gameObject.uniqueID, gameObject);
            });
            On(Handshake.Method.updateComponent, payload -> {
                GameComponent gameComponent = payload.GetData();
                gameObjects.get(gameComponent.gameObject.uniqueID).updateComponent(gameComponent);
            });
            On(Handshake.Method.createGameObjectConfirm, payload -> {
                try {
                    blockingQueue.put(payload);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            Listen();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void foreach(Consumer<? super GameObject> function) {
        gameObjects.values().forEach(function);
    }
    public static Client getInstance() {
        return instance;
    }
    public int availableID() {
        for(int i = 0; true; i++)
            if(!gameObjects.containsKey(i))
                return i;
    }
    BlockingQueue<Payload> blockingQueue;
    public GameObject addGameObject(GameObject gameObject) {
        Payload payload = new Payload(Handshake.Method.createGameObject, gameObject);
        try {
            Communicator.Write(client, payload);
            payload = blockingQueue.take();
            gameObject = payload.GetData();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        gameObjects.put(gameObject.uniqueID, gameObject);
        return gameObject;
    }
    public void update(GameComponent gameComponent) {
        Payload payload = new Payload(Handshake.Method.updateComponent, gameComponent);
        try {
            Communicator.Write(client, payload);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        try {
            TimeUnit.MINUTES.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}