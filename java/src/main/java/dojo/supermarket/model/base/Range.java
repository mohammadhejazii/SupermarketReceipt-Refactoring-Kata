package dojo.supermarket.model.base;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

/**
 * @author mohammad hejazi - smohammadhejazii@gmail.com
 * @since 2023.08.22
 */
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Range<T> implements Serializable {

    private final String LOCAL_DATE_REGEX = "^\\d{4}\\-(0[1-9]|1[0-2])\\-(0[1-9]|[1-2][0-9]|3[0-1])$";
    private final String LOCAL_TIME_REGEX = "^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$";
    private final String LOCAL_DATE_TIME_REGEX = "^\\d{4}\\-(0[1-9]|1[0-2])\\-(0[1-9]|[1-2][0-9]|3[0-1])T(0[0-9]|1[0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9]).(0{3}|0{6})Z$";

    private T from;
    private T to;

    public static <T> Range<T> equal(final T value) {
        return Range.<T>builder().from(value).to(value).build();
    }

    public static <T> Range<T> of(final T from, final T to) {
        return Range.<T>builder().from(from).to(to).build();
    }

    public static <T> Range<T> of(final T from) {
        return Range.<T>builder().from(from).build();
    }

    public static <T> Range<T> from(final T from) {
        return Range.<T>builder().from(from).build();
    }

    public static <T> Range<T> to(final T to) {
        return Range.<T>builder().to(to).build();
    }

    public boolean isPresent() {
        return from != null || to != null;
    }

    public boolean fullPresent() {
        return from != null && to != null;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj.getClass() != this.getClass()) {
            return false;
        }
        final Range other = (Range) obj;
        if (!Objects.equals(this.from, other.from)) {
            return false;
        }
        if (!Objects.equals(this.to, other.to)) {
            return false;
        }
        return true;
    }

    public T getFrom() {
        T result = from;
        if (from != null) {
            if (from.toString().matches(LOCAL_DATE_REGEX)) {
                result = (T) LocalDate.parse(from.toString());
            } else if (from.toString().matches(LOCAL_TIME_REGEX)) {
                result = (T) LocalTime.parse(from.toString());
            } else if (from.toString().matches(LOCAL_DATE_TIME_REGEX)) {
                result = (T) Instant.parse(from.toString());
            }
        }
        return result;
    }

    public T getTo() {
        T result = to;
        if (to != null) {
            if (to.toString().matches(LOCAL_DATE_REGEX)) {
                result = (T) LocalDate.parse(to.toString());
            } else if (to.toString().matches(LOCAL_TIME_REGEX)) {
                result = (T) LocalTime.parse(to.toString());
            } else if (to.toString().matches(LOCAL_DATE_TIME_REGEX)) {
                result = (T) Instant.parse(to.toString());
            }
        }
        return result;
    }


}
