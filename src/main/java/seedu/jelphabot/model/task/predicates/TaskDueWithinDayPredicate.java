//@@author yaojiethng
package seedu.jelphabot.model.task.predicates;

import java.time.LocalDate;
import java.util.function.Predicate;

import seedu.jelphabot.model.task.DateTime;
import seedu.jelphabot.model.task.Task;

/**
 * Tests that a {@code Task}'s {@code DateTime} falls within the given Date.
 *
 * @@author yaojiethng
 */
public class TaskDueWithinDayPredicate implements Predicate<Task> {
    private final LocalDate date;

    // default constructor sets the date to the instant the method was called
    public TaskDueWithinDayPredicate() {
        date = LocalDate.now();
    }

    public TaskDueWithinDayPredicate(LocalDate date) {
        this.date = date;
    }

    public TaskDueWithinDayPredicate(DateTime dateTime) {
        this.date = dateTime.getDate();
    }

    public LocalDate getDate() {
        return date;
    }

    /**
     * Tests that a {@code Task}'s {@code DateTime} falls within the given Date.
     */
    @Override
    public boolean test(Task task) {
        LocalDate taskDate = task.getDateTime().getDate();
        return taskDate.isEqual(date);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                   || (other instanceof TaskDueWithinDayPredicate) // instanceof handles null
                          && this.date.equals(((TaskDueWithinDayPredicate) other).date);
    }
}
