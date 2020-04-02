package seedu.recipe.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.recipe.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.recipe.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.recipe.logic.commands.CommandTestUtil.showRecipeAtIndex;
import static seedu.recipe.testutil.TypicalIndexes.INDEX_FIRST_RECIPE;
import static seedu.recipe.testutil.TypicalIndexes.INDEX_SECOND_RECIPE;
import static seedu.recipe.testutil.TypicalRecipes.getTypicalRecipeBook;
import static seedu.recipe.testutil.TypicalRecords.getTypicalRecordBook;

import org.junit.jupiter.api.Test;

import seedu.recipe.commons.core.Messages;
import seedu.recipe.commons.core.index.Index;
import seedu.recipe.model.Model;
import seedu.recipe.model.ModelManager;
import seedu.recipe.model.UserPrefs;
import seedu.recipe.model.plan.PlannedBook;
import seedu.recipe.model.recipe.Recipe;
import seedu.recipe.testutil.RecipeBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteStepCommand}.
 */
public class DeleteStepCommandTest {

    private Model model = new ModelManager(getTypicalRecipeBook(), new UserPrefs(),
            getTypicalRecordBook(), new PlannedBook());
    private final Integer[] indexSecondStep = new Integer[] {1}; // Steps are zero-indexed by design
    private final Integer[] indexFirstStep = new Integer[] {0};
    private final Integer[] indexOutOfBoundsStep = new Integer[] {Integer.MAX_VALUE};

    @Test
    public void execute_validRecipeAndStepIndexUnfilteredList_success() {
        Recipe recipeToDeleteSteps = model.getFilteredRecipeList().get(INDEX_SECOND_RECIPE.getZeroBased());
        DeleteStepCommand deleteStepCommand = new DeleteStepCommand(INDEX_SECOND_RECIPE, indexSecondStep);

        String expectedMessageTemplate = DeleteStepCommand.MESSAGE_DELETE_STEPS_SUCCESS;
        String expectedMessage = String.format(expectedMessageTemplate, recipeToDeleteSteps.getName().toString());

        ModelManager expectedModel = new ModelManager(model.getRecipeBook(), new UserPrefs(),
                model.getRecordBook(), new PlannedBook());
        Recipe expectedRecipe = new RecipeBuilder().withName("Grilled Sandwich")
                .withTime("10")
                .withGrains("50g, Bread")
                .withOthers("50g, Cheese")
                .withSteps("Spread butter on bread")
                .withGoals("Wholesome Wholemeal").build();
        expectedModel.setRecipe(recipeToDeleteSteps, expectedRecipe);

        assertCommandSuccess(deleteStepCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredRecipeList().size() + 1);
        DeleteStepCommand deleteStepCommand = new DeleteStepCommand(outOfBoundIndex, indexSecondStep);

        assertCommandFailure(deleteStepCommand, model, Messages.MESSAGE_INVALID_RECIPE_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidStepIndexUnfilteredList_throwsCommandException() {
        DeleteStepCommand deleteStepCommand = new DeleteStepCommand(INDEX_FIRST_RECIPE, indexOutOfBoundsStep);

        assertCommandFailure(deleteStepCommand, model, DeleteStepCommand.MESSAGE_INVALID_STEP_INDEX);
    }

    @Test
    public void execute_invalidIndexAndStepIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredRecipeList().size() + 1);
        DeleteStepCommand deleteStepCommand = new DeleteStepCommand(outOfBoundIndex, indexOutOfBoundsStep);

        // The error for RECIPE index out of bounds should be thrown first even though STEP index is also out of bounds
        assertCommandFailure(deleteStepCommand, model, Messages.MESSAGE_INVALID_RECIPE_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validRecipeAndStepIndexFilteredList_success() {
        showRecipeAtIndex(model, INDEX_SECOND_RECIPE);

        Recipe recipeToDeleteSteps = model.getFilteredRecipeList().get(INDEX_FIRST_RECIPE.getZeroBased());
        DeleteStepCommand deleteStepCommand = new DeleteStepCommand(INDEX_FIRST_RECIPE, indexSecondStep);

        String expectedMessageTemplate = DeleteStepCommand.MESSAGE_DELETE_STEPS_SUCCESS;
        String expectedMessage = String.format(expectedMessageTemplate, recipeToDeleteSteps.getName().toString());

        ModelManager expectedModel = new ModelManager(model.getRecipeBook(), new UserPrefs(),
                model.getRecordBook(), new PlannedBook());
        Recipe expectedRecipe = new RecipeBuilder().withName("Grilled Sandwich")
                .withTime("10")
                .withGrains("50g, Bread")
                .withOthers("50g, Cheese")
                .withSteps("Spread butter on bread")
                .withGoals("Wholesome Wholemeal").build();
        expectedModel.setRecipe(recipeToDeleteSteps, expectedRecipe);

        assertCommandSuccess(deleteStepCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showRecipeAtIndex(model, INDEX_FIRST_RECIPE);

        Index outOfBoundIndex = INDEX_SECOND_RECIPE;
        // Ensures that outOfBoundIndex is still in bounds of recipe book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getRecipeBook().getRecipeList().size());

        DeleteStepCommand deleteStepCommand = new DeleteStepCommand(outOfBoundIndex, indexSecondStep);

        assertCommandFailure(deleteStepCommand, model, Messages.MESSAGE_INVALID_RECIPE_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidStepIndexFilteredList_throwsCommandException() {
        showRecipeAtIndex(model, INDEX_FIRST_RECIPE);

        DeleteStepCommand deleteStepCommand = new DeleteStepCommand(INDEX_FIRST_RECIPE, indexOutOfBoundsStep);

        assertCommandFailure(deleteStepCommand, model, DeleteStepCommand.MESSAGE_INVALID_STEP_INDEX);
    }

    @Test
    public void execute_invalidIndexAndStepIndexFilteredList_throwsCommandException() {
        showRecipeAtIndex(model, INDEX_FIRST_RECIPE);

        Index outOfBoundIndex = INDEX_SECOND_RECIPE;
        // Ensures that outOfBoundIndex is still in bounds of recipe book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getRecipeBook().getRecipeList().size());

        DeleteStepCommand deleteStepCommand = new DeleteStepCommand(outOfBoundIndex, indexOutOfBoundsStep);

        // The error for RECIPE index out of bounds should be thrown first even though STEP index is also out of bounds
        assertCommandFailure(deleteStepCommand, model, Messages.MESSAGE_INVALID_RECIPE_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        // Base command for comparison
        DeleteStepCommand deleteStepFirstCommand = new DeleteStepCommand(INDEX_FIRST_RECIPE, indexSecondStep);
        // Different recipe, same step index
        DeleteStepCommand deleteStepSecondCommand = new DeleteStepCommand(INDEX_SECOND_RECIPE, indexSecondStep);
        // Same recipe, different step index
        DeleteStepCommand deleteStepThirdCommand = new DeleteStepCommand(INDEX_FIRST_RECIPE, indexFirstStep);

        // same object -> returns true
        assertTrue(deleteStepFirstCommand.equals(deleteStepFirstCommand));

        // same values -> returns true
        DeleteStepCommand deleteStepFirstCommandCopy = new DeleteStepCommand(INDEX_FIRST_RECIPE, indexSecondStep);
        assertTrue(deleteStepFirstCommand.equals(deleteStepFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteStepFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteStepFirstCommand.equals(null));

        // different recipe, same step index -> returns false
        assertFalse(deleteStepFirstCommand.equals(deleteStepSecondCommand));

        // same recipe, different step index -> returns false
        assertFalse(deleteStepFirstCommand.equals(deleteStepThirdCommand));
    }
}
