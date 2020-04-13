//@@author eedenong
package seedu.jelphabot.model.task.predicates;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.jelphabot.model.task.DateTime;
import seedu.jelphabot.model.task.Task;
import seedu.jelphabot.testutil.TaskBuilder;

public class TaskCompletedWithinDayPredicateTest {
    private final TaskCompletedWithinDayPredicate testPred = new TaskCompletedWithinDayPredicate(
        new DateTime("Apr-30-2020 23 59"));

    @Test
    void test_taskDoneSameDayDifferentTime_returnsTrue() {
        Task task = new TaskBuilder().withDateTime("Apr-30-2020 00 01").build();
        task.setDoneTime(new DateTime("Apr-30-2020 12 00"));
        assertTrue(testPred.test(task));
    }

    @Test
    void test_taskDoneDifferentDay_returnsFalse() {
        Task task = new TaskBuilder().withDateTime("Apr-30-2020 00 01").build();
        task.setDoneTime(new DateTime("May-10-2020 00 01"));
        assertFalse(testPred.test(task));
    }
}
