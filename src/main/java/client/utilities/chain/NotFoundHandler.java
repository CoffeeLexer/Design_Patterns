package client.utilities.chain;

import client.utilities.interpreter.Context;

public class NotFoundHandler extends Handler<Context> {
    @Override
    public boolean work(Context request) {
        request.error = "Command not found!";
        return true;
    }
}
