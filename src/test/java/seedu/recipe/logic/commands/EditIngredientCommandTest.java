package seedu.recipe.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.recipe.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.recipe.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.recipe.logic.commands.CommandTestUtil.showRecipeAtIndex;
import static seedu.recipe.testutil.TypicalIndexes.INDEX_FIRST_RECIPE;
import static seedu.recipe.testutil.TypicalIndexes.INDEX_SECOND_RECIPE;
import static seedu.recipe.testutil.TypicalIndexes.INDEX_THIRD_RECIPE;
import static seedu.recipe.testutil.TypicalRecipes.getTypicalRecipeBook;
import static seedu.recipe.testutil.TypicalRecords.getTypicalRecordBook;

import java.util.Set;
import java.util.TreeSet;

import org.junit.jupiter.api.Test;

import seedu.recipe.commons.core.Messages;
import seedu.recipe.commons.core.index.Index;
import seedu.recipe.logic.commands.EditCommand.EditRecipeDescriptor;
import seedu.recipe.model.Model;
import seedu.recipe.model.ModelManager;
import seedu.recipe.model.UserPrefs;
import seedu.recipe.model.plan.PlannedBook;
import seedu.recipe.model.recipe.Recipe;
import seedu.recipe.model.recipe.ingredient.Grain;
import seedu.recipe.model.recipe.ingredient.Ingredient;
import seedu.recipe.model.recipe.ingredient.Other;
import seedu.recipe.testutil.RecipeBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code EditIngredientCommand}.
 */
public class EditIngredientCommandTest {

    private Model model = new ModelManager(getTypicalRecipeBook(), new UserPrefs(),
            getTypicalRecordBook(), new PlannedBook());
    private final Other otherWithNewQuantity = new Other("Cheese", RecipeBuilder.DEFAULT_QUANTITY);
    private final Grain grainWithNewQuantity = new Grain("Bread", RecipeBuilder.DEFAULT_QUANTITY);

    @Test
    public void execute_validIndexAndIngredientFieldUnfilteredList_success() {
        Recipe recipeToEditIngredients = model.getFilteredRecipeList().get(INDEX_SECOND_RECIPE.getZeroBased());

        EditRecipeDescriptor editRecipeDescriptor = new EditRecipeDescriptor();
        Set<Other> editedOther = new TreeSet<>();
        editedOther.add(otherWithNewQuantity);
        editRecipeDescriptor.setOthers(editedOther);

        EditIngredientCommand editIngredientCommand = new EditIngredientCommand(
                INDEX_SECOND_RECIPE, editRecipeDescriptor);

        String expectedMessageTemplate = EditIngredientCommand.MESSAGE_EDIT_INGREDIENTS_SUCCESS;
        String expectedMessage = String.format(expectedMessageTemplate, recipeToEditIngredients.getName().toString());

        ModelManager expectedModel = new ModelManager(model.getRecipeBook(), new UserPrefs(),
                model.getRecordBook(), new PlannedBook());
        Recipe expectedRecipe = new RecipeBuilder().withName("Grilled Sandwich")
                .withTime("10")
                .withGrains("50g, Bread")
                .withOthers("100g, Cheese")
                .withSteps("Spread butter on bread", "Heat pan to medium heat")
                .withGoals("Wholesome Wholemeal").build();
        expectedModel.setRecipe(recipeToEditIngredients, expectedRecipe);

        assertCommandSuccess(editIngredientCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredRecipeList().size() + 1);

        EditRecipeDescriptor editRecipeDescriptor = new EditRecipeDescriptor();
        Set<Other> editedOther = new TreeSet<>();
        editedOther.add(otherWithNewQuantity);
        editRecipeDescriptor.setOthers(editedOther);

        EditIngredientCommand editIngredientCommand = new EditIngredientCommand(outOfBoundIndex, editRecipeDescriptor);

        assertCommandFailure(editIngredientCommand, model, Messages.MESSAGE_INVALID_RECIPE_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidIngredientFieldUnfilteredList_throwsCommandException() {
        EditRecipeDescriptor editRecipeDescriptor = new EditRecipeDescriptor();
        editRecipeDescriptor.setOthers(new TreeSet<>());

        EditIngredientCommand editIngredientCommand = new EditIngredientCommand(
                INDEX_SECOND_RECIPE, editRecipeDescriptor);

        String expectedMessage = Ingredient.MESSAGE_MISSING_FIELD;

        assertCommandFailure(editIngredientCommand, model, expectedMessage);
    }

    @Test
    public void execute_validIndexAndIngredientFieldFilteredList_success() {
        showRecipeAtIndex(model, INDEX_SECOND_RECIPE);

        Recipe recipeToEditIngredients = model.getFilteredRecipeList().get(INDEX_FIRST_RECIPE.getZeroBased());

        EditRecipeDescriptor editRecipeDescriptor = new EditRecipeDescriptor();
        Set<Other> editedOther = new TreeSet<>();
        editedOther.add(otherWithNewQuantity);
        editRecipeDescriptor.setOthers(editedOther);

        EditIngredientCommand editIngredientCommand = new EditIngredientCommand(
                INDEX_FIRST_RECIPE, editRecipeDescriptor);

        String expectedMessageTemplate = EditIngredientCommand.MESSAGE_EDIT_INGREDIENTS_SUCCESS;
        String expectedMessage = String.format(expectedMessageTemplate, recipeToEditIngredients.getName().toString());

        ModelManager expectedModel = new ModelManager(model.getRecipeBook(), new UserPrefs(),
                model.getRecordBook(), new PlannedBook());
        Recipe expectedRecipe = new RecipeBuilder().withName("Grilled Sandwich")
                .withTime("10")
                .withGrains("50g, Bread")
                .withOthers("100g, Cheese")
                .withSteps("Spread butter on bread", "Heat pan to medium heat")
                .withGoals("Wholesome Wholemeal").build();
        expectedModel.setRecipe(recipeToEditIngredients, expectedRecipe);

        assertCommandSuccess(editIngredientCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showRecipeAtIndex(model, INDEX_SECOND_RECIPE);

        Index outOfBoundIndex = INDEX_THIRD_RECIPE;
        // ensures that outOfBoundIndex is still in bounds of recipe book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getRecipeBook().getRecipeList().size());

        EditRecipeDescriptor editRecipeDescriptor = new EditRecipeDescriptor();
        Set<Other> editedOther = new TreeSet<>();
        editedOther.add(otherWithNewQuantity);
        editRecipeDescriptor.setOthers(editedOther);

        EditIngredientCommand editIngredientCommand = new EditIngredientCommand(outOfBoundIndex, editRecipeDescriptor);

        assertCommandFailure(editIngredientCommand, model, Messages.MESSAGE_INVALID_RECIPE_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidIngredientFieldFilteredList_throwsCommandException() {
        showRecipeAtIndex(model, INDEX_SECOND_RECIPE);

        EditRecipeDescriptor editRecipeDescriptor = new EditRecipeDescriptor();
        editRecipeDescriptor.setOthers(new TreeSet<>());

        EditIngredientCommand editIngredientCommand = new EditIngredientCommand(
                INDEX_FIRST_RECIPE, editRecipeDescriptor);

        String expectedMessage = Ingredient.MESSAGE_MISSING_FIELD;

        assertCommandFailure(editIngredientCommand, model, expectedMessage);
    }

    @Test
    public void equals() {
        // Create first EditRecipeDescriptor
        Set<Other> editedOther = new TreeSet<>();
        editedOther.add(otherWithNewQuantity);
        EditRecipeDescriptor firstEditRecipeDescriptor = new EditRecipeDescriptor();
        firstEditRecipeDescriptor.setOthers(editedOther);

        // Create different EditRecipeDescriptor
        Set<Grain> editedGrain = new TreeSet<>();
        editedGrain.add(grainWithNewQuantity);
        EditRecipeDescriptor secondEditRecipeDescriptor = new EditRecipeDescriptor();
        secondEditRecipeDescriptor.setGrains(editedGrain);

        // Base command for comparison
        EditIngredientCommand editIngredientFirstCommand = new EditIngredientCommand(
                INDEX_SECOND_RECIPE, firstEditRecipeDescriptor);
        // Different recipe, same EditRecipeDescriptor
        EditIngredientCommand editIngredientSecondCommand = new EditIngredientCommand(
                INDEX_THIRD_RECIPE, firstEditRecipeDescriptor);
        // Same recipe, different EditRecipeDescriptor
        EditIngredientCommand editIngredientThirdCommand = new EditIngredientCommand(
                INDEX_SECOND_RECIPE, secondEditRecipeDescriptor);

        // same object -> returns true
        assertTrue(editIngredientFirstCommand.equals(editIngredientFirstCommand));

        // same values -> returns true
        EditIngredientCommand editIngredientFirstCommandCopy = new EditIngredientCommand(
                INDEX_SECOND_RECIPE, firstEditRecipeDescriptor);
        assertTrue(editIngredientFirstCommand.equals(editIngredientFirstCommandCopy));

        // different types -> returns false
        assertFalse(editIngredientFirstCommand.equals(1));

        // null -> returns false
        assertFalse(editIngredientFirstCommand.equals(null));

        // different recipe, same EditRecipeDescriptor -> returns false
        assertFalse(editIngredientFirstCommand.equals(editIngredientSecondCommand));

        // same recipe, different EditRecipeDescriptor -> returns false
        assertFalse(editIngredientFirstCommand.equals(editIngredientThirdCommand));
    }
}
