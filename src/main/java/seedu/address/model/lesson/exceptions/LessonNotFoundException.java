package seedu.address.model.lesson.exceptions;

/**
 * Signals that the operation is unable to find the specified Lesson.
 */
public class LessonNotFoundException extends RuntimeException {
    public LessonNotFoundException() {
        super("Operation is unable to find the specified Lesson.");
    }
}
