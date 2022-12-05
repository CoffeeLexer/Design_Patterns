package client.utilities.chain;

import client.utilities.interpreter.Context;

public class ListPlayersHandler extends Handler<Context> {
    @Override
    public boolean work(Context request) {
        if(request.args[0].toLowerCase().equals("listplayers")) {
            request.method = Context.Method.listPlayers;
            return true;
        }
        return false;
    }
}
