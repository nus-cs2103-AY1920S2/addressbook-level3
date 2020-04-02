package seedu.address.model.client;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.math.BigDecimal;

/**
 * Represents current weight (in kg) of a client in FitBiz. Guarantees:
 * immutable; is valid as declared in {@link #isValidWeight(String)}
 */
public class CurrentWeight {

    public static final String MESSAGE_CONSTRAINTS = "Input weight must either be whole or decimal number"
            + " (eg. 65 or 86.22)";
    public static final String VALIDATION_REGEX = "[0-9]{1,3}(\\.[0-9]{0,3})?";
    private static final String EMPTY_STRING = "";

    public final String value;

    /**
     * Constructs a {@code CurrentWeight}.
     *
     * @param weight A valid weight.
     */
    public CurrentWeight(String weight) {
        requireNonNull(weight);
        checkArgument(isValidWeight(weight), MESSAGE_CONSTRAINTS);
        String formattedWeight = formatWeight(weight);
        value = formattedWeight;
    }

    private static boolean isEmptyString(String toTest) {
        return toTest.equals(EMPTY_STRING);
    }

    /**
     * Formats the weight using {@code BigDecimal}.
     *
     * @param weight weight to format
     * @return formatted weight to 2 decimal places
     */
    private static String formatWeight(String weight) {
        if (isEmptyString(weight)) {
            return EMPTY_STRING;
        }
        BigDecimal bd = new BigDecimal(weight);
        return String.format("%1$.2f", bd);
    }

    /**
     * Returns true if a given string is a valid weight.
     */
    public static boolean isValidWeight(String test) {
        return isEmptyString(test) || test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CurrentWeight // instanceof handles nulls
                        && value.equals(((CurrentWeight) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
