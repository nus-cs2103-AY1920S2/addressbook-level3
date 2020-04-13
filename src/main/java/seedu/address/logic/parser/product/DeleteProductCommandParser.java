package seedu.address.logic.parser.product;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.product.DeleteProductCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteProductCommand object
 */
public class DeleteProductCommandParser implements Parser<DeleteProductCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteProductCommand
     * and returns a DeleteProductCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteProductCommand parse(String args) throws ParseException {
        Index index;

        try {
            index = ParserUtil.parseIndex(args);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteProductCommand.MESSAGE_USAGE), pe);
        }

        return new DeleteProductCommand(index);
    }

}
