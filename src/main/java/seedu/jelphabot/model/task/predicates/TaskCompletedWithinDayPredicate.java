//@@author eedenong
package seedu.jelphabot.model.task.predicates;

import static seedu.jelphabot.commons.util.DateUtil.dateToLocalDate;

import java.time.LocalDate;
import java.util.Date;
import java.util.function.Predicate;

import seedu.jelphabot.model.task.DateTime;
import seedu.jelphabot.model.task.Task;

/**
 * Tests that a {@code Task} is completed within the specified Date
 */
public class TaskCompletedWithinDayPredicate implements Predicate<Task> {
    private final LocalDate date;

    // default constructor sets the date to the instant the constructor is called
    public TaskCompletedWithinDayPredicate() {
        this.date = LocalDate.now();
    }

    public TaskCompletedWithinDayPredicate(Date date) {
        this.date = dateToLocalDate(date);
    }

    public TaskCompletedWithinDayPredicate(DateTime dateTime) {
        this.date = dateTime.getDate();
    }

    /**
     * Tests that a {@code Task}'s {@code DateTime}
     * @param task
     * @return
     */
    @Override
    public boolean test(Task task) {
        try {
            LocalDate taskDoneTime = task.getDoneTime().toLocalDate();
            return !taskDoneTime.isAfter(date.plusDays(1))
                       && !taskDoneTime.isBefore(date.minusDays(1));
        } catch (NullPointerException e) {
            return false;
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                   || (other instanceof TaskCompletedWithinDayPredicate) // instanceof handles null
                          && this.date.equals(((TaskCompletedWithinDayPredicate) other).date);

    }
}
