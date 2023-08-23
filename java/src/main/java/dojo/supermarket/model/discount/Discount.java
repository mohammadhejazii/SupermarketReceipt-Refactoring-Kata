package dojo.supermarket.model.discount;

import dojo.supermarket.model.base.Range;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

/**
 * @author mohammad hejazi - smohammadhejazii@gmail.com
 * @since 2023.08.22
 */

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Discount implements Serializable {

    private Range<Instant> available;
    private DiscountType type;
    private DiscountUsage usage;
    private BigDecimal value;
    private String code;
    private String user;
    private BigDecimal minimumAffectedAmountOnPurchase;
    private BigDecimal maximumAffectedAmountOnPurchase;

    public static Discount promotionWithAmount(final Range<Instant> available,
                                               final BigDecimal amount,
                                               final String code) {
        return Discount.builder()
                .available(available)
                .type(DiscountType.AMOUNT)
                .value(amount)
                .usage(DiscountUsage.PROMOTION)
                .code(code)
                .build();
    }

    public static Discount promotionWithPercentage(final Range<Instant> available,
                                                   final double percent,
                                                   final String code) {
        return Discount.builder()
                .available(available)
                .type(DiscountType.PERCENT)
                .value(BigDecimal.valueOf(percent))
                .usage(DiscountUsage.PROMOTION)
                .code(code)
                .build();
    }

    public static Discount userWithAmount(final Range<Instant> available, final BigDecimal amount, final String code, final String user) {
        return Discount.builder()
                .available(available)
                .type(DiscountType.AMOUNT)
                .value(amount)
                .usage(DiscountUsage.SPECIFIC_USER)
                .user(user)
                .code(code)
                .build();
    }

    public static Discount userWithPercentage(final Range<Instant> available, final double percent, final String code, final String user) {
        return Discount.builder()
                .available(available)
                .type(DiscountType.PERCENT)
                .value(BigDecimal.valueOf(percent))
                .usage(DiscountUsage.SPECIFIC_USER)
                .user(user)
                .code(code)
                .build();
    }

    public Discount minimum(final BigDecimal amount) {
        this.minimumAffectedAmountOnPurchase = amount;
        return this;
    }

    public Discount maximum(final BigDecimal amount) {
        this.maximumAffectedAmountOnPurchase = amount;
        return this;
    }


}
