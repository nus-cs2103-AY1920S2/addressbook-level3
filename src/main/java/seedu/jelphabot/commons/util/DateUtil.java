package seedu.jelphabot.commons.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.function.Predicate;

import seedu.jelphabot.model.task.Task;
import seedu.jelphabot.model.task.predicates.TaskDueAfterDatePredicate;
import seedu.jelphabot.model.task.predicates.TaskDueBeforeDatePredicate;

/**
 * Helper functions for handling dates.
 */
public class DateUtil {
    /**
     * Get the date at the time the method is called.
     *
     * @return LocalDate representing the current day's date.
     */
    public static LocalDate getDateToday() {
        return LocalDate.now();
    }

    /**
     * Get the date at the time one day after the method is called.
     *
     * @return LocalDate representing the next day's date.
     */
    public static LocalDate getDateTomorrow() {
        return LocalDate.now().plusDays(1);

    }

    /**
     * Get the date at the time one week after the method is called.
     *
     * @return LocalDate representing the date one week from now.
     */
    public static LocalDate getDateNextWeek() {
        return LocalDate.now().plusWeeks(1);
    }

    /**
     * Convert a Java7 Date object to a Java8 LocalDate object representing the same instant in time.
     *
     * @param date Java7 Date object to be converted.
     * @return LocalDate with the same date as the input Date.
     */
    public static LocalDate dateToLocalDate(Date date) {
        return date.toInstant()
                   .atZone(ZoneId.systemDefault())
                   .toLocalDate();
    }

    /**
     * Convert a Java7 Date object to a Java8 LocalDateTime object representing the same instant in time.
     *
     * @param date Java7 Date object to be converted.
     * @return LocalDate with the same date and time as the input Date.
     */
    public static LocalDateTime dateToLocalDateTime(Date date) {
        return date.toInstant()
                   .atZone(ZoneId.systemDefault())
                   .toLocalDateTime();
    }

    /**
     * @return a predicate which filters tasks with a due date before the instant the method was called.
     */
    public static Predicate<Task> getOverduePredicate() {
        return new TaskDueBeforeDatePredicate();
    }

    /**
     * @return a predicate which filters tasks with a due date the same day as the instant the method was called.
     */
    public static Predicate<Task> getDueTodayPredicate() {
        return new TaskDueAfterDatePredicate().and(new TaskDueBeforeDatePredicate(getDateTomorrow()));
    }

    /**
     * @return a predicate which filters tasks with a due date between one and seven days after the instant the
     * method was called.
     */
    public static Predicate<Task> getDueThisWeekPredicate() {
        return new TaskDueAfterDatePredicate(getDateTomorrow()).and(new TaskDueBeforeDatePredicate(getDateNextWeek()));
    }

    /**
     * @return a predicate which filters tasks with a due date more than seven days after the instant the
     * method was called.
     */
    public static Predicate<Task> getDueSomedayPredicate() {
        return new TaskDueAfterDatePredicate(getDateNextWeek());
    }
}
