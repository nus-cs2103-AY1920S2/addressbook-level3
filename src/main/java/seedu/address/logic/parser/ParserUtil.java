package seedu.address.logic.parser;


import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {
    private static final String PREFIX = "-";

    public static final String MESSAGE_NO_ARGUMENTS_BEFORE_PREFIX = "There is no arguments before prefix";

    public static String parseArgumentsBeforePrefix (String arguments) throws ParseException {
        String trimmedArguments = arguments.trim();
        String argumentsBeforePrefix = trimmedArguments.substring(0, trimmedArguments.indexOf(PREFIX)).trim();
        if (argumentsBeforePrefix.equals("")) {
            throw new ParseException(MESSAGE_NO_ARGUMENTS_BEFORE_PREFIX);
        }
        return argumentsBeforePrefix;
    }

    public static String parseArgumentsAfterPrefix (String arguments) throws ParseException {
        String trimmedArguments = arguments.trim();
        String argumentsBeforePrefix = trimmedArguments.substring(0, trimmedArguments.indexOf(PREFIX)).trim();
        if (argumentsBeforePrefix.equals("")) {
            throw new ParseException(MESSAGE_NO_ARGUMENTS_BEFORE_PREFIX);
        }
        return argumentsBeforePrefix;
    }
}
