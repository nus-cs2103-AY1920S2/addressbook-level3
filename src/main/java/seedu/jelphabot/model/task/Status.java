//@@author yaojiethng
package seedu.jelphabot.model.task;

/**
 * Enum class containing the possible states of a task
 */
public enum Status {
    COMPLETE,
    INCOMPLETE;

    public static Status toStatus(String test) {
        return Status.valueOf(test.strip().toUpperCase());
    }
}
