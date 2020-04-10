package team.easytravel.logic.parser.schedule;

import static java.util.Objects.requireNonNull;
import static team.easytravel.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static team.easytravel.commons.core.Messages.MESSAGE_INVALID_DISPLAYED_INDEX_FORMAT;
import static team.easytravel.logic.parser.CliSyntax.PREFIX_SCHEDULE_DAY;
import static team.easytravel.logic.parser.CliSyntax.PREFIX_SCHEDULE_TIME;

import java.util.stream.Stream;

import team.easytravel.commons.core.index.Index;
import team.easytravel.commons.core.time.Time;
import team.easytravel.logic.commands.schedule.ScheduleCommand;
import team.easytravel.logic.parser.ArgumentMultimap;
import team.easytravel.logic.parser.ArgumentTokenizer;
import team.easytravel.logic.parser.Parser;
import team.easytravel.logic.parser.ParserUtil;
import team.easytravel.logic.parser.Prefix;
import team.easytravel.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ScheduleCommand.
 */
public class ScheduleCommandParser implements Parser<ScheduleCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ScheduleCommand
     * and returns an ScheduleCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ScheduleCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_SCHEDULE_DAY, PREFIX_SCHEDULE_TIME);

        if (!arePrefixesPresent(argMultimap, PREFIX_SCHEDULE_DAY, PREFIX_SCHEDULE_TIME)
                || argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScheduleCommand.MESSAGE_USAGE));
        }

        Index activityIndex;
        try {
            activityIndex = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_DISPLAYED_INDEX_FORMAT, "activity"), pe);
        }

        Index dayIndex;
        try {
            dayIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_SCHEDULE_DAY).get());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_DISPLAYED_INDEX_FORMAT, "day"), pe);
        }

        Time time;
        try {
            time = ParserUtil.parseTime(argMultimap.getValue(PREFIX_SCHEDULE_TIME).get());
        } catch (ParseException pe) {
            throw new ParseException("Invalid time format.", pe);
        }

        return new ScheduleCommand(activityIndex, dayIndex, time);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
