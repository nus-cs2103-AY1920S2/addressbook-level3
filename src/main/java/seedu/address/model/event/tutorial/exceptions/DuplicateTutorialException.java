package seedu.address.model.event.tutorial.exceptions;

/**
 * Signals that the operation will result in duplicate Tutorials (Tutorials are considered duplicates
 * if they have the same module and tutorial name).
 */

public class DuplicateTutorialException extends RuntimeException {
    public DuplicateTutorialException() {
        super("Operation would result in duplicate tutorials");
    }
}
