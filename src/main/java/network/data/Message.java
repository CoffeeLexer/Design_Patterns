package network.data;

import java.io.Serializable;

public class Message implements Serializable {
    public String author;
    public String content;
    public Message() {
        this(null, null);
    }
    public Message(String author, String content) {
        this.author = author;
        this.content = content;
    }
}
