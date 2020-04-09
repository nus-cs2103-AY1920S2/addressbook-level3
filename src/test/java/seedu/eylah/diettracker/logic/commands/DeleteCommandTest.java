package seedu.eylah.diettracker.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.eylah.diettracker.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.eylah.diettracker.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.eylah.diettracker.logic.commands.CommandTestUtil.showFoodAtIndex;
import static seedu.eylah.diettracker.testutil.TypicalFood.getTypicalFoodBook;
import static seedu.eylah.diettracker.testutil.TypicalMyself.getTypicalMyself;
import static seedu.eylah.testutil.TypicalIndexes.INDEX_FIRST_FOOD;
import static seedu.eylah.testutil.TypicalIndexes.INDEX_SECOND_FOOD;

import org.junit.jupiter.api.Test;

import seedu.eylah.commons.core.Messages;
import seedu.eylah.commons.core.index.Index;
import seedu.eylah.commons.model.UserPrefs;
import seedu.eylah.diettracker.model.DietModel;
import seedu.eylah.diettracker.model.DietModelManager;

import seedu.eylah.diettracker.model.food.Food;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private DietModel model = new DietModelManager(getTypicalFoodBook(), new UserPrefs(), getTypicalMyself());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Food foodToDelete = model.getFilteredFoodList().get(INDEX_FIRST_FOOD.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_FOOD);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_FOOD_SUCCESS, foodToDelete);

        DietModelManager expectedModel = new DietModelManager(model.getFoodBook(), new UserPrefs(), model.getMyself());
        expectedModel.deleteFood(foodToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredFoodList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_FOOD_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showFoodAtIndex(model, INDEX_FIRST_FOOD);

        Food foodToDelete = model.getFilteredFoodList().get(INDEX_FIRST_FOOD.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_FOOD);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_FOOD_SUCCESS, foodToDelete);

        DietModel expectedModel = new DietModelManager(model.getFoodBook(), new UserPrefs(), model.getMyself());
        expectedModel.deleteFood(foodToDelete);
        showNoFood(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showFoodAtIndex(model, INDEX_FIRST_FOOD);

        Index outOfBoundIndex = INDEX_SECOND_FOOD;
        // ensures that outOfBoundIndex is still in bounds of food book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getFoodBook().getFoodList().size());

        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_FOOD_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteCommand deleteFirstCommand = new DeleteCommand(INDEX_FIRST_FOOD);
        DeleteCommand deleteSecondCommand = new DeleteCommand(INDEX_SECOND_FOOD);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(INDEX_FIRST_FOOD);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different food -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoFood(DietModel model) {
        model.updateFilteredFoodList(p -> false);

        assertTrue(model.getFilteredFoodList().isEmpty());
    }
}
