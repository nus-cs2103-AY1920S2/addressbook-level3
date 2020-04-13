//@@author eedenong
package seedu.jelphabot.model.task.predicates;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.jelphabot.model.task.Task;
import seedu.jelphabot.testutil.TaskBuilder;

public class TaskIsCompletedPredicateTest {

    @Test
    public void equals() {
        TaskIsCompletedPredicate predicate = new TaskIsCompletedPredicate();

        // same object returns true
        assertEquals(predicate, predicate);

        // different types returns false
        assertNotEquals(1, predicate);

        // null returns false
        assertNotEquals(null, predicate);
    }

    @Test
    public void test_taskStatusEqual_returnsTrue() {
        // true if the status of the Task is set to COMPLETED
        TaskIsCompletedPredicate predicate = new TaskIsCompletedPredicate();
        Task completedTask = new TaskBuilder().withStatus("COMPLETE").build();
        assertTrue(predicate.test(completedTask));
    }
}
