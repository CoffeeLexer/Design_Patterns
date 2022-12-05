package client.utilities.chain;

import client.utilities.interpreter.Context;

public class KickHandler extends Handler<Context> {
    @Override
    public boolean work(Context request) {
        if(request.args[0].toLowerCase().equals("kick")) {
            request.playerIsRequired = true;
            request.method = Context.Method.kick;
            return true;
        }
        return false;
    }
}
