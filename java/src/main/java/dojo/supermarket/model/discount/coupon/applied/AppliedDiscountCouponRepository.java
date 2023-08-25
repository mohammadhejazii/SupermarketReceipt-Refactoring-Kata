package dojo.supermarket.model.discount.coupon.applied;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author mohammad hejazi - smohammadhejazii@gmail.com
 * @since 2023.08.22
 */
public class AppliedDiscountCouponRepository {

    private static AppliedDiscountCouponRepository instance;
    private List<AppliedDiscountCoupon> list = new ArrayList<>();

    private AppliedDiscountCouponRepository() {
    }

    public static AppliedDiscountCouponRepository instance() {
        if (instance == null) {
            instance = new AppliedDiscountCouponRepository();
        }
        return instance;
    }

    public void save(final AppliedDiscountCoupon appliedDiscountCoupon) {
        this.list.add(appliedDiscountCoupon);
    }


    public Optional<AppliedDiscountCoupon> findByCodeAndUser(final String code,
                                                             final String user) {
        return this.list.stream()
                .filter(item -> item.getCoupon().getCode().equals(code))
                .filter(item -> item.getUser().equals(user))
                .findFirst();
    }


}
