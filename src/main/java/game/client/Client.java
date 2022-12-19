package game.client;

import game.data.Payload;
import game.protocol.TCP;
import game.protocol.UDP;

import javax.swing.*;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client extends JFrame {
    // Start the Client
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(Client::new);
    }

    public Client() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create new TCP Client socket
        TCP.Client serverClient;
        try {
            serverClient = new TCP.Client(new Socket("localhost", 8080));
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // bind UDP
        UDP.Receiver udp = new UDP.Receiver();
        UDP.Identifier identifier = udp.getIdentifier();
        serverClient.send(new Payload(Payload.Method.bindUDP, identifier));

        // Create panels where objects are rendered
        StaticPanel staticPanel = new StaticPanel();
        DynamicPanel dynamicPanel = new DynamicPanel();

        // Start Client engine
        Engine engine = new Engine(staticPanel, dynamicPanel);
        engine.start();

        // Layered pane allows Static and Dynamic panels to overlap
        JLayeredPane layeredPane = new JLayeredPane();

        // Add keyboard listeners
        var keyboardListener = new Controller.Keyboard(serverClient);
        layeredPane.addKeyListener(keyboardListener);

        // Add mouse listeners (also allow "focus" event with mouse)
        var mouseListener = new Controller.Mouse(serverClient);
        layeredPane.addMouseListener(mouseListener);
        layeredPane.setFocusable(true);

        // Add Static and Dynamic panels to the layered pane
        layeredPane.add(staticPanel, 1);
        layeredPane.add(dynamicPanel, 0);

        // Start listening to TCP
        serverClient.autonomousListen((self, payload) -> {
            switch (payload.method) {
                case setObject -> engine.setObject(payload.getData());
                case removeObject -> engine.removeObject(payload.getData());
                default -> System.out.printf("Uncaught - %s\n", payload.method.toString());
            }
        });

        // Synchronize all Static objects
        serverClient.send(new Payload(Payload.Method.syncEngine));

        // Start listening to UDp
        udp.autonomousListen((payload) -> {
            switch (payload.method) {
                case setObject -> engine.setObject(payload.getData());
                case removeObject -> engine.removeObject(payload.getData());
                default -> System.out.printf("Uncaught - %s\n", payload.method.toString());
            }
        });

        // Finally create the window where everything will be displayed for the Client
        add(layeredPane);
        setSize(1600, 900);
        setVisible(true);
        setLocationRelativeTo(null);
    }
}
