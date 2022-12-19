package example;

import game.protocol.TCP;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

public class _TCP {
    //  Example of using TCP sockets
    public static void main(String[] args) {
        var listener = new TCP.ConnectionListener();

        listener.listen();

        try {
            var client_a = new TCP.Client(new Socket("localhost", 8080));
            var client_b = new TCP.Client(new Socket("localhost", 8080));
            var client_c = new TCP.Client(new Socket("localhost", 8080));

            TimeUnit.SECONDS.sleep(1);

        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        listener.stop();
    }
}
