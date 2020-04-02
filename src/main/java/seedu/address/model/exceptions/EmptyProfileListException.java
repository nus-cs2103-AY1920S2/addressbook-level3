package seedu.address.model.exceptions;

/**
 * Exception class to be invoked when a profile list is empty.
 */
public class EmptyProfileListException extends Exception {

    public EmptyProfileListException(String message) {
        super(message);
    }
}
