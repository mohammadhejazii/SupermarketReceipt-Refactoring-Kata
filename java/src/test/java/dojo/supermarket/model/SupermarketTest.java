package dojo.supermarket.model;

import dojo.supermarket.service.receipt.ReceiptPrinter;
import dojo.supermarket.model.base.Range;
import dojo.supermarket.model.discount.coupon.DiscountCoupon;
import dojo.supermarket.model.product.Product;
import dojo.supermarket.model.product.ProductUnit;
import dojo.supermarket.model.product.offer.OfferType;
import dojo.supermarket.model.receipt.Receipt;
import dojo.supermarket.model.receipt.ReceiptItem;
import dojo.supermarket.model.shop.ShopCatalog;
import dojo.supermarket.model.shop.ShoppingCart;
import dojo.supermarket.service.cashier.CashierService;
import dojo.supermarket.service.discount.coupon.DiscountCouponService;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SupermarketTest {


    @Test
    public void tenPercentOfTotalDiscount() {
        ShopCatalog catalog = new MockCatalog();
        Product toothbrush = new Product("toothbrush", ProductUnit.EACH);
        catalog.add(toothbrush, BigDecimal.valueOf(0.99));
        Product apples = new Product("apples", ProductUnit.KILO);
        catalog.add(apples, BigDecimal.valueOf(1.99));

        CashierService cashier = new CashierService(catalog);
        cashier.offer(OfferType.TEN_PERCENT_DISCOUNT, apples);

        ShoppingCart cart = new ShoppingCart();
        cart.addItemQuantity(apples, 2.5);

        // ACT
        Receipt receipt = cashier.receipt(cart);
        ReceiptPrinter.print(receipt);

        // ASSERT
        assertEquals(4.975, receipt.totalPriceWithOutOffer().doubleValue(), 0.01);
        assertEquals(1, receipt.items().size());
        ReceiptItem receiptItem = receipt.items().get(0);
        assertEquals(apples, receiptItem.getProduct());
        assertEquals(1.99, receiptItem.getPrice().doubleValue());
        assertEquals(2.5 * 1.99, receiptItem.getTotalPrice().doubleValue());
        assertEquals(2.5, receiptItem.getQuantity());

    }


    @Test
    public void twoPercentOnTotalAmountDiscount() {
        ShopCatalog catalog = new MockCatalog();
        Product toothbrush = new Product("toothbrush", ProductUnit.EACH);
        catalog.add(toothbrush, BigDecimal.valueOf(0.99));
        Product apples = new Product("apples", ProductUnit.KILO);
        catalog.add(apples, BigDecimal.valueOf(1.99));
        CashierService cashier = new CashierService(catalog);
        cashier.offer(OfferType.TWO_PERCENT_ON_TOTAL_AMOUNT, apples);
        cashier.offer(OfferType.FIVE_PERCENT_ON_TOTAL_AMOUNT, toothbrush);

        ShoppingCart cart = new ShoppingCart();
        cart.addItemQuantity(apples, 2.5);
        cart.addItemQuantity(toothbrush, 5);

        // ACT
        Receipt receipt = cashier.receipt(cart);
        ReceiptPrinter.print(receipt);
        // ASSERT
        assertEquals(9.925, receipt.totalPriceWithOutOffer().doubleValue(), 0.01);
        assertEquals(2, receipt.items().size());
        ReceiptItem receiptItem = receipt.items().get(0);
        assertEquals(apples, receiptItem.getProduct());
        assertEquals(1.99, receiptItem.getPrice().doubleValue());
        assertEquals(2.5 * 1.99, receiptItem.getTotalPrice().doubleValue());
        assertEquals(2.5, receiptItem.getQuantity());

    }


    @Test
    public void discountPromotion() {
        ShopCatalog catalog = new MockCatalog();
        Product toothbrush = new Product("toothbrush", ProductUnit.EACH);
        catalog.add(toothbrush, BigDecimal.valueOf(0.99));
        Product apples = new Product("apples", ProductUnit.KILO);
        catalog.add(apples, BigDecimal.valueOf(1.99));

        CashierService cashier = new CashierService(catalog);
        cashier.offer(OfferType.TEN_PERCENT_DISCOUNT, apples);

        ShoppingCart cart = new ShoppingCart();
        cart.addItemQuantity(apples, 2.5);
        Instant start = Instant.now().minus(1, ChronoUnit.DAYS);
        Range<Instant> availableDateTime = Range.of(start, Instant.now().plus(4, ChronoUnit.DAYS));

        DiscountCoupon promotionWithAmount = DiscountCoupon.promotionWithAmount(availableDateTime, BigDecimal.valueOf(0.5), "P-0.5")
                .minimum(BigDecimal.valueOf(3))
                .maximum(BigDecimal.valueOf(4));
        DiscountCoupon promotionWithPercentage = DiscountCoupon.promotionWithPercentage(availableDateTime, 20.0, "P-20%")
                .minimum(BigDecimal.valueOf(3))
                .maximum(BigDecimal.valueOf(6));
        DiscountCouponService.instance().save(promotionWithAmount);
        DiscountCouponService.instance().save(promotionWithPercentage);

        // ACT
        Receipt receipt = cashier.receipt(cart);
        receipt = cashier.coupon(receipt, "P-20%", "user1");
        ReceiptPrinter.print(receipt);
    }

    @Test
    public void discountSpecificUser() {
        ShopCatalog catalog = new MockCatalog();
        Product toothbrush = new Product("toothbrush", ProductUnit.EACH);
        catalog.add(toothbrush, BigDecimal.valueOf(0.99));
        Product apples = new Product("apples", ProductUnit.KILO);
        catalog.add(apples, BigDecimal.valueOf(1.99));

        CashierService cashier = new CashierService(catalog);
        cashier.offer(OfferType.TEN_PERCENT_DISCOUNT, apples);

        ShoppingCart cart = new ShoppingCart();
        cart.addItemQuantity(apples, 2.5);
        Instant start = Instant.now().minus(1, ChronoUnit.DAYS);
        Range<Instant> availableDateTime = Range.of(start, Instant.now().plus(4, ChronoUnit.DAYS));

        DiscountCoupon promotionWithAmount = DiscountCoupon.userWithAmount(availableDateTime, BigDecimal.valueOf(1), "A-0.5", "user1")
                .minimum(BigDecimal.valueOf(3))
                .maximum(BigDecimal.valueOf(4));
        DiscountCoupon promotionWithPercentage = DiscountCoupon.userWithPercentage(availableDateTime, 20.0, "A-20%", "user1")
                .minimum(BigDecimal.valueOf(3))
                .maximum(BigDecimal.valueOf(4));
        DiscountCouponService.instance().save(promotionWithAmount);
        DiscountCouponService.instance().save(promotionWithPercentage);

        // ACT
        Receipt receipt = cashier.receipt(cart);
        receipt = cashier.coupon(receipt, "A-20%", "user1");
        ReceiptPrinter.print(receipt);
    }

}
