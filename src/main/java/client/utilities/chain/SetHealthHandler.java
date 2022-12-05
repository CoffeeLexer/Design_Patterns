package client.utilities.chain;

import client.utilities.interpreter.Context;

public class SetHealthHandler extends Handler<Context> {
    @Override
    public boolean work(Context request) {
        if(request.args[0].toLowerCase().equals("sethealth")) {
            request.playerIsRequired = true;
            request.valueIsRequired = true;
            request.method = Context.Method.setHealth;
            return true;
        }
        return false;
    }
}
