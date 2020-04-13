package seedu.address.logic.commands.task;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalModManager.getTypicalModManager;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.task.Task;
import seedu.address.testutil.TaskBuilder;

public class TaskDeleteCommandTest {

    private Model model = new ModelManager(getTypicalModManager(), new UserPrefs());


    @Test
    public void execute_moduleCodeNotExist_throwsCommandException() {
        Task taskToDelete = new TaskBuilder().withModuleCode("CS3232").build();

        TaskDeleteCommand deleteCommand = new TaskDeleteCommand(
                taskToDelete.getModuleCode(), taskToDelete.getTaskNum());

        String expectedMessage = String.format(
                TaskDeleteCommand.MESSAGE_MODULE_NOT_FOUND, taskToDelete.getModuleCode());

        assertCommandFailure(deleteCommand, model, expectedMessage);
    }

    @Test
    public void execute_taskNumNotExist_throwsCommandException() {
        ModuleCode modCode = new ModuleCode("CS2103T");
        TaskDeleteCommand deleteCommand = new TaskDeleteCommand(modCode, 1000);
        String expectedMessage = String.format(
                TaskDeleteCommand.MESSAGE_TASK_NOT_FOUND, modCode, 1000);

        assertCommandFailure(deleteCommand, model, expectedMessage);
    }

    @Test
    public void equals() {
        ModuleCode moduleCodeOne = new ModuleCode("CS2103T");
        ModuleCode moduleCodeTwo = new ModuleCode("CS2101");
        TaskDeleteCommand taskDeleteCommand = new TaskDeleteCommand(moduleCodeOne, 1);
        TaskDeleteCommand taskDeleteCommandSameBoth = new TaskDeleteCommand(moduleCodeOne, 1);
        TaskDeleteCommand taskDeleteCommandDiffNum = new TaskDeleteCommand(moduleCodeOne, 2);
        TaskDeleteCommand taskDeleteCommandDiffCode = new TaskDeleteCommand(moduleCodeTwo, 3);

        //same object -> return true
        assertTrue(taskDeleteCommand.equals(taskDeleteCommand));

        //same moduleCode and taskNum -> return true
        assertTrue(taskDeleteCommand.equals(taskDeleteCommandSameBoth));

        //different moduleCode -> return false
        assertFalse(taskDeleteCommand.equals(taskDeleteCommandDiffCode));

        //different taskNum -> return false
        assertFalse(taskDeleteCommand.equals(taskDeleteCommandDiffNum));

    }


}
