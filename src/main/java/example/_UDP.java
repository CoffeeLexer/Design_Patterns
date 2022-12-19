package example;

import game.data.Payload;
import game.protocol.UDP;

public class _UDP {
    //  Example of using UDP sockets
    public static void main(String[] args) {
        var sender = new UDP.Sender();
        var receiver = new UDP.Receiver();

        var address = receiver.getAddress();
        var port = receiver.getPort();
        sender.send(address, port, new Payload(Payload.Method.empty));

        // If Sender.send(...) sends packet which is lost, this will cause deadlock!
        // Recommendation is to use BlockingQueue and Multi-Threading. Then you would
        // need to use /src/main/java/game/client/ResponseToServer.java to communicate
        // with the server.
        Payload p = (Payload) receiver.receive();

        System.out.println(p.method.toString());
    }
}
