//@@author aakanksha-rai

package tatracker.logic.commands.module;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static tatracker.commons.core.Messages.MESSAGE_INVALID_MODULE_CODE;
import static tatracker.testutil.Assert.assertThrows;
import static tatracker.testutil.TypicalTaTracker.getTypicalTaTracker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import tatracker.logic.commands.CommandResult;
import tatracker.logic.commands.exceptions.CommandException;
import tatracker.model.Model;
import tatracker.model.ModelManager;
import tatracker.model.UserPrefs;
import tatracker.model.module.Module;
import tatracker.testutil.module.ModuleBuilder;

public class DeleteModuleIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalTaTracker(), new UserPrefs());
    }

    @Test
    public void execute_moduleRemovedFromModel_deleteSuccessful() throws Exception {

        Module validModule = new ModuleBuilder().build();
        model.addModule(validModule);
        DeleteModuleCommand deleteModuleCommand = new DeleteModuleCommand(validModule.getIdentifier());

        CommandResult commandResult = deleteModuleCommand.execute(model);

        assertEquals(String.format(DeleteModuleCommand.MESSAGE_DELETE_MODULE_SUCCESS,
                validModule.getIdentifier()), commandResult.getFeedbackToUser());
        assertFalse(model.hasModule(validModule.getIdentifier()));
    }

    @Test
    public void execute_noModule_throwsCommandException() throws CommandException {

        Module validModule = new ModuleBuilder().build();
        model.addModule(validModule);
        DeleteModuleCommand deleteModuleCommand = new DeleteModuleCommand("CS1232");

        assertThrows(CommandException.class, MESSAGE_INVALID_MODULE_CODE, () ->
                deleteModuleCommand.execute(model));
    }
}
