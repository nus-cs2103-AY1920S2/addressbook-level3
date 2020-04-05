package seedu.address.logic.parser.notes;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.notes.NotesFilterCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.notes.NotesContainKeywordsPredicate;

/**
 *  Represents a Parser for NotesFilterCommand.
 */
public class NotesFilterCommandParser implements Parser<NotesFilterCommand> {

    /**
     * Parses the given arguments in the context of NotesFilterCommand and
     * returns a NotesFilterCommand object for execution.
     * @param args
     * @throws ParseException if the user input does not conform to the expected format
     */
    public NotesFilterCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    NotesFilterCommand.MESSAGE_USAGE));
        }

        String[] keywords = trimmedArgs.split("\\s+");

        return new NotesFilterCommand(new NotesContainKeywordsPredicate(Arrays.asList(keywords)));
    }
}
