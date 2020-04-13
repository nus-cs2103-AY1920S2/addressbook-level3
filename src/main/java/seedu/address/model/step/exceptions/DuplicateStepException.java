package seedu.address.model.step.exceptions;

/**
 * Signals that the operation will result in duplicate Steps (Steps are considered duplicates if they have the same
 * stepDescription).
 */
public class DuplicateStepException extends RuntimeException {
    public DuplicateStepException() {
        super("Operation would result in duplicate step");
    }
}
