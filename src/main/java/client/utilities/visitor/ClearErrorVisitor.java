package client.utilities.visitor;

import client.utilities.interpreter.*;

public class ClearErrorVisitor extends Visitor {
    public  void VisitArgument(ArgumentExpression argument) {
        argument.error = "";
    }
    public  void VisitCommand(CommandExpression command) {
        command.error = "";
    }
    public  void VisitPlayer(PlayerExpression player) {
        player.error = "";
    }
    public  void VisitServer(ServerExpression server) {
        server.error = "";
    }
    public  void VisitValue(ValueExpression value) {
        value.error = "";
    }
}
