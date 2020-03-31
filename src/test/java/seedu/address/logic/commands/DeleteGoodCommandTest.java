package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showGoodAtIndex;
import static seedu.address.testutil.TypicalGoods.APPLE;
import static seedu.address.testutil.TypicalGoods.getTypicalInventory;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_GOOD;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_GOOD;
import static seedu.address.testutil.TypicalSuppliers.getTypicalAddressBook;
import static seedu.address.testutil.TypicalTransactions.getTypicalTransactionHistory;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.good.Good;

class DeleteGoodCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalInventory(),
            getTypicalTransactionHistory(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Good goodToDelete = model.getFilteredGoodList().get(INDEX_FIRST_GOOD.getZeroBased());
        DeleteGoodCommand deleteGoodCommand = new DeleteGoodCommand(INDEX_FIRST_GOOD);

        String expectedMessage = String.format(DeleteGoodCommand.MESSAGE_DELETE_GOOD_SUCCESS,
                goodToDelete.getGoodName().fullGoodName);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), getTypicalInventory(),
                getTypicalTransactionHistory(), new UserPrefs());
        expectedModel.deleteGood(goodToDelete);

        assertCommandSuccess(deleteGoodCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredGoodList().size() + 1);
        DeleteGoodCommand deleteGoodCommand = new DeleteGoodCommand(outOfBoundIndex);

        assertCommandFailure(deleteGoodCommand, model, Messages.MESSAGE_INVALID_GOOD_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showGoodAtIndex(model, INDEX_FIRST_GOOD);

        Good goodToDelete = model.getFilteredGoodList().get(INDEX_FIRST_GOOD.getZeroBased());
        DeleteGoodCommand deleteGoodCommand = new DeleteGoodCommand(INDEX_FIRST_GOOD);

        String expectedMessage = String.format(DeleteGoodCommand.MESSAGE_DELETE_GOOD_SUCCESS,
                goodToDelete.getGoodName().fullGoodName);

        Model expectedModel = new ModelManager(model.getAddressBook(), getTypicalInventory(),
                getTypicalTransactionHistory(), new UserPrefs());
        expectedModel.deleteGood(goodToDelete);
        showNoInventory(expectedModel);

        assertCommandSuccess(deleteGoodCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validIndex_callsModelCommit() throws CommandException {
        ModelStubCommit modelStub = new ModelStubCommit();
        modelStub.addGood(APPLE);
        new DeleteGoodCommand(INDEX_FIRST_GOOD).execute(modelStub);

        assertTrue(modelStub.isCommitted());
    }

    @Test
    public void equals() {
        DeleteGoodCommand deleteFirstCommand = new DeleteGoodCommand(INDEX_FIRST_GOOD);
        DeleteGoodCommand deleteSecondCommand = new DeleteGoodCommand(INDEX_SECOND_GOOD);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteGoodCommand deleteFirstCommandCopy = new DeleteGoodCommand(INDEX_FIRST_GOOD);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different Good -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no goods.
     */
    private void showNoInventory(Model model) {
        model.updateFilteredGoodList(p -> false);

        assertTrue(model.getFilteredGoodList().isEmpty());
    }
}


