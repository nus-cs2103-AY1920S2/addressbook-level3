package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DURATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENTDATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENTTITLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PLACE;

import java.util.stream.Stream;

import seedu.address.logic.commands.AddEventCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.Duration;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventDate;
import seedu.address.model.event.EventTitle;
import seedu.address.model.event.Place;

/**
 * Parses input arguments and creates a new AddEventCommand object.
 */
public class AddEventCommandParser implements Parser<AddEventCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddEventCommand
     * and returns an AddEventCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddEventCommand parse(String args) throws ParseException {

        // does order matter for this command? pls double check
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DURATION, PREFIX_EVENTDATE, PREFIX_EVENTTITLE, PREFIX_PLACE);

        if (!arePrefixesPresent(argMultimap, PREFIX_DURATION, PREFIX_EVENTDATE, PREFIX_EVENTTITLE, PREFIX_PLACE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE));
        }

        Duration duration = ParserUtil.parseDuration(argMultimap.getValue(PREFIX_DURATION).get());
        EventTitle eventTitle = ParserUtil.parseEventTitle(argMultimap.getValue(PREFIX_EVENTTITLE).get());
        EventDate eventDate = ParserUtil.parseEventDate(argMultimap.getValue(PREFIX_EVENTDATE).get());
        Place place = ParserUtil.parsePlace(argMultimap.getValue(PREFIX_PLACE).get());

        Event event = new Event(eventTitle, eventDate, duration, place);

        return new AddEventCommand(event);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}

