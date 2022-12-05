package client.utilities.interpreter;

import client.utilities.chain.*;

public class CommandExpression extends Expression {

    private Handler<Context> root = null;

    public CommandExpression() {
        root = new DestroyObjectHandler();
        var helpHandler = new HelpHandler();
        var kickHandler = new KickHandler();
        var killHandler = new KillHandler();
        var listObjectHandler = new ListObjectsHandler();
        var listPlayersHandler = new ListPlayersHandler();
        var setHealthHandler = new SetHealthHandler();
        var notFoundHandler = new NotFoundHandler();

        root.setSuccessor(helpHandler);
        helpHandler.setSuccessor(kickHandler);
        kickHandler.setSuccessor(killHandler);
        killHandler.setSuccessor(listObjectHandler);
        listObjectHandler.setSuccessor(listPlayersHandler);
        listPlayersHandler.setSuccessor(setHealthHandler);
        setHealthHandler.setSuccessor(notFoundHandler);
    }

    @Override
    public void Interpret(Context ctx) {
        if(ctx.error.equals("")) root.handle(ctx);
    }
}
