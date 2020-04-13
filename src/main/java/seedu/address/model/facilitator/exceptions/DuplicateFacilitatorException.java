package seedu.address.model.facilitator.exceptions;

/**
 * Signals that the operation will result in duplicate Facilitators
 * (Facilitators are considered duplicates if they have the same identity).
 */
public class DuplicateFacilitatorException extends RuntimeException {
    public DuplicateFacilitatorException() {
        super("Operation would result in duplicate facilitators.");
    }
}
