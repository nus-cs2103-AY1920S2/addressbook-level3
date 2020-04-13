package seedu.recipe.model.cooked.exceptions;

/**
 * Signals that the operation will result in duplicate Records.
 */
public class DuplicateRecordException extends RuntimeException {
    public DuplicateRecordException() {
        super("Operation would result in duplicate records");
    }
}
