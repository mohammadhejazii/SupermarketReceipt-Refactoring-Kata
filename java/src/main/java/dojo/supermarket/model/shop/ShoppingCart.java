package dojo.supermarket.model.shop;

import dojo.supermarket.model.discount.Discount;
import dojo.supermarket.model.product.Product;
import dojo.supermarket.model.product.ProductQuantity;
import dojo.supermarket.model.product.offer.Offer;
import dojo.supermarket.model.product.offer.OfferType;
import dojo.supermarket.model.receipt.Receipt;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import java.math.BigDecimal;
import java.util.*;

public class ShoppingCart {

    private final List<ProductQuantity> items = new ArrayList<>();
    private final Map<Product, Double> productQuantities = new HashMap<>();

    List<ProductQuantity> getItems() {
        return Collections.unmodifiableList(items);
    }

    void addItem(Product product) {
        addItemQuantity(product, 1.0);
    }

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

    void applyOffers(Receipt receipt, Map<Product, Offer> offers, ShopCatalog catalog) {
        for (Product p : productQuantities().keySet()) {
            double quantity = productQuantities.get(p);
            if (offers.containsKey(p)) {
                Offer offer = offers.get(p);
                BigDecimal unitPrice = catalog.priceOfProduct(p);
                int quantityAsInt = (int) quantity;
                Discount discount = null;
                int x = 1;
                if (offer.getOfferType() == OfferType.THREE_FOR_TWO) {
                    x = 3;

                } else if (offer.getOfferType() == OfferType.TWO_FOR_AMOUNT) {
                    x = 2;
                    if (quantityAsInt >= 2) {
                        double total = offer.getArgument() * (quantityAsInt / x) + quantityAsInt % 2 * unitPrice.doubleValue();
                        BigDecimal discountN = unitPrice.multiply(BigDecimal.valueOf(quantity)).min(BigDecimal.valueOf(total));
                        Money money = Money.of(CurrencyUnit.USD, discountN.abs());
                        discount = new Discount(p, "2 for " + offer.getArgument(), money);
                    }

                }
                if (offer.getOfferType() == OfferType.FIVE_FOR_AMOUNT) {
                    x = 5;
                }
                int numberOfXs = quantityAsInt / x;
                if (offer.getOfferType() == OfferType.THREE_FOR_TWO && quantityAsInt > 2) {
                    double discountAmount = unitPrice.multiply(BigDecimal.valueOf(quantity)).doubleValue() - ((numberOfXs * 2 * unitPrice.doubleValue()) + quantityAsInt % 3 * unitPrice.doubleValue());
                    Money money = Money.of(CurrencyUnit.USD, -discountAmount);
                    discount = new Discount(p, "3 for 2", money);
                }
                if (offer.getOfferType() == OfferType.TEN_PERCENT_DISCOUNT) {
                    Money money = Money.of(CurrencyUnit.USD, unitPrice.multiply(BigDecimal.valueOf(quantity).abs()).multiply(BigDecimal.valueOf(offer.getArgument())).divide(BigDecimal.valueOf(100)));
                    discount = new Discount(p, offer.getArgument() + "% off", money);
                }
                if (offer.getOfferType() == OfferType.FIVE_FOR_AMOUNT && quantityAsInt >= 5) {
                    double discountTotal = unitPrice.multiply(BigDecimal.valueOf(quantity)).doubleValue() - (offer.getArgument() * numberOfXs + quantityAsInt % 5 * unitPrice.doubleValue());
                    Money money = Money.of(CurrencyUnit.USD, discountTotal);
                    discount = new Discount(p, x + " for " + offer.getArgument(), money);
                }
                if (discount != null) receipt.addDiscount(discount);
            }
        }
    }
}
