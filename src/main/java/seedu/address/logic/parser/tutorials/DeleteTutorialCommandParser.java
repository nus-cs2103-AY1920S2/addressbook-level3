package seedu.address.logic.parser.tutorials;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.tutorials.DeleteTutorialCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteTutorialCommand object
 */
public class DeleteTutorialCommandParser implements Parser<DeleteTutorialCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteTutorialCommand
     * and returns a DeleteTutorialCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public DeleteTutorialCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteTutorialCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTutorialCommand.MESSAGE_USAGE), pe);
        }
    }

}
