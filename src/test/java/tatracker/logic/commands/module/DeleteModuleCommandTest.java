//@@author aakanksha-rai

package tatracker.logic.commands.module;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tatracker.commons.core.Messages.MESSAGE_INVALID_MODULE_CODE;
import static tatracker.testutil.Assert.assertThrows;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import tatracker.logic.commands.CommandResult;
import tatracker.logic.commands.exceptions.CommandException;
import tatracker.model.ModelStub;
import tatracker.model.module.Module;
import tatracker.testutil.module.ModuleBuilder;

public class DeleteModuleCommandTest {

    @Test
    public void execute_moduleRemovedFromModel_deleteSuccessful() throws Exception {

        Module validModule = new ModuleBuilder().build();
        ModelStub.ModelStubAcceptingModuleAdded modelStub = new ModelStub.ModelStubAcceptingModuleAdded();
        modelStub.addModule(validModule);
        DeleteModuleCommand deleteModuleCommand = new DeleteModuleCommand(validModule.getIdentifier());

        CommandResult commandResult = deleteModuleCommand.execute(modelStub);

        assertEquals(String.format(DeleteModuleCommand.MESSAGE_DELETE_MODULE_SUCCESS, validModule.getIdentifier()),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(), modelStub.modulesAdded);
    }

    @Test
    public void execute_noModule_throwsCommandException() {

        Module validModule = new ModuleBuilder().build();
        ModelStub.ModelStubAcceptingModuleAdded modelStub = new ModelStub.ModelStubAcceptingModuleAdded();
        modelStub.addModule(validModule);
        DeleteModuleCommand deleteModuleCommand = new DeleteModuleCommand("CS1232");

        assertThrows(CommandException.class, MESSAGE_INVALID_MODULE_CODE, () ->
                deleteModuleCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        DeleteModuleCommand deleteSoftwareEngineeringCommand = new DeleteModuleCommand("CS2103T");
        DeleteModuleCommand deleteArtificialIntelligenceCommand = new DeleteModuleCommand("CS3243");

        // same object -> returns true
        assertTrue(deleteSoftwareEngineeringCommand.equals(deleteSoftwareEngineeringCommand));

        // same values -> returns true
        DeleteModuleCommand deleteSoftwareEngineeringCommandCopy = new DeleteModuleCommand("CS2103T");
        assertTrue(deleteSoftwareEngineeringCommand.equals(deleteSoftwareEngineeringCommandCopy));

        // different types -> returns false
        assertFalse(deleteSoftwareEngineeringCommand.equals(1));

        // null -> returns false
        assertFalse(deleteSoftwareEngineeringCommand == null);

        // different person -> returns false
        assertFalse(deleteSoftwareEngineeringCommand.equals(deleteArtificialIntelligenceCommand));
    }
}
