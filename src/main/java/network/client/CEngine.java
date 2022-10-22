package network.client;

import client.components.Renderer;
import client.gameObjects.GameObject;
import client.gameObjects.Tag;
import client.panels.DynamicPanel;
import client.panels.StaticPanel;

import java.awt.*;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.TimeUnit;

//  Client Engine
public class CEngine {
    public static final int frameRate = 60;
    public DynamicPanel dynamicPanel = null;
    public StaticPanel staticPanel = null;
    Map<Integer, GameObject> gameObjects = null;

    public final String playerTexture = "images/tank-blue.png";
    public int playerID = -1;
    boolean needStaticRedraw;
    private static final CEngine instance = new CEngine();
    public static CEngine getInstance() {
        return instance;
    }
    private CEngine() {
        dynamicPanel = new DynamicPanel();
        staticPanel = new StaticPanel();

        gameObjects = new ConcurrentSkipListMap<>();
        new Thread(this::Run).start();
    }
    public void Set(GameObject obj) {
        switch (obj.tag) {
            case Static -> {
                gameObjects.put(obj.uniqueID, obj);
                needStaticRedraw = true;
            }
            case Dynamic -> {
                if(obj.uniqueID == playerID) ((Renderer)obj.getComponent(Renderer.Key())).setTexture(playerTexture);
                gameObjects.put(obj.uniqueID, obj);
            }
        }
    }
    public void Remove(int id) {
        GameObject obj = gameObjects.remove(id);
        if(obj != null && obj.tag == Tag.Static) needStaticRedraw = true;
    }
    private void Run() {
        long current, delay;
        long frameDelay = 1000 / frameRate;
        boolean active = true;
        staticPanel.revalidate();
        dynamicPanel.revalidate();
        while (active)
        {
            current = System.currentTimeMillis();

            dynamicPanel.repaint();
            if(needStaticRedraw) {
                staticPanel.repaint();
                needStaticRedraw = false;
            }

            try {
                delay = frameDelay - (System.currentTimeMillis() - current);
                if (delay > 0) {
                    TimeUnit.MILLISECONDS.sleep(delay);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void RenderDynamic(Graphics2D g2d) {
        for(var obj: gameObjects.values()) {
            if(obj.tag == Tag.Dynamic) obj.render(g2d);
        }
    }
    public void RenderStatic(Graphics2D g2d) {
        for(var obj: gameObjects.values()) {
            if(obj.tag == Tag.Static) obj.render(g2d);
        }
    }
}
