package dojo.supermarket.model.discount.coupon.applied;

import dojo.supermarket.model.discount.coupon.DiscountCoupon;
import dojo.supermarket.model.receipt.Receipt;
import lombok.*;

import java.io.Serializable;
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
public class AppliedDiscountCoupon implements Serializable {

    private Receipt receipt;
    private DiscountCoupon coupon;
    private String user;
    private boolean used;
    private Instant usedDateTime;
}
