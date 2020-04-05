package seedu.address.logic.parser.notes;

import seedu.address.logic.commands.notes.NotesExportCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Represents a Parser for NotesExportCommand
 */
public class NotesExportCommandParser implements Parser<NotesExportCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the NotesExportCommand
     * and returns a NotesExportCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public NotesExportCommand parse(String args) throws ParseException {
        if (!args.trim().equals("")) {
            throw new ParseException("Notes Export command does not accept any arguments.");
        }
        return new NotesExportCommand();
    }
}
