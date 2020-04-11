package seedu.eylah.diettracker.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.eylah.diettracker.logic.commands.CommandTestUtil.DESC_PASTA;
import static seedu.eylah.diettracker.logic.commands.CommandTestUtil.DESC_PIZZA;
import static seedu.eylah.diettracker.logic.commands.CommandTestUtil.VALID_NAME_PASTA;
//import static seedu.eylah.diettracker.logic.commands.CommandTestUtil.VALID_TAG_FAVORITE;
import static seedu.eylah.diettracker.logic.commands.CommandTestUtil.assertCommandFailure;
//import static seedu.eylah.diettracker.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.eylah.diettracker.logic.commands.CommandTestUtil.showFoodAtIndex;
import static seedu.eylah.diettracker.testutil.TypicalFood.getTypicalFoodBook;
import static seedu.eylah.diettracker.testutil.TypicalMyself.getTypicalMyself;
import static seedu.eylah.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.eylah.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.jupiter.api.Test;

import seedu.eylah.commons.core.Messages;
import seedu.eylah.commons.core.index.Index;
import seedu.eylah.commons.model.UserPrefs;
import seedu.eylah.diettracker.logic.commands.EditCommand.EditFoodDescriptor;
//import seedu.eylah.diettracker.model.FoodBook;
import seedu.eylah.diettracker.model.DietModel;
import seedu.eylah.diettracker.model.DietModelManager;
//import seedu.eylah.diettracker.model.food.Food;
import seedu.eylah.diettracker.testutil.EditFoodDescriptorBuilder;
//import seedu.eylah.diettracker.testutil.FoodBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private DietModel model = new DietModelManager(getTypicalFoodBook(), new UserPrefs(), getTypicalMyself());

    // @Test
    // public void execute_allFieldsSpecifiedUnfilteredList_success() {
    //     Food editedFood = new FoodBuilder().build();
    //     EditFoodDescriptor descriptor = new EditFoodDescriptorBuilder(editedFood).build();
    //     EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON, descriptor);

    //     String expectedMessage = String.format(EditCommand.MESSAGE_SUCCESS, editedFood);

    //     Model expectedModel = new ModelManager(new FoodBook(model.getFoodBook()), new UserPrefs());
    //     expectedModel.setFood(model.getFilteredFoodList().get(0), editedFood);

    //     assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    // }

    // @Test
    // public void execute_someFieldsSpecifiedUnfilteredList_success() {
    //     Index indexLastFood = Index.fromOneBased(model.getFilteredFoodList().size());
    //     Food lastFood = model.getFilteredFoodList().get(indexLastFood.getZeroBased());

    //     FoodBuilder foodInList = new FoodBuilder(lastFood);
    //     Food editedFood = foodInList.withName(VALID_NAME_PASTA).withTags(VALID_TAG_FAVORITE).build();

    //     EditFoodDescriptor descriptor = new EditFoodDescriptorBuilder().withName(VALID_NAME_PASTA)
    //             .withTags(VALID_TAG_FAVORITE).build();
    //     EditCommand editCommand = new EditCommand(indexLastFood, descriptor);

    //     String expectedMessage = String.format(EditCommand.MESSAGE_SUCCESS, editedFood);

    //     Model expectedModel = new ModelManager(new FoodBook(model.getFoodBook()), new UserPrefs());
    //     expectedModel.setFood(lastFood, editedFood);

    //     assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    // }

    // @Test
    // public void execute_noFieldSpecifiedUnfilteredList_success() {
    //     EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON, new EditFoodDescriptor());
    //     Food editedFood = model.getFilteredFoodList().get(INDEX_FIRST_PERSON.getZeroBased());

    //     String expectedMessage = String.format(EditCommand.MESSAGE_SUCCESS, editedFood);

    //     Model expectedModel = new ModelManager(new FoodBook(model.getFoodBook()), new UserPrefs());

    //     assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    // }

    // @Test
    // public void execute_filteredList_success() {
    //     showFoodAtIndex(model, INDEX_FIRST_PERSON);

    //     Food foodInFilteredList = model.getFilteredFoodList().get(INDEX_FIRST_PERSON.getZeroBased());
    //     Food editedFood = new FoodBuilder(foodInFilteredList).withName(VALID_NAME_PASTA).build();
    //     EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON,
    //             new EditFoodDescriptorBuilder().withName(VALID_NAME_PASTA).build());

    //     String expectedMessage = String.format(EditCommand.MESSAGE_SUCCESS, editedFood);

    //     Model expectedModel = new ModelManager(new FoodBook(model.getFoodBook()), new UserPrefs());
    //     expectedModel.setFood(model.getFilteredFoodList().get(0), editedFood);

    //     assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    // }

    @Test
    public void execute_invalidFoodIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredFoodList().size() + 1);
        EditFoodDescriptor descriptor = new EditFoodDescriptorBuilder().withName(VALID_NAME_PASTA).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_FOOD_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidFoodIndexFilteredList_failure() {
        showFoodAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getFoodBook().getFoodList().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                new EditFoodDescriptorBuilder().withName(VALID_NAME_PASTA).build());

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_FOOD_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_PERSON, DESC_PASTA);

        // same values -> returns true
        EditFoodDescriptor copyDescriptor = new EditFoodDescriptor(DESC_PASTA);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_PERSON, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // // different types -> returns false
        // assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_PERSON, DESC_PASTA)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_PERSON, DESC_PIZZA)));
    }

}
