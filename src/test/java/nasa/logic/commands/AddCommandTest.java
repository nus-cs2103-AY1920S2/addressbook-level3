package nasa.logic.commands;

import static nasa.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import nasa.logic.commands.module.AddModuleCommand;
import nasa.model.module.Module;
import nasa.testutil.ModuleBuilder;

public class AddCommandTest {

    @Test
    public void constructor_nullModule_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddModuleCommand(null));
    }

    /*
    @Test
    public void execute_moduleAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingModuleAdded modelStub = new ModelStubAcceptingModuleAdded();
        Module validModule = new ModuleBuilder().build();

        CommandResult commandResult = new AddModuleCommand(validModule).execute(modelStub);

        assertEquals(String.format(AddModuleCommand.MESSAGE_SUCCESS, validModule), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validModule), modelStub.modulesAdded);
    }

     */

    /*
    @Test
    public void execute_duplicateModule_throwsCommandException() {
        Module validModule = new ModuleBuilder().build();
        AddModuleCommand addCommand = new AddModuleCommand(validModule);
        ModelStub modelStub = new ModelStubWithModule(validModule);

        assertThrows(CommandException.class, AddModuleCommand.MESSAGE_DUPLICATED_MODULE, () ->
                addCommand.execute(modelStub));
    }

     */

    @Test
    public void equals() {
        Module cs2103t = new ModuleBuilder().withCode("CS2103T").build();
        Module cs2101 = new ModuleBuilder().withCode("CS2101").build();
        AddModuleCommand addCS2103TCommand = new AddModuleCommand(cs2103t);
        AddModuleCommand addCS2101Command = new AddModuleCommand(cs2101);

        // same object -> returns true
        assertTrue(addCS2103TCommand.equals(addCS2103TCommand));

        // same values -> returns true
        AddModuleCommand addCS2103TCommandCopy = new AddModuleCommand(cs2103t);
        assertTrue(addCS2103TCommand.equals(addCS2103TCommandCopy));

        // different types -> returns false
        assertFalse(addCS2103TCommand.equals(1));

        // null -> returns false
        assertFalse(addCS2103TCommand.equals(null));

        // different module -> returns false
        assertFalse(addCS2103TCommand.equals(addCS2101Command));
    }
}
