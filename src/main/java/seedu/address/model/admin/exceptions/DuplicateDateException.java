package seedu.address.model.admin.exceptions;

import static seedu.address.commons.core.Messages.MESSAGE_DUPLICATE_DATE_ADMIN;

/**
 * Signals that the operation will result in duplicate Dates (Dates are considered duplicates if they
 * have the same date).
 */
public class DuplicateDateException extends RuntimeException {
    public DuplicateDateException() {
        super(MESSAGE_DUPLICATE_DATE_ADMIN);
    }
}
