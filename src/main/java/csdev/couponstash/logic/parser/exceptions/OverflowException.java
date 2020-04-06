package csdev.couponstash.logic.parser.exceptions;

import csdev.couponstash.commons.exceptions.IllegalValueException;

/**
 * Represents an error thrown when integer overflow occurs
 */
public class OverflowException extends IllegalValueException {

    public OverflowException(String message) {
        super(message);
    }
}
