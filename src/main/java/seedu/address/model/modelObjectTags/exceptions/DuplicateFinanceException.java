package seedu.address.model.modelObjectTags.exceptions;

/**
 * Signals that the operation will result in duplicate Finances (Finances are considered duplicates
 * if they have the same identity).
 */
public class DuplicateFinanceException extends RuntimeException {

    public DuplicateFinanceException() {
        super("Operation would result in duplicate finances");
    }
}
