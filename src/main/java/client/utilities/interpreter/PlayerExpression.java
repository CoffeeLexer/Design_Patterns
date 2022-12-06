package client.utilities.interpreter;

import client.utilities.chain.*;
import client.utilities.visitor.IElement;
import client.utilities.visitor.Visitor;

public class PlayerExpression extends Expression implements IElement {
    @Override
    public void Interpret(Context ctx) {
        if(ctx.playerIsRequired && !ctx.criticalError) {
            if(ctx.index >= ctx.args.length) {
                error = "Player not provided!";
                ctx.failed = true;
            }
            else {
                ctx.player = ctx.args[ctx.index];
                ctx.index++;
            }
        }
    }
    @Override
    public void Accept(Visitor visitor) {
        visitor.VisitPlayer(this);
    }
}
