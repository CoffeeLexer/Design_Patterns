package client.components;

import java.util.concurrent.Callable;
import java.util.function.Consumer;

public class OnDestroy extends GameComponent {
    Consumer<Void> function = null;
    public OnDestroy(Consumer<Void> function) {
        this.function = function;
    }

    @Override
    public void destroy() {
        function.accept(null);
    }

    @Override
    public String key() {
        return Transform.Key();
    }
    public static String Key() {
        return "OnDestroy";
    }
}
