package client.utilities.visitor;

import client.utilities.interpreter.*;

public class CollectErrorVisitor extends Visitor {
    String error;
    public CollectErrorVisitor() {
        error = "";
    }
    public String getTotalError() {
        // Trim last trailing '\n'
        return error.equals("") ? "" : error.substring(0, error.length() - 1);
    }
    public  void VisitArgument(ArgumentExpression argument) {

    }
    public  void VisitCommand(CommandExpression command) {
        if(!command.error.equals("")) {
            error += "CRITICAL ERROR: ";
            error += command.error;
            error += '\n';
        }
    }
    public  void VisitPlayer(PlayerExpression player) {
        if(!player.error.equals("")) {
            error += "ARGUMENT ERROR: ";
            error += player.error;
            error += '\n';
        }
    }
    public  void VisitServer(ServerExpression server) {
        if(!server.error.equals("")) {
            error += "SERVER ERROR: ";
            error += server.error;
            error += '\n';
        }
    }
    public  void VisitValue(ValueExpression value) {
        if(!value.error.equals("")) {
            error += "ARGUMENT ERROR: ";
            error += value.error;
            error += '\n';
        }
    }
}
