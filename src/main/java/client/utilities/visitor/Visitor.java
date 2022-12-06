package client.utilities.visitor;

import client.utilities.interpreter.*;

public abstract class Visitor {
    public abstract void VisitArgument(ArgumentExpression argument);
    public abstract void VisitCommand(CommandExpression command);
    public abstract void VisitPlayer(PlayerExpression player);
    public abstract void VisitServer(ServerExpression server);
    public abstract void VisitValue(ValueExpression value);
}
