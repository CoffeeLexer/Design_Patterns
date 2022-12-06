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
            var res = (Response) Client.GetInstance().InvokeWithResponse(new Payload(Handshake.Method.interpreter, ctx));
            ctx.result = res.getResult();
            error = res.getError();
        }
    }
    @Override
    public void Accept(Visitor visitor) {
        visitor.VisitServer(this);
    }
    public interface Response {
        void setResult(String s);
        void setError(String s);
        String getResult();
        String getError();
    }
    public static class RealResponse implements Serializable, Response {
        String result = "";
        String error = "";
        @Override
        public void setResult(String result) {
            this.result = result;
        }
        @Override
        public void setError(String error) {
            this.error = error;
        }
        @Override
        public String getError() {
            return error;
        }
        @Override
        public String getResult() {
            return result;
        }
    }
    public static class Proxy implements Serializable, Response {
        RealResponse faked = null;
        RealResponse real = null;
        public Proxy() {
        }
        private RealResponse getFaked() {
            if(faked == null) {
                faked = new RealResponse();
            }
            return faked;
        }
        @Override
        public void setError(String s) {
            if(real == null) {
                var f = getFaked();
                f.error = s;
            }
            else {
                real.error = s;
            }
        }

        @Override
        public void setResult(String s) {
            if(real == null) {
                var f = getFaked();
                f.result = s;
            }
            else {
                real.result = s;
            }
        }

        @Override
        public synchronized String getError() {
            if(real == null) {
                if(faked == null) {
                    try {
                        wait();
                    }
                    catch (Exception ignored) {}
                    return real.error;
                }
                else {
                    return faked.error;
                }
            }
            else {
                return real.error;
            }
        }

        @Override
        public synchronized String getResult() {
            if(real == null) {
                if(faked == null) {
                    try {
                        wait();
                    }
                    catch (Exception ignored) {}
                    return real.result;
                }
                else {
                    return faked.result;
                }
            }
            else {
                return real.result;
            }
        }
        public synchronized void setReal(RealResponse res) {
            real = res;
            notify();
        }
    }
}
