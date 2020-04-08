package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTES_FILE_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTES_PATH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTES_PATH_TYPE;

import java.io.File;
import java.util.stream.Stream;

import seedu.address.logic.commands.NotesOpenCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.notes.Notes;

/**
 * Parses input arguments and creates a new NotesCommand object
 */
public class NotesOpenCommandParser implements Parser<NotesOpenCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the NotesCommand
     * and returns an NotesCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public NotesOpenCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NOTES_PATH, PREFIX_NOTES_FILE_NAME, PREFIX_NOTES_PATH_TYPE);

        if (!arePrefixesPresent(argMultimap, PREFIX_NOTES_PATH, PREFIX_NOTES_PATH_TYPE, PREFIX_NOTES_FILE_NAME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, NotesOpenCommand.MESSAGE_USAGE));
        }
        String fullPathName = argMultimap.getValue(PREFIX_NOTES_PATH).get() + File.separator
                + argMultimap.getValue(PREFIX_NOTES_FILE_NAME).get();
        Notes note = ParserUtil.parseNotesOpenOperation(fullPathName,
                argMultimap.getValue(PREFIX_NOTES_PATH_TYPE).get());

        return new NotesOpenCommand(note);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
