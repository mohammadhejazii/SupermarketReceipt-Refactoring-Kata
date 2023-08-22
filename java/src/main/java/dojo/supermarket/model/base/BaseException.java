package dojo.supermarket.model.base;

import java.util.function.Supplier;

/**
 * @author mohammad hejazi - smohammadhejazii@gmail.com
 * @since 2023.08.22
 */
public class BaseException extends RuntimeException implements Supplier<BaseException> {

    private BaseException(final String result) {
        super(result);
    }

    public static BaseException of(String message, String... params) {
        String result = String.format(message, params);
        return new BaseException(result);
    }


    @Override
    public BaseException get() {
        return this;
    }
}
