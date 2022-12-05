package client.utilities.chain;

import client.utilities.interpreter.Context;

public class HelpHandler extends Handler<Context> {
    @Override
    public boolean work(Context request) {
        if(request.args[0].toLowerCase().equals("help")) {
            request.isLocal = true;
            request.method = Context.Method.help;
            request.result = "Help:\n\tdestroyObject <id>";
            request.result += "\n\thelp";
            request.result += "\n\tkick <player>";
            request.result += "\n\tkill <player>";
            request.result += "\n\tlistObjects";
            request.result += "\n\tlistPlayers";
            request.result += "\n\tsetHealth <player> <value>";
            return true;
        }
        return false;
    }
}
