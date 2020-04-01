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
import seedu.recipe.testutil.RecipeBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteIngredientCommandTest}.
 */
public class DeleteIngredientCommandTest {

    private Model model = new ModelManager(getTypicalRecipeBook(), new PlannedBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Recipe recipeToDeleteIngredients = model.getFilteredRecipeList().get(INDEX_SECOND_RECIPE.getZeroBased());
        EditRecipeDescriptor editRecipeDescriptor = new EditRecipeDescriptor();
        editRecipeDescriptor.setOthers(new TreeSet<>());
        DeleteIngredientCommand deleteIngredientCommand = new DeleteIngredientCommand(
                INDEX_SECOND_RECIPE, editRecipeDescriptor);

        String expectedMessageTemplate = "Successfully deleted ingredient(s) from %1$s!";
        String expectedMessage = String.format(expectedMessageTemplate, recipeToDeleteIngredients.getName().toString());

        ModelManager expectedModel = new ModelManager(model.getRecipeBook(), new PlannedBook(), new UserPrefs());
        Recipe expectedRecipe = new RecipeBuilder().withName("Grilled Sandwich")
                .withTime("10")
                .withGrains("50g, Bread")
                .withSteps("Spread butter on bread", "Heat pan to medium heat")
                .withGoals("Wholesome Wholemeal").build();
        expectedModel.setRecipe(recipeToDeleteIngredients, expectedRecipe);

        assertCommandSuccess(deleteIngredientCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredRecipeList().size() + 1);
        DeleteIngredientCommand deleteIngredientCommand = new DeleteIngredientCommand(
                outOfBoundIndex, new EditRecipeDescriptor());

        assertCommandFailure(deleteIngredientCommand, model, Messages.MESSAGE_INVALID_RECIPE_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showRecipeAtIndex(model, INDEX_SECOND_RECIPE);

        Recipe recipeToDeleteIngredients = model.getFilteredRecipeList().get(INDEX_FIRST_RECIPE.getZeroBased());
        EditRecipeDescriptor editRecipeDescriptor = new EditRecipeDescriptor();
        editRecipeDescriptor.setOthers(new TreeSet<>());
        DeleteIngredientCommand deleteIngredientCommand = new DeleteIngredientCommand(
                INDEX_FIRST_RECIPE, editRecipeDescriptor);

        String expectedMessageTemplate = "Successfully deleted ingredient(s) from %1$s!";
        String expectedMessage = String.format(expectedMessageTemplate, recipeToDeleteIngredients.getName().toString());

        Model expectedModel = new ModelManager(model.getRecipeBook(), new PlannedBook(), new UserPrefs());
        Recipe expectedRecipe = new RecipeBuilder().withName("Grilled Sandwich")
                .withTime("10")
                .withGrains("50g, Bread")
                .withSteps("Spread butter on bread", "Heat pan to medium heat")
                .withGoals("Wholesome Wholemeal").build();
        expectedModel.setRecipe(recipeToDeleteIngredients, expectedRecipe);

        assertCommandSuccess(deleteIngredientCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showRecipeAtIndex(model, INDEX_SECOND_RECIPE);

        Index outOfBoundIndex = INDEX_THIRD_RECIPE;
        // ensures that outOfBoundIndex is still in bounds of recipe book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getRecipeBook().getRecipeList().size());

        DeleteIngredientCommand deleteIngredientCommand = new DeleteIngredientCommand(
                outOfBoundIndex, new EditRecipeDescriptor());

        assertCommandFailure(deleteIngredientCommand, model, Messages.MESSAGE_INVALID_RECIPE_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        EditRecipeDescriptor firstEditRecipeDescriptor = new EditRecipeDescriptor();
        EditRecipeDescriptor secondEditRecipeDescriptor = new EditRecipeDescriptor();

        DeleteIngredientCommand deleteIngredientFirstCommand = new DeleteIngredientCommand(
                INDEX_SECOND_RECIPE, firstEditRecipeDescriptor);
        DeleteIngredientCommand deleteIngredientSecondCommand = new DeleteIngredientCommand(
                INDEX_THIRD_RECIPE, secondEditRecipeDescriptor);

        // same object -> returns true
        assertTrue(deleteIngredientFirstCommand.equals(deleteIngredientFirstCommand));

        // same values -> returns true
        DeleteIngredientCommand deleteIngredientFirstCommandCopy = new DeleteIngredientCommand(
                INDEX_SECOND_RECIPE, firstEditRecipeDescriptor);
        assertTrue(deleteIngredientFirstCommand.equals(deleteIngredientFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteIngredientFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteIngredientFirstCommand.equals(null));

        // different recipe -> returns false
        assertFalse(deleteIngredientFirstCommand.equals(deleteIngredientSecondCommand));
    }
}
