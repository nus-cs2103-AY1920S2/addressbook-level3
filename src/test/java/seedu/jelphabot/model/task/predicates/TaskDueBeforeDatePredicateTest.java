//@@author yaojiethng
package seedu.jelphabot.model.task.predicates;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.jelphabot.model.task.DateTime;
import seedu.jelphabot.model.task.Task;
import seedu.jelphabot.testutil.TaskBuilder;

// @@author yaojiethng
class TaskDueBeforeDatePredicateTest {

    @Test
    void and() {
        TaskDueBeforeDatePredicate condition1 = new TaskDueBeforeDatePredicate(new DateTime("May-30-2020 00 01"));
        TaskDueBeforeDatePredicate condition2 = new TaskDueBeforeDatePredicate(new DateTime("May-2-2020 00 01"));
        Predicate<Task> predicate = condition1.and(condition2);
        Task taskBeforeDate = new TaskBuilder().withDateTime("May-1-2020 00 01").build();
        assertTrue(predicate.test(taskBeforeDate));
    }

    @Test
    void test_taskDateAfterDate_returnsTrue() {
        TaskDueBeforeDatePredicate predicate = new TaskDueBeforeDatePredicate(new DateTime("Apr-30-2020 00 01"));
        Task taskBeforeDate = new TaskBuilder().withDateTime("May-1-2020 00 01").build();
        assertFalse(predicate.test(taskBeforeDate));
    }

    @Test
    void test_taskDateBeforeDate_returnsFalse() {
        TaskDueBeforeDatePredicate predicate = new TaskDueBeforeDatePredicate(new DateTime("May-2-2020 00 01"));
        Task taskBeforeDate = new TaskBuilder().withDateTime("May-1-2020 00 01").build();
        assertTrue(predicate.test(taskBeforeDate));
    }

    @Test
    void testEquals() {
        TaskDueBeforeDatePredicate predicate1 = new TaskDueBeforeDatePredicate(new DateTime("Apr-30-2020 00 01"));
        TaskDueBeforeDatePredicate predicate2 = new TaskDueBeforeDatePredicate(new DateTime("Apr-30-2020 00 01"));

        assertEquals(predicate1, predicate2);
        assertEquals(predicate1, predicate1);

        TaskDueBeforeDatePredicate predicate3 = new TaskDueBeforeDatePredicate(new DateTime("May-01-2020 00 01"));
        assertNotEquals(predicate2, predicate3);
    }
}
