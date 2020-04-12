package seedu.address.model.modelObjectTags.exceptions;

public class DuplicateAssignmentException extends RuntimeException {
    public DuplicateAssignmentException() {
        super("Operation would result in duplicate Assignments");
    }
}
