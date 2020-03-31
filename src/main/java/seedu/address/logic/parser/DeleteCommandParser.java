package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.FLAG_ORDER_BOOK;
import static seedu.address.logic.parser.CliSyntax.FLAG_RETURN_BOOK;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {

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
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE), pe);
        }
    }

    private String removeFlagString(Flag flag, String value) throws ParseException {
        String flagRegex = getFlagRegex(flag);
        return value.replaceAll(flagRegex, "");
    }

    private Flag getFlag(String args) throws ParseException {
        String orderListRegex = getFlagRegex(FLAG_ORDER_BOOK);
        String returnListRegex = getFlagRegex(FLAG_RETURN_BOOK);

        if (hasRegex(orderListRegex, args)) {
            return FLAG_ORDER_BOOK;
        }
        if (hasRegex(returnListRegex, args)) {
            return FLAG_RETURN_BOOK;
        }
        throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }

    private String getFlagRegex(Flag flag) throws ParseException {
        String regex = "\\s*%s\\s+";
        if (flag.equals(FLAG_ORDER_BOOK)) {
            return String.format(regex, FLAG_ORDER_BOOK);
        }
        if (flag.equals(FLAG_RETURN_BOOK)) {
            return String.format(regex, FLAG_RETURN_BOOK);
        }
        throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }

    private boolean hasRegex(String regex, String searchTerm) {
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(searchTerm);
        return m.find();
    }
}
