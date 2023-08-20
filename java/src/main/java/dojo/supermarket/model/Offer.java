package dojo.supermarket.model;

import dojo.supermarket.model.product.Product;
import dojo.supermarket.model.product.offer.OfferType;

public class Offer {

    OfferType offerType;
    private final Product product;
    double argument;

    public Offer(OfferType offerType, Product product, double argument) {
        this.offerType = offerType;
        this.argument = argument;
        this.product = product;
    }

    Product getProduct() {
        return product;
    }
}
