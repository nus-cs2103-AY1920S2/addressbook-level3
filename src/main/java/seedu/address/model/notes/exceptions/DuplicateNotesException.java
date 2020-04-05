package seedu.address.model.notes.exceptions;

import static seedu.address.commons.core.Messages.MESSAGE_DUPLICATE_NOTES;

/**
 * Represents an Exception handling scenario of duplicated notes.
 */
public class DuplicateNotesException extends RuntimeException {
    public DuplicateNotesException() {
        super(MESSAGE_DUPLICATE_NOTES);
    }
}
