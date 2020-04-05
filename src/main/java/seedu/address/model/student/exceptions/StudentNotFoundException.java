package seedu.address.model.student.exceptions;

import static seedu.address.commons.core.Messages.MESSAGE_STUDENT_NOT_FOUND;

/**
 * Signals that the operation is unable to find the specified student.
 */
public class StudentNotFoundException extends RuntimeException {
    public StudentNotFoundException() {
        super(MESSAGE_STUDENT_NOT_FOUND);
    }
}
