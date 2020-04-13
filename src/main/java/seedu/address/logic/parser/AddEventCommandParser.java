package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PLACE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;

import java.util.logging.Logger;
import java.util.stream.Stream;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddEventCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.Event;
import seedu.address.model.person.Time;

/**
 * Parses input arguments and creates a new {@code AddEventCommand object}
 */
public class AddEventCommandParser implements Parser<AddEventCommand> {

    private static final Logger logger = LogsCenter.getLogger(AddEventCommandParser.class);

    /**
     * Parses the given {@code String} of arguments in the context of the {@code AddEventCommand}
     * and returns a {@code AddEventCommand} object for execution
     * @throws ParseException if the user input does not conform to the expected format
     */
    public AddEventCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_MEMBER, PREFIX_GROUP, PREFIX_TIME,
                PREFIX_PLACE);
        if ((!arePrefixesPresent(argMultimap, PREFIX_PLACE, PREFIX_TIME, PREFIX_GROUP)
                && !arePrefixesPresent(argMultimap, PREFIX_PLACE, PREFIX_TIME, PREFIX_MEMBER))
                || argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE));
        }
        if (arePrefixesPresent(argMultimap, PREFIX_MEMBER, PREFIX_GROUP)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE));
        }
        String activity = argMultimap.getPreamble();
        if (activity.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE));
        }

        String place = argMultimap.getValue(PREFIX_PLACE).get();
        if (place.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE));
        }

        String input = argMultimap.getValue(PREFIX_TIME).get();
        String mins = "";
        String hours = "";
        if (input.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE));
        } else {
            String[] process = argMultimap.getValue(PREFIX_TIME).get().split(" ");
            if (process.length > 1) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        AddEventCommand.MESSAGE_USAGE));
            }
            char[] time = process[0].toCharArray();
            if (time.length < 2) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        AddEventCommand.MESSAGE_INVALID_TIME_INPUT));
            } else {
                int marker = time.length - 2;
                for (int i = marker; i < time.length; i++) {
                    mins += time[i];
                }
                for (int i = 0; i < marker; i++) {
                    hours += time[i];
                }
                if (hours.equals("")) {
                    hours += "0";
                }
            }
        }

        try {
            Integer.parseInt(mins);
            Integer.parseInt(hours);
        } catch (IllegalArgumentException e) {
            throw new ParseException(AddEventCommand.MESSAGE_INVALID_TIME_INPUT);
        }

        try {
            Time time = new Time(Integer.parseInt(mins), Integer.parseInt(hours));
            Event event = new Event(activity, place, time);

            Index index;
            if (argMultimap.getValue(PREFIX_MEMBER).isEmpty()) {
                index = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_GROUP).get());
                int idx = index.getOneBased();
                event.setWithGroup(idx);
            } else {
                index = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_MEMBER).get());
                int idx = index.getOneBased();
                event.setWithPerson(idx);
                logger.info("The parsed index is " + idx);
            }

            return new AddEventCommand(event);
        } catch (IllegalArgumentException e) {
            throw new ParseException(e.getMessage());
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given {@code
     * ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}

