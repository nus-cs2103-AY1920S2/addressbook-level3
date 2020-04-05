package seedu.address.model.academics.exceptions;

/**
 * Signals that the operation will result in duplicate Assessment (Assessment are considered duplicates if they
 * have the same description name).
 */
public class DuplicateAssessmentException extends RuntimeException {
    public DuplicateAssessmentException() {
        super("Operation would result in duplicate assessments");
    }
}
