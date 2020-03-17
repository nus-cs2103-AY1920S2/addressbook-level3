package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTES_OPERATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTES_PATH;

import java.util.stream.Stream;

import seedu.address.logic.commands.NotesCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.notes.Notes;

/**
 * Parses input arguments and creates a new NotesCommand object
 */
public class NotesCommandParser implements Parser<NotesCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the NotesCommand
     * and returns an NotesCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public NotesCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NOTES_OPERATION, PREFIX_NOTES_PATH);

        if (!arePrefixesPresent(argMultimap, PREFIX_NOTES_OPERATION, PREFIX_NOTES_PATH)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, NotesCommand.MESSAGE_USAGE));
        }

        Notes note = ParserUtil.parseNotesOperation(argMultimap.getValue(PREFIX_NOTES_OPERATION).get(),
                argMultimap.getValue(PREFIX_NOTES_PATH).get());

        return new NotesCommand(note);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
