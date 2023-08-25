package dojo.supermarket.model.discount.coupon;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author mohammad hejazi - smohammadhejazii@gmail.com
 * @since 2023.08.22
 */
public class DiscountCouponRepository {

    private static DiscountCouponRepository instance;
    private Map<String, DiscountCoupon> list = new HashMap<>();

    private DiscountCouponRepository() {
    }

    public static DiscountCouponRepository instance() {
        if (instance == null) {
            instance = new DiscountCouponRepository();
        }
        return instance;
    }

    public void save(final DiscountCoupon discountCoupon) {
        this.list.put(discountCoupon.getCode(), discountCoupon);
    }


    public Optional<DiscountCoupon> findByCode(final String code) {
        DiscountCoupon discountCoupon = this.list.get(code);
        return discountCoupon != null ? Optional.of(discountCoupon) : Optional.empty();
    }
}
