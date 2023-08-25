package dojo.supermarket.service.discount;

import dojo.supermarket.model.base.BaseException;
import dojo.supermarket.model.discount.coupon.DiscountCoupon;
import dojo.supermarket.model.discount.coupon.DiscountCouponRepository;
import dojo.supermarket.model.discount.applied.AppliedDiscount;
import dojo.supermarket.model.discount.applied.AppliedDiscountRepository;
import dojo.supermarket.model.receipt.Receipt;

import java.time.Instant;

/**
 * @author mohammad hejazi - smohammadhejazii@gmail.com
 * @since 2023.08.22
 */
public class DiscountService {
    private static DiscountService instance;

    private DiscountService() {
    }

    public static DiscountService instance() {
        if (instance == null) {
            instance = new DiscountService();
        }
        return instance;
    }


    public void save(final DiscountCoupon discountCoupon) {
        DiscountCouponRepository.instance().save(discountCoupon);
    }

    public DiscountCoupon findByCode(final String code) {
        return DiscountCouponRepository.instance().findByCode(code).orElseThrow(BaseException.of("discount with code %s not found,code invalid.", code));
    }


    public void verify(final DiscountCoupon discountCoupon, final String user) {
        AppliedDiscountRepository.instance().findByCodeAndUser(discountCoupon.getCode(), user).ifPresent(appliedDiscount -> {
            throw BaseException.of("discount with code: %s already used by user: %s", discountCoupon.getCode(), user);
        });
    }

    public void used(final DiscountCoupon discountCoupon,
                     final Receipt receipt) {
        AppliedDiscount appliedDiscount = AppliedDiscount.builder()
                .user(discountCoupon.getUser())
                .receipt(receipt)
                .used(true)
                .usedDateTime(Instant.now())
                .coupon(discountCoupon)
                .build();
        AppliedDiscountRepository.instance().save(appliedDiscount);
    }
}
