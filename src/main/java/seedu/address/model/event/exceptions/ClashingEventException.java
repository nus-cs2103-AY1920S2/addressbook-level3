package seedu.address.model.event.exceptions;

/**
 * Signals that the operation will result in clashing Events.
 * Events are considered duplicate if they have same Title and DateTime.
 */
public class ClashingEventException extends RuntimeException {
    public ClashingEventException() {
        super("Operation results in clashing events!");
    }
}
