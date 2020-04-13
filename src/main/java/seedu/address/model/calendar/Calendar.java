package seedu.address.model.calendar;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import seedu.address.model.task.Task;

/**
 * Represents the Calendar in Mod Manager.
 */
public class Calendar {

    public static final String MESSAGE_CONSTRAINTS = "Week should be 'this' or 'next' only";
    public static final int DAYS_IN_WEEK = 7;

    private LocalDate localDate;

    public Calendar(LocalDate localDate) {
        this.localDate = localDate;
    }

    /**
     * Returns the calendar with the now date.
     */
    public static Calendar getNowCalendar() {
        return new Calendar(LocalDate.now());
    }

    /**
     * Returns the calendar with the date of next week.
     */
    public static Calendar getNextWeekCalendar() {
        return new Calendar(LocalDate.now().plusDays(7));
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    /**
     * Returns the index of the day of the week.
     *
     * @return the index of the day of the week.
     */
    public int getDayOfWeek() {
        return localDate.getDayOfWeek().getValue();
    }

    /**
     * Returns an array of days in the same week as the current date.
     *
     * @return the array of days in the same week as the current date.
     */
    public Calendar[] getWeek() {
        Calendar[] week = new Calendar[7];
        int index = getDayOfWeek();
        week[index - 1] = this;
        for (int i = 1; i < index; i++) {
            week[index - 1 - i] = new Calendar(getLocalDate().minusDays(i));
        }

        for (int i = 1; i <= 7 - index; i++) {
            week[index - 1 + i] = new Calendar(getLocalDate().plusDays(i));
        }

        return week;
    }

    /**
     * Checks if task is within the localDate.
     */
    public boolean isWithinDate(Task task) {
        if (task.getTimeString().equals("")) {
            return false;
        }
        LocalDate date = LocalDate.parse(task.getTimeString(),
                DateTimeFormatter.ofPattern("dd/MM/yyyy[ HH:mm]"));
        if (localDate.compareTo(date) == 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Calendar)) {
            return false;
        }

        Calendar otherCalendar = (Calendar) other;
        return otherCalendar.getLocalDate().equals(getLocalDate());
    }

}
