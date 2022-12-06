package client.utilities.interpreter;

import client.utilities.visitor.Visitor;

public class ValueExpression extends Expression {
    @Override
    public void Interpret(Context ctx) {
        if(ctx.valueIsRequired && !ctx.criticalError) {
            if(ctx.index >= ctx.args.length) {
                error = "Value not provided!";
            }
            else {
                ctx.value = ctx.args[ctx.index];
                ctx.index++;
            }
        }
    }
    @Override
    public void Accept(Visitor visitor) {
        visitor.VisitValue(this);
    }
}
