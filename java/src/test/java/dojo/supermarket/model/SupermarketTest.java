package dojo.supermarket.model;

import dojo.supermarket.ReceiptPrinter;
import dojo.supermarket.model.product.Product;
import dojo.supermarket.model.product.ProductUnit;
import dojo.supermarket.model.product.offer.OfferType;
import dojo.supermarket.model.receipt.Receipt;
import dojo.supermarket.model.receipt.ReceiptItem;
import dojo.supermarket.model.shop.ShopCatalog;
import dojo.supermarket.model.shop.ShoppingCart;
import dojo.supermarket.service.cashier.CashierService;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Collections;

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

}
