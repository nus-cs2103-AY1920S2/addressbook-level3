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
class TaskDueAfterDatePredicateTest {

    @Test
    void and() {
        TaskDueAfterDatePredicate condition1 = new TaskDueAfterDatePredicate(new DateTime("Apr-30-2020 00 01"));
        TaskDueAfterDatePredicate condition2 = new TaskDueAfterDatePredicate(new DateTime("May-2-2020 00 01"));
        Predicate<Task> predicate = condition1.and(condition2);
        Task taskAfterDate = new TaskBuilder().withDateTime("May-1-2020 00 01").build();
        assertFalse(predicate.test(taskAfterDate));
    }

    @Test
    void test_taskDateAfterDate_returnsTrue() {
        TaskDueAfterDatePredicate predicate = new TaskDueAfterDatePredicate(new DateTime("Apr-30-2020 00 01"));
        Task taskAfterDate = new TaskBuilder().withDateTime("May-1-2020 00 01").build();
        assertTrue(predicate.test(taskAfterDate));
    }

    @Test
    void test_taskDateBeforeDate_returnsFalse() {
        TaskDueAfterDatePredicate predicate = new TaskDueAfterDatePredicate(new DateTime("May-2-2020 00 01"));
        Task taskAfterDate = new TaskBuilder().withDateTime("May-1-2020 00 01").build();
        assertFalse(predicate.test(taskAfterDate));
    }

    @Test
    void testEquals() {
        TaskDueAfterDatePredicate predicate1 = new TaskDueAfterDatePredicate(new DateTime("Apr-30-2020 00 01"));
        TaskDueAfterDatePredicate predicate2 = new TaskDueAfterDatePredicate(new DateTime("Apr-30-2020 00 01"));

        assertEquals(predicate1, predicate2);
        assertEquals(predicate1, predicate1);

        TaskDueAfterDatePredicate predicate3 = new TaskDueAfterDatePredicate(new DateTime("May-01-2020 00 01"));
        assertNotEquals(predicate2, predicate3);
    }
}
