package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_EVENT_DATETIME_RANGE;
import static seedu.address.commons.util.EventUtil.makeUniqueIdentifier;
import static seedu.address.commons.util.EventUtil.validateDateTime;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COLOR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GET_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RECUR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VIEW;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VIEW_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VIEW_MODE;
import static seedu.address.logic.parser.ParserUtil.parseColorCode;
import static seedu.address.logic.parser.ParserUtil.parseEventName;
import static seedu.address.logic.parser.ParserUtil.parseLocalDateTime;
import static seedu.address.logic.parser.ParserUtil.parseRecurrenceType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Stream;

import jfxtras.icalendarfx.components.VEvent;
import jfxtras.icalendarfx.properties.component.descriptive.Categories;
import jfxtras.icalendarfx.properties.component.recurrence.RecurrenceRule;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.event.EventAddCommand;
import seedu.address.logic.commands.event.EventCommand;
import seedu.address.logic.commands.event.EventDisplayCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;



/**
 * Parses input arguments and creates a new event command object
 */
public class EventCommandParser implements Parser<EventCommand> {
    private static final Logger logger = LogsCenter.getLogger(EventCommandParser.class);

    public static boolean arePrefixesPresent(ArgumentMultimap argMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argMultimap.getValue(prefix).isPresent());
    }

    /**
     * Parses the given {@code String} of arguments in the context of the AcademicsCommand
     * and returns an EventCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public EventCommand parse(String args) throws ParseException, CommandException {
        requireNonNull(args);

        if (args.equals("")) {
            return eventDisplayCommand();
        }

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_EVENT_NAME,
                PREFIX_ADD,
                PREFIX_START_DATETIME,
                PREFIX_END_DATETIME,
                PREFIX_VIEW,
                PREFIX_COLOR,
                PREFIX_RECUR,
                PREFIX_GET_INDEX,
                PREFIX_VIEW_MODE,
                PREFIX_VIEW_DATE);
        boolean isEdit = false;
        Index index = Index.fromZeroBased(0);
        try {
            String preamble = argMultimap.getPreamble();

            if (!preamble.isBlank()) {
                index = ParserUtil.parseIndex(preamble);
                isEdit = true;
            }
        } catch (ParseException e) {
            logger.info("Parser unable to parse preamble index.");
            throw new ParseException("Check out the Help Tab for Command information");
        }
        if (argMultimap.getValue(PREFIX_ADD).isPresent()) {
            return addCommand(argMultimap);
        }
        /*if (argMultimap.getValue(PREFIX_VIEW).isPresent()) {
            return viewCommand(argMultimap);
        } else if (argMultimap.getValue(PREFIX_DELETE).isPresent()) {
            return deleteCommand(argMultimap);
        } else if (argMultimap.getValue(PREFIX_GET_INDEX).isPresent()) {
            return indexOfCommand(argMultimap);
        } else if (isEdit) {
            return editCommand(index, argMultimap);
        } else {*/
        return addCommand(argMultimap);
    }


    /**
     * Displays schedule of events.
     * {@code ArgumentMultimap}.
     */
    private EventDisplayCommand eventDisplayCommand() throws ParseException, CommandException {
        return new EventDisplayCommand();
    }

    /**
     * Adds the given assessment details to academic report.
     * {@code ArgumentMultimap}.
     */
    public EventAddCommand addCommand(ArgumentMultimap argMultimap) throws ParseException {
        if (!arePrefixesPresent(argMultimap, PREFIX_EVENT_NAME, PREFIX_START_DATETIME, PREFIX_END_DATETIME,
                PREFIX_RECUR, PREFIX_COLOR) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EventAddCommand.MESSAGE_USAGE));
        }
        String eventName = argMultimap.getValue(PREFIX_EVENT_NAME).orElse("").trim();
        String startDateTimeString = argMultimap.getValue(PREFIX_START_DATETIME).orElse("").trim();
        String endDateTimeString = argMultimap.getValue(PREFIX_END_DATETIME).orElse("").trim();
        String recurType = argMultimap.getValue(PREFIX_RECUR).orElse("").trim();
        String colorNumber = argMultimap.getValue(PREFIX_COLOR).orElse("").trim();
        if (eventName.isBlank() || startDateTimeString.isBlank() || endDateTimeString.isBlank() || recurType.isBlank()
                || colorNumber.isBlank()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EventAddCommand.MESSAGE_USAGE));
        }
        eventName = parseEventName(eventName);
        LocalDateTime startDateTime = parseLocalDateTime(startDateTimeString);
        LocalDateTime endDateTime = parseLocalDateTime(endDateTimeString);
        RecurrenceRule recurrenceRule = parseRecurrenceType(recurType);
        List<Categories> colorCategory = parseColorCode(colorNumber);
        String uniqueIdentifier = makeUniqueIdentifier(eventName, startDateTimeString, endDateTimeString);
        if (!validateDateTime(startDateTime, endDateTime)) {
            throw new ParseException(MESSAGE_INVALID_EVENT_DATETIME_RANGE);
        }
        VEvent vEvent = new VEvent().withSummary(eventName).withDateTimeStart(startDateTime)
                .withDateTimeEnd(endDateTime).withRecurrenceRule(recurrenceRule).withCategories(colorCategory)
                .withUniqueIdentifier(uniqueIdentifier);

        return new EventAddCommand(vEvent);
    }

}
