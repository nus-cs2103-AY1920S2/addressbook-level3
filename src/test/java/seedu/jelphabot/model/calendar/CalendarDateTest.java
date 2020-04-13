package seedu.jelphabot.model.calendar;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

//@@ author alam8064
public class CalendarDateTest {

    @Test
    public void getMonthNameOf_returnsTrue() {
        LocalDate localDate = LocalDate.parse("Mar-1-2020", DateTimeFormatter.ofPattern("MMM-d-uuuu"));
        CalendarDate first = new CalendarDate(localDate);
        String month = CalendarDate.getMonthNameOf(first.getMonth());
        assertTrue(month.equals("March"));
    }

    @Test
    public void getMonthNameOf_returnsFalse() {
        LocalDate localDate = LocalDate.parse("Mar-1-2020", DateTimeFormatter.ofPattern("MMM-d-uuuu"));
        CalendarDate first = new CalendarDate(localDate);
        String month = CalendarDate.getMonthNameOf(first.getMonth());
        assertFalse(month.equals("April"));
    }

    @Test
    public void getFirstDay_returnsTrue() {
        LocalDate date = LocalDate.parse("Mar-31-2020", DateTimeFormatter.ofPattern("MMM-d-uuuu"));
        LocalDate firstDate = LocalDate.parse("Mar-1-2020", DateTimeFormatter.ofPattern("MMM-d-uuuu"));
        CalendarDate calendarDay = new CalendarDate(date);
        CalendarDate firstCalendarDay = new CalendarDate(firstDate);
        assertTrue(calendarDay.getFirstDay().equals(firstCalendarDay));
    }

    @Test
    public void getDay_returnsCorrectDay() {
        LocalDate localDate = LocalDate.parse("Mar-1-2020", DateTimeFormatter.ofPattern("MMM-d-uuuu"));
        CalendarDate calendarDate = new CalendarDate(localDate);
        assertEquals(calendarDate.getDay(), 1);
    }

    @Test
    public void getMonth_returnsCorrectMonth() {
        LocalDate localDate = LocalDate.parse("Mar-1-2020", DateTimeFormatter.ofPattern("MMM-d-uuuu"));
        CalendarDate calendarDate = new CalendarDate(localDate);
        assertEquals(calendarDate.getMonth(), 3);
    }

    @Test
    public void getYear_returnsCorrectYear() {
        LocalDate localDate = LocalDate.parse("Mar-1-2020", DateTimeFormatter.ofPattern("MMM-d-uuuu"));
        CalendarDate calendarDate = new CalendarDate(localDate);
        assertEquals(calendarDate.getYear(), 2020);
    }

    @Test
    public void getDayOfWeek_returnsDayOfWeek() {
        LocalDate localDate = LocalDate.parse("Mar-1-2020", DateTimeFormatter.ofPattern("MMM-d-uuuu"));
        CalendarDate calendarDate = new CalendarDate(localDate);
        assertEquals(calendarDate.getDayOfWeek(), 7);
    }

    @Test
    public void isToday_validDate_returnsTrue() {
        LocalDate localDate = LocalDate.now();
        CalendarDate calendarDate = new CalendarDate(localDate);
        assertTrue(calendarDate.isToday() == true);
    }

    @Test
    public void isToday_invalidDate_returnsFalse() {
        LocalDate localDate = LocalDate.parse("Mar-1-2020", DateTimeFormatter.ofPattern("MMM-d-uuuu"));
        CalendarDate calendarDate = new CalendarDate(localDate);
        assertTrue(calendarDate.isToday() == false);
    }

    @Test
    public void equals() {
        LocalDate firstDate = LocalDate.parse("1-Mar-2020", DateTimeFormatter.ofPattern("d-MMM-uuuu"));
        CalendarDate firstCalendarDate = new CalendarDate(firstDate);
        LocalDate secondDate = LocalDate.parse("2-Mar-2020", DateTimeFormatter.ofPattern("d-MMM-uuuu"));
        CalendarDate secondCalendarDate = new CalendarDate(secondDate);

        // same object -> returns true
        assertTrue(firstCalendarDate.equals(firstCalendarDate));

        // same values -> returns true
        CalendarDate calendarDateCopy = new CalendarDate(firstDate);
        assertTrue(firstCalendarDate.equals(calendarDateCopy));

        // different types -> returns false
        assertFalse(firstCalendarDate.equals(1));

        // null -> returns false
        assertFalse(firstCalendarDate.equals(null));

        // different commands -> returns false
        assertFalse(firstCalendarDate.equals(secondCalendarDate));
    }
}
