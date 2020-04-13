package fithelper.logic.commands;

import static fithelper.logic.commands.CommandTestUtil.FOOD;
import static fithelper.logic.commands.SortCommand.ASCENDING_ORDER;
import static fithelper.logic.commands.SortCommand.BY;
import static fithelper.logic.commands.SortCommand.DESCENDING_ORDER;
import static fithelper.testutil.AssertUtil.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import fithelper.commons.core.Messages;
import fithelper.commons.exceptions.IllegalValueException;
import fithelper.model.Model;
import fithelper.model.ModelStub;
import fithelper.model.entry.SortBy;
import fithelper.model.entry.Type;

public class SortCommandTest {

    @Test
    public void createCommand_withoutSortBy_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new SortCommand(new Type(FOOD), null, false));
    }

    @Test
    public void createCommand_withSortBy_successful() throws IllegalValueException {
        Model model = new ModelStub();

        // all fields present
        SortCommand command = new SortCommand(new Type(FOOD), new SortBy("name"), false);
        CommandResult commandResult = command.execute(model);
        String expectedMessage = Messages.MESSAGE_FOOD_ENTRY_LIST_SORTED + BY
                + command.getSortBy() + DESCENDING_ORDER;
        assertEquals(expectedMessage, commandResult.getFeedbackToUser());

        // sort type filed missing
        command = new SortCommand(null, new SortBy("time"), true);
        commandResult = command.execute(model);
        expectedMessage = Messages.MESSAGE_BOTH_ENTRY_LIST_SORTED + BY
                + command.getSortBy() + ASCENDING_ORDER;
        assertEquals(expectedMessage, commandResult.getFeedbackToUser());
    }
}
