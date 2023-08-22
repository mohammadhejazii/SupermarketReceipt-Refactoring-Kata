package dojo.supermarket.model.product.offer;

import dojo.supermarket.model.product.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Offer {

    private final OfferType offerType;
    private final Product product;


}
