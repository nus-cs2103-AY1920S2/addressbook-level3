package team.easytravel.model.listmanagers.accommodationbooking;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import team.easytravel.commons.util.AppUtil;

/**
 * Represents a AccommodationBooking's day in the AccommodationBookingManager.
 * Guarantees: immutable; is valid as declared in {@link #isValidDay(Integer)}
 */
public class Day {
    public static final String MESSAGE_CONSTRAINTS = "Day should be a positive integer.";

    private static final int MIN_VALUE = 1;
    public static final Predicate<Integer> VALIDATION_PREDICATE = i -> i >= MIN_VALUE;

    public final Integer value;

    /**
     * Constructs an {@code Day}.
     *
     * @param day A valid day within the length of the trip.
     */
    public Day(Integer day) {
        requireNonNull(day);
        AppUtil.checkArgument(isValidDay(day), MESSAGE_CONSTRAINTS);
        value = day;
    }

    /**
     * Returns true if a given string is a valid day.
     */
    public static boolean isValidDay(Integer test) {
        return VALIDATION_PREDICATE.test(test);
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Day // instanceof handles nulls
                && value.equals(((Day) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
