package seedu.address.logic.parser.parserDelete;

import seedu.address.logic.commands.commandDelete.DeleteAssignmentCommand;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.modelObjectTags.ID;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteAssignmentCommandParser extends DeleteCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand and returns a
     * DeleteCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteAssignmentCommand parse(String args) throws ParseException {
        try {
            ID id = ParserUtil.parseID(args);
            return new DeleteAssignmentCommand(id);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteAssignmentCommand.MESSAGE_USAGE), pe);
        }
    }

}
