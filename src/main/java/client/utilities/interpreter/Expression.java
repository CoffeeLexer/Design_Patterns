package client.utilities.interpreter;

import client.utilities.visitor.IElement;

public abstract class Expression implements IElement {
    public String error = "";
    public abstract void Interpret(Context context);
}
