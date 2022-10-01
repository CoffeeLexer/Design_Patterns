package network;

import network.data.Handshake;
import network.data.Message;
import network.data.Payload;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class Client {
    private SocketChannel client = null;
    private Map<Handshake.Method, Consumer<Void>> functions = null;
    private boolean listening = false;

    public Client() {
        try {
            client = SocketChannel.open(new InetSocketAddress("localhost", 8080));
            client.configureBlocking(true);
            functions = new TreeMap<>();
            Login();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void On(Handshake.Method invoker, Consumer<Void> function) throws Exception {
        if(!listening) functions.put(invoker, function);
        else throw new Exception("Adding listener after Listen() was invoked!");
    }

    private void _listen() {
        System.out.println("Listening");
        while (true) {
            try {
                Payload payload = Communicator.Read(client);
                if(functions.containsKey(payload.method))
                    functions.get(payload.method).accept(null);
            }
            catch (Exception e) {
                if(e.getMessage().equalsIgnoreCase("connection reset")) {
                    System.out.println(e.getMessage());
                    return;
                }
                e.printStackTrace();
            }
        }
    }
    public void Listen() {
        listening = true;
        new Thread(this::_listen).start();
    }
    private void Login() {
        Payload payload = new Payload(Handshake.Method.login, null);
        try {
            Communicator.Write(client, payload);
            client.finishConnect();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Client c1 = new Client();
        Client c2 = new Client();

        try {
            c1.On(Handshake.Method.ping, (Void) -> {
                System.out.println("OI Someone pinged me!!!");
                try {
                    Message m = new Message();
                    m.author = "Joe";
                    m.content = "Stop pinging me server!";
                    Payload payload = new Payload(Handshake.Method.message, m);
                    Communicator.Write(c1.client, payload);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            c2.On(Handshake.Method.ping, (Void) -> {
                System.out.println("F*ck you Pingger!!!");
            });

//            TimeUnit.SECONDS.sleep(2);
            c1.Listen();
            c2.Listen();
//            TimeUnit.SECONDS.sleep(2);

            Payload payload = new Payload(Handshake.Method.ping);
            Communicator.Write(c1.client, payload);
            TimeUnit.MINUTES.sleep(5);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}