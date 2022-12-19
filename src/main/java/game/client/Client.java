package game.client;

import game.data.Payload;
import game.protocol.TCP;
import game.protocol.UDP;

import javax.swing.*;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client extends JFrame {
    public Client() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        TCP.Client serverClient;
        try {
            serverClient = new TCP.Client(new Socket("localhost", 8080));
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        UDP.Receiver udp = new UDP.Receiver();

        UDP.Identifier identifier = udp.getIdentifier();
        serverClient.send(new Payload(Payload.Method.bindUDP, identifier));

        StaticPanel staticPanel = new StaticPanel();
        DynamicPanel dynamicPanel = new DynamicPanel();
        Engine engine = new Engine(staticPanel, dynamicPanel);

        engine.start();

        JLayeredPane layeredPane = new JLayeredPane();

        var keyboardListener = new Controller.Keyboard(serverClient);
        layeredPane.addKeyListener(keyboardListener);
        var mouseListener = new Controller.Mouse(serverClient);
        layeredPane.addMouseListener(mouseListener);
        layeredPane.setFocusable(true);

        layeredPane.add(staticPanel, 1);
        layeredPane.add(dynamicPanel, 0);

        serverClient.autonomousListen((self, payload) -> {
            switch (payload.method) {
                case setObject -> engine.setObject(payload.getData());
                case removeObject -> engine.removeObject(payload.getData());
                default -> System.out.printf("Uncaught - %s\n", payload.method.toString());
            }
        });

        serverClient.send(new Payload(Payload.Method.syncEngine));

        udp.autonomousListen((payload) -> {
            switch (payload.method) {
                case setObject -> engine.setObject(payload.getData());
                case removeObject -> engine.removeObject(payload.getData());
                default -> System.out.printf("Uncaught - %s\n", payload.method.toString());
            }
        });

        add(layeredPane);

        setSize(1600, 900);
        setVisible(true);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(Client::new);
    }
}
