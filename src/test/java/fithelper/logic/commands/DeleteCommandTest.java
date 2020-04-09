package fithelper.logic.commands;

import static fithelper.logic.commands.CommandTestUtil.assertCommandFailure;
import static fithelper.logic.commands.CommandTestUtil.assertCommandSuccess;
import static fithelper.logic.commands.CommandTestUtil.showEntryAtIndex;
import static fithelper.testutil.TypicalEntriesUtil.getTypicalFitHelper;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static fithelper.testutil.TypicalIndexes.INDEX_FIRST_ENTRY;
import static fithelper.testutil.TypicalIndexes.INDEX_SECOND_ENTRY;

import org.junit.jupiter.api.Test;

import fithelper.model.UserProfile;
import fithelper.model.WeightRecords;
import fithelper.model.entry.Type;
import fithelper.commons.core.Messages;
import fithelper.commons.core.index.Index;
import fithelper.model.Model;
import fithelper.model.ModelManager;
import fithelper.model.entry.Entry;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalFitHelper(), new UserProfile(), new WeightRecords());

    @Test
    public void execute_validIndexUnfilteredFoodList_success() {
        Entry foodEntryToDelete = model.getFilteredFoodEntryList().get(INDEX_FIRST_ENTRY.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(new Type("food"), INDEX_FIRST_ENTRY);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_ENTRY_SUCCESS, foodEntryToDelete);

        ModelManager expectedModel = new ModelManager(model.getFitHelper(), new UserProfile(), new WeightRecords());
        expectedModel.deleteEntry(foodEntryToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validIndexUnfilteredSportsList_success() {
        Entry sportEntryToDelete = model.getFilteredSportsEntryList().get(INDEX_FIRST_ENTRY.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(new Type("sport"), INDEX_FIRST_ENTRY);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_ENTRY_SUCCESS, sportEntryToDelete);

        ModelManager expectedModel = new ModelManager(model.getFitHelper(), new UserProfile(), new WeightRecords());
        expectedModel.deleteEntry(sportEntryToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredFoodList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredFoodEntryList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(new Type("food"), outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_ENTRY_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidIndexUnfilteredSportList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredSportsEntryList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(new Type("sport"), outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_ENTRY_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredFoodList_success() {
        showEntryAtIndex(model, INDEX_FIRST_ENTRY);

        Entry foodEntryToDelete = model.getFilteredFoodEntryList().get(INDEX_FIRST_ENTRY.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(new Type("food"), INDEX_FIRST_ENTRY);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_ENTRY_SUCCESS, foodEntryToDelete);

        Model expectedModel = new ModelManager(model.getFitHelper(), new UserProfile(), new WeightRecords());
        expectedModel.deleteEntry(foodEntryToDelete);
        showNoEntry(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validIndexFilteredSportList_success() {
        showEntryAtIndex(model, INDEX_FIRST_ENTRY);

        Entry sportEntryToDelete = model.getFilteredSportsEntryList().get(INDEX_FIRST_ENTRY.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(new Type("sport"), INDEX_FIRST_ENTRY);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_ENTRY_SUCCESS, sportEntryToDelete);

        Model expectedModel = new ModelManager(model.getFitHelper(), new UserProfile(), new WeightRecords());
        expectedModel.deleteEntry(sportEntryToDelete);
        showNoEntry(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredFoodList_throwsCommandException() {
        showEntryAtIndex(model, INDEX_FIRST_ENTRY);

        Index outOfBoundIndex = INDEX_SECOND_ENTRY;
        // ensures that outOfBoundIndex is still in bounds of fithelper list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getFitHelper().getFoodList().size());

        DeleteCommand deleteCommand = new DeleteCommand(new Type("food"), outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_ENTRY_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidIndexFilteredSportList_throwsCommandException() {
        showEntryAtIndex(model, INDEX_FIRST_ENTRY);

        Index outOfBoundIndex = INDEX_SECOND_ENTRY;
        // ensures that outOfBoundIndex is still in bounds of fithelper list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getFitHelper().getSportsList().size());

        DeleteCommand deleteCommand = new DeleteCommand(new Type("sport"), outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_ENTRY_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteCommand deleteFirstFoodCommand = new DeleteCommand(new Type("food"), INDEX_FIRST_ENTRY);
        DeleteCommand deleteSecondFoodCommand = new DeleteCommand(new Type("food"), INDEX_SECOND_ENTRY);

        // same object -> returns true
        assertTrue(deleteFirstFoodCommand.equals(deleteFirstFoodCommand));

        // same values -> returns true
        DeleteCommand deleteFirstFoodCommandCopy = new DeleteCommand(new Type("food"), INDEX_FIRST_ENTRY);
        assertTrue(deleteFirstFoodCommand.equals(deleteFirstFoodCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstFoodCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstFoodCommand.equals(null));

        // different Entry -> returns false
        assertFalse(deleteFirstFoodCommand.equals(deleteSecondFoodCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoEntry(Model model) {
        model.updateFilteredFoodEntryList(p -> false);

        assertTrue(model.getFilteredFoodEntryList().isEmpty());

        model.updateFilteredSportEntryList(p -> false);

        assertTrue(model.getFilteredSportsEntryList().isEmpty());
    }
}
