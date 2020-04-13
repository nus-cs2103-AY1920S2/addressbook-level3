package seedu.address.logic.commands.task;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.AN_INT_VALUE;
import static seedu.address.logic.commands.CommandTestUtil.DOES_NOT_EXIST_MODULE_GGG1234P;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_CODE_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_CODE_CS2103T;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.task.TaskForOneModuleCommand.MESSAGE_MODULE_DOES_NOT_EXIST;
import static seedu.address.testutil.TypicalModManager.getTypicalModManager;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CommandType;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.task.TaskContainsInModulePredicate;

class TaskForOneModuleCommandTest {
    private Model model = new ModelManager(getTypicalModManager(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalModManager(), new UserPrefs());

    @Test
    public void equals() {
        TaskContainsInModulePredicate firstPredicate =
                new TaskContainsInModulePredicate(VALID_MODULE_CODE_CS2103T);
        TaskContainsInModulePredicate secondPredicate =
                new TaskContainsInModulePredicate(VALID_MODULE_CODE_CS2101);

        TaskForOneModuleCommand taskInModuleFirstCommand = new TaskForOneModuleCommand(firstPredicate);
        TaskForOneModuleCommand taskInModuleSecondCommand = new TaskForOneModuleCommand(secondPredicate);

        // same object -> returns true
        assertTrue(taskInModuleFirstCommand.equals(taskInModuleFirstCommand));

        // same module code -> returns true
        TaskForOneModuleCommand taskInModuleFirstCommandCopy = new TaskForOneModuleCommand(firstPredicate);
        assertTrue(taskInModuleFirstCommand.equals(taskInModuleFirstCommandCopy));

        // different types -> returns false
        assertFalse(taskInModuleFirstCommand.equals(AN_INT_VALUE));

        // null -> returns false
        assertFalse(taskInModuleFirstCommand.equals(null));

        // different module codes -> returns false
        assertFalse(taskInModuleFirstCommand.equals(taskInModuleSecondCommand));
    }

    @Test
    public void execute_moduleDoesNotExist_failure() {
        String moduleCode = DOES_NOT_EXIST_MODULE_GGG1234P;
        TaskContainsInModulePredicate predicate =
                new TaskContainsInModulePredicate(moduleCode);
        TaskForOneModuleCommand taskInModuleFirstCommand = new TaskForOneModuleCommand(predicate);

        String expectedMessage = String.format(MESSAGE_MODULE_DOES_NOT_EXIST, moduleCode);
        assertCommandFailure(taskInModuleFirstCommand, model, expectedMessage);
    }

    @Test
    public void execute_moduleExists_success() {
        String moduleCode = VALID_MODULE_CODE_CS2101;
        TaskContainsInModulePredicate predicate =
                new TaskContainsInModulePredicate(moduleCode);
        TaskForOneModuleCommand taskInModuleFirstCommand = new TaskForOneModuleCommand(predicate);
        expectedModel.updateFilteredTaskList(predicate);
        String expectedMessage = String.format(TaskForOneModuleCommand.MESSAGE_SUCCESS, moduleCode);
        assertCommandSuccess(taskInModuleFirstCommand, model, expectedMessage,
                CommandType.TASK, expectedModel);
    }
}
