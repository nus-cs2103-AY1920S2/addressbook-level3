package seedu.address.model.graph;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the y-axis of the graph of exercises.
 */
public class Axis {
    public static final String MESSAGE_CONSTRAINTS =
        "The y-axis can only be \"reps\" or \"weight\" (case insensitive) and it should not be blank. ";

    public final AxisType value;

    public Axis(String axis) {
        requireNonNull(axis);
        checkArgument(isValidAxis(axis), MESSAGE_CONSTRAINTS);
        value = AxisType.getAxisType(axis.toLowerCase());
    }

    public AxisType getAxisType() {
        return value;
    }

    public static boolean isValidAxis(String test) {
        return test.equalsIgnoreCase("reps") || test.equalsIgnoreCase("weight");
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Axis // instanceof handles nulls
                        && value.equals(((Axis) other).value)); // state check
    }
}
