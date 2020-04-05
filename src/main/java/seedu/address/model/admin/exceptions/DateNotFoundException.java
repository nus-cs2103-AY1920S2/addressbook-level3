package seedu.address.model.admin.exceptions;

import static seedu.address.commons.core.Messages.MESSAGE_DATE_NOT_FOUND_ADMIN;

/**
 * Signals that the operation is unable to find the specified date.
 */
public class DateNotFoundException extends RuntimeException {
    public DateNotFoundException() {
        super(MESSAGE_DATE_NOT_FOUND_ADMIN);
    }
}
