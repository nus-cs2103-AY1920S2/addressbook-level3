package seedu.address.model.activity;

import static java.util.Objects.requireNonNull;
//import static seedu.address.commons.util.AppUtil.checkArgument;

public class TaskName {

    public static final String MESSAGE_CONSTRAINTS = "Task can take any values, and it should not be blank";

    /*
     * The first character of the task must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";

    public final String codeID;

    /**
     * Constructs an {@code TaskName}.
     *
     * @param codeID A valid code name.
     */
    public TaskName(String codeID) {
        requireNonNull(codeID);
        //checkArgument(isValidAddress(address), MESSAGE_CONSTRAINTS);
        this.codeID = codeID;
    }

    /**
     * Returns true if a given string is a valid task name.
     */
    public static boolean isValidTask(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return codeID;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskName // instanceof handles nulls
                && codeID.equals(((TaskName) other).codeID)); // state check
    }

    @Override
    public int hashCode() {
        return codeID.hashCode();
    }

}