//@@author aakanksha-rai

package tatracker.logic.commands.module;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tatracker.commons.core.Messages.MESSAGE_DUPLICATE_MODULE;
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

public class AddModuleCommandTest {

    @Test
    public void constructor_nullModule_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddModuleCommand(null));
    }

    @Test
    public void execute_moduleAcceptedByModel_addSuccessful() throws Exception {
        ModelStub.ModelStubAcceptingModuleAdded modelStub = new ModelStub.ModelStubAcceptingModuleAdded();
        Module validModule = new ModuleBuilder().build();

        CommandResult commandResult = new AddModuleCommand(validModule).execute(modelStub);

        assertEquals(String.format(AddModuleCommand.MESSAGE_ADD_MODULE_SUCCESS, validModule.getIdentifier()),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validModule), modelStub.modulesAdded);
    }

    @Test
    public void execute_duplicateModule_throwsCommandException() {
        Module validModule = new ModuleBuilder().build();
        AddModuleCommand addModuleCommand = new AddModuleCommand(validModule);
        ModelStub modelStub = new ModelStub.ModelStubWithModule(validModule);

        assertThrows(CommandException.class, MESSAGE_DUPLICATE_MODULE, () ->
                addModuleCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        AddModuleCommand addSoftwareEngineeringCommand = new AddModuleCommand(CS2103T);
        AddModuleCommand addArtificialIntelligenceCommand = new AddModuleCommand(CS3243);

        // same object -> returns true
        assertTrue(addSoftwareEngineeringCommand.equals(addSoftwareEngineeringCommand));

        // same values -> returns true
        AddModuleCommand addSoftwareEngineeringCommandCopy = new AddModuleCommand(CS2103T);
        assertTrue(addSoftwareEngineeringCommand.equals(addSoftwareEngineeringCommandCopy));

        // different types -> returns false
        assertFalse(addSoftwareEngineeringCommand.equals(1));

        // null -> returns false
        assertFalse(addSoftwareEngineeringCommand == null);

        // different person -> returns false
        assertFalse(addSoftwareEngineeringCommand.equals(addArtificialIntelligenceCommand));
    }
}
