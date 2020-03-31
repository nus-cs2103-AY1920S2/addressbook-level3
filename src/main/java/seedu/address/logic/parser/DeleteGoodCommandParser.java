package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteGoodCommand;
import seedu.address.logic.commands.DeleteSupplierCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteSupplierCommand object
 */
public class DeleteGoodCommandParser implements Parser<DeleteGoodCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteGoodCommand
     * and returns a DeleteGoodCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteGoodCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteGoodCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteGoodCommand.MESSAGE_USAGE), pe);
        }
    }

}
