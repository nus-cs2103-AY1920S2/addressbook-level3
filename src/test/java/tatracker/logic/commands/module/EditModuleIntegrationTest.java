//@@author aakanksha-rai

package tatracker.logic.commands.module;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

public class EditModuleIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalTaTracker(), new UserPrefs());
    }

    @Test
    public void execute_moduleAcceptedByModel_editSuccessful() throws Exception {
        Module validModule = new ModuleBuilder().build();
        model.addModule(validModule);
        String newName = "New Name";
        EditModuleCommand editModuleCommand =
                new EditModuleCommand(validModule.getIdentifier(), newName);

        CommandResult commandResult = editModuleCommand.execute(model);

        assertEquals(String.format(EditModuleCommand.MESSAGE_EDIT_MODULE_SUCCESS, validModule.getIdentifier()),
                commandResult.getFeedbackToUser());
        Module moduleInModel = model.getModule(validModule.getIdentifier());
        assertEquals(newName, moduleInModel.getName());
    }

    @Test
    public void execute_emptyModuleName_throwsCommandException() {
        Module validModule = new ModuleBuilder().build();
        EditModuleCommand editModuleCommand = new EditModuleCommand(validModule.getIdentifier(), "");
        model.addModule(validModule);

        assertThrows(CommandException.class, Module.CONSTRAINTS_MODULE_NAME, () ->
                editModuleCommand.execute(model));
    }
}
