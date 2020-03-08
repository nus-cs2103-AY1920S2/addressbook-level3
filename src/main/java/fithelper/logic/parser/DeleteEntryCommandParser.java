package fithelper.logic.parser;

import static fithelper.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import fithelper.commons.core.index.Index;
import fithelper.logic.commands.DeleteEntryCommand;
import fithelper.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteEntryCommand object
 */
public class DeleteEntryCommandParser implements Parser<DeleteEntryCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteEntryCommand
     * and returns a DeleteEntryCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteEntryCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteEntryCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteEntryCommand.MESSAGE_USAGE), pe);
        }
    }

}
