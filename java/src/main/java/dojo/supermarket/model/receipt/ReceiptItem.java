package dojo.supermarket.model.receipt;

import dojo.supermarket.model.product.Product;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
public class ReceiptItem {

    private final Product product;
    private final BigDecimal price;
    private final BigDecimal totalPrice;
    private final double quantity;

    ReceiptItem(Product p, double quantity, BigDecimal price, BigDecimal totalPrice) {
        this.product = p;
        this.quantity = quantity;
        this.price = price;
        this.totalPrice = totalPrice;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReceiptItem)) return false;
        ReceiptItem that = (ReceiptItem) o;
        return that.getPrice().compareTo(getPrice()) == 0 &&
                that.getTotalPrice().compareTo(getTotalPrice()) == 0 &&
                Double.compare(that.getQuantity(), getQuantity()) == 0 &&
                Objects.equals(that.getProduct(), getProduct());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProduct(), getPrice(), getTotalPrice(), getQuantity());
    }
}
