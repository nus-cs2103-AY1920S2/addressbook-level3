package seedu.recipe.model.achievement;

/**
 * Signals that the operation will result in duplicate Quotes
 */
public class DuplicateQuoteException extends RuntimeException {
    public DuplicateQuoteException() {
        super("Operation would result in duplicate quotes");
    }
}
