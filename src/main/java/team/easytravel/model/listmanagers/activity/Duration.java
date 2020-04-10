package team.easytravel.model.listmanagers.activity;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import team.easytravel.commons.util.AppUtil;

/**
 * Represents a Activity's duration in an ActivityManager.
 * Guarantees: immutable; is valid as declared in {@link #isValidDuration(Integer)}
 */
public class Duration {
    public static final String MESSAGE_CONSTRAINTS = "Time (Hours) must be a valid number less than 24";

    private static final int MAX_VALUE = 24;
    private static final int MIN_VALUE = 1;
    public static final Predicate<Integer> VALIDATION_PREDICATE = i -> i >= MIN_VALUE && i <= MAX_VALUE;


    public final Integer value;

    public Duration(Integer duration) {
        requireNonNull(duration);
        AppUtil.checkArgument(isValidDuration(duration), MESSAGE_CONSTRAINTS);
        value = duration;
    }

    /**
     * Returns true if a given string is a valid duration.
     */
    public static boolean isValidDuration(Integer test) {
        return VALIDATION_PREDICATE.test(test);
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Duration // instanceof handles nulls
                && value.equals(((Duration) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
