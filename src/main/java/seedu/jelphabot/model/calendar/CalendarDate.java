package seedu.jelphabot.model.calendar;

import java.time.LocalDate;

import seedu.jelphabot.commons.util.DateUtil;

//@@author alam8064
/**
 * Class representing a date without time, mainly for the calendar UI.
 */
public class CalendarDate {

    private static final String[] months = {"January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December"};

    private final LocalDate date;

    public CalendarDate(LocalDate date) {
        this.date = date;
    }

    public static CalendarDate getCurrentDate() {
        return new CalendarDate(DateUtil.getDateToday());
    }

    public LocalDate getDate() {
        return date;
    }

    public boolean isToday() {
        return date.equals(DateUtil.getDateToday());
    }

    public int getDay() {
        return date.getDayOfMonth();
    }

    public int getMonth() {
        return date.getMonthValue();
    }

    public int getYear() {
        return date.getYear();
    }

    public String getMonthName() {
        return months[getMonth() - 1];
    }

    public static String getMonthNameOf(int monthValue) {
        return months[monthValue - 1];
    }

    public int getDayOfWeek() {
        return date.getDayOfWeek().getValue();
    }

    public CalendarDate getFirstDay() {
        return new CalendarDate(date.minusDays(getDay() - 1));
    }

    public int getLengthPrevMonth() {
        LocalDate prevMonth = date.minusMonths(1);
        return prevMonth.lengthOfMonth();
    }

    /**
     * Creates a date of the previous month with the specified day input.
     * @param day Specified day for the previous month.
     * @return CalendarDate object.
     */
    public CalendarDate createPrevMonthDate(int day) {
        LocalDate prevMonth = date.minusMonths(1);
        LocalDate datePrevMonth = prevMonth.withDayOfMonth(day);
        return new CalendarDate(datePrevMonth);
    }

    public boolean isSameMonth(int month) {
        return getMonth() == month;
    }

    public CalendarDate dateNextDay() {
        return new CalendarDate(date.plusDays(1));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                   || (other instanceof CalendarDate // instanceof handles nulls
                           && date.equals(((CalendarDate) other).date)); // state check
    }
}
