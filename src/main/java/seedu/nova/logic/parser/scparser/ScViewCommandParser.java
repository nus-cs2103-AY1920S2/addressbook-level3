package seedu.nova.logic.parser.scparser;

import static seedu.nova.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.nova.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.nova.logic.parser.CliSyntax.PREFIX_INDEX;

import java.time.LocalDate;
import java.util.stream.Stream;

import seedu.nova.commons.core.index.Index;
import seedu.nova.logic.commands.sccommands.ScViewCommand;
import seedu.nova.logic.commands.sccommands.ScViewDayCommand;
import seedu.nova.logic.commands.sccommands.ScViewWeekCommand;
import seedu.nova.logic.parser.ArgumentMultimap;
import seedu.nova.logic.parser.ArgumentTokenizer;
import seedu.nova.logic.parser.Parser;
import seedu.nova.logic.parser.ParserUtil;
import seedu.nova.logic.parser.Prefix;
import seedu.nova.logic.parser.exceptions.ParseException;

/**
 * The parser for the view command of schedule.
 */
public class ScViewCommandParser implements Parser<ScViewCommand> {

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {

        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    @Override
    public ScViewCommand parse(String args) throws ParseException {

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_DATE, PREFIX_INDEX);

        if ((arePrefixesPresent(argMultimap, PREFIX_DATE) && arePrefixesPresent(argMultimap, PREFIX_INDEX))
                || (!arePrefixesPresent(argMultimap, PREFIX_DATE) && !arePrefixesPresent(argMultimap, PREFIX_INDEX))) {

            if (argMultimap.getPreamble().isEmpty() || !argMultimap.getPreamble().equals("week")) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScViewCommand.MESSAGE_USAGE));
            } else {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        ScViewWeekCommand.MESSAGE_USAGE));
            }
        }

        if (argMultimap.getPreamble().isEmpty()) {
            //View date command

            if (!arePrefixesPresent(argMultimap, PREFIX_DATE)) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScViewCommand.MESSAGE_USAGE));
            }

            LocalDate date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get());
            return new ScViewDayCommand(date);

        } else {
            //View with preamble
            if (!argMultimap.getPreamble().equals("week")) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScViewCommand.MESSAGE_USAGE));
            }

            if (!arePrefixesPresent(argMultimap, PREFIX_INDEX)) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        ScViewWeekCommand.MESSAGE_USAGE));
            }

            //view week command
            Index weekNumber = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_INDEX).get());
            return new ScViewWeekCommand(weekNumber.getOneBased());
        }

    }

}
