package dojo.supermarket.model.product.offer;

import dojo.supermarket.model.discount.DiscountReceipt;
import dojo.supermarket.model.product.Product;

import java.math.BigDecimal;

/**
 * @author mohammad hejazi - smohammadhejazii@gmail.com
 * @since 2023.08.20
 */
public interface OfferCalculator {

    DiscountReceipt applyOffer(Product product, BigDecimal unitPrice, Double quantity, Offer offer);

    Double percentage();

}
