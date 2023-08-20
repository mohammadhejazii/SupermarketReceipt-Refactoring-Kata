package dojo.supermarket.model.product;

public class ProductQuantity {

    private final Product product;
    private final double quantity;

    public ProductQuantity(Product product, double weight) {
        this.product = product;
        this.quantity = weight;
    }

    public Product product() {
        return product;
    }

    public double quantity() {
        return quantity;
    }
}
