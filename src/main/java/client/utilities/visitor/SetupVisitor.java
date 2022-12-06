package client.utilities.visitor;

import client.utilities.chain.*;
import client.utilities.interpreter.*;

public class SetupVisitor extends Visitor {
    public  void VisitArgument(ArgumentExpression argument) {

    }
    public  void VisitCommand(CommandExpression command) {
        command.root = new DestroyObjectHandler();
        var helpHandler = new HelpHandler();
        var kickHandler = new KickHandler();
        var killHandler = new KillHandler();
        var listObjectHandler = new ListObjectsHandler();
        var listPlayersHandler = new ListPlayersHandler();
        var setHealthHandler = new SetHealthHandler();
        var notFoundHandler = new NotFoundHandler(command);

        command.root.setSuccessor(helpHandler);
        helpHandler.setSuccessor(kickHandler);
        kickHandler.setSuccessor(killHandler);
        killHandler.setSuccessor(listObjectHandler);
        listObjectHandler.setSuccessor(listPlayersHandler);
        listPlayersHandler.setSuccessor(setHealthHandler);
        setHealthHandler.setSuccessor(notFoundHandler);
    }
    public  void VisitPlayer(PlayerExpression player) {

    }
    public  void VisitServer(ServerExpression server) {

    }
    public  void VisitValue(ValueExpression value) {

    }
}
