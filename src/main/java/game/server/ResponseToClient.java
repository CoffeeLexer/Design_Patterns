package game.server;

import game.data.Payload;
import game.protocol.TCP;
import game.protocol.UDP;

public class ResponseToClient {
    public static void Respond(TCP.Client client, Payload payload, Engine engine) {
        switch (payload.method) {
            case bindUDP -> {
                UDP.Identifier identifier = payload.getData();
                client.bindUDP(identifier.address, identifier.port);
                System.out.printf("%s - UDP bound to %s\n", client.identify(), identifier.toString());
            }
            case syncEngine -> {
                engine.synchronizeStaticObjects(client);
            }
            case keyTyped -> ResponseToInput.Keyboard.Typed(client, payload.getData(), engine);
            case keyPressed -> ResponseToInput.Keyboard.Pressed(client, payload.getData(), engine);
            case keyReleased -> ResponseToInput.Keyboard.Released(client, payload.getData(), engine);

            case mousePressed -> ResponseToInput.Mouse.Pressed(client, payload.getData(), engine);
            case mouseReleased -> ResponseToInput.Mouse.Released(client, payload.getData(), engine);
            case mouseClicked -> ResponseToInput.Mouse.Clicked(client, payload.getData(), engine);
            case mouseEntered -> ResponseToInput.Mouse.Entered(client, payload.getData(), engine);
            case mouseExited -> ResponseToInput.Mouse.Exited(client, payload.getData(), engine);
            default -> {
                System.out.printf("%s - %s - not implemented, BUT request received!\n", client.identify(), payload.method.toString());
            }
        }
    }
}
