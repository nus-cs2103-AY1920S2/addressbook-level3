package team.easytravel.model.trip;

import static team.easytravel.commons.util.AppUtil.checkArgument;
import static team.easytravel.commons.util.CollectionUtil.requireAllNonNull;

import java.util.function.Predicate;


/**
 * Represent a Budget in the Trip.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Budget {

    public static final String MESSAGE_CONSTRAINTS = "Budget should be a whole number greater than 0 and less than or "
            + "equal to 1,000,000";

    private static final int MIN_VALUE = 1;
    private static final int MAX_VALUE = 1_000_000;
    public static final Predicate<Integer> VALIDATION_PREDICATE = i -> i >= MIN_VALUE && i <= MAX_VALUE;

    public final Integer value;

    public Budget(Integer amount) {
        requireAllNonNull(amount);
        checkArgument(isValidBudget(amount), MESSAGE_CONSTRAINTS);
        value = amount;
    }

    /**
     * Returns true if a given Integer is a valid budget number.
     */
    public static boolean isValidBudget(Integer test) {
        return VALIDATION_PREDICATE.test(test);
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
