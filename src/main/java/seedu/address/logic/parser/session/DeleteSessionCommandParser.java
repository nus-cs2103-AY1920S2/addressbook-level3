package seedu.address.logic.parser.session;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteSessionCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteSessionCommand object
 */
public class DeleteSessionCommandParser implements Parser<DeleteSessionCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteSessionCommand
     * and returns an DeleteSessionCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteSessionCommand parse(String args) throws ParseException {

        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteSessionCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteSessionCommand.MESSAGE_INVALID_INDEX), pe);
        }
    }
}
