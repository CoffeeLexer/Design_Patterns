package shared;

public abstract class Engine {

    protected abstract void initializeMap();
    protected abstract void initializeObjects();
    public abstract void connectPlayer();


    public final void startGame() {
        initializeMap();
        initializeObjects();
    }

}
