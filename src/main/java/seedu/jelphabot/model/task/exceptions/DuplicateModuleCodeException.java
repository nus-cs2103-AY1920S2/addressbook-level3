package seedu.jelphabot.model.task.exceptions;

/**
 * Signals that the operation will result in duplicate ModuleCode (Module Codes are considered duplicates if they have
 * the same internal string).
 */
public class DuplicateModuleCodeException extends RuntimeException {
    public DuplicateModuleCodeException() {
        super("Operation would result in duplicate tasks");
    }
}
