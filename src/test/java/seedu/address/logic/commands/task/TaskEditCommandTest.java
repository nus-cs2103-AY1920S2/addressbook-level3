package seedu.address.logic.commands.task;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalModManager.getTypicalModManager;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.CommandType;
import seedu.address.model.ModManager;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.task.Task;
import seedu.address.model.task.util.TaskDateTime;
import seedu.address.testutil.EditTaskDescriptorBuilder;
import seedu.address.testutil.TaskBuilder;


public class TaskEditCommandTest {
    private ModuleCode defaultCode = new ModuleCode(TaskBuilder.DEFAULT_MOD_CODE);
    private int defaultNum = TaskBuilder.DEFAULT_TASK_NUM;

    @Test
    void execute_allFieldsSuccess() {
        Model model = new ModelManager(getTypicalModManager(), new UserPrefs());
        Task editedTask = new TaskBuilder().withDescription("Megaman")
                .withTaskDateTime(new TaskDateTime("18/01/2021", "23:50")).build();

        TaskEditCommand.EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder(editedTask).build();
        TaskEditCommand command = new TaskEditCommand(defaultCode, defaultNum, descriptor);

        String expectedMsg = String.format(TaskEditCommand.MESSAGE_EDIT_TASK_SUCCESS, editedTask);

        Model expectedModel = new ModelManager(new ModManager(model.getModManager()), new UserPrefs());

        expectedModel.setTask(expectedModel.getFilteredTaskList().get(1), editedTask);
        assertCommandSuccess(command, model, expectedMsg, CommandType.TASK, expectedModel);
    }

    @Test
    void execute_allFieldNonDateSuccess() {
        Model model = new ModelManager(getTypicalModManager(), new UserPrefs());
        TaskBuilder editedTask = new TaskBuilder().withDescription("Megaman")
                .withTaskDateTime(Task.TABOO_DATE_TIME);

        TaskEditCommand.EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder(editedTask.build()).build();
        TaskEditCommand command = new TaskEditCommand(defaultCode, defaultNum, descriptor);

        String expectedMsg = String.format(TaskEditCommand.MESSAGE_EDIT_TASK_SUCCESS,
                editedTask.withTaskDateTime(null).build());

        Model expectedModel = new ModelManager(new ModManager(model.getModManager()), new UserPrefs());

        expectedModel.setTask(expectedModel.getFilteredTaskList().get(1), editedTask.withTaskDateTime(null).build());
        assertCommandSuccess(command, model, expectedMsg, CommandType.TASK, expectedModel);
    }

    @Test
    void execute_noDescriptionNonDateSuccess() {
        Model model = new ModelManager(getTypicalModManager(), new UserPrefs());
        TaskBuilder editedTask = new TaskBuilder(model.getFilteredTaskList().get(1))
                .withTaskDateTime(Task.TABOO_DATE_TIME);

        TaskEditCommand.EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder(editedTask.build()).build();
        TaskEditCommand command = new TaskEditCommand(defaultCode, defaultNum, descriptor);

        String expectedMsg = String.format(TaskEditCommand.MESSAGE_EDIT_TASK_SUCCESS,
                editedTask.withTaskDateTime(null).build());

        Model expectedModel = new ModelManager(new ModManager(model.getModManager()), new UserPrefs());

        expectedModel.setTask(expectedModel.getFilteredTaskList().get(1), editedTask.withTaskDateTime(null).build());
        assertCommandSuccess(command, model, expectedMsg, CommandType.TASK, expectedModel);
    }

    @Test
    void execute_descriptionNoDateSuccess() {
        Model model = new ModelManager(getTypicalModManager(), new UserPrefs());
        Task editedTask = new TaskBuilder().withDescription("Megaman").build();

        TaskEditCommand.EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder(editedTask).build();
        TaskEditCommand command = new TaskEditCommand(defaultCode, defaultNum, descriptor);

        String expectedMsg = String.format(TaskEditCommand.MESSAGE_EDIT_TASK_SUCCESS, editedTask);

        Model expectedModel = new ModelManager(new ModManager(model.getModManager()), new UserPrefs());

        expectedModel.setTask(expectedModel.getFilteredTaskList().get(1), editedTask);
        assertCommandSuccess(command, model, expectedMsg, CommandType.TASK, expectedModel);
    }

    @Test
    void execute_duplicateTaskUnsuccessful() {
        Model model = new ModelManager(getTypicalModManager(), new UserPrefs());
        Task editedTask = new TaskBuilder(model.getFilteredTaskList().get(1)).build();

        TaskEditCommand.EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder(editedTask).build();
        TaskEditCommand command = new TaskEditCommand(defaultCode, defaultNum, descriptor);

        String expectedMsg = Messages.MESSAGE_NOT_EDITED;

        assertCommandFailure(command, model, expectedMsg);
    }
}
