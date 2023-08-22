package dojo.supermarket.model.discount.applied;

import dojo.supermarket.model.discount.Discount;
import dojo.supermarket.model.receipt.Receipt;
import lombok.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * @author mohammad hejazi - smohammadhejazii@gmail.com
 * @since 2023.08.22
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppliedDiscount implements Serializable {

    private Receipt receipt;
    private Discount discount;
    private String user;
    private boolean used;
    private Instant usedDateTime;
}
