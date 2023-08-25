package dojo.supermarket.model.discount.coupon;

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
public class DiscountCoupon implements Serializable {

    private Range<Instant> available;
    private DiscountCouponType type;
    private DiscountCouponUsage usage;
    private BigDecimal value;
    private String code;
    private String user;
    private DiscountCouponState state;
    private BigDecimal minimumAffectedAmountOnPurchase;
    private BigDecimal maximumAffectedAmountOnPurchase;

    public static DiscountCoupon promotionWithAmount(final Range<Instant> available,
                                                     final BigDecimal amount,
                                                     final String code) {
        return DiscountCoupon.builder()
                .available(available)
                .type(DiscountCouponType.AMOUNT)
                .value(amount)
                .usage(DiscountCouponUsage.PROMOTION)
                .code(code)
                .state(DiscountCouponState.APPROVED)
                .build();
    }

    public static DiscountCoupon promotionWithPercentage(final Range<Instant> available,
                                                         final double percent,
                                                         final String code) {
        return DiscountCoupon.builder()
                .available(available)
                .type(DiscountCouponType.PERCENT)
                .value(BigDecimal.valueOf(percent))
                .usage(DiscountCouponUsage.PROMOTION)
                .code(code)
                .state(DiscountCouponState.APPROVED)
                .build();
    }

    public static DiscountCoupon userWithAmount(final Range<Instant> available, final BigDecimal amount, final String code, final String user) {
        return DiscountCoupon.builder()
                .available(available)
                .type(DiscountCouponType.AMOUNT)
                .value(amount)
                .usage(DiscountCouponUsage.SPECIFIC_USER)
                .user(user)
                .code(code)
                .state(DiscountCouponState.APPROVED)
                .build();
    }

    public static DiscountCoupon userWithPercentage(final Range<Instant> available, final double percent, final String code, final String user) {
        return DiscountCoupon.builder()
                .available(available)
                .type(DiscountCouponType.PERCENT)
                .value(BigDecimal.valueOf(percent))
                .usage(DiscountCouponUsage.SPECIFIC_USER)
                .user(user)
                .code(code)
                .state(DiscountCouponState.APPROVED)
                .build();
    }

    public DiscountCoupon minimum(final BigDecimal amount) {
        this.minimumAffectedAmountOnPurchase = amount;
        return this;
    }

    public DiscountCoupon maximum(final BigDecimal amount) {
        this.maximumAffectedAmountOnPurchase = amount;
        return this;
    }


}
