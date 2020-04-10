package team.easytravel.model.util.uniquelist.exceptions;

/**
 * Signals that the operation will result in duplicate element.
 * Elements are considered duplicates if they are equal.
 */
public class DuplicateElementException extends RuntimeException {
    public DuplicateElementException() {
        super("Operation would result in duplicate elements.");
    }
}
