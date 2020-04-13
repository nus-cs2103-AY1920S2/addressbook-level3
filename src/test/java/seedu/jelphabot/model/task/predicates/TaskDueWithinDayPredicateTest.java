//@@author yaojiethng
package seedu.jelphabot.model.task.predicates;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.jelphabot.model.task.DateTime;
import seedu.jelphabot.model.task.Task;
import seedu.jelphabot.testutil.TaskBuilder;

class TaskDueWithinDayPredicateTest {

    @Test
    void test_taskSameDateDifferentTime_returnsTrue() {
        TaskDueWithinDayPredicate predicate = new TaskDueWithinDayPredicate(new DateTime("Apr-30-2020 00 01"));
        Task task = new TaskBuilder().withDateTime("Apr-30-2020 00 02").build();
        assertTrue(predicate.test(task));
    }

    @Test
    void test_taskDateDifferent_returnsFalse() {
        TaskDueWithinDayPredicate predicate = new TaskDueWithinDayPredicate(new DateTime("May-2-2020 00 01"));
        Task task = new TaskBuilder().withDateTime("May-1-2020 00 01").build();
        assertFalse(predicate.test(task));

        task = new TaskBuilder().withDateTime("May-3-2020 00 01").build();
        assertFalse(predicate.test(task));
    }

    @Test
    void testEquals() {
        TaskDueWithinDayPredicate predicate1 = new TaskDueWithinDayPredicate(new DateTime("Apr-30-2020 00 01"));
        TaskDueWithinDayPredicate predicate2 = new TaskDueWithinDayPredicate(new DateTime("Apr-30-2020 00 01"));

        assertEquals(predicate1, predicate2);
        assertEquals(predicate1, predicate1);

        TaskDueWithinDayPredicate predicate3 = new TaskDueWithinDayPredicate(new DateTime("May-01-2020 00 01"));
        assertNotEquals(predicate2, predicate3);
    }
}
