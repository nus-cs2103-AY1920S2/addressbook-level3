//@@author aakanksha-rai

package tatracker.logic.commands.module;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tatracker.commons.core.Messages.MESSAGE_DUPLICATE_MODULE;
import static tatracker.logic.commands.module.AddModuleCommand.MESSAGE_ADD_MODULE_SUCCESS;
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

public class AddModuleIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalTaTracker(), new UserPrefs());
    }


    @Test
    public void execute_moduleAcceptedByModel_addSuccessful() throws Exception {
        Module validModule = new ModuleBuilder().build();

        CommandResult commandResult = new AddModuleCommand(validModule).execute(model);

        assertEquals(String.format(MESSAGE_ADD_MODULE_SUCCESS, validModule.getIdentifier()),
                commandResult.getFeedbackToUser());
        assertTrue(model.hasModule(validModule.getIdentifier()));
    }

    @Test
    public void execute_duplicateModule_throwsCommandException() throws CommandException {
        Module validModule = new ModuleBuilder().build();
        model.addModule(validModule);
        AddModuleCommand addModuleCommand = new AddModuleCommand(validModule);

        assertThrows(CommandException.class, MESSAGE_DUPLICATE_MODULE, () ->
                addModuleCommand.execute(model));
    }

}
