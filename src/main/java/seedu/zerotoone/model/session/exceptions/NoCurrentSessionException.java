package seedu.zerotoone.model.session.exceptions;

/**
 * Signals that there is no current session in progress.
 */
public class NoCurrentSessionException extends RuntimeException {
    public NoCurrentSessionException() {
        super("No Session in progress!");
    }
}
