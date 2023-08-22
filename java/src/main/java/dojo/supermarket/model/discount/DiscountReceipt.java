package dojo.supermarket.model.discount;

import dojo.supermarket.model.product.Product;
import dojo.supermarket.model.receipt.Receipt;
import lombok.*;
import org.joda.money.Money;

import java.io.Serializable;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public final class DiscountReceipt implements Serializable {
    private String description;
    private Money total;
    private Money discount;
    private Product product;
    private Receipt receipt;

    public static DiscountReceipt product(final String description,
                                          final Money total,
                                          final Money discount,
                                          final Product product) {
        return DiscountReceipt.builder()
                .description(description)
                .total(total)
                .discount(discount)
                .product(product)
                .build();
    }

    public static DiscountReceipt receipt(final String description,
                                          final Money total,
                                          final Money discount,
                                          final Receipt receipt) {
        return DiscountReceipt.builder()
                .description(description)
                .total(total)
                .discount(discount)
                .receipt(receipt)
                .build();
    }


}
