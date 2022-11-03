package network.server;

import client.components.Collider;
import client.gameObjects.GameObject;
import client.gameObjects.Tag;
import client.gameObjects.Wall;
import network.data.Connection;
import network.data.Handshake;
import network.data.Payload;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

//  Server Engine
public class SEngine {
    // TODO: Lock for add/sync/remove
    public static final int frameRate = 60;
    Map<Integer, GameObject> gameObjects = null;
    ReentrantLock lock = null;
    private static SEngine instance = new SEngine();
    public static SEngine GetInstance() {
        return instance;
    }
    private SEngine()
    {
        //gameObjects = new TreeMap<>();
        gameObjects = new ConcurrentSkipListMap<>();
        lock = new ReentrantLock();
        new Thread(this::Run).start();

        for(int i = 0; i < 10; i++) {
            for(int j = 0; j < 10; j++) {
                if(i % 9 == 0 || j % 9 == 0)
                    Add(new Wall("images/wall.jpg", 50*i, 50*j));
            }
        }

    }
    public int Add(GameObject obj) {
        lock.lock();
        int id = AvailableID(gameObjects);
        obj.uniqueID = id;
        gameObjects.put(id, obj);
        lock.unlock();
        return id;
    }
    public GameObject Get(int index) {
        return gameObjects.get(index);
    }
    public void Set(GameObject obj) {
        lock.lock();
        gameObjects.put(obj.uniqueID, obj);
        lock.unlock();
    }
    public void Destroy(GameObject gameObject) {
        lock.lock();
        gameObject.destroy();
        Server.GetInstance().NotifyTCP(new Payload(Handshake.Method.removeGameObject, gameObject.uniqueID));
        gameObjects.remove(gameObject.uniqueID);
        lock.unlock();
    }
    public void Destroy(int id) {
        lock.lock();
        GameObject gameObject = gameObjects.get(id);
        if(gameObject == null) return;
        gameObject.destroy();
        Server.GetInstance().NotifyTCP(new Payload(Handshake.Method.removeGameObject, gameObject.uniqueID));
        gameObjects.remove(gameObject.uniqueID);
        lock.unlock();
    }
    public static int AvailableID(Map<Integer, GameObject> map) {
        for(int i = 0;; i++)
            if(!map.containsKey(i))
                return i;
    }
    public void Info() {
        for (var e: gameObjects.values()) {
            System.out.printf("Object %s: %s\n", e.uniqueID, e.getClass().getName());
        }
        System.out.print('\n');
    }
    public void SyncEngine(Connection connection) {
        List<GameObject> list = new ArrayList<>(gameObjects.size());
        for (GameObject obj: gameObjects.values()) {
            list.add(obj.ClientParse());
        }
        connection.lock.lock();
        try {
            connection.output.writeObject(new Payload(Handshake.Method.syncEngine, list));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        connection.lock.unlock();
    }

    private void Run()
    {
        long current, previous, delta, delay;
        float frameDelay = (float)Math.ceil(1000.0f / frameRate);
        boolean active = true;
        current = System.currentTimeMillis();
        while (active) {
            previous = current;
            current = System.currentTimeMillis();
            delta = current - previous;

            lock.lock();
            for (var obj: gameObjects.values())
            {
                if(obj.tag != Tag.Dynamic) continue;
                obj.update(frameDelay / delta);
            }
            for (var obj: gameObjects.values())
            {
                if(obj.tag != Tag.Dynamic) continue;
                GameObject parsed = obj.ClientParse();
                Payload payload = new Payload(Handshake.Method.setGameObject, parsed);
                Server.GetInstance().NotifyUDP(payload);
            }
            ArrayList<GameObject> list = new ArrayList<>(gameObjects.size());
            list.addAll(gameObjects.values());
            for(int i = 0; i < list.size(); i++) {
                GameObject obj = list.get(i);
                if(obj == null) continue;
                Collider collider = obj.getComponent(Collider.Key());
                if(collider == null) continue;
                for(int j = i + 1; j < list.size(); j++) {
                    GameObject objOther = list.get(j);
                    if(objOther == null) continue;
                    Collider colliderOther = objOther.getComponent(Collider.Key());
                    if(colliderOther == null) continue;
                    if(collider.isColliding(colliderOther) && colliderOther.isColliding(collider)) {
                        collider.onCollision(objOther);
                        colliderOther.onCollision(obj);
                        collider.isColliding(colliderOther);
                        colliderOther.isColliding(collider);
                    }
                }
            }
            lock.unlock();
            try
            {
                delay = (long)frameDelay - (System.currentTimeMillis() - current);
                if(delay > 0)
                {
                    TimeUnit.MILLISECONDS.sleep(delay);
                }
            }
            catch (InterruptedException e)
            {
                throw new RuntimeException(e);
            }
        }
    }
}
