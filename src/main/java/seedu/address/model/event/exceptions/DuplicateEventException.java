package seedu.address.model.event.exceptions;

/**
 * Signals that the operation will result in clashing Events.
 * Events are considered duplicate if they have same Title and DateTime.
 */
public class DuplicateEventException extends RuntimeException {
    public DuplicateEventException() {
        super("Operation results in duplicate events!");
    }
}
