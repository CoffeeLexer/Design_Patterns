package client.utilities.interpreter;

import client.utilities.chain.*;

import java.util.Arrays;

public class ArgumentExpression extends Expression {
    @Override
    public void Interpret(Context ctx) {
        var args = ctx.data.split(" ");
        args = Arrays.stream(args).filter(s -> !s.isEmpty() && !s.isBlank()).toArray(String[]::new);
        if(args.length == 0) ctx.error = "Empty Command!";
        else if(args.length > 3) ctx.error = "Too much arguments!";
        else ctx.args = args;
    }
}
