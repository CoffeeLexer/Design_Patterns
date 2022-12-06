package client.utilities.interpreter;

import client.utilities.chain.*;
import client.utilities.visitor.IElement;
import client.utilities.visitor.Visitor;

import java.util.Arrays;

public class ArgumentExpression extends Expression implements IElement {

    @Override
    public void Interpret(Context ctx) {
        var args = ctx.data.split(" ");
        args = Arrays.stream(args).filter(s -> !s.isEmpty() && !s.isBlank()).toArray(String[]::new);
        if(args.length == 0) {
            error = "Empty Command!";
            ctx.criticalError = true;
            ctx.failed = true;
        }
        else if(args.length > 3) {
            error = "Too much arguments!";
            ctx.criticalError = true;
            ctx.failed = true;
        }
        else ctx.args = args;
    }
    @Override
    public void Accept(Visitor visitor) {
        visitor.VisitArgument(this);
    }
}
