package client.utilities.interpreter;

public class ValueExpression extends Expression {
    @Override
    public void Interpret(Context ctx) {
        if(ctx.valueIsRequired && ctx.error.equals("")) {
            if(ctx.index >= ctx.args.length) {
                ctx.error = "Value not provided!";
            }
            else {
                ctx.value = ctx.args[ctx.index];
                ctx.index++;
            }
        }
    }
}
