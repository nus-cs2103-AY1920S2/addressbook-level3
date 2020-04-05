package seedu.address.logic.parser.notes;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.notes.NotesDeleteCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Represents a Parser for NotesDeleteCommand
 */
public class NotesDeleteCommandParser implements Parser<NotesDeleteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the NotesDeleteCommand
     * and returns a NotesDeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public NotesDeleteCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new NotesDeleteCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, NotesDeleteCommand.MESSAGE_USAGE), pe);
        }
    }
}
