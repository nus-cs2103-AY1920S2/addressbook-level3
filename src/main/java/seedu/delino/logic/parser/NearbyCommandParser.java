package seedu.delino.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.delino.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.delino.commons.core.Messages.MESSAGE_MISSING_FLAG;
import static seedu.delino.logic.parser.CliSyntax.FLAG_ORDER_BOOK;
import static seedu.delino.logic.parser.CliSyntax.FLAG_RETURN_BOOK;

import java.util.logging.Logger;
import java.util.regex.Pattern;

import seedu.delino.commons.core.LogsCenter;
import seedu.delino.logic.commands.NearbyCommand;
import seedu.delino.logic.parser.exceptions.ParseException;

//@@author JeremyLoh
/**
 * Parses input arguments and creates a new NearbyCommand object.
 */
public class NearbyCommandParser implements Parser<NearbyCommand> {
    public static final String NEWLINE = System.lineSeparator();
    private static final Logger logger = LogsCenter.getLogger(NearbyCommandParser.class);

    /**
     * Parses the given {@code String} of arguments in the context of the NearbyCommand
     * and returns a NearbyCommand object for execution.
     *
     * @param args to be parsed
     * @throws ParseException if {@code args} does not conform the expected format
     */
    @Override
    public NearbyCommand parse(String args) throws ParseException {
        try {
            String trimmedArgs = args.trim();
            if (isValidArg(trimmedArgs)) {
                return new NearbyCommand(trimmedArgs);
            }
        } catch (ParseException pe) {
            logger.info("Invalid Nearby Command arguments given for parsing");
            throw new ParseException(String.format("%s" + NEWLINE + "%s", pe.getMessage(),
                    NearbyCommand.MESSAGE_USAGE));
        }
        logger.info("Invalid arguments given for NearbyCommandParser");
        throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                NearbyCommand.MESSAGE_USAGE));
    }

    /**
     * Checks if given {@code arg} has valid arguments
     */
    private boolean isValidArg(String arg) throws ParseException {
        requireNonNull(arg);
        if (arg.length() == 0) {
            return false;
        }
        String[] argWords = arg.trim().split("\\s+");
        boolean hasOrderFlag = hasRegex(FLAG_ORDER_BOOK.toString(), argWords);
        boolean hasReturnFlag = hasRegex(FLAG_RETURN_BOOK.toString(), argWords);
        if ((hasOrderFlag && hasReturnFlag) || (!hasOrderFlag && !hasReturnFlag)) {
            throw new ParseException(MESSAGE_MISSING_FLAG);
        } else {
            return true;
        }
    }

    /**
     * Check if the given {@code regex} is present (full match) in any argument in the given {@code searchValues}.
     */
    private boolean hasRegex(String regex, String[] searchValues) {
        requireNonNull(regex);
        requireNonNull(searchValues);
        for (String value : searchValues) {
            if (Pattern.matches(regex, value)) {
                return true;
            }
        }
        return false;
    }
}
