package seedu.address.logic.commands.task;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalModManager.getTypicalModManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CommandType;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.task.Task;
import seedu.address.testutil.ScheduledTaskBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code TaskAddCommand}.
 */
public class TaskAddCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalModManager(), new UserPrefs());
    }

    @Test
    public void execute_newTask_success() {
        Task validTask = new ScheduledTaskBuilder().build();

        Model expectedModel = new ModelManager(model.getModManager(), new UserPrefs());
        expectedModel.addTask(validTask);

        assertCommandSuccess(new TaskAddCommand(validTask), model,
                String.format(TaskAddCommand.MESSAGE_SUCCESS, validTask),
                CommandType.TASK, expectedModel);
    }

    @Test
    public void execute_duplicateTask_throwsCommandException() {
        Task taskInList = model.getModManager().getTaskList().get(0);
        assertCommandFailure(new TaskAddCommand(taskInList), model,
                TaskAddCommand.MESSAGE_DUPLICATE_TASK);
    }

}
