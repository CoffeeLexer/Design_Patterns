package game.client;

import game.data.Payload;
import game.protocol.TCP;
import game.protocol.UDP;
import game.server.Engine;

// Describes what the Client should do when it receives a message from the server
public class ResponseToServer {
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
            default -> {
                System.out.printf("%s - %s - not implemented, BUT request received!\n", client.identify(), payload.method.toString());
            }
        }
    }
}
