package dojo.supermarket.model;

import dojo.supermarket.model.product.Product;
import dojo.supermarket.model.product.ProductUnit;
import dojo.supermarket.model.product.offer.OfferType;
import dojo.supermarket.model.receipt.Receipt;
import dojo.supermarket.model.receipt.ReceiptItem;
import dojo.supermarket.model.shop.ShopCatalog;
import dojo.supermarket.model.shop.ShoppingCart;
import dojo.supermarket.model.shop.Teller;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SupermarketTest {

    // Todo: test all kinds of discounts are applied properly

    @Test
    public void tenPercentDiscount() {
        ShopCatalog catalog = new FakeCatalog();
        Product toothbrush = new Product("toothbrush", ProductUnit.EACH);
        catalog.add(toothbrush, BigDecimal.valueOf(0.99));
        Product apples = new Product("apples", ProductUnit.KILO);
        catalog.add(apples, BigDecimal.valueOf(1.99));

        Teller teller = new Teller(catalog);
        teller.offer(OfferType.TEN_PERCENT_DISCOUNT, toothbrush, 10.0);

        ShoppingCart cart = new ShoppingCart();
        cart.addItemQuantity(apples, 2.5);

        // ACT
        Receipt receipt = teller.receipt(cart);

        // ASSERT
        assertEquals(4.975, receipt.totalPrice().doubleValue(), 0.01);
        assertEquals(Collections.emptyList(), receipt.discounts());
        assertEquals(1, receipt.items().size());
        ReceiptItem receiptItem = receipt.items().get(0);
        assertEquals(apples, receiptItem.getProduct());
        assertEquals(1.99, receiptItem.getPrice().doubleValue());
        assertEquals(2.5 * 1.99, receiptItem.getTotalPrice().doubleValue());
        assertEquals(2.5, receiptItem.getQuantity());

    }


}
