package team.easytravel.model.trip;

import static team.easytravel.commons.util.AppUtil.checkArgument;
import static team.easytravel.commons.util.CollectionUtil.requireAllNonNull;

import java.util.function.Predicate;

/**
 * Represent a ExchangeRate in the Trip.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class ExchangeRate {

    public static final String MESSAGE_CONSTRAINTS =
            "ExchangeRate should be a number greater than 0.0 and less than or "
            + "equal to 100.0";

    private static final double MIN_VALUE = 0.0;
    private static final double MAX_VALUE = 100.0;
    public static final Predicate<Double> VALIDATION_PREDICATE = i -> i > MIN_VALUE && i <= MAX_VALUE;

    public final Double value;

    public ExchangeRate(Double amount) {
        requireAllNonNull(amount);
        checkArgument(isValidExchangeRate(amount), MESSAGE_CONSTRAINTS);
        value = amount;
    }

    /**
     * Returns true if a given Double is a valid Exchange Rate.
     */
    public static boolean isValidExchangeRate(Double test) {
        return VALIDATION_PREDICATE.test(test);
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
