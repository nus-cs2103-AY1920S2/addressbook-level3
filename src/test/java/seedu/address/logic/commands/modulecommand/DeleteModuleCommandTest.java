package seedu.address.logic.commands.modulecommand;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalNusModules.getTypicalModuleBook;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.calender.Task;
import seedu.address.model.nusmodule.ModuleCode;
import seedu.address.model.nusmodule.NusModule;
import seedu.address.testutil.TypicalNusModules;

/**
 * Contains unit tests for {@code DeleteModuleCommand}.
 */
class DeleteModuleCommandTest {

    private Model model = new ModelManager(new AddressBook(), new UserPrefs(),
            getTypicalModuleBook(), FXCollections.observableList(new ArrayList<Task>()));

    @Test
    public void execute_invalidModule_throwsCommandException() {
        DeleteModuleCommand deleteModuleCommand = new DeleteModuleCommand(new ModuleCode("CS2040"));

        assertCommandFailure(deleteModuleCommand, model, Messages.MESSAGE_NO_SUCH_MODULE);
    }

    @Test
    public void execute_validModule_deleteSuccessful() throws Exception {
        NusModule validModule = TypicalNusModules.CS2030;
        CommandResult commandResult = new DeleteModuleCommand(validModule.getModuleCode()).execute(model);

        assertEquals(DeleteModuleCommand.MESSAGE_SUCCESS + validModule.getModuleCode(),
                commandResult.getFeedbackToUser());
    }

    @Test
    public void equals() {
        NusModule module1 = TypicalNusModules.CS2030;
        NusModule module2 = TypicalNusModules.CS2103;
        DeleteModuleCommand deleteModuleCommand1 = new DeleteModuleCommand(module1.getModuleCode());
        DeleteModuleCommand deleteModuleCommand2 = new DeleteModuleCommand(module2.getModuleCode());

        // same object -> returns true
        assertTrue(deleteModuleCommand1.equals(deleteModuleCommand1));

        // same values -> returns true
        DeleteModuleCommand deleteModuleCommand1Copy = new DeleteModuleCommand(module1.getModuleCode());
        assertTrue(deleteModuleCommand1.equals(deleteModuleCommand1Copy));

        // different types -> returns false
        assertFalse(deleteModuleCommand1.equals(1));

        // null -> returns false
        assertFalse(deleteModuleCommand1.equals(null));

        // different person -> returns false
        assertFalse(deleteModuleCommand1.equals(deleteModuleCommand2));
    }
}
