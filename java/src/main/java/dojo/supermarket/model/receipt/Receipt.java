package dojo.supermarket.model.receipt;

import dojo.supermarket.model.discount.Discount;
import dojo.supermarket.model.discount.DiscountReceipt;
import dojo.supermarket.model.product.Product;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Getter
@Setter
public class Receipt {

    private final List<ReceiptItem> items = new ArrayList<>();
    private final List<DiscountReceipt> offers = new ArrayList<>();
    private DiscountReceipt discountReceipt;
    private Discount discount;

    public BigDecimal totalPriceWithOutOffer() {
        BigDecimal total = BigDecimal.ZERO;
        for (ReceiptItem item : items) {
            total = total.add(item.getTotalPrice());
        }
        return total;
    }

    public BigDecimal totalPriceWithOfferApply() {
        BigDecimal total = totalPriceWithOutOffer();
        for (DiscountReceipt offer : offers) {
            total = total.subtract(offer.getDiscount().getAmount());
        }
        return total;
    }


    public BigDecimal totalPriceWithOfferAndDiscountApply() {
        BigDecimal total = totalPriceWithOfferApply();
        if (discountReceipt != null) {
            total = total.subtract(discountReceipt.getDiscount().getAmount());
        }
        return total;
    }


    public void add(Product p, double quantity, BigDecimal price, BigDecimal totalPrice) {
        items.add(new ReceiptItem(p, quantity, price, totalPrice));
    }

    public List<ReceiptItem> items() {
        return Collections.unmodifiableList(items);
    }

    public void addOfferDiscountReceipt(DiscountReceipt discountReceipt) {
        offers.add(discountReceipt);
    }

    public void setDiscountReceipt(DiscountReceipt discountReceipt) {
        this.discountReceipt = discountReceipt;
    }

    public List<DiscountReceipt> offers() {
        return offers;
    }

    public DiscountReceipt getDiscountReceipt() {
        return discountReceipt;
    }

    public Discount getDiscount() {
        return discount;
    }


}
