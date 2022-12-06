package client.utilities.interpreter;

import client.utilities.chain.*;
import client.utilities.visitor.IElement;
import client.utilities.visitor.Visitor;

public class CommandExpression extends Expression implements IElement {

    public Handler<Context> root = null;
    @Override
    public void Interpret(Context ctx) {
        if(!ctx.criticalError) root.handle(ctx);
    }
    @Override
    public void Accept(Visitor visitor) {
        visitor.VisitCommand(this);
    }
}
