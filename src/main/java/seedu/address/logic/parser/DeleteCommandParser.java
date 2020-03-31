package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.FLAG_ORDER_BOOK;
import static seedu.address.logic.parser.CliSyntax.FLAG_RETURN_BOOK;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {
    private static final Logger logger = LogsCenter.getLogger(DeleteCommandParser.class);

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCommand parse(String args) throws ParseException {
        try {
            Flag listType = getFlag(args);
            String removeFlagString = removeFlagString(listType, args);
            Index index = ParserUtil.parseIndex(removeFlagString);
            return new DeleteCommand(index, listType);
        } catch (ParseException pe) {
            logger.info("Invalid Delete Command arguments given for parsing");
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE), pe);
        }
    }

    /**
     * Remove given {@code flag} string representation from given string {@code str}.
     */
    private String removeFlagString(Flag flag, String str) throws ParseException {
        requireNonNull(flag);
        requireNonNull(str);
        String flagRegex = getFlagRegex(flag);
        return str.replaceAll(flagRegex, "");
    }

    /**
     * Convert given {@code args} into its corresponding Flag object.
     */
    private Flag getFlag(String args) throws ParseException {
        requireNonNull(args);
        String orderListRegex = getFlagRegex(FLAG_ORDER_BOOK);
        String returnListRegex = getFlagRegex(FLAG_RETURN_BOOK);

        if (hasRegex(orderListRegex, args)) {
            return FLAG_ORDER_BOOK;
        }
        if (hasRegex(returnListRegex, args)) {
            return FLAG_RETURN_BOOK;
        }
        logger.info(String.format("Invalid arguments given for getFlag function: %s", args));
        throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }

    /**
     * Obtain the string regex for a given {@code flag}.
     */
    private String getFlagRegex(Flag flag) throws ParseException {
        requireNonNull(flag);
        String regex = "\\s*%s\\s+";
        if (flag.equals(FLAG_ORDER_BOOK)) {
            return String.format(regex, FLAG_ORDER_BOOK);
        }
        if (flag.equals(FLAG_RETURN_BOOK)) {
            return String.format(regex, FLAG_RETURN_BOOK);
        }
        logger.info(String.format("Invalid flag given for getFlagRegex: %s", flag.toString()));
        throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }

    /**
     * Check if the given {@code regex} is present (partial match) in the given {@code searchTerm}.
     */
    private boolean hasRegex(String regex, String searchTerm) {
        requireNonNull(regex);
        requireNonNull(searchTerm);
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(searchTerm);
        return m.find();
    }
}
