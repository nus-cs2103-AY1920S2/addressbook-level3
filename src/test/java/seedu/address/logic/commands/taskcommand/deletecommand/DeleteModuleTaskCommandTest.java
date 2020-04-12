package seedu.address.logic.commands.taskcommand.deletecommand;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalNusModules.getTypicalModuleBook;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ModuleBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.calender.Task;
import seedu.address.model.nusmodule.ModuleCode;
import seedu.address.testutil.TypicalNusModules;

class DeleteModuleTaskCommandTest {

    private static final ModuleCode validModuleCode = TypicalNusModules.CS2030.getModuleCode();

    //module code which hasn't been added to module book yet
    private static final ModuleCode invalidModuleCode = new ModuleCode("CS1101S");

    private static final ModuleCode anotherValidModuleCode = TypicalNusModules.CS2103.getModuleCode();

    private Model model = new ModelManager(new AddressBook(), new UserPrefs(),
            getTypicalModuleBook(), FXCollections.observableList(new ArrayList<Task>()));


    @Test
    public void execute_deleteModuleTask_successful() throws Exception {
        DeleteModuleTaskCommand deleteModuleTaskCommand = new DeleteModuleTaskCommand(validModuleCode,
                Index.fromZeroBased(0));

        model.addDeadlineTask(TypicalNusModules.TYPICAL_MODULE_TASK_1);

        Model expectedModel = new ModelManager(new AddressBook(), new UserPrefs(),
                new ModuleBook(model.getModuleBook()), FXCollections.observableList(new ArrayList<Task>()));
        expectedModel.addDeadlineTask(TypicalNusModules.TYPICAL_MODULE_TASK_1);
        assertCommandSuccess(deleteModuleTaskCommand, model, expectedModel);
    }

    @Test
    public void execute_argumentWithInvalidModuleCode_throwsCommandException() {
        DeleteModuleTaskCommand deleteModuleTaskCommand = new DeleteModuleTaskCommand(invalidModuleCode,
                Index.fromZeroBased(0));

        assertThrows(CommandException.class, Messages.MESSAGE_NO_SUCH_MODULE, (
        ) -> deleteModuleTaskCommand.execute(model));
    }

    @Test
    public void execute_argumentWithInvalidIndex_throwsCommandException() {
        DeleteModuleTaskCommand deleteModuleTaskCommand = new DeleteModuleTaskCommand(validModuleCode,
                Index.fromZeroBased(10000));

        assertThrows(CommandException.class, Messages.MESSAGE_INVALID_MODULE_TASK_INDEX, (
        ) -> deleteModuleTaskCommand.execute(model));
    }

    @Test
    public void equals() {
        final DeleteModuleTaskCommand standardCommand = new DeleteModuleTaskCommand(validModuleCode,
                Index.fromZeroBased(0));

        // same values -> returns true
        DeleteModuleTaskCommand commandWithSameValues = new DeleteModuleTaskCommand(validModuleCode,
                Index.fromZeroBased(0));
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different module code -> returns false
        assertFalse(standardCommand.equals(new DeleteModuleTaskCommand(anotherValidModuleCode,
                Index.fromZeroBased(0))));

        // different index -> returns false
        assertFalse(standardCommand.equals(new DeleteModuleTaskCommand(validModuleCode, Index.fromZeroBased(1))));
    }
}
