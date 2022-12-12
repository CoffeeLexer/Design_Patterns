package client.components;

import network.client.PlayerClient;

public class ClientReference extends GameComponent {

    public PlayerClient client = null;

    @Override
    public String key() {
        return Key();
    }
    public static String Key() {
        return "ClientReference";
    }

    @Override
    public void destroy() {
        super.destroy();
        if(client != null)
            ((ClientReference)gameObject.getComponent(ClientReference.Key())).client.setId(-1);
    }

    @Override
    public Prototype cloneShallow() {
        return new ClientReference();
    }

    @Override
    public Prototype cloneDeep() {
        return new ClientReference();
    }
}
