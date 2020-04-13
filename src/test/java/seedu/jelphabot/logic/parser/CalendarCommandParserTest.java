package seedu.jelphabot.logic.parser;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.jelphabot.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

import seedu.jelphabot.commons.util.DateUtil;
import seedu.jelphabot.logic.commands.CalendarCommand;
import seedu.jelphabot.model.task.predicates.TaskDueWithinDayPredicate;

//@@author alam8064
public class CalendarCommandParserTest {

    private CalendarCommandParser parser = new CalendarCommandParser();

    @Test
    public void parse_date_returnsCalendarCommand() {
        LocalDate date = LocalDate.parse("1-Mar-2020", DateTimeFormatter.ofPattern("d-MMM-uuuu"));
        CalendarCommand expectedCalendarCommand = new CalendarCommand(new TaskDueWithinDayPredicate(date));
        assertParseSuccess(parser, "1-Mar-2020", expectedCalendarCommand);
    }

    @Test
    public void parse_yearMonth_returnsCalendarCommand() {
        YearMonth yearMonth = YearMonth.parse("Mar-2020", DateTimeFormatter.ofPattern("MMM-uuuu"));
        CalendarCommand expectedCalendarCommand = new CalendarCommand(yearMonth);
        assertParseSuccess(parser, "Mar-2020", expectedCalendarCommand);
    }

    @Test
    public void parse_today_returnsCalendarCommand() {
        LocalDate date = DateUtil.getDateToday();
        CalendarCommand expectedCalendarCommand = new CalendarCommand(new TaskDueWithinDayPredicate(date), true);
        assertParseSuccess(parser, "today", expectedCalendarCommand);
    }

    @Test
    public void isValidDate_invalidDate_returnsFalse() {

        // invalid Date
        assertFalse(CalendarCommandParser.isValidDate("")); // empty string
        assertFalse(CalendarCommandParser.isValidDate(" ")); // spaces only

        // invalid variations of MMM-d-uuuu / d-MMM-uuuu
        assertFalse(CalendarCommandParser.isValidDate("2020-1-May")); // wrong format: yyyy-d-MMM
        assertFalse(CalendarCommandParser.isValidDate("05-1-2020")); // wrong format: MM-d-yyyy

        // invalid variations of MMM/d/uuuu / d/MMM/uuuu
        assertFalse(CalendarCommandParser.isValidDate("May1/2020")); // Missing / between month and day
        assertFalse(CalendarCommandParser.isValidDate("1May/2020")); // Missing / between month and day
        assertFalse(CalendarCommandParser.isValidDate("May/12020")); // Missing / between month and year
        assertFalse(CalendarCommandParser.isValidDate("1/May2020")); // Missing / between month and year
        assertFalse(CalendarCommandParser.isValidDate("May12020")); // Missing / between month, day and year
        assertFalse(CalendarCommandParser.isValidDate("1May2020")); // Missing / between month, day and year
    }

    @Test
    public void constructDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM-d-uuuu");
        String stringRep = formatter.format(LocalDate.now());
        LocalDate fromFormat = LocalDate.parse(stringRep, formatter);
    }

    @Test
    public void isValidDate_validDate_returnsTrue() {
        // valid DateTime formats
        assertTrue(CalendarCommandParser.isValidDate("May-1-2020")); // MMM-d-yyyy
        assertTrue(CalendarCommandParser.isValidDate("1-May-2020")); // d-MMM-yyyy
        assertTrue(CalendarCommandParser.isValidDate("May/1/2020")); // MMM/d/yyyy
    }

    @Test
    public void isValidYearMonth_invalidYearMonth_returnsFalse() {

        // invalid YearMonth
        assertFalse(CalendarCommandParser.isValidYearMonth("")); // empty string
        assertFalse(CalendarCommandParser.isValidYearMonth(" ")); // spaces only
        assertFalse(CalendarCommandParser.isValidYearMonth("2020")); // year with no month

        // invalid variations of MMM-yyyy / yyyy-MMM / yyyy-MM / yy-MM
        assertFalse(CalendarCommandParser.isValidYearMonth("05-2020")); // wrong format: MM-yyyy
        assertFalse(CalendarCommandParser.isValidYearMonth("05-20")); // wrong format: MM-yy

        // invalid variations of MMM/yyyy / yyyy/MMM / yyyy/MM / yy/MM
        assertFalse(CalendarCommandParser.isValidYearMonth("05/2020")); // wrong format: MM/yyyy
        assertFalse(CalendarCommandParser.isValidYearMonth("05/20")); // wrong format: MM/yy
        assertFalse(CalendarCommandParser.isValidYearMonth("May2020")); // Missing / between month and year
        assertFalse(CalendarCommandParser.isValidYearMonth("202005")); // Missing / between year and month
    }

    @Test
    public void constructYearMonth() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM-uuuu");
        String stringRep = formatter.format(YearMonth.now());
        YearMonth fromFormat = YearMonth.parse(stringRep, formatter);
    }

    @Test
    public void isValidYearMonth_validYearMonth_returnsTrue() {

        // valid DateTime formats
        assertTrue(CalendarCommandParser.isValidYearMonth("May-2020")); // MMM-yyyy
        assertTrue(CalendarCommandParser.isValidYearMonth("2020/May")); // yyyy/MMM
        assertTrue(CalendarCommandParser.isValidYearMonth("May/2020")); // MMM/yyyy
        assertTrue(CalendarCommandParser.isValidYearMonth("2020/05")); // yyyy/MM
        assertTrue(CalendarCommandParser.isValidYearMonth("20-05")); // yy/MM
    }

}
