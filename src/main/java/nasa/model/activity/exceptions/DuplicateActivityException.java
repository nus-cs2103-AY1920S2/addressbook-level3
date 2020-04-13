package nasa.model.activity.exceptions;

/**
 * Signals that the operation will result in duplicate Activity (Activity are considered duplicates if
 * they have the same notes and date.
 */
public class DuplicateActivityException extends RuntimeException {
    public DuplicateActivityException() {
        super("Operation would result in duplicate activity");
    }
}
