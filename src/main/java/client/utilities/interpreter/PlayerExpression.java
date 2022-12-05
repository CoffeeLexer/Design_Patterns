package client.utilities.interpreter;

import client.utilities.chain.*;

public class PlayerExpression extends Expression {
    @Override
    public void Interpret(Context ctx) {
        if(ctx.playerIsRequired && ctx.error.equals("")) {
            if(ctx.index >= ctx.args.length) {
                ctx.error = "Player not provided!";
            }
            else {
                ctx.player = ctx.args[ctx.index];
                ctx.index++;
            }
        }
    }
}
