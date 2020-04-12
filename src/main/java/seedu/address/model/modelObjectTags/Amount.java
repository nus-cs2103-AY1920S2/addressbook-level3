package seedu.address.model.modelObjectTags;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's salary number in the address book. Guarantees: immutable; is valid as
 * declared in {@link #isValidAmount(String)}
 */
public class Amount {


    public static final String MESSAGE_CONSTRAINTS =
            "Amount numbers should only contain numbers (with negative sign in front if needed), it should be at least 1 digits long" +
                    "and the value does not over 10 millions";
    public static final String VALIDATION_REGEX_WITH_DECIMAL = "^(-*\\s*[0-9]+\\.[0-9]+)$";
    public static final String VALIDATION_REGEX_WITHOUT_DECIMAL = "^(-*\\s*[0-9]+)$";
    public static final Integer VALIDATION_RANGE = 10000000;
    public final String value;

    /**
     * Constructs a {@code Amount}.
     *
     * @param amount A valid amount number.
     */
    public Amount(String amount) {
        requireNonNull(amount);
        checkArgument(isValidAmount(amount), MESSAGE_CONSTRAINTS);
        value = amount;
    }

    /**
     * Returns true if a given string is a valid salary number.
     */
    public static boolean isValidAmount(String test) {
        return (test.matches(VALIDATION_REGEX_WITH_DECIMAL) || test.matches(VALIDATION_REGEX_WITHOUT_DECIMAL))
                && isValidRange(test);
    }

    /**
     * Returns true if a given value in string is in range.
     */
    public static boolean isValidRange(String test) {
        float amount = 0;
        if (test.substring(0, 1).equals("-")) {
            amount = Float.parseFloat(test.replaceFirst("-", ""));
        } else {
            amount = Float.parseFloat(test);
        }
        return !(Math.abs(amount) > VALIDATION_RANGE);
    }

    /**
     * Returns the value of amount in float type.
     *
     * @return amount in float type.
     */
    public float getValue() {
        float amount = 0;
        if (this.value.substring(0, 1).equals("-")) {
            amount = Float.parseFloat(value.replaceFirst("-", ""));
        } else {
            amount = Float.parseFloat(value);
        }
        return amount;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Amount // instanceof handles nulls
                && value.equals(((Amount) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
