package client.utilities.interpreter;

import client.utilities.chain.*;
import client.utilities.visitor.Visitor;
import network.client.Client;
import network.data.Handshake;
import network.data.Payload;

import java.io.Serializable;

public class ServerExpression extends Expression {
    @Override
    public void Interpret(Context ctx) {
        if(!ctx.isLocal && !ctx.failed) {
            var res = (Response) Client.GetInstance().InvokeWithResponse(new Payload(Handshake.Method.interpreter, ctx)).GetData();
            ctx.result = res.result;
            error = res.error;
        }
    }
    @Override
    public void Accept(Visitor visitor) {
        visitor.VisitServer(this);
    }
    public static class Response implements Serializable {
        public Response(){}
        public String result = "";
        public String error = "";
    }
}
