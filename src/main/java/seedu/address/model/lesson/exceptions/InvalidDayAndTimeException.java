package seedu.address.model.lesson.exceptions;

/**
 * Signals that the operation cannot proceed because the day and time provided is invalid.
 */
public class InvalidDayAndTimeException extends RuntimeException {
    public InvalidDayAndTimeException() {
        super("Day and time provided are invalid.");
    }
}
