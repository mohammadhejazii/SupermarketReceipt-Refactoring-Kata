package dojo.supermarket.model.receipt;

import dojo.supermarket.model.discount.Discount;
import dojo.supermarket.model.product.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Receipt {

    private final List<ReceiptItem> items = new ArrayList<>();
    private final List<Discount> discounts = new ArrayList<>();

    public BigDecimal getTotalPrice() {
        BigDecimal total = BigDecimal.ZERO;
        for (ReceiptItem item : items) {
            total = total.add(item.getTotalPrice());
        }
        for (Discount discount : discounts) {
            total = total.add(discount.getMoney().getAmount());
        }
        return total;
    }

    public void addProduct(Product p, double quantity, BigDecimal price, BigDecimal totalPrice) {
        items.add(new ReceiptItem(p, quantity, price, totalPrice));
    }

    public List<ReceiptItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    public void addDiscount(Discount discount) {
        discounts.add(discount);
    }

    public List<Discount> getDiscounts() {
        return discounts;
    }
}
