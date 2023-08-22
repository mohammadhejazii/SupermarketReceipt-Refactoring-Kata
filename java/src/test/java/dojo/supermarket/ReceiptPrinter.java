package dojo.supermarket;

import dojo.supermarket.model.discount.DiscountReceipt;
import dojo.supermarket.model.product.ProductUnit;
import dojo.supermarket.model.receipt.Receipt;
import dojo.supermarket.model.receipt.ReceiptItem;

import java.math.BigDecimal;
import java.util.Locale;

public class ReceiptPrinter {

    private final int columns;

    public ReceiptPrinter() {
        this(40);
    }

    public ReceiptPrinter(int columns) {
        this.columns = columns;
    }

    public static void print(Receipt receipt) {
        ReceiptPrinter printer = new ReceiptPrinter();
        StringBuilder result = new StringBuilder();
        result.append("========================================\n");
        for (ReceiptItem item : receipt.items()) {
            String receiptItem = printer.presentReceiptItem(item);
            result.append(receiptItem);
        }
        for (DiscountReceipt discountReceipt : receipt.offers()) {
            String discountPresentation = printer.presentProductDiscount(discountReceipt);
            result.append(discountPresentation);
        }

        if (receipt.getDiscountReceipt() != null) {
            result.append("------\n");
            result.append(printer.presentReceiptDiscount(receipt));
        }
        result.append("\n");
        result.append(printer.presentTotal(receipt));
        result.append("========================================\n");

        System.out.println(result);
    }

    private String presentReceiptItem(ReceiptItem item) {
        String totalPricePresentation = presentPrice(item.getTotalPrice());
        String name = item.getProduct().name();

        String line = formatLineWithWhitespace(name, totalPricePresentation);

        if (item.getQuantity() != 1) {
            line += "  " + presentPrice(item.getPrice()) + " * " + presentQuantity(item) + "\n";
        }
        return line;
    }


    private String presentReceiptDiscount(Receipt receipt) {
        String discount = formatLineWithWhitespace("use discount with code:", receipt.getDiscount().getCode());
        String name = receipt.getDiscountReceipt().getReceipt().totalPriceWithOfferApply() + " - " + receipt.getDiscountReceipt().getDiscount().getAmount()  + receipt.getDiscount().getType().getValue()+ " :";
        BigDecimal subtract = receipt.getDiscountReceipt().getReceipt().totalPriceWithOfferApply().subtract(receipt.getDiscountReceipt().getDiscount().getAmount());
        String value = presentPrice(subtract);
        return discount + formatLineWithWhitespace(name, value);
    }

    private String presentProductDiscount(DiscountReceipt discountReceipt) {
        String name = discountReceipt.getDescription() + "(" + discountReceipt.getProduct().name() + ")";
        String value = presentPrice(discountReceipt.getDiscount().getAmount());
        return formatLineWithWhitespace(name, value);
    }

    private String presentTotal(Receipt receipt) {
        String name = "Total: ";
        String value = presentPrice(receipt.totalPriceWithOfferAndDiscountApply());
        return formatLineWithWhitespace(name, value);
    }

    private String formatLineWithWhitespace(String name, String value) {
        StringBuilder line = new StringBuilder();
        line.append(name);
        int whitespaceSize = this.columns - name.length() - value.length();
        for (int i = 0; i < whitespaceSize; i++) {
            line.append(" ");
        }
        line.append(value);
        line.append('\n');
        return line.toString();
    }

    private static String presentPrice(BigDecimal price) {
        return String.format(Locale.UK, "%.2f", price);
    }

    private static String presentQuantity(ReceiptItem item) {
        return ProductUnit.EACH.equals(item.getProduct().unit())
                ? String.format("%d", (int) item.getQuantity())
                : String.format(Locale.UK, "%.3f", item.getQuantity());
    }
}
