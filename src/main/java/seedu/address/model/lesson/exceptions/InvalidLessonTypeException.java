package seedu.address.model.lesson.exceptions;

/**
 * Signals that the operation cannot proceed because the lesson type provided is invalid.
 */
public class InvalidLessonTypeException extends RuntimeException {
    public InvalidLessonTypeException() {
        super("Lesson type provided is invalid.");
    }
}
