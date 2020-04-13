package seedu.address.model.event.consult.exceptions;

/**
 * Signals that the operation will result in duplicate Consults (Consults are considered duplicates
 * if they have the same identity).
 */
public class DuplicateConsultException extends RuntimeException {
    public DuplicateConsultException() {
        super("Operation would result in duplicate consults");
    }
}
