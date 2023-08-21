package dojo.supermarket.service.product.offer;

import dojo.supermarket.model.discount.Discount;
import dojo.supermarket.model.product.Product;
import dojo.supermarket.model.product.offer.Offer;
import dojo.supermarket.model.product.offer.OfferCalculator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author mohammad hejazi - smohammadhejazii@gmail.com
 * @since 2023.08.21
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BaseOfferCalculator implements OfferCalculator {

    private BigDecimal amount;
    private Double percentage;
    private Double minimum;


    public static BaseOfferCalculator amountOffer(final Double minimumQuantity,
                                                  final BigDecimal amount) {
        return BaseOfferCalculator.builder()
                .minimum(minimumQuantity)
                .amount(amount)
                .build();
    }


    public static BaseOfferCalculator percentageOffer(final Double minimumQuantity,
                                                      final Double percentage) {
        return BaseOfferCalculator.builder()
                .minimum(minimumQuantity)
                .percentage(percentage)
                .build();
    }


    @Override
    public Discount applyOffer(final Product product, final BigDecimal unitPrice, final Double quantity, final Offer offer) {
        Discount discount = null;
        int quantityAsInt = quantity.intValue();
        int numberOfXs = quantityAsInt / percentage().intValue();
        if (isMinimumQuantityPassed(quantity)) {
            if (percentage != null) {
                double total = offer.getArgument() * (quantityAsInt / percentage()) + quantityAsInt % percentage() * unitPrice.doubleValue();
                BigDecimal discountN = unitPrice.multiply(BigDecimal.valueOf(quantity)).min(BigDecimal.valueOf(total));
                Money money = Money.of(CurrencyUnit.USD, discountN.doubleValue(), RoundingMode.FLOOR);
                discount = new Discount(product, percentage() + " for " + offer.getArgument(), money);
            } else if (amount != null) {
                double discountTotal = unitPrice.multiply(BigDecimal.valueOf(quantity)).doubleValue() - (offer.getArgument() * numberOfXs + quantityAsInt % percentage() * unitPrice.doubleValue());
                Money money = Money.of(CurrencyUnit.USD, discountTotal, RoundingMode.FLOOR);
                discount = new Discount(product, percentage() + " for " + offer.getArgument(), money);
            }
        }
        return discount;
    }

    @Override
    public Double percentage() {
        return percentage;
    }

    private boolean isMinimumQuantityPassed(final Double quantity) {
        return quantity >= this.minimum;
    }

}
