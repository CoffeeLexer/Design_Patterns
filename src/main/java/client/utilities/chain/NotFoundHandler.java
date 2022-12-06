package client.utilities.chain;

import client.utilities.interpreter.CommandExpression;
import client.utilities.interpreter.Context;

public class NotFoundHandler extends Handler<Context> {
    public CommandExpression exp;
    public NotFoundHandler(CommandExpression commandExpression) {
        exp = commandExpression;
    }
    @Override
    public boolean work(Context request) {
        exp.error = "Command not found!";
        request.criticalError = true;
        request.failed = true;
        return true;
    }
}
