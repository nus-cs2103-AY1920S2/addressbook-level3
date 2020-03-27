package seedu.address.logic.commands;

import org.junit.jupiter.api.Test;
import seedu.address.commons.core.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalGoods.APPLE;
import static seedu.address.testutil.TypicalPersons.ALICE;

public class UndoCommandTest {
    private Model model = new ModelManager();

    @Test
    public void execute_changesCommitted_success() {
        Model expectedModel = new ModelManager(model.getAddressBook(), model.getInventory(), model.getUserPrefs());
        model.addGood(APPLE);
        model.addPerson(ALICE);
        model.commit();

        assertCommandSuccess(new UndoCommand(), model, UndoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_noChangesCommitted_throwsCommandException() {
        assertCommandFailure(new UndoCommand(), model, Messages.MESSAGE_UNDO_AT_INITIAL_STATE);
    }
}
