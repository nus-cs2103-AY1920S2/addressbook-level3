//@@author eedenong
package seedu.jelphabot.model.task.predicates;

import java.util.function.Predicate;

import seedu.jelphabot.model.task.Status;
import seedu.jelphabot.model.task.Task;

/**
 * Tests that a {@code Task}'s {@code Status} is COMPLETE.
 */
public class TaskIsCompletedPredicate implements Predicate<Task> {

    @Override
    public boolean test(Task task) {
        return task.getStatus() == Status.COMPLETE;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same objcet
                || (other instanceof TaskIsCompletedPredicate); // instanceof handles null
    }
}
