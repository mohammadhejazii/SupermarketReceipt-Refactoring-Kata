package dojo.supermarket.model.shop;

import dojo.supermarket.model.product.Product;

import java.math.BigDecimal;

public interface ShopCatalog {

    void addProduct(Product product, BigDecimal price);

    BigDecimal getUnitPrice(Product product);
}
