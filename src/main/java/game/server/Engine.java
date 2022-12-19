package game.server;

import game.data.GameObject;
import game.data.Payload;
import game.protocol.TCP;
import game.protocol.UDP;

import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class Engine implements Runnable {

    // Framerate cap
    public static final int frameRate = 60;
    // Engine state
    private boolean isRunning;
    // Structure to store game objects
    private Map<Integer, game.data.GameObject> objects;
    // Lock for data race situations
    private ReentrantLock lock;
    // Engines thread reference
    private Thread thread;
    private TCP.ConnectionListener clientListeners;
    private UDP.Sender udpSender;

    public Engine() {
        isRunning = false;
        objects = new ConcurrentSkipListMap<>();
        lock = new ReentrantLock();
    }

    public int createObject(GameObject object) {
        lock.lock();

        int id = findAvailableId();
        object.id = id;
        objects.put(id, object);

        if(object.tag.equals(GameObject.Tag.Static))
            clientListeners.notifyAll(new Payload(Payload.Method.setObject, object));

        lock.unlock();
        return id;
    }

    public void setObject(GameObject object) {
        if(object.id == -1) throw new RuntimeException("setObject() called without createObject() call beforehand.\n" +
                "createObject() binds object to available id in engine!");
        lock.lock();

        objects.put(object.id, object);

        if(object.tag.equals(GameObject.Tag.Static))
            clientListeners.notifyAll(new Payload(Payload.Method.setObject, object));

        lock.unlock();
    }

    public void removeObject(GameObject object) {
        lock.lock();

        objects.remove(object.id);
        clientListeners.notifyAll(new Payload(Payload.Method.removeObject, object));

        lock.unlock();
    }

    private int findAvailableId() {
        // Tightly packed check
        if(objects.containsKey(objects.size()))
            return objects.size();
        // Find first hole
        int i = 0;
        for (var k: objects.keySet()) {
            if(i != k) break;
            i++;
        }
        return i;
    }
    public void synchronizeStaticObjects(TCP.Client client) {
        for (var obj: objects.values()) {
            if(obj.tag.equals(GameObject.Tag.Static)) {
                client.send(new Payload(Payload.Method.setObject, obj));
            }
        }
    }
    public void start(TCP.ConnectionListener clientListeners, UDP.Sender udp) {
        this.clientListeners = clientListeners;
        this.udpSender = udp;
        isRunning = true;
        thread = new Thread(this);
        thread.start();
    }

    public void stop(boolean waitTillEngineDown) {
        isRunning = false;
        if(waitTillEngineDown) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void join() {
        try {
            thread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        System.out.println("Engine loop started!");
        long current, previous, delta, delay;
        float frameDelay = (float)Math.ceil(1000.0f / frameRate);
        current = System.currentTimeMillis();
        while (isRunning) {
            previous = current;
            current = System.currentTimeMillis();
            delta = current - previous;

            lock.lock();
            long finalDelta = delta;
            objects.values().forEach(object -> {
                if(object.tag != game.data.GameObject.Tag.Dynamic) return;
                object.update(frameDelay / finalDelta);
            });
            lock.unlock();
            objects.values().forEach(object -> {
                if(object.tag != game.data.GameObject.Tag.Dynamic) return;
                Payload payload = new Payload(Payload.Method.setObject, object);
                clientListeners.notifyAll(udpSender, payload);
            });
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
