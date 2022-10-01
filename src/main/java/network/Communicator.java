package network;

import org.apache.commons.lang3.SerializationUtils;

import java.io.IOException;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.TimeUnit;

public class Communicator {
    public static <T extends Serializable> T Read(SocketChannel client) throws IOException, InterruptedException {
        //  Allocate 1xINT compatible buffer (INT is datatype of array size)
        ByteBuffer buffer = ByteBuffer.allocate(4);

        int size = client.read(buffer);
        //  Closed connection from Client-Side. Needs closing from server side
        if(size < 1) {
            client.close();
            buffer.clear();
            throw new IOException("Client Disconnected while sending data!");
        }
        //  Setting upcoming objects size
        int objectSize = buffer.getInt(0);
        buffer.clear();
        //  Reading object
        buffer = ByteBuffer.allocate(objectSize);
        client.read(buffer);
        TimeUnit.MILLISECONDS.sleep(5);
        T obj = SerializationUtils.deserialize(buffer.array());


        buffer.clear();

        return obj;
    }

    public static <T extends Serializable> void Write(SocketChannel client, T obj) throws IOException {
        byte[] bytes = SerializationUtils.serialize(obj);
        //  Send size of object
        ByteBuffer bb = ByteBuffer.allocate(4).putInt(bytes.length).flip();
        client.write(bb);
        bb.clear();
        //  Send object
        bb = ByteBuffer.allocate(bytes.length).put(bytes).flip();
        client.write(bb);
        bb.clear();
    }
}
