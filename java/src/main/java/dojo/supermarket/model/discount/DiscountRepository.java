package dojo.supermarket.model.discount;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author mohammad hejazi - smohammadhejazii@gmail.com
 * @since 2023.08.22
 */
public class DiscountRepository {

    private static DiscountRepository instance;
    private Map<String, Discount> list = new HashMap<>();

    private DiscountRepository() {
    }

    public static DiscountRepository instance() {
        if (instance == null) {
            instance = new DiscountRepository();
        }
        return instance;
    }

    public void save(final Discount discount) {
        this.list.put(discount.getCode(), discount);
    }


    public Optional<Discount> findByCode(final String code) {
        Discount discount = this.list.get(code);
        return discount != null ? Optional.of(discount) : Optional.empty();
    }
}
