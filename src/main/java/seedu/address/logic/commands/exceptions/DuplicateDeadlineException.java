package seedu.address.logic.commands.exceptions;

/**
 * Represents a command error encountered when there is already an existing deadline with the same description and date
 */
public class DuplicateDeadlineException extends CommandException {

    public static final String DUPLICATE_DEADLINE_EXCEPTION_MESSAGE = "You have already added a task with the same "
            + "task name to this module!";

    public DuplicateDeadlineException(String message) {
        super(message);
    }

    public DuplicateDeadlineException() {
        super(DUPLICATE_DEADLINE_EXCEPTION_MESSAGE);
    }

}
