package deprecated;

import org.apache.commons.lang3.SerializationUtils;

import java.io.IOException;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class Communicator {
    public static <T extends Serializable> T Read(SocketChannel client) throws IOException, InterruptedException {
        //  Allocate 1xINT compatible buffer (INT is datatype of array size)
        ByteBuffer buffer = ByteBuffer.allocate(4);
        //  Setting upcoming objects size
        buffer.rewind();
        int size = client.read(buffer);
        buffer.flip();
        int objectSize = buffer.getInt(0);
        buffer.clear();
        //  Closed connection from Client-Side. Needs closing from server side
        if(size < 1 && objectSize == 0) {
            client.close();
            buffer.clear();
            throw new IOException("Client Disconnected while sending data!");
        }
        buffer = ByteBuffer.allocate(objectSize);
        client.read(buffer);
        T obj = SerializationUtils.deserialize(buffer.array());
        buffer.clear();
        return obj;
    }

    public static <T extends Serializable> void Write(SocketChannel client, T obj) throws IOException {
        byte[] bytes = SerializationUtils.serialize(obj);
        //  Send size of object, then object
        ByteBuffer bb = ByteBuffer.allocate(4 + bytes.length);
        //  Push size of object
        bb.putInt(bytes.length);
        //  Push object
        bb.put(bytes);
        bb.flip();
        //  Send object
        //client.write(bb);

        while(bb.remaining() > 0) {
            int written = client.write(bb);
        }
        bb.compact();
    }
}
