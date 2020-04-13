package seedu.nova.model.progresstracker;

import static java.util.Objects.requireNonNull;
import static seedu.nova.commons.util.AppUtil.checkArgument;

/**
 * Creates an object encapsulating the task description
 */
public class TaskDesc {
    public static final String MESSAGE_CONSTRAINTS = "Task description should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";

    private String desc;

    /**
     * Creates TaskDesc object
     * @param desc task description
     */
    public TaskDesc(String desc) {
        requireNonNull(desc);
        checkArgument(isValidTaskDesc(desc), MESSAGE_CONSTRAINTS);
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     * Returns true if a given string is a valid email.
     */
    public static boolean isValidTaskDesc(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return desc;
    }
}
