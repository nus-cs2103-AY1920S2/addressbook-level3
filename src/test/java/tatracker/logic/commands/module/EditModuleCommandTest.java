//@@author aakanksha-rai

package tatracker.logic.commands.module;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tatracker.commons.core.Messages.MESSAGE_INVALID_MODULE_CODE;
import static tatracker.testutil.Assert.assertThrows;
import static tatracker.testutil.module.TypicalModules.CS2103T;
import static tatracker.testutil.module.TypicalModules.CS3243;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import tatracker.logic.commands.CommandResult;
import tatracker.logic.commands.exceptions.CommandException;
import tatracker.model.ModelStub;
import tatracker.model.module.Module;
import tatracker.testutil.module.ModuleBuilder;

public class EditModuleCommandTest {

    @Test
    public void execute_moduleAcceptedByModel_editSuccessful() throws Exception {
        ModelStub.ModelStubAcceptingModuleAdded modelStub = new ModelStub.ModelStubAcceptingModuleAdded();
        Module validModule = new ModuleBuilder().build();
        modelStub.addModule(validModule);
        EditModuleCommand editModuleCommand = new EditModuleCommand(validModule.getIdentifier(), "New Name");

        assertThrows(CommandException.class, MESSAGE_INVALID_MODULE_CODE, () -> new
                EditModuleCommand("CS2030", "Invalid").execute(modelStub));

        CommandResult commandResult = editModuleCommand.execute(modelStub);

        assertEquals(String.format(EditModuleCommand.MESSAGE_EDIT_MODULE_SUCCESS, validModule.getIdentifier()),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validModule), modelStub.modulesAdded);
    }

    @Test
    public void execute_emptyModuleName_throwsCommandException() {
        Module validModule = new ModuleBuilder().build();
        EditModuleCommand editModuleCommand = new EditModuleCommand(validModule.getIdentifier(), "");
        ModelStub.ModelStubAcceptingModuleAdded modelStub = new ModelStub.ModelStubAcceptingModuleAdded();
        modelStub.addModule(validModule);

        assertThrows(CommandException.class, Module.CONSTRAINTS_MODULE_NAME, () ->
                editModuleCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        EditModuleCommand editSoftwareEngineeringCommand =
                new EditModuleCommand(CS2103T.getIdentifier(), "New Name");
        EditModuleCommand editArtificialIntelligenceCommand =
                new EditModuleCommand(CS3243.getIdentifier(), "New Name");

        // same object -> returns true
        assertTrue(editSoftwareEngineeringCommand.equals(editSoftwareEngineeringCommand));

        // same values -> returns true
        EditModuleCommand editSoftwareEngineeringCommandCopy = new EditModuleCommand(CS2103T.getIdentifier(),
                "New Name");
        assertTrue(editSoftwareEngineeringCommand.equals(editSoftwareEngineeringCommandCopy));

        // different values -> returns false
        EditModuleCommand editSoftwareEngineeringCommandCopyTwo = new EditModuleCommand(CS2103T.getIdentifier(),
                "New Name2");
        assertFalse(editSoftwareEngineeringCommand.equals(editSoftwareEngineeringCommandCopyTwo));

        // different types -> returns false
        assertFalse(editSoftwareEngineeringCommand.equals(1));

        // null -> returns false
        assertFalse(editSoftwareEngineeringCommand == null);

        // different person -> returns false
        assertFalse(editSoftwareEngineeringCommand.equals(editArtificialIntelligenceCommand));
    }
}
