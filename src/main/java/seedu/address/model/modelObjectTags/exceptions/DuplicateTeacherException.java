package seedu.address.model.modelObjectTags.exceptions;

/**
 * Signals that the operation will result in duplicate Teachers (Teachers are considered duplicates
 * if they have the same identity).
 */
public class DuplicateTeacherException extends RuntimeException {

    public DuplicateTeacherException() {
        super("Operation would result in duplicate teachers");
    }
}
