package dojo.supermarket.model;

import dojo.supermarket.model.product.Product;
import dojo.supermarket.model.shop.ShopCatalog;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class MockCatalog implements ShopCatalog {
    private Map<String, Product> products = new HashMap<>();
    private Map<String, BigDecimal> prices = new HashMap<>();

    @Override
    public void add(Product product, BigDecimal price) {
        this.products.put(product.name(), product);
        this.prices.put(product.name(), price);
    }

    @Override
    public BigDecimal priceOfProduct(Product p) {
        return this.prices.get(p.name());
    }
}
