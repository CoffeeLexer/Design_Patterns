package network.observer;

public class ObserverClient extends Observer {
    private static int NextID = Integer.MIN_VALUE;
    private int id = Integer.MIN_VALUE;;
    public ObserverClient() {
        id = NextID++;
    }
    @Override
    void Update() {

    }
}
