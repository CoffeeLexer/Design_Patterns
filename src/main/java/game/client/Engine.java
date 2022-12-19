package game.client;

import game.data.GameObject;

import java.awt.*;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class Engine {
    // Framerate cap
    public static final int frameRate = 60;
    // Engine state
    private boolean isRunning;
    private Thread thread;
    // Structure to store game objects
    private final Map<Integer, game.data.GameObject> objects;
    // Lock for data race situations
    private final ReentrantLock lock;
    private final StaticPanel staticPanel;
    private DynamicPanel dynamicPanel;

    public Engine(StaticPanel staticPanel, DynamicPanel dynamicPanel) {
        this.staticPanel = staticPanel;
        this.dynamicPanel = dynamicPanel;
        this.staticPanel.bind(this);
        this.dynamicPanel.bind(this);
        objects = new ConcurrentSkipListMap<>();
        lock = new ReentrantLock();
    }

    public void render(Graphics2D g2d, GameObject.Tag tag) {
        lock.lock();
        for (var object: objects.values()) {
            if(object.tag == tag)
                object.render(g2d);
        }
        lock.unlock();
    }

    public void setObject(GameObject object) {
        lock.lock();

        objects.put(object.id, object);

        if(object.tag.equals(GameObject.Tag.Static)) {
            staticPanel.repaint();
        }

        lock.unlock();
    }

    public void removeObject(GameObject object) {
        lock.lock();

        // TODO: fast fix, no sync error
        if(objects.containsKey(object.id)) {

            objects.remove(object.id);
            if(object.tag.equals(GameObject.Tag.Static)) {
                staticPanel.repaint();
            }
        }

        lock.unlock();
    }
    public void start() {
        isRunning = true;
        thread = new Thread(this::run);
        thread.start();
    }
    public void run() {
        System.out.println("Engine loop started!");
        long current, delay;
        float frameDelay = (float)Math.ceil(1000.0f / frameRate);
        current = System.currentTimeMillis();
        while (isRunning) {
            current = System.currentTimeMillis();

            dynamicPanel.repaint();

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
