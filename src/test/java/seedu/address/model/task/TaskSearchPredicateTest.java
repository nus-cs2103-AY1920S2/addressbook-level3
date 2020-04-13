package seedu.address.model.task;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.TASK_DAY_25;
import static seedu.address.logic.commands.CommandTestUtil.TASK_DAY_26;
import static seedu.address.logic.commands.CommandTestUtil.TASK_MONTH_03;
import static seedu.address.logic.commands.CommandTestUtil.TASK_MONTH_06;
import static seedu.address.logic.commands.CommandTestUtil.TASK_YEAR_2020;
import static seedu.address.logic.parser.task.TaskSearchCommandParser.DATE_STRING;
import static seedu.address.logic.parser.task.TaskSearchCommandParser.MONTH_STRING;
import static seedu.address.logic.parser.task.TaskSearchCommandParser.YEAR_STRING;
import static seedu.address.testutil.TypicalTasks.scheduledAssignmentTask;
import static seedu.address.testutil.TypicalTasks.scheduledHomeworkTask;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

class TaskSearchPredicateTest {
    private static HashMap<String, Integer> keywords = new HashMap<>();

    @Test
    public void equals() {
        HashMap<String, Integer> firstHashMap = new HashMap<String, Integer>() {
            {
                put(DATE_STRING, Integer.parseInt(TASK_DAY_26));
                put(YEAR_STRING, Integer.parseInt(TASK_YEAR_2020));
            }
        };

        HashMap<String, Integer> secondHashMap = new HashMap<String, Integer>() {
            {
                put(DATE_STRING, Integer.parseInt(TASK_DAY_26));
            }
        };

        TaskSearchPredicate firstPredicate =
                new TaskSearchPredicate(firstHashMap);
        TaskSearchPredicate secondPredicate =
                new TaskSearchPredicate(secondHashMap);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same HashMap values -> returns true
        TaskSearchPredicate firstPredicateCopy = new TaskSearchPredicate(firstHashMap);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different predicates (different HashMap contents) -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }


    @Test
    public void test_oneFieldPresentMatches_returnsTrue() {
        HashMap<String, Integer> year2020HashMap = new HashMap<String, Integer>() {
            {
                put(YEAR_STRING, Integer.parseInt(TASK_YEAR_2020));
            }
        };

        TaskSearchPredicate predicate = new TaskSearchPredicate(year2020HashMap);

        // task on date 1/4/2020
        assertTrue(predicate.test(scheduledAssignmentTask));

        // task on date 25/6/2020
        assertTrue(predicate.test(scheduledHomeworkTask));
    }

    @Test
    public void test_oneFieldPresentDoesNotMatch_returnsFalse() {
        // one and only valid field, year only
        HashMap<String, Integer> year2020HashMap = new HashMap<String, Integer>() {
            {
                put(MONTH_STRING, Integer.parseInt(TASK_MONTH_03));
            }
        };

        TaskSearchPredicate predicate = new TaskSearchPredicate(year2020HashMap);

        // not in March
        assertFalse(predicate.test(scheduledAssignmentTask));
        assertFalse(predicate.test(scheduledHomeworkTask));
    }

    @Test
    public void test_multipleFieldsMatchAll_returnsTrue() {
        // one and only valid field, year only
        HashMap<String, Integer> multipleFieldsHashMap = new HashMap<String, Integer>() {
            {
                put(DATE_STRING, Integer.parseInt(TASK_DAY_25)); // matching date
                put(MONTH_STRING, Integer.parseInt(TASK_MONTH_06)); // matching month
            }
        };

        TaskSearchPredicate predicate = new TaskSearchPredicate(multipleFieldsHashMap);

        assertTrue(predicate.test(scheduledHomeworkTask));
    }

    @Test
    public void test_multipleFieldsPartialMatch_returnsFalse() {
        HashMap<String, Integer> multipleFieldsHashMap = new HashMap<String, Integer>() {
            {
                put(DATE_STRING, Integer.parseInt(TASK_DAY_25));
                put(MONTH_STRING, Integer.parseInt(TASK_MONTH_03)); // changed to different month
            }
        };

        TaskSearchPredicate predicate = new TaskSearchPredicate(multipleFieldsHashMap);

        assertFalse(predicate.test(scheduledHomeworkTask));
    }

    @Test
    public void test_multipleFieldsMatchNone_returnsFalse() {
        HashMap<String, Integer> multipleFieldsHashMap = new HashMap<String, Integer>() {
            {
                // changed to different date
                put(DATE_STRING, Integer.parseInt(TASK_DAY_26));
                // changed to different month
                put(MONTH_STRING, Integer.parseInt(TASK_MONTH_03));
            }
        };

        TaskSearchPredicate predicate = new TaskSearchPredicate(multipleFieldsHashMap);

        assertFalse(predicate.test(scheduledHomeworkTask));
    }
}
