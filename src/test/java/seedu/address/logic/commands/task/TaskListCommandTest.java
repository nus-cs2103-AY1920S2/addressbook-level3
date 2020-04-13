package seedu.address.logic.commands.task;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalModManager.getTypicalModManager;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CommandType;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code TaskListCommand}.
 */
public class TaskListCommandTest {

    private Model model = new ModelManager(getTypicalModManager(), new UserPrefs());
    private Model expectedModel = new ModelManager(model.getModManager(), new UserPrefs());

    @Test
    public void execute_listIsShown_success() {
        assertCommandSuccess(new TaskListCommand(), model, TaskListCommand.MESSAGE_SUCCESS, CommandType.TASK,
                expectedModel);
    }
}
