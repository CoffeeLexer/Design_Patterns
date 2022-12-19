package game.server;

import game.data.GameObject;
import game.data.GameObject_Example;
import game.data.Vector;
import game.protocol.TCP;
import game.protocol.UDP;
import java.util.concurrent.TimeUnit; // import is used for commented code

public class Server {
    // Start the Server
    public static void main(String[] args) {
        // Create UDP sender to send data through UDP protocol
        UDP.Sender udp = new UDP.Sender();
        // Create server engine to run game
        Engine engine = new Engine();
        // Create TCP listeners. Used for users to login and work with data
        TCP.ConnectionListener clientListener = new TCP.ConnectionListener();

        // Create TCP listener remapped callback
        clientListener.setDefaultAutonomousResponse((receiver, payload) -> {
            ResponseToClient.Respond(receiver, payload, engine);
        });

        clientListener.setAttachmentBinder((attachment) -> {
            attachment.bindToEngine(engine);
        });

        // Start server with TCP and UDP bindings
        engine.start(clientListener, udp);

        // Start listening to client TCP protocols
        clientListener.listen();

        //######################################################
        // Example of object manipulation with the server
        //######################################################

        // Static objects are updated only once they are used in:
        // Engine::createObject()
        // Engine::setObject()
        // Engine::destroyObject()
        GameObject obj_0 = new GameObject();
        obj_0.position = new Vector(400, 200);
        obj_0.tag = GameObject.Tag.Static;
        obj_0.texture = "images/wall.jpg";

        // Dynamic objects are updated every frame.
        // * If a packet is lost, client will have the object out of sync.
        //   This is the reason why we send dynamic objects every frame.
        // * If a packet is lost, next frame we will get new one
        var obj_1 = new GameObject_Example();
        obj_1.position = new Vector(200, 200);
        obj_1.rotation = Math.toRadians(25);
        obj_1.tag = GameObject.Tag.Dynamic;
        obj_1.texture = "images/thebible2.jpg";

        engine.createObject(obj_0);
        engine.createObject(obj_1);

        // example of creating an object and removing it once very second
        /*
        while(true) {
            try {
                engine.createObject(obj_0);
                TimeUnit.SECONDS.sleep(1);
                engine.removeObject(obj_0);
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        */

        // Wait for engine to stop working. In order words, the project will work till server engine stops.
        engine.join();
    }
}
