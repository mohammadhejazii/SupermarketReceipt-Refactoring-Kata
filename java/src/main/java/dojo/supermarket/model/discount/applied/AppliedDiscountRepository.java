package dojo.supermarket.model.discount.applied;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author mohammad hejazi - smohammadhejazii@gmail.com
 * @since 2023.08.22
 */
public class AppliedDiscountRepository {

    private static AppliedDiscountRepository instance;
    private List<AppliedDiscount> list = new ArrayList<>();

    private AppliedDiscountRepository() {
    }

    public static AppliedDiscountRepository instance() {
        if (instance == null) {
            instance = new AppliedDiscountRepository();
        }
        return instance;
    }

    public void save(final AppliedDiscount appliedDiscount) {
        this.list.add(appliedDiscount);
    }


    public Optional<AppliedDiscount> findByCodeAndUser(final String code,
                                                       final String user) {
        return this.list.stream()
                .filter(item -> item.getCoupon().getCode().equals(code))
                .filter(item -> item.getUser().equals(user))
                .findFirst();
    }


}
