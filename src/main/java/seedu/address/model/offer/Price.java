package seedu.address.model.offer;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

public class Price {
    final long centValue;

    public static final String PRICE_CONSTRAINTS = "Price should only be\n"
            + "1. a positive number with\n"
            + "2. at most 2 digits after the decimal point and\n"
            + "3. at most 10 digits before the decimal point and\n"
            + "4. no special characters, including the dollar sign.";
    public static final String VALIDATION_REGEX = "^\\d{1,10}(\\.(\\d{0,2})?)?$";

    /**
     * Constructs a {@code Price}
     * @param dollars the string representation of the price in dollars
     */
    public Price(String dollars) {
        requireNonNull(dollars);
        checkArgument(isValidPrice(dollars), PRICE_CONSTRAINTS);
        centValue = parseCents(dollars);
    }

    /**
     * Returns the value in cents of the given value in dollars.
     * @param dollars the string representation of the value in dollars
     * @return the value in cents
     */
    private long parseCents(String dollars) {
        // rounding is necessary due to floating point approximation
        double dollarValue = Double.parseDouble(dollars);
        return Math.round(dollarValue * 100);
    }

    public long getCentValue() {
        return centValue;
    }

    /**
     * Returns if a given string is a valid price.
     */
    public static boolean isValidPrice(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return String.format("$%d.%02d", centValue / 100, centValue % 100);
    }
}

