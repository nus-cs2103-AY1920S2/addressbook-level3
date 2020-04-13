package seedu.address.model.mod.exceptions;

/**
 * Signals that the operation will result in duplicate modules (Modules are considered duplicates
 * if they have the same module code).
 */

public class DuplicateModException extends RuntimeException {
    public DuplicateModException() {
        super("Operation would result in duplicate modules");
    }
}
