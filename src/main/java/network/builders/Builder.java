package network.builders;

public interface Builder {
    public Builder build();
    public void addProduct(Product product);
    public Product getProduct(); 
}
