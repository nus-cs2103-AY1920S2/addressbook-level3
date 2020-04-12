//@@author aakanksha-rai

package tatracker.logic.commands.sort;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tatracker.commons.core.Messages.MESSAGE_INVALID_MODULE_CODE;
import static tatracker.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import tatracker.logic.commands.exceptions.CommandException;
import tatracker.model.ModelStub;

public class SortModuleCommandTest {

    @Test
    public void execute_invalidModule_throwsCommandException() throws CommandException {
        SortModuleCommand sortModuleCommand =
                new SortModuleCommand(SortType.ALPHABETIC,
                        "CS2030");
        ModelStub.ModelStubAcceptingModuleAdded modelStub = new ModelStub.ModelStubAcceptingModuleAdded();

        assertThrows(CommandException.class, MESSAGE_INVALID_MODULE_CODE, () ->
                sortModuleCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        SortModuleCommand sortModuleCommand =
                new SortModuleCommand(SortType.ALPHABETIC,
                        "CS2030");
        SortModuleCommand sortModuleCommandCopy =
                new SortModuleCommand(SortType.ALPHABETIC,
                        "CS2030");
        SortModuleCommand sortNewModuleCommand =
                new SortModuleCommand(SortType.ALPHABETIC,
                        "CS2040");
        SortModuleCommand sortNewTypeModuleCommand =
                new SortModuleCommand(SortType.RATING_ASC,
                        "CS2030");

        // same object -> returns true
        assertTrue(sortModuleCommand.equals(sortModuleCommand));

        // same values -> returns true
        assertTrue(sortModuleCommand.equals(sortModuleCommandCopy));

        // different types -> returns false
        assertFalse(sortModuleCommand.equals(1));

        // null -> returns false
        assertFalse(sortModuleCommand == null);

        // different modules -> returns false
        assertFalse(sortModuleCommand.equals(sortNewModuleCommand));

        // different types -> returns false
        assertFalse(sortModuleCommand.equals(sortNewTypeModuleCommand));
    }

}
