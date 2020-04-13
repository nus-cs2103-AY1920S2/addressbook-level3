//@@author eedenong
package seedu.jelphabot.model.task;
import static seedu.jelphabot.commons.util.AppUtil.checkArgument;

/**
 * Enum class representative of the different possible priorities that a task can have.
 * By default, the priority of a task is set to MEDIUM.
 */
public enum Priority {
    HIGH,
    MEDIUM,
    LOW;

    public static final String MESSAGE_CONSTRAINTS =
            "Priority should be either -1, 0, or 1, or the string representation HIGH, MEDIUM or LOW";

    /**
     * Returns if the given string is a valid priority.
     * @param test The date to be checked.
     * @return The boolean representing whether the priority provided is valid.
     */
    public static boolean isValidPriority(String test) {
        switch (test.toLowerCase()) {
        case "high":
        case "medium":
        case "low":
        case "-1":
        case "1":
        case "0":
            return true;
        default:
            return false;
        }
    }

    /**
     * Converts input String to Priority object.
     * @param test String to convert.
     * @return Priority of the input string.
     */
    public static Priority toPriority(String test) {
        switch (test.toLowerCase()) {
        case "high": // fallthrough
        case "1":
            return Priority.HIGH;
        case "medium": // fallthrough
        case "0":
            return Priority.MEDIUM;
        case "low": // fallthrough
        case "-1":
            return Priority.LOW;
        default:
            checkArgument(isValidPriority(test), MESSAGE_CONSTRAINTS);
            return null;
        }
    }
}
