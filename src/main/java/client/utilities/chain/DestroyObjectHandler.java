package client.utilities.chain;

import client.utilities.interpreter.Context;

public class DestroyObjectHandler extends Handler<Context> {
    @Override
    public boolean work(Context request) {
        if(request.args[0].equals("destroyobject")) {
            request.valueIsRequired = true;
            request.method = Context.Method.destroyObject;
            return true;
        }
        return false;
    }
}
