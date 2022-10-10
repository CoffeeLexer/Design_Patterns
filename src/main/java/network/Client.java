package network;

import client.gameObjects.GameComponent;
import client.gameObjects.GameObject;
import network.data.Handshake;
import network.data.Payload;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class Client {
    public static String hostname = "localhost";
    public static int port = 8080;
    private Thread listener = null;
    private Map<Integer, GameObject> gameObjects = null;
    private BlockingQueue<Payload> blockingQueue;
    private SocketChannel client = null;
    private Map<Handshake.Method, Consumer<Payload>> functions = null;
    private boolean listening = false;
    public GameObject getGameObject(int uniqueID) {
        return gameObjects.get(uniqueID);
    }
    public GameObject setGameObject(GameObject obj) {
        return gameObjects.put(obj.uniqueID, obj);
    }
    public GameObject createGameObject(GameObject obj) {
        Payload payload = new Payload(Handshake.Method.createGameObject, obj);
        GameObject syncObject = null;
        try {
            Communicator.Write(client, payload);
            Payload responsePayload = blockingQueue.take();
            syncObject = responsePayload.GetData();
        }
        catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return syncObject;
    }
    public static Client getInstance() {
        return instance;
    }
    //  Singletons creation
    private static final Client instance = new Client();
    private Client() {
        Connect();
        Login();
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
    //  Connecting to server
    public void Connect() {
        try {
            //  Connect to server
            client = SocketChannel.open(new InetSocketAddress(hostname, port));
            client.configureBlocking(true);
            //  Create map for functions (attention for GET BY INDEX speed)
            functions = new TreeMap<>();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //  Stabilize connection (sometimes without received message server ignores socket's data)
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
    //  Add function listener for functions coming from server
    //  Listen() should be last function. Therefore, On() throws exception, if you are adding listener after Client listening
    public void On(Handshake.Method invoker, Consumer<Payload> function) throws Exception {
        if(!listening) functions.put(invoker, function);
        else throw new Exception("Adding listener after Listen() was invoked!");
    }
    //  worker threads function for listening for server and invoking functions
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
    //  Launching new thread for listening servers notifications
    public void Listen() {
        listening = true;
        listener = new Thread(this::_listen);
        listener.start();
    }
    //  Invoke function for all game objects
    public void foreach(Consumer<? super GameObject> function) {
        gameObjects.values().forEach(function);
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