package seedu.address.model.student;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Student's temperature in the address book.
 */
public class Temperature {

    public static final String MESSAGE_CONSTRAINTS_1 =
            "Temperature should only contain a 2 digits number followed by 1 decimal place.";
    public static final String MESSAGE_CONSTRAINTS_2 =
            "Temperature should be between 25.0°C and 41.0°C";
    public static final String VALIDATION_REGEX = "\\d{2}" + "\\." + "\\d";
    public final String value;

    /**
     * Constructs a {@code Temperature}.
     *
     * @param temperature A valid temperature.
     */
    public Temperature(String temperature) {
        requireNonNull(temperature);
        checkArgument(isValidTemperatureFirst(temperature), MESSAGE_CONSTRAINTS_1);
        checkArgument(isValidTemperatureSecond(temperature), MESSAGE_CONSTRAINTS_2);
        value = temperature;
    }

    /**
     * Returns true if a given temperature is in the correct format.
     */
    public static boolean isValidTemperatureFirst(String test) {
        if (test.equals("Insert temperature here!")) {
            return true;
        } else {
            return test.matches(VALIDATION_REGEX);
        }
    }

    /**
     * Returns true if a given temperature is in the given range.
     */
    public static boolean isValidTemperatureSecond(String test) {
        if (test.equals("Insert temperature here!")) {
            return true;
        } else {
            Double value = Double.valueOf(test);
            return 25.0 <= value && value <= 41.0;
        }
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Temperature // instanceof handles nulls
                && value.equals(((Temperature) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}

