package dojo.supermarket.model.discount;

import dojo.supermarket.model.product.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.joda.money.Money;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class Discount {

    private final String description;
    private final Money total;
    private final Money discount;
    private final Product product;

}
