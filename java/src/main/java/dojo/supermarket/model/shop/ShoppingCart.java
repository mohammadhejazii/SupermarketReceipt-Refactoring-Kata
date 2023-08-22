package dojo.supermarket.model.shop;

import dojo.supermarket.model.product.Product;
import dojo.supermarket.model.product.ProductQuantity;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
public class ShoppingCart {

    private final List<ProductQuantity> items = new ArrayList<>();
    private final Map<Product, Double> productQuantities = new HashMap<>();

    Map<Product, Double> productQuantities() {
        return Collections.unmodifiableMap(productQuantities);
    }

    public void addItemQuantity(Product product, double quantity) {
        items.add(new ProductQuantity(product, quantity));
        if (productQuantities.containsKey(product)) {
            productQuantities.put(product, productQuantities.get(product) + quantity);
        } else {
            productQuantities.put(product, quantity);
        }
    }


}
