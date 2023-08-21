package dojo.supermarket.model.product.offer;

import dojo.supermarket.service.product.offer.BaseOfferCalculator;

public enum OfferType {
    THREE_FOR_TWO(BaseOfferCalculator.percentageOffer(2d, 3d)),
    TEN_PERCENT_DISCOUNT(BaseOfferCalculator.percentageOffer(0d, 10d)),
    TWO_FOR_AMOUNT(BaseOfferCalculator.percentageOffer(0d, 2d)),
    FIVE_FOR_AMOUNT(BaseOfferCalculator.percentageOffer(0d, 5d));

    private OfferCalculator calculator;

    OfferType(final OfferCalculator calculator) {
        this.calculator = calculator;
    }

    public OfferCalculator calculator() {
        return calculator;
    }
}
