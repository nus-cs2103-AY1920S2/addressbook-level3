//@@author fatin99

package tatracker.model.student.exceptions;

/**
 * Signals that the operation will result in duplicate Student (Student are considered duplicates if they have the same
 * identity).
 */
public class DuplicateStudentException extends RuntimeException {
    public DuplicateStudentException() {
        super("Operation would result in duplicate students");
    }
}
