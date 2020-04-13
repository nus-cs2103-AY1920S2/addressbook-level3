package seedu.eylah.expensesplitter.logic.parser;

import static seedu.eylah.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.eylah.commons.core.index.Index;
import seedu.eylah.commons.logic.parser.Parser;
import seedu.eylah.commons.logic.parser.exception.ParseException;
import seedu.eylah.expensesplitter.logic.commands.DeleteItemCommand;

/**
 * Parses input arguments and creates a new DeleteItemCommand object
 */
public class DeleteItemCommandParser implements Parser<DeleteItemCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteItemCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteItemCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteItemCommand.MESSAGE_USAGE), pe);
        }
    }
}
