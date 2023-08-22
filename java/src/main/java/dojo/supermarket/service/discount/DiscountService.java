package dojo.supermarket.service.discount;

import dojo.supermarket.model.base.BaseException;
import dojo.supermarket.model.discount.Discount;
import dojo.supermarket.model.discount.DiscountRepository;
import dojo.supermarket.model.discount.applied.AppliedDiscount;
import dojo.supermarket.model.discount.applied.AppliedDiscountRepository;
import dojo.supermarket.model.receipt.Receipt;

import java.time.Instant;

/**
 * @author mohammad hejazi - smohammadhejazii@gmail.com
 * @since 2023.08.22
 */
public class DiscountService {
    private static DiscountService instance;

    private DiscountService() {
    }

    public static DiscountService instance() {
        if (instance == null) {
            instance = new DiscountService();
        }
        return instance;
    }


    public void save(final Discount discount) {
        DiscountRepository.instance().save(discount);
    }

    public Discount findByCode(final String code) {
        return DiscountRepository.instance().findByCode(code).orElseThrow(BaseException.of("discount with code %s not found,code invalid.", code));
    }


    public void verify(final Discount discount, final String user) {
        AppliedDiscountRepository.instance().findByCodeAndUser(discount.getCode(), user).ifPresent(appliedDiscount -> {
            throw BaseException.of("discount with code: %s already used by user: %s", discount.getCode(), user);
        });
    }

    public void used(final Discount discount,
                     final Receipt receipt) {
        AppliedDiscount appliedDiscount = AppliedDiscount.builder()
                .user(discount.getUser())
                .receipt(receipt)
                .used(true)
                .usedDateTime(Instant.now())
                .discount(discount)
                .build();
        AppliedDiscountRepository.instance().save(appliedDiscount);
    }
}
