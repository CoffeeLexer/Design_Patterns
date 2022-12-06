package client.utilities.interpreter;

import java.io.Serializable;

public class Context implements Serializable {
    public enum Method {
        destroyObject,
        help,
        kick,
        kill,
        listObjects,
        listPlayers,
        setHealth
    }
    public Method method;
    public boolean failed = false;
    public boolean criticalError = false;
    public String data = "";
    public String result = "";
    public String[] args;
    public int index = 1;
    public boolean playerIsRequired = false;
    public boolean valueIsRequired = false;
    public boolean isLocal = false;
    public String player = "";
    public String value = "";
}
