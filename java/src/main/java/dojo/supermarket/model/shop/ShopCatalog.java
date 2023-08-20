package dojo.supermarket.model.shop;

import dojo.supermarket.model.product.Product;

import java.math.BigDecimal;

public interface ShopCatalog {

    void add(Product product, BigDecimal price);

    BigDecimal priceOfProduct(Product product);
}
