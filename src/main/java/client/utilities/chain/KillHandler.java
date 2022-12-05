package client.utilities.chain;

import client.utilities.interpreter.Context;

public class KillHandler extends Handler<Context> {
    @Override
    public boolean work(Context request) {
        if(request.args[0].toLowerCase().equals("kill")) {
            request.playerIsRequired = true;
            request.method = Context.Method.kill;
            return true;
        }
        return false;
    }
}
