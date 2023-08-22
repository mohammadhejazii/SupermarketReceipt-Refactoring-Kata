package dojo.supermarket.model.product.offer;

import dojo.supermarket.service.product.offer.BaseOfferCalculator;

public enum OfferType {
    THREE_PERCENT_ON_QUANTITY_GT_TWO(BaseOfferCalculator.percentageOffer(2d, 3.0, 2)),
    TEN_PERCENT_DISCOUNT(BaseOfferCalculator.percentageOffer(0d, 10.0, 100)),
    TWO_PERCENT_ON_TOTAL_AMOUNT(BaseOfferCalculator.percentageOffer(0d, 2.0, 100)),
    FIVE_PERCENT_ON_TOTAL_AMOUNT(BaseOfferCalculator.percentageOffer(0d, 5.0, 100));

    private OfferCalculator calculator;

    OfferType(final OfferCalculator calculator) {
        this.calculator = calculator;
    }

    public OfferCalculator calculator() {
        return calculator;
    }
}
