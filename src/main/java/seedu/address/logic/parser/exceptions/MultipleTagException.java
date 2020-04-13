package seedu.address.logic.parser.exceptions;

/**
 * Represents a parse error encountered by a parser when multiple tags were given by a user.
 */
public class MultipleTagException extends ParseException {

    public static final String MULTIPLE_TAGS_EXCEPTION_MESSAGE = "Multiple valid tags were provided. "
            + "Please try again with only one valid tag!";

    public MultipleTagException(String message) {
        super(message);
    }

    public MultipleTagException() {
        super(MULTIPLE_TAGS_EXCEPTION_MESSAGE);
    }
}
