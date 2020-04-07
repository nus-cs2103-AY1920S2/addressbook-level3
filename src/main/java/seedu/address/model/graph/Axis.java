package seedu.address.model.graph;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the y-axis of the graph of exercises.
 */
public class Axis {
    public static final String MESSAGE_CONSTRAINTS =
        "The y-axis can only be exactly \"reps\" or \"weight\" and it should not be blank. ";

    public final String value;

    public Axis(String axis) {
        requireNonNull(axis);
        checkArgument(isValidAxis(axis), MESSAGE_CONSTRAINTS);
        value = axis;
    }

    public boolean isReps(){
        return value.equals("reps");
    }

    public boolean isWeight() {
        return value.equals("weight");
    }

    public static boolean isValidAxis(String test) {
        return test.equalsIgnoreCase("reps") || test.equalsIgnoreCase("weight");
    }
}
