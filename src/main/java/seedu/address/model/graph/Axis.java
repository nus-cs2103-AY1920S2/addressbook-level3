package seedu.address.model.graph;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

public class Axis {
    public static final String MESSAGE_CONSTRAINTS = 
        "The y-axis can only be exactly \"reps\" or \"weight\" and it should not be blank. ";
    
    public final String value;

    public Axis(String axis) {
        requireNonNull(axis);
        checkArgument(isValidAxis(axis), MESSAGE_CONSTRAINTS);
        value = axis;
    }

    public static boolean isValidAxis(String test) {
        return (test.equals("reps") || test.equals("weight"))
                ? true
                : false;
    }
}