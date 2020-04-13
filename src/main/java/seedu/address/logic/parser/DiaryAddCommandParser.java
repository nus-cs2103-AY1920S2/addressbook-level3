package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ENTRY_CONTENT;

import java.util.stream.Stream;

import seedu.address.logic.commands.diarycommand.DiaryAddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.diary.DiaryEntry;

/**
 * Dummy Java docs.
 */
public class DiaryAddCommandParser implements Parser<DiaryAddCommand> {

    /**
     * Dummy Java docs.
     */
    public DiaryAddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_ENTRY_CONTENT);

        if (!arePrefixesPresent(argMultimap, PREFIX_ENTRY_CONTENT)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DiaryAddCommand.MESSAGE_USAGE));
        }

        String entryContent = argMultimap.getValue(PREFIX_ENTRY_CONTENT).get();

        DiaryEntry diaryEntry = new DiaryEntry(entryContent);

        return new DiaryAddCommand(diaryEntry);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
