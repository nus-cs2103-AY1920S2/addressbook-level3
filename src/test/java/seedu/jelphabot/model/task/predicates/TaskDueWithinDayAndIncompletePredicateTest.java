//@@author eedenong
package seedu.jelphabot.model.task.predicates;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.jelphabot.model.task.DateTime;
import seedu.jelphabot.model.task.Task;
import seedu.jelphabot.testutil.TaskBuilder;

class TaskDueWithinDayAndIncompletePredicateTest {

    @Test
    void test_taskSameDateDifferentTime_returnsTrue() {
        DateTime dt = new DateTime("Jun-01-2020 00 01");
        TaskDueWithinDayAndIncompletePredicate predicate = new TaskDueWithinDayAndIncompletePredicate(dt);
        Task task = new TaskBuilder().withDateTime("Jun-01-2020 22 02").build();
        assertTrue(predicate.test(task));
    }

    @Test
    void test_taskDateDifferent_returnsFalse() {
        DateTime dt = new DateTime("May-2-2020 00 01");
        TaskDueWithinDayAndIncompletePredicate pred = new TaskDueWithinDayAndIncompletePredicate(dt);
        Task task = new TaskBuilder().withDateTime("May-1-2020 00 01").build();
        assertFalse(pred.test(task));
    }

    @Test
    void test_taskDateSameStatusComplete_returnsFalse() {
        DateTime dt = new DateTime("Jan-01-2020 00 22");
        TaskDueWithinDayAndIncompletePredicate pred = new TaskDueWithinDayAndIncompletePredicate(dt);
        Task task = new TaskBuilder().withDateTime("Jan-01-2020 00 22").withStatus("COMPLETE").build();
        assertFalse(pred.test(task));
    }
}
