package dojo.supermarket.service.cashier;

import dojo.supermarket.model.base.BaseException;
import dojo.supermarket.model.discount.Discount;
import dojo.supermarket.model.discount.DiscountReceipt;
import dojo.supermarket.model.discount.DiscountUsage;
import dojo.supermarket.model.product.Product;
import dojo.supermarket.model.product.ProductQuantity;
import dojo.supermarket.model.product.offer.Offer;
import dojo.supermarket.model.product.offer.OfferType;
import dojo.supermarket.model.receipt.Receipt;
import dojo.supermarket.model.shop.ShopCatalog;
import dojo.supermarket.model.shop.ShoppingCart;
import dojo.supermarket.service.discount.DiscountService;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CashierService {

    private final ShopCatalog catalog;
    private final Map<Product, Offer> offers = new HashMap<>();

    public CashierService(ShopCatalog catalog) {
        this.catalog = catalog;
    }

    public void offer(OfferType offerType, Product product) {
        offers.put(product, new Offer(offerType, product));
    }


    public Receipt discount(final Receipt receipt,
                            final String code,
                            final String user) {
        if (receipt.getDiscountReceipt() != null) {
            throw BaseException.of("already used overall discount on this receipt.");
        }
        Discount discount = DiscountService.instance().findByCode(code);
        if (!discount.getAvailable().getFrom().isBefore(Instant.now()) || !discount.getAvailable().getTo().isAfter(Instant.now())) {
            throw BaseException.of("this discount available time not in range from: %s , to: %s valid.", discount.getAvailable().getFrom().toString(), discount.getAvailable().getTo().toString());
        }
        if (discount.getUsage().equals(DiscountUsage.SPECIFIC_USER) && user == null) {
            throw BaseException.of("this discount valid for specific user,please enter candidate user.");
        }
        if (discount.getUsage().equals(DiscountUsage.SPECIFIC_USER) && !user.equals(discount.getUser())) {
            throw BaseException.of("discount not match with candidate user: %s.", user);
        }

        BigDecimal totalPrice = receipt.totalPriceWithOfferApply();
        if (totalPrice.compareTo(discount.getMinimumAffectedAmountOnPurchase()) < 0) {
            throw BaseException.of("minimum amount is %s per purchase to use discount with code %s, total receipt is: %s", discount.getMinimumAffectedAmountOnPurchase().toString(), discount.getCode(), totalPrice.toString());
        }


        DiscountService.instance().verify(discount, user);
        DiscountService.instance().used(discount, receipt);


        Money total = Money.of(CurrencyUnit.EUR, totalPrice, RoundingMode.FLOOR);
        Money discountMoney;


        switch (discount.getType()) {
            case AMOUNT -> discountMoney = Money.of(CurrencyUnit.EUR, discount.getValue(), RoundingMode.FLOOR);
            case PERCENT -> {
                if (totalPrice.compareTo(discount.getMaximumAffectedAmountOnPurchase()) > 0) {
                    BigDecimal discountAmount = discount.getMaximumAffectedAmountOnPurchase().multiply(discount.getValue()).divide(BigDecimal.valueOf(100));
                    discountMoney = Money.of(CurrencyUnit.EUR, discountAmount, RoundingMode.FLOOR);
                } else {
                    BigDecimal discountAmount = totalPrice.multiply(discount.getValue()).divide(BigDecimal.valueOf(100));
                    discountMoney = Money.of(CurrencyUnit.EUR, discountAmount, RoundingMode.FLOOR);
                }
            }
            default -> throw BaseException.of("discount type not in range");
        }


        DiscountReceipt discountReceipt = DiscountReceipt.receipt("", total, discountMoney, receipt);
        receipt.setDiscount(discount);
        receipt.setDiscountReceipt(discountReceipt);
        return receipt;
    }


    public Receipt receipt(ShoppingCart shoppingCart) {
        Receipt receipt = new Receipt();
        List<ProductQuantity> productQuantities = shoppingCart.getItems();
        for (ProductQuantity pq : productQuantities) {
            Product p = pq.product();
            double quantity = pq.quantity();
            BigDecimal unitPrice = catalog.priceOfProduct(p);
            BigDecimal price = unitPrice.multiply(BigDecimal.valueOf(quantity));
            receipt.add(p, quantity, unitPrice, price);
        }
        applyOffers(shoppingCart, receipt, offers, catalog);

        return receipt;
    }


    public void applyOffers(ShoppingCart shoppingCart, Receipt receipt, Map<Product, Offer> offers, ShopCatalog catalog) {
        for (Product product : shoppingCart.getProductQuantities().keySet()) {
            if (offers.containsKey(product)) {
                Offer offer = offers.get(product);
                DiscountReceipt discountReceipt = offer.getOfferType().calculator().applyOffer(product, catalog.priceOfProduct(product), shoppingCart.getProductQuantities().get(product), offer);
                if (discountReceipt != null) receipt.addOfferDiscountReceipt(discountReceipt);
            }
        }
    }

}
