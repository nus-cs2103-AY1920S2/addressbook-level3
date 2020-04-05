package seedu.address.model.notes.exceptions;

import static seedu.address.commons.core.Messages.MESSAGE_UNAVAILABLE_NOTES;

/**
 * Represents an Exception handling scenario of invalid notes.
 */
public class NotesUnavailableException extends RuntimeException {
    public NotesUnavailableException() {
        super(MESSAGE_UNAVAILABLE_NOTES);
    }
}
