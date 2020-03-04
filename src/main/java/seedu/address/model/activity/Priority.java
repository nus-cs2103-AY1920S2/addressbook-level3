package seedu.address.model.activity;

import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents an activity's priority.
 * Guarantees: an integer of range 1 to 5, default value set to 1 if no value received upon instantiation.
 */
public class Priority {

    public static final String PRIORITY_RANGE_CONSTRAINTS = "Priority should range from 1 to 5 inclusive only.";

    /**
     * Valid integers cannot start with the digit '0'
     */
    public static final String VALID_INTEGER_REGEX = "-?(0|[1-9]\\d*)";
    /*
     * Priority level set to 1 as default value if not specified.
     * Variable is not set as final to allow user to set default priority level (TBD)
     */
    public static int DEFAULT_PRIORITY_LEVEL = 1;

    private final int priorityLevel;

    /**
     * Constructs a {@code Priority} with default priority level, when no argument is passed.
     */
    public Priority() {
        this.priorityLevel = DEFAULT_PRIORITY_LEVEL;
    }

    /**
     * Constructs a {@code Priority} with  the value priorityLevel .
     *
     * @param priorityLevel priority level of the given activity, a String in the integer range of 1 to 5 inclusive.
     */
    public Priority(String priorityLevel) {
        checkArgument(isValidPriorityValue(priorityLevel), PRIORITY_RANGE_CONSTRAINTS);
        this.priorityLevel = Integer.parseInt(priorityLevel);
    }

    /**
     * Returns true if a given priority level string is in the valid integer range of 1 to 5.
     */
    public static boolean isValidPriorityValue(String test) {
        if (test.matches(VALID_INTEGER_REGEX)) {
            int intTest = Integer.parseInt(test);
            if (intTest >= 1 && intTest <= 5) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return Integer.toString(priorityLevel);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Priority // instanceof handles nulls
                && priorityLevel == ((Priority) other).priorityLevel); // state check
    }

}
