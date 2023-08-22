package dojo.supermarket.service.product.offer;

import dojo.supermarket.model.discount.DiscountReceipt;
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
    private Integer offerApplyOnQuantity;


    public static BaseOfferCalculator amountOffer(final Double minimumQuantity,
                                                  final BigDecimal amount,
                                                  final Integer offerApplyOnQuantity) {
        return BaseOfferCalculator.builder()
                .minimum(minimumQuantity)
                .amount(amount)
                .offerApplyOnQuantity(offerApplyOnQuantity)
                .build();
    }


    public static BaseOfferCalculator percentageOffer(final Double minimumQuantity,
                                                      final Double percentage,
                                                      final Integer offerApplyOnQuantity) {
        return BaseOfferCalculator.builder()
                .minimum(minimumQuantity)
                .percentage(percentage)
                .offerApplyOnQuantity(offerApplyOnQuantity)
                .build();
    }


    @Override
    public DiscountReceipt applyOffer(final Product product,
                                      final BigDecimal unitPrice,
                                      final Double quantity,
                                      final Offer offer) {
        DiscountReceipt discountReceipt = null;
        int quantityAsInt = quantity.intValue();
        int numberOfXs = quantityAsInt / percentage().intValue();
        if (isMinimumQuantityPassed(quantity)) {
            if (percentage != null) {
                if (offerApplyOnQuantity == 100) {
                    BigDecimal totalPriceBeforeApplyOffer = unitPrice.multiply(BigDecimal.valueOf(quantity));
                    BigDecimal discountAmount = totalPriceBeforeApplyOffer.multiply(BigDecimal.valueOf(percentage())).divide(BigDecimal.valueOf(100));
                    Money totalMoney = Money.of(CurrencyUnit.EUR, totalPriceBeforeApplyOffer, RoundingMode.FLOOR);
                    Money discountMoney = Money.of(CurrencyUnit.EUR, discountAmount, RoundingMode.FLOOR);
                    discountReceipt = DiscountReceipt.product(String.format("%.2f%s on total", percentage(), "%"), totalMoney, discountMoney, product);
                } else {
                    double candidateQuantityWithOutOffer = quantity - offerApplyOnQuantity;
                    BigDecimal withOutOfferPrice = unitPrice.multiply(BigDecimal.valueOf(candidateQuantityWithOutOffer));
                    BigDecimal withOfferPrice = unitPrice.multiply(BigDecimal.valueOf(offerApplyOnQuantity));
                    BigDecimal discountAmount = withOfferPrice.multiply(BigDecimal.valueOf(percentage())).divide(BigDecimal.valueOf(100));
                    Money totalMoney = Money.of(CurrencyUnit.EUR, withOutOfferPrice.add(discountAmount), RoundingMode.FLOOR);
                    Money discountMoney = Money.of(CurrencyUnit.EUR, discountAmount, RoundingMode.FLOOR);
                    discountReceipt = DiscountReceipt.product(String.format("%.2f%s on %s quantity", percentage(), "%", offerApplyOnQuantity), totalMoney, discountMoney, product);
                }
                return discountReceipt;
            } else if (amount != null) {
                double discountTotal = unitPrice.multiply(BigDecimal.valueOf(quantity)).doubleValue() - (numberOfXs + quantityAsInt % percentage() * unitPrice.doubleValue());
                Money money = Money.of(CurrencyUnit.USD, discountTotal, RoundingMode.FLOOR);
//                discount = new Discount(product, percentage() + " for " + discountTotal, money);
            }
        }
        return discountReceipt;
    }

    @Override
    public Double percentage() {
        return percentage;
    }

    private boolean isMinimumQuantityPassed(final Double quantity) {
        return quantity >= this.minimum;
    }

}
