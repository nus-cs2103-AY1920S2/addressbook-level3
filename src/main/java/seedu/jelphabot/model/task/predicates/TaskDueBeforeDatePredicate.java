//@@author yaojiethng
package seedu.jelphabot.model.task.predicates;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.function.Predicate;

import seedu.jelphabot.model.task.DateTime;
import seedu.jelphabot.model.task.Task;

/**
 * Tests that a {@code Task}'s {@code DateTime} falls before the given Date.
 */
public class TaskDueBeforeDatePredicate implements Predicate<Task> {

    private final LocalDateTime date;

    // default constructor sets the date to the instant the method was called
    public TaskDueBeforeDatePredicate() {
        date = LocalDateTime.now();
    }

    public TaskDueBeforeDatePredicate(LocalDate date) {
        this.date = date.atStartOfDay();
    }

    /**
     * Support for methods that use Java7 Date.
     *
     * @param dateTime the internal model representation of DateTime.
     */
    public TaskDueBeforeDatePredicate(DateTime dateTime) {
        this.date = dateTime.getDateTime();
    }

    @Override
    public boolean test(Task task) {
        LocalDateTime taskDate = task.getDateTime().getDateTime();
        return taskDate.isBefore(this.date);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                   || (other instanceof TaskDueBeforeDatePredicate) // instanceof handles null
                          && this.date.equals(((TaskDueBeforeDatePredicate) other).date);
    }
}
