package dojo.supermarket.model;

import dojo.supermarket.model.product.Product;
import dojo.supermarket.model.shop.ShopCatalog;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class FakeCatalog implements ShopCatalog {
    private Map<String, Product> products = new HashMap<>();
    private Map<String, BigDecimal> prices = new HashMap<>();

    @Override
    public void addProduct(Product product, BigDecimal price) {
        this.products.put(product.getName(), product);
        this.prices.put(product.getName(), price);
    }

    @Override
    public BigDecimal getUnitPrice(Product p) {
        return this.prices.get(p.getName());
    }
}
