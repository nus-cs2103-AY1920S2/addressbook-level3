package seedu.address.logic.parser.notes;

import seedu.address.logic.commands.notes.NotesCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Represents a Parser for NotesComamnd
 */
public class NotesCommandParser implements Parser<NotesCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the NotesCommand
     * and returns a NotesCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public NotesCommand parse(String args) throws ParseException {
        if (!args.trim().equals("")) {
            throw new ParseException("Notes command does not accept any arguments.");
        }
        return new NotesCommand();
    }
}
