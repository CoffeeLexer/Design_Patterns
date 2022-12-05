package client.utilities.interpreter;

import client.utilities.chain.*;
import network.client.Client;
import network.data.Handshake;
import network.data.Payload;

public class ServerExpression extends Expression {
    @Override
    public void Interpret(Context ctx) {
        if(!ctx.isLocal && ctx.error.equals("")) {
            var res = (Context) Client.GetInstance().InvokeWithResponse(new Payload(Handshake.Method.interpreter, ctx)).GetData();
            ctx.result = res.result;
            ctx.error = res.error;
        }
    }
}
