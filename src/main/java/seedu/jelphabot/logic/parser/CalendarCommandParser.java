package seedu.jelphabot.logic.parser;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

import seedu.jelphabot.commons.core.Messages;
import seedu.jelphabot.commons.util.DateUtil;
import seedu.jelphabot.logic.commands.CalendarCommand;
import seedu.jelphabot.logic.parser.exceptions.ParseException;
import seedu.jelphabot.model.task.predicates.TaskDueWithinDayPredicate;

//@@author alam8064
/**
 * Parses input argument and creates a new CalendarCommand object
 */
public class CalendarCommandParser implements Parser<CalendarCommand> {

    private static final DateTimeFormatter ACCEPTED_DATE_FORMATS =
        new DateTimeFormatterBuilder()
            .appendOptional(DateTimeFormatter.ofPattern("MMM-d-uuuu"))
            .appendOptional(DateTimeFormatter.ofPattern("MMM/d/uuuu"))
            .appendOptional(DateTimeFormatter.ofPattern("d-MMM-uuuu"))
            .appendOptional(DateTimeFormatter.ofPattern("d/MMM/uuuu"))
            .toFormatter().withResolverStyle(ResolverStyle.STRICT);

    private static final DateTimeFormatter ACCEPTED_YEARMONTH_FORMATS =
        new DateTimeFormatterBuilder()
            .appendOptional(DateTimeFormatter.ofPattern("MMM-uuuu"))
            .appendOptional(DateTimeFormatter.ofPattern("MMM/uuuu"))
            .appendOptional(DateTimeFormatter.ofPattern("uuuu/MMM"))
            .appendOptional(DateTimeFormatter.ofPattern("uuuu-MMM"))
            .appendOptional(DateTimeFormatter.ofPattern("uuuu-MM"))
            .appendOptional(DateTimeFormatter.ofPattern("uuuu/MM"))
            .appendOptional(DateTimeFormatter.ofPattern("uu-MM"))
            .appendOptional(DateTimeFormatter.ofPattern("uu/MM"))
            .toFormatter().withResolverStyle(ResolverStyle.STRICT);

    /**
     * Parses the given {@code String} of argument in the context of the CalendarCommand
     * and returns a CalendarCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public CalendarCommand parse(String args) throws ParseException {
        String input = args.trim();
        if (input.isEmpty()) {
            // throw new ParseException(
            //     String.format(MESSAGE_INVALID_COMMAND_FORMAT, CalendarCommand.MESSAGE_USAGE));
            return new CalendarCommand();
        } else if (input.length() > 8) {
            //case for switching date for task list
            if (!isValidDate(input)) {
                throw new ParseException(Messages.MESSAGE_INVALID_DATE_FORMAT);
            }
            LocalDate date = LocalDate.parse(input, ACCEPTED_DATE_FORMATS);
            return new CalendarCommand(new TaskDueWithinDayPredicate(date));
        } else if (input.equals("today")) {
            YearMonth yearMonth = YearMonth.now();
            return new CalendarCommand(new TaskDueWithinDayPredicate(DateUtil.getDateToday()), true);
        } else {
            //case for switching month view
            if (!isValidYearMonth(input)) {
                throw new ParseException(Messages.MESSAGE_INVALID_YEARMONTH_FORMAT);
            }
            YearMonth yearMonth = YearMonth.parse(input, ACCEPTED_YEARMONTH_FORMATS);
            return new CalendarCommand(yearMonth);
        }
    }

    /**
     * Returns if the given string is a valid date format, specified in
     * the ACCEPTED_DATE_FORMATS DateTimeFormatter.
     *
     * @param test The date to be checked.
     * @return The boolean representing whether the date provided is valid.
     */
    public static boolean isValidDate(String test) {
        try {
            LocalDate.parse(test, ACCEPTED_DATE_FORMATS);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Returns if the given string is a valid date format, specified in
     * the ACCEPTED_YEARMONTH_FORMATS DateTimeFormatter.
     *
     * @param test The date of yearmonth to be checked.
     * @return The boolean representing whether the date provided is valid.
     */
    public static boolean isValidYearMonth(String test) {
        try {
            YearMonth.parse(test, ACCEPTED_YEARMONTH_FORMATS);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

}
