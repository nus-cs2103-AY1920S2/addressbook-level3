package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTES_PATH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTES_PATH_TYPE;

import java.util.stream.Stream;

import seedu.address.logic.commands.NotesListCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.notes.Notes;

/**
 * Parses input arguments and creates a new NotesCommand object
 */
public class NotesListCommandParser implements Parser<NotesListCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the NotesCommand
     * and returns an NotesCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public NotesListCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NOTES_PATH, PREFIX_NOTES_PATH_TYPE);

        if (!arePrefixesPresent(argMultimap, PREFIX_NOTES_PATH, PREFIX_NOTES_PATH_TYPE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, NotesListCommand.MESSAGE_USAGE));
        }

        Notes note = ParserUtil.parseNotesListOperation(argMultimap.getValue(PREFIX_NOTES_PATH).get(),
                argMultimap.getValue(PREFIX_NOTES_PATH_TYPE).get());

        return new NotesListCommand(note);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
