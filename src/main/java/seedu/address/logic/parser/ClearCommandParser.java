package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.FLAG_FORCE_CLEAR;
import static seedu.address.logic.parser.CliSyntax.FLAG_ORDER_BOOK;
import static seedu.address.logic.parser.CliSyntax.FLAG_RETURN_BOOK;

import java.util.HashSet;

import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ClearCommand object.
 */
public class ClearCommandParser implements Parser<ClearCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the ClearCommand
     * and returns an ClearCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ClearCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
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

        String[] values = input.split(" ");
        for (String flag : values) {
            if (flag.equals(FLAG_FORCE_CLEAR.toString()) || flag.equals(FLAG_ORDER_BOOK.toString())
                    || flag.equals(FLAG_RETURN_BOOK.toString())) {
                if (flags.contains(flag)) {
                    throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ClearCommand.MESSAGE_USAGE));
                }
                if (flag.equals(FLAG_RETURN_BOOK.toString()) && flags.contains(FLAG_ORDER_BOOK.toString())) {
                    throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ClearCommand.MESSAGE_USAGE));
                } else if (flag.equals(FLAG_ORDER_BOOK.toString()) && flags.contains(FLAG_RETURN_BOOK.toString())) {
                    throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ClearCommand.MESSAGE_USAGE));
                }
                flags.add(flag);
            } else {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ClearCommand.MESSAGE_USAGE));
            }
        }
        return flags;
    }
}
