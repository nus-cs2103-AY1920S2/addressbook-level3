package seedu.address.logic.commands.task;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.AN_INT_VALUE;
import static seedu.address.logic.commands.CommandTestUtil.TASK_DAY_26;
import static seedu.address.logic.commands.CommandTestUtil.TASK_MONTH_03;
import static seedu.address.logic.commands.CommandTestUtil.TASK_YEAR_2020;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.parser.task.TaskSearchCommandParser.DATE_STRING;
import static seedu.address.logic.parser.task.TaskSearchCommandParser.MONTH_STRING;
import static seedu.address.logic.parser.task.TaskSearchCommandParser.YEAR_STRING;
import static seedu.address.testutil.TypicalModManager.getTypicalModManager;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.CommandType;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.task.TaskSearchPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code TaskSearchCommand}.
 *
 * Note: Since {@code TaskSearchCommandParser} has already thrown {@code ParseException}
 * for invalid inputs, the inputs to {@code TaskSearchCommand} will always be valid.
 */

public class TaskSearchCommandTest {
    private Model model = new ModelManager(getTypicalModManager(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalModManager(), new UserPrefs());

    @Test
    public void equals() {
        HashMap<String, Integer> dateYearHashMap = new HashMap<String, Integer>() {
            {
                put(DATE_STRING, Integer.parseInt(TASK_DAY_26));
                put(YEAR_STRING, Integer.parseInt(TASK_YEAR_2020));
            }
        };

        HashMap<String, Integer> dateHashMap = new HashMap<String, Integer>() {
            {
                put(DATE_STRING, Integer.parseInt(TASK_DAY_26));
            }
        };
        TaskSearchPredicate firstPredicate =
                new TaskSearchPredicate(dateYearHashMap);
        TaskSearchPredicate secondPredicate =
                new TaskSearchPredicate(dateYearHashMap);
        TaskSearchPredicate thirdDifferentPredicate =
                new TaskSearchPredicate(dateHashMap);
        TaskSearchCommand searchFirstCommand = new TaskSearchCommand(firstPredicate);
        TaskSearchCommand searchSecondCommand = new TaskSearchCommand(secondPredicate);
        TaskSearchCommand searchThirdCommand = new TaskSearchCommand(thirdDifferentPredicate);

        // same object -> returns true
        assertTrue(searchFirstCommand.equals(searchFirstCommand));

        // same values -> returns true
        TaskSearchCommand searchFirstCommandCopy = new TaskSearchCommand(firstPredicate);
        assertTrue(searchFirstCommand.equals(searchFirstCommandCopy));

        // different types -> returns false
        assertFalse(searchFirstCommand.equals(AN_INT_VALUE));

        // null -> returns false
        assertFalse(searchFirstCommand.equals(null));

        // same parameters to search for -> returns true
        assertTrue(searchFirstCommand.equals(searchSecondCommand));

        // different parameters to search for -> returns false
        assertFalse(searchFirstCommand.equals(searchThirdCommand));
    }

    @Test
    public void execute_oneFieldPresent_success() {
        // one and only valid field, year only
        HashMap<String, Integer> yearHashMap = new HashMap<String, Integer>() {
            {
                put(YEAR_STRING, Integer.parseInt(TASK_YEAR_2020));
            }
        };

        TaskSearchPredicate firstTaskSearchPredicate = new TaskSearchPredicate(yearHashMap);
        TaskSearchCommand firstTaskSearchCommand = new TaskSearchCommand(firstTaskSearchPredicate);
        expectedModel.updateFilteredTaskList(firstTaskSearchPredicate);
        String expectedMessage = String.format(Messages.MESSAGE_TASKS_FILTERED_OVERVIEW,
                expectedModel.getFilteredTaskList().size());
        assertCommandSuccess(firstTaskSearchCommand, model, expectedMessage,
                CommandType.TASK, expectedModel);
    }

    @Test
    public void execute_twoFieldsPresent_success() {
        // two and only two valid fields, day and month
        HashMap<String, Integer> dayMonthHashMap = new HashMap<String, Integer>() {
            {
                put(DATE_STRING, Integer.parseInt(TASK_DAY_26));
                put(MONTH_STRING, Integer.parseInt(TASK_MONTH_03));
            }
        };

        TaskSearchPredicate firstTaskSearchPredicate = new TaskSearchPredicate(dayMonthHashMap);
        TaskSearchCommand firstTaskSearchCommand = new TaskSearchCommand(firstTaskSearchPredicate);
        expectedModel.updateFilteredTaskList(firstTaskSearchPredicate);
        String expectedMessage = String.format(Messages.MESSAGE_TASKS_FILTERED_OVERVIEW,
                expectedModel.getFilteredTaskList().size());
        assertCommandSuccess(firstTaskSearchCommand, model, expectedMessage,
                CommandType.TASK, expectedModel);
    }

    @Test
    public void execute_allThreeFieldsPresent_success() {
        HashMap<String, Integer> hashMap = new HashMap<String, Integer>() {
            {
                put(DATE_STRING, Integer.parseInt(TASK_DAY_26));
                put(MONTH_STRING, Integer.parseInt(TASK_MONTH_03));
                put(YEAR_STRING, Integer.parseInt(TASK_YEAR_2020));
            }
        };
        TaskSearchPredicate taskSearchPredicate = new TaskSearchPredicate(hashMap);
        TaskSearchCommand taskSearchCommand = new TaskSearchCommand(taskSearchPredicate);
        expectedModel.updateFilteredTaskList(taskSearchPredicate);
        String expectedMessage = String.format(Messages.MESSAGE_TASKS_FILTERED_OVERVIEW,
                expectedModel.getFilteredTaskList().size());
        assertCommandSuccess(taskSearchCommand, model, expectedMessage,
                CommandType.TASK, expectedModel);
    }

}

