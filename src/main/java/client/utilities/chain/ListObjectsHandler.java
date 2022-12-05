package client.utilities.chain;

import client.utilities.interpreter.Context;

public class ListObjectsHandler extends Handler<Context> {
    @Override
    public boolean work(Context request) {
        if(request.args[0].toLowerCase().equals("listobjects")) {
            request.method = Context.Method.listObjects;
            return true;
        }
        return false;
    }
}
