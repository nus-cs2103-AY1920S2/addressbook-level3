package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DIARY_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ENTRY_ID;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.stream.Stream;

import seedu.address.logic.commands.diarycommand.DiaryShowCommand;
import seedu.address.logic.commands.diarycommand.DiaryShowDateCommand;
import seedu.address.logic.commands.diarycommand.DiaryShowIdCommand;
import seedu.address.logic.parser.exceptions.ParseException;


/**
 * Parses input arguments and creates a new DiaryShowCommand object
 */
public class DiaryShowCommandParser implements Parser<DiaryShowCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DiaryShowCommand
     * and returns an DiaryShowCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DiaryShowCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap1 =
                ArgumentTokenizer.tokenize(args, PREFIX_ENTRY_ID);

        ArgumentMultimap argMultimap2 =
                ArgumentTokenizer.tokenize(args, PREFIX_DIARY_DATE);

        if (arePrefixesPresent(argMultimap1, PREFIX_ENTRY_ID)
                && argMultimap1.getPreamble().isEmpty()) {

            String entryIdString = argMultimap1.getValue(PREFIX_ENTRY_ID).get();
            int entryId = 0;

            try {
                entryId = Integer.parseInt(entryIdString);
            } catch (NumberFormatException e) {
                throw new ParseException("Please enter an integer!");
            }

            return new DiaryShowIdCommand(entryId);

        } else if (arePrefixesPresent(argMultimap2, PREFIX_DIARY_DATE)
                && argMultimap2.getPreamble().isEmpty()) {

            String dateString = argMultimap2.getValue(PREFIX_DIARY_DATE).get().trim();
            LocalDate date = LocalDate.now();

            try {
                date = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            } catch (DateTimeParseException e) {
                throw new ParseException("Invalid date! format:{dd-mm-yyyy}");
            }

            return new DiaryShowDateCommand(date);

        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DiaryShowCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
