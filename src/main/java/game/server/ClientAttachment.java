package game.server;

import game.data.GameObject;
import game.data.Vector;

// Structure to bind clients data
// Player, keyboard state, mouse state, points etc.
public class ClientAttachment {
    public boolean amSleeping;
    public GameObject playerExample;


    // Default values setup
    public ClientAttachment() {
        amSleeping = false;
        playerExample = new GameObject();
        playerExample.pivot = new Vector(0.5f, 0.5f);
        playerExample.dimensions = new Vector(75, 50);
        playerExample.texture = "images/tank-blue.png";
        playerExample.tag = GameObject.Tag.Dynamic;
        playerExample.rotation = 25/3.14;
        playerExample.position = new Vector(500, 500);
    }

    public void bindToEngine(Engine engine) {
        engine.createObject(playerExample);
    }
}
