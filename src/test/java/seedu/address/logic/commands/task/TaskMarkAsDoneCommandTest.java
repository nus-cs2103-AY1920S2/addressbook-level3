package seedu.address.logic.commands.task;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.AN_INT_VALUE;
import static seedu.address.logic.commands.CommandTestUtil.DOES_NOT_EXIST_MODULE_GGG1234P;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TASK_ID;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_CODE_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_CODE_CS2103T;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_ID_FIRST;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_ID_SECOND;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.task.TaskEditCommand.MESSAGE_TASK_NOT_FOUND;
import static seedu.address.logic.commands.task.TaskForOneModuleCommand.MESSAGE_MODULE_DOES_NOT_EXIST;
import static seedu.address.logic.commands.task.TaskMarkAsDoneCommand.MESSAGE_SUCCESS;
import static seedu.address.testutil.TypicalModManager.getTypicalModManager;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CommandType;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.task.Task;

class TaskMarkAsDoneCommandTest {
    private static final int TASK_INDEX_TO_CHOOSE = 0;

    private Model model = new ModelManager(getTypicalModManager(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalModManager(), new UserPrefs());

    @Test
    public void equals() {
        TaskMarkAsDoneCommand firstCommand = new TaskMarkAsDoneCommand(
                VALID_MODULE_CODE_CS2103T, VALID_TASK_ID_FIRST);
        TaskMarkAsDoneCommand secondCommand = new TaskMarkAsDoneCommand(
                VALID_MODULE_CODE_CS2103T, VALID_TASK_ID_SECOND);

        // same object -> returns true
        assertTrue(firstCommand.equals(firstCommand));

        // different types -> returns false
        assertFalse(firstCommand.equals(AN_INT_VALUE));

        // null -> returns false
        assertFalse(firstCommand.equals(null));

        // same module code and task ID -> returns true
        TaskMarkAsDoneCommand firstCommandCopy = new TaskMarkAsDoneCommand(
                VALID_MODULE_CODE_CS2103T, VALID_TASK_ID_FIRST);
        assertTrue(firstCommand.equals(firstCommandCopy));

        // different module codes -> returns false
        TaskMarkAsDoneCommand thirdCommand = new TaskMarkAsDoneCommand(
                VALID_MODULE_CODE_CS2101, VALID_TASK_ID_FIRST);
        assertFalse(firstCommand.equals(thirdCommand));

        // same module code but different Task IDs -> returns false
        assertFalse(firstCommand.equals(secondCommand));
    }

    @Test
    public void execute_taskModuleDoesNotExist_failure() {
        String moduleCode = DOES_NOT_EXIST_MODULE_GGG1234P;
        TaskMarkAsDoneCommand command = new TaskMarkAsDoneCommand(
                moduleCode, VALID_TASK_ID_FIRST);

        String expectedMessage = String.format(MESSAGE_MODULE_DOES_NOT_EXIST, moduleCode);
        assertCommandFailure(command, model, expectedMessage);
    }

    @Test
    public void execute_taskIdDoesNotExistInModule_failure() {
        String moduleCode = VALID_MODULE_CODE_CS2103T;
        TaskMarkAsDoneCommand command = new TaskMarkAsDoneCommand(
                moduleCode, INVALID_TASK_ID);

        String expectedMessage = String.format(MESSAGE_TASK_NOT_FOUND, moduleCode,
                Integer.parseInt(INVALID_TASK_ID));
        assertCommandFailure(command, model, expectedMessage);
    }

    @Test
    public void execute_taskMarkedAsDone_success() {
        Task taskToMark = expectedModel.getFilteredTaskList().get(TASK_INDEX_TO_CHOOSE);

        assertFalse(taskToMark.isTaskDone()); // task should not be done yet

        Task editedTask = taskToMark.getClone(); // returns a defensive copy
        editedTask.markAsDone();

        assertTrue(editedTask.isTaskDone()); // task is now done

        expectedModel.setTask(taskToMark, editedTask);

        TaskMarkAsDoneCommand command = new TaskMarkAsDoneCommand(
                editedTask.getModuleCode().toString(), String.valueOf(editedTask.getTaskNum())
        );
        String expectedMessage = String.format(MESSAGE_SUCCESS, editedTask);

        assertCommandSuccess(command, model, expectedMessage, CommandType.TASK, expectedModel);
    }
}
