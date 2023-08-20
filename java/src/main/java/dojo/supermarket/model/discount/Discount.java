package dojo.supermarket.model.discount;

import dojo.supermarket.model.product.Product;
import lombok.Getter;
import lombok.Setter;
import org.joda.money.Money;

@Getter
@Setter
public class Discount {

    private final String description;
    private final Money money;
    private final Product product;

    public Discount(Product product, String description, Money money) {
        this.product = product;
        this.description = description;
        this.money = money;
    }
}
