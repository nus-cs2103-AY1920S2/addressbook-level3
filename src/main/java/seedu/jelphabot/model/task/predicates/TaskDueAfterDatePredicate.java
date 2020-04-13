//@@author yaojiethng
package seedu.jelphabot.model.task.predicates;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.function.Predicate;

import seedu.jelphabot.model.task.DateTime;
import seedu.jelphabot.model.task.Task;

/**
 * Tests that a {@code Task}'s {@code DateTime} falls after the given Date.
 *
 * @@author yaojiethng
 */
public class TaskDueAfterDatePredicate implements Predicate<Task> {

    private final LocalDateTime date;

    // default constructor sets the date to the instant the method was called
    public TaskDueAfterDatePredicate() {
        date = LocalDateTime.now();
    }

    public TaskDueAfterDatePredicate(LocalDate date) {
        this.date = date.atStartOfDay();
    }

    public TaskDueAfterDatePredicate(DateTime dateTime) {
        this.date = dateTime.getDateTime();
    }

    @Override
    public boolean test(Task task) {
        LocalDateTime taskDate = task.getDateTime().getDateTime();
        return taskDate.isAfter(this.date);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                   || (other instanceof TaskDueAfterDatePredicate) // instanceof handles null
                          && this.date.equals(((TaskDueAfterDatePredicate) other).date);
    }
}
