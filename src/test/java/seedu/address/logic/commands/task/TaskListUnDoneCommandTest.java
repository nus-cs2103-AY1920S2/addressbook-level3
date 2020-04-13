package seedu.address.logic.commands.task;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalModManager.getTypicalModManager;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.CommandType;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

class TaskListUnDoneCommandTest {
    private Model model = new ModelManager(getTypicalModManager(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalModManager(), new UserPrefs());

    @Test
    public void execute_listWithUndoneTasksIsShown_success() {
        String expectedMessage = String.format(Messages.MESSAGE_TASKS_UNDONE_OVERVIEW,
                model.getFilteredTaskList().size());
        assertCommandSuccess(new TaskListUnDoneCommand(), model, expectedMessage,
                CommandType.TASK, expectedModel);
    }
}

