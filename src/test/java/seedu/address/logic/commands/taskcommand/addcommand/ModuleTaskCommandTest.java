package seedu.address.logic.commands.taskcommand.addcommand;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalNusModules.getTypicalModuleBook;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ModuleBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.calender.Task;
import seedu.address.model.nusmodule.ModuleTask;
import seedu.address.testutil.TypicalNusModules;

class ModuleTaskCommandTest {
    private static ModuleTask taskValid = TypicalNusModules.TYPICAL_MODULE_TASK_1;
    private static ModuleTask taskInvalid = TypicalNusModules.TYPICAL_MODULE_TASK_2;

    private Model model = new ModelManager(new AddressBook(), new UserPrefs(),
            getTypicalModuleBook(), FXCollections.observableList(new ArrayList<Task>()));

    @Test
    public void execute_addInNewModuleTask_successful() throws Exception {
        ModuleTaskCommand moduleTaskCommand = new ModuleTaskCommand(taskValid);

        Model expectedModel = new ModelManager(new AddressBook(), new UserPrefs(),
                new ModuleBook(model.getModuleBook()), FXCollections.observableList(new ArrayList<Task>()));
        expectedModel.addModuleTask(taskValid);

        assertCommandSuccess(moduleTaskCommand, model, expectedModel);
    }

    @Test
    public void execute_taskWithInvalidModuleCode_throwsCommandException() {
        ModuleTaskCommand moduleTaskCommand = new ModuleTaskCommand(taskInvalid);

        assertThrows(CommandException.class, ModuleTaskCommand.MESSAGE_NO_SUCH_MODULE, (
        ) -> moduleTaskCommand.execute(model));
    }

    @Test
    public void equals() {
        final ModuleTaskCommand standardCommand = new ModuleTaskCommand(taskValid);

        // same values -> returns true
        ModuleTaskCommand commandWithSameValues = new ModuleTaskCommand(taskValid);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different module tasks -> returns false
        assertFalse(standardCommand.equals(new ModuleTaskCommand(taskInvalid)));
    }
}
