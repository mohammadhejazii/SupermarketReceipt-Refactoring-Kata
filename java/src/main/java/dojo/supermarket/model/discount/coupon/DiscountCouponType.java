package dojo.supermarket.model.discount.coupon;

/**
 * @author mohammad hejazi - smohammadhejazii@gmail.com
 * @since 2023.08.22
 */
public enum DiscountCouponType {
    AMOUNT("$"),
    PERCENT("%");

    private final String value;


    DiscountCouponType(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
