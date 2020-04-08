package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ENTRY_ID;

import java.util.stream.Stream;

import seedu.address.logic.commands.diarycommand.DiaryDeleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Dummy java docs
 */
public class DiaryDeleteCommandParser implements Parser<DiaryDeleteCommand> {

    /**
     * Dummy java docs.
     * @param args
     * @return
     * @throws ParseException
     */
    public DiaryDeleteCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_ENTRY_ID);

        if (!arePrefixesPresent(argMultimap, PREFIX_ENTRY_ID)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DiaryDeleteCommand.MESSAGE_USAGE));
        }

        String entryIdString = argMultimap.getValue(PREFIX_ENTRY_ID).get();
        int entryId = 0;

        try {
            entryId = Integer.parseInt(entryIdString);
        } catch (NumberFormatException e) {
            throw new ParseException("Please enter an integer!");
        }

        return new DiaryDeleteCommand(entryId);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
