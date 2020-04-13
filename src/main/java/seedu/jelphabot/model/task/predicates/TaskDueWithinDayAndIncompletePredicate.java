//@@author eedenong
package seedu.jelphabot.model.task.predicates;

import java.time.LocalDate;
import java.util.function.Predicate;

import seedu.jelphabot.model.task.DateTime;
import seedu.jelphabot.model.task.Status;
import seedu.jelphabot.model.task.Task;

/**
 * Tests that a {@code Task}'s {@code DateTime} is due within the day and is incomplete
 */
public class TaskDueWithinDayAndIncompletePredicate implements Predicate<Task> {
    private final LocalDate date;

    public TaskDueWithinDayAndIncompletePredicate() {
        date = LocalDate.now();
    }

    public TaskDueWithinDayAndIncompletePredicate(LocalDate date) {
        this.date = date;
    }

    public TaskDueWithinDayAndIncompletePredicate(DateTime dateTime) {
        this.date = dateTime.getDate();
    }

    @Override
    public boolean test(Task task) {
        LocalDate taskDate = task.getDateTime().getDate();
        Status status = task.getStatus();
        return !taskDate.isAfter(date)
                   && !taskDate.isBefore(date)
                   && (status != Status.COMPLETE);
    }
}
