package seedu.address.model.event.tutorial.exceptions;

/**
 * Signals that the operation will result in duplicate Students in the same Tutorial (Students are
 * considered identical if they have the same matriculation number).
 */

public class DuplicateTutorialStudentException extends RuntimeException {
    public DuplicateTutorialStudentException() {
        super("Operation would result in duplicate students in the same tutorial");
    }
}
