package dojo.supermarket.model.product.offer;

import dojo.supermarket.model.product.Product;
import lombok.Getter;

@Getter
public class Offer {

    private final OfferType offerType;
    private final Product product;
    private final double argument;

    public Offer(OfferType offerType, Product product, double argument) {
        this.offerType = offerType;
        this.argument = argument;
        this.product = product;
    }

}
