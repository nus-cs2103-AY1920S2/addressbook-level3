package seedu.nova.logic.parser.scparser.eventparsers;

import static seedu.nova.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.nova.commons.core.Messages.MESSAGE_WRONG_TIME;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.stream.Stream;

import seedu.nova.logic.commands.sccommands.eventcommands.EventAddMeetingCommand;
import seedu.nova.logic.parser.ArgumentMultimap;
import seedu.nova.logic.parser.ArgumentTokenizer;
import seedu.nova.logic.parser.CliSyntax;
import seedu.nova.logic.parser.Parser;
import seedu.nova.logic.parser.ParserUtil;
import seedu.nova.logic.parser.Prefix;
import seedu.nova.logic.parser.exceptions.ParseException;
import seedu.nova.model.schedule.event.Event;
import seedu.nova.model.schedule.event.Meeting;

/**
 * Parses input arguments and creates a new EventAddMeetingCommand object.
 */
public class EventAddMeetingCommandParser implements Parser<EventAddMeetingCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EventAddMeetingCommand
     * and returns an EventAddMeetingCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EventAddMeetingCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, CliSyntax.PREFIX_DESC, CliSyntax.PREFIX_VENUE, CliSyntax.PREFIX_TIME);

        if (!arePrefixesPresent(argMultimap, CliSyntax.PREFIX_DESC, CliSyntax.PREFIX_VENUE, CliSyntax.PREFIX_TIME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EventAddMeetingCommand.MESSAGE_USAGE));
        }

        String desc = argMultimap.getValue(CliSyntax.PREFIX_DESC).get();
        String venue = argMultimap.getValue(CliSyntax.PREFIX_VENUE).get();

        String dateTime = argMultimap.getValue(CliSyntax.PREFIX_TIME).get();
        String[] dateTimeArr = dateTime.split(" ");

        if (dateTimeArr.length != 3) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EventAddMeetingCommand.MESSAGE_USAGE));
        }

        LocalDate date = ParserUtil.parseDate(dateTimeArr[0]);
        LocalTime startTime = ParserUtil.parseTime(dateTimeArr[1]);
        LocalTime endTime = ParserUtil.parseTime(dateTimeArr[2]);

        if (startTime.compareTo(endTime) >= 0) {
            throw new ParseException(MESSAGE_WRONG_TIME);
        }

        Event meeting = new Meeting(desc, venue, startTime, endTime, date);
        return new EventAddMeetingCommand(meeting);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
