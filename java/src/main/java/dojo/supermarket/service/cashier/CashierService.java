package dojo.supermarket.service.cashier;

import dojo.supermarket.model.product.Product;
import dojo.supermarket.model.product.ProductQuantity;
import dojo.supermarket.model.product.offer.Offer;
import dojo.supermarket.model.product.offer.OfferType;
import dojo.supermarket.model.receipt.Receipt;
import dojo.supermarket.model.shop.ShopCatalog;
import dojo.supermarket.model.shop.ShoppingCart;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CashierService {

    private final ShopCatalog catalog;
    private final Map<Product, Offer> offers = new HashMap<>();

    public CashierService(ShopCatalog catalog) {
        this.catalog = catalog;
    }

    public void offer(OfferType offerType, Product product, double argument) {
        offers.put(product, new Offer(offerType, product, argument));
    }

    public Receipt receipt(ShoppingCart theCart) {
        Receipt receipt = new Receipt();
        List<ProductQuantity> productQuantities = theCart.getItems();
        for (ProductQuantity pq : productQuantities) {
            Product p = pq.product();
            double quantity = pq.quantity();
            BigDecimal unitPrice = catalog.priceOfProduct(p);
            BigDecimal price = unitPrice.multiply(BigDecimal.valueOf(quantity));
            receipt.add(p, quantity, unitPrice, price);
        }
        theCart.applyOffers(receipt, offers, catalog);

        return receipt;
    }
}
