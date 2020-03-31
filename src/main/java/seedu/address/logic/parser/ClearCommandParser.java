package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.FLAG_FORCE_CLEAR;
import static seedu.address.logic.parser.CliSyntax.FLAG_ORDER_BOOK;
import static seedu.address.logic.parser.CliSyntax.FLAG_RETURN_BOOK;

import java.util.HashSet;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ClearCommand object.
 */
public class ClearCommandParser implements Parser<ClearCommand> {
    private static final Logger logger = LogsCenter.getLogger(ClearCommandParser.class);
    /**
     * Parses the given {@code String} of arguments in the context of the ClearCommand
     * and returns an ClearCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ClearCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            logger.fine("Arguments are empty - passing null value to Clear Command");
            return new ClearCommand(null);
        }

        return new ClearCommand(retrieveFlags(trimmedArgs));
    }

    /**
     * Retrieve the flags from the input and return hash set with the flags back.
     * @param input user input after the clear command.
     * @return hash set with the flags back.
     * @throws ParseException when invalid format occurs.
     */
    private HashSet<String> retrieveFlags(String input) throws ParseException {
        HashSet<String> flags = new HashSet<>();

        logger.fine("Processing the input data...");
        String[] values = input.split(" ");
        for (String flag : values) {
            if (!isValidFlag(flag) || isInvalidFlagFormat(flag, flags)) {
                logger.info("Encountered invalid flag input or invalid flag format.");
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ClearCommand.MESSAGE_USAGE));
            }

            flags.add(flag);
        }
        logger.fine("Passing the flags back");
        return flags;
    }

    /**
     * Check whether the flag belongs to one of the three valid flags for clear command.
     * @param flag to be checked.
     * @return true if it belongs to one of the three valid flags, otherwise return false.
     */
    private boolean isValidFlag(String flag) {
        logger.fine("Check whether is it a valid flag");
        return flag.equals(FLAG_FORCE_CLEAR.toString()) || flag.equals(FLAG_ORDER_BOOK.toString())
                || flag.equals(FLAG_RETURN_BOOK.toString());
    }

    /**
     * Check whether there are '-o' flag inside flags if the current flag is '-r' flag and vice versa.
     * @param flag to be checked.
     * @param flags flags that appeared before the current flag in the same command line.
     * @return true if '-o' and '-r' flags are found in the same command line, otherwise return false.
     */
    private boolean isInvalidFlagFormat(String flag, HashSet<String> flags) {
        logger.fine("Ensure both '-r' and '-o' flags are not in the arguments");
        return (flag.equals(FLAG_RETURN_BOOK.toString()) && flags.contains(FLAG_ORDER_BOOK.toString()))
                || (flag.equals(FLAG_ORDER_BOOK.toString()) && flags.contains(FLAG_RETURN_BOOK.toString()));
    }
}
