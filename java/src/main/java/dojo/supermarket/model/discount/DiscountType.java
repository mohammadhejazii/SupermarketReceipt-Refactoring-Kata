package dojo.supermarket.model.discount;

/**
 * @author mohammad hejazi - smohammadhejazii@gmail.com
 * @since 2023.08.22
 */
public enum DiscountType {
    AMOUNT("$"),
    PERCENT("%");

    private final String value;


    DiscountType(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
