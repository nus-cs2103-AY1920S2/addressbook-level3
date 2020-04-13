//@@author aakanksha-rai

package tatracker.logic.commands.sort;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tatracker.commons.core.Messages.MESSAGE_INVALID_GROUP_CODE;
import static tatracker.commons.core.Messages.MESSAGE_INVALID_MODULE_CODE;
import static tatracker.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import tatracker.logic.commands.exceptions.CommandException;
import tatracker.model.ModelStub;
import tatracker.model.module.Module;
import tatracker.testutil.module.ModuleBuilder;

public class SortGroupCommandTest {

    @Test
    public void execute_invalidGroup_throwsCommandException() throws CommandException {
        Module validModule = new ModuleBuilder().build();
        ModelStub.ModelStubAcceptingModuleAdded modelStub = new ModelStub.ModelStubAcceptingModuleAdded();
        modelStub.addModule(validModule);
        SortGroupCommand sortGroupCommand =
                new SortGroupCommand(SortType.ALPHABETIC, "G03",
                        validModule.getIdentifier());

        assertThrows(CommandException.class, MESSAGE_INVALID_GROUP_CODE, () ->
                sortGroupCommand.execute(modelStub));
    }

    @Test
    public void execute_invalidModule_throwsCommandException() throws CommandException {
        SortGroupCommand sortGroupCommand =
                new SortGroupCommand(SortType.ALPHABETIC, "G03",
                        "CS2030");
        ModelStub.ModelStubAcceptingModuleAdded modelStub = new ModelStub.ModelStubAcceptingModuleAdded();

        assertThrows(CommandException.class, MESSAGE_INVALID_MODULE_CODE, () ->
                sortGroupCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Module validModule = new ModuleBuilder().build();
        SortGroupCommand sortGroupCommand =
                new SortGroupCommand(SortType.ALPHABETIC, "G03",
                        validModule.getIdentifier());
        SortGroupCommand sortGroupCommandCopy =
                new SortGroupCommand(SortType.ALPHABETIC, "G03",
                        validModule.getIdentifier());
        SortGroupCommand sortGroupCommandTwo =
                new SortGroupCommand(SortType.ALPHABETIC, "G03",
                        "CS3243");
        SortGroupCommand sortGroupCommandThree =
                new SortGroupCommand(SortType.ALPHABETIC, "G04",
                        validModule.getIdentifier());
        SortGroupCommand sortGroupCommandFour =
                new SortGroupCommand(SortType.RATING_ASC, "G04",
                        validModule.getIdentifier());

        // same object -> returns true
        assertTrue(sortGroupCommand.equals(sortGroupCommand));

        // same values -> returns true
        assertTrue(sortGroupCommand.equals(sortGroupCommandCopy));

        // different types -> returns false
        assertFalse(sortGroupCommand.equals(1));

        // null -> returns false
        assertFalse(sortGroupCommand == null);

        // different modules -> returns false
        assertFalse(sortGroupCommand.equals(sortGroupCommandTwo));

        // different groups -> returns false
        assertFalse(sortGroupCommand.equals(sortGroupCommandThree));

        // different types -> returns false
        assertFalse(sortGroupCommand.equals(sortGroupCommandFour));
    }

}
