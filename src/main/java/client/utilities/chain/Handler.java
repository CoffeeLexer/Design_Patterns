package client.utilities.chain;

public abstract class Handler<T> {
    protected Handler<T> successor;
    public void setSuccessor(Handler<T> successor)
    {
        this.successor = successor;
    }
    // if is handled returns true (isHandled)
    protected abstract boolean work(T request);
    protected void pass(T request) {
        if(successor != null) successor.handle(request);
    }
    public final void handle(T request) {
        if(!work(request)) pass(request);
    }
}
