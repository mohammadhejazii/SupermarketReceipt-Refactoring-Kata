package dojo.supermarket.model.shop;

import dojo.supermarket.model.discount.Discount;
import dojo.supermarket.model.product.Product;
import dojo.supermarket.model.product.ProductQuantity;
import dojo.supermarket.model.product.offer.Offer;
import dojo.supermarket.model.receipt.Receipt;
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

    public void applyOffers(Receipt receipt, Map<Product, Offer> offers, ShopCatalog catalog) {
        for (Product product : productQuantities().keySet()) {
            if (offers.containsKey(product)) {
                Offer offer = offers.get(product);
                Discount discount = offer.getOfferType().calculator().applyOffer(product, catalog.priceOfProduct(product), productQuantities.get(product), offer);
                if (discount != null) receipt.addDiscount(discount);
            }
        }
    }
}
