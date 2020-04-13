//@@author aakanksha-rai

package tatracker.model.module.exception;

/**
 * Signals that the operation will result in duplicate Modules
 * (Modules are considered duplicates if they have the same
 * identifiers).
 */
public class DuplicateModuleException extends RuntimeException {
    public DuplicateModuleException() {
        super("Operation would result in duplicate modules");
    }
}
