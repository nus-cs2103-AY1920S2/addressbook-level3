package cookbuddy.logic.parser.exceptions;

import cookbuddy.commons.exceptions.IllegalValueException;

/**
 * Represents a parse error encountered by a parser.
 */
@SuppressWarnings("serial")
public class ParseException extends IllegalValueException {

    public ParseException(String message) {
        super(message);
    }

    public ParseException(String message, Throwable cause) {
        super(message, cause);
    }
}
