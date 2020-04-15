package seedu.address.logic.parser.transaction;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.transaction.UndoTransactionCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new UndoTransactionCommand object
 */
public class UndoTransactionCommandParser implements Parser<UndoTransactionCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the UndoTransactionCommand
     * and returns a UndoTransactionCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public UndoTransactionCommand parse(String args) throws ParseException {
        Index index;

        try {
            index = ParserUtil.parseIndex(args);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, UndoTransactionCommand.MESSAGE_USAGE), pe);
        }

        return new UndoTransactionCommand(index);
    }

}

