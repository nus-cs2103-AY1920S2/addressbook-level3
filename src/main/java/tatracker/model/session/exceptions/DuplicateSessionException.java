//@@author Chuayijing

package tatracker.model.session.exceptions;

/**
 * Signals that the operation will result in duplicate {@code Session}
 * (Session are considered duplicates if they have the same identity).
 */
public class DuplicateSessionException extends RuntimeException {
    public DuplicateSessionException() {
        super("Operation would result in duplicate session");
    }
}
