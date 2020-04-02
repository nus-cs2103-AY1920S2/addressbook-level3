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
import seedu.recipe.model.recipe.Step;
import seedu.recipe.testutil.RecipeBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code EditStepCommand}.
 */
public class EditStepCommandTest {

    private Model model = new ModelManager(getTypicalRecipeBook(), new UserPrefs(),
            getTypicalRecordBook(), new PlannedBook());
    private final int indexFirstStep = 0; // Steps are zero-indexed by design
    private final int indexSecondStep = 1;
    private final int indexOutOfBoundsStep = Integer.MAX_VALUE;
    private final Step editedStep = new Step("Edited step");
    private final Step differentStep = new Step("Different step");

    @Test
    public void execute_validRecipeAndStepIndexAndStepFieldUnfilteredList_success() {
        Recipe recipeToEditSteps = model.getFilteredRecipeList().get(INDEX_SECOND_RECIPE.getZeroBased());
        EditStepCommand editStepCommand = new EditStepCommand(INDEX_SECOND_RECIPE, indexFirstStep, editedStep);

        String expectedMessageTemplate = EditStepCommand.MESSAGE_EDIT_STEPS_SUCCESS;
        String expectedMessage = String.format(expectedMessageTemplate,
                indexFirstStep + 1, recipeToEditSteps.getName().toString());

        ModelManager expectedModel = new ModelManager(model.getRecipeBook(), new UserPrefs(),
                model.getRecordBook(), new PlannedBook());
        Recipe expectedRecipe = new RecipeBuilder().withName("Grilled Sandwich")
                .withTime("10")
                .withGrains("50g, Bread")
                .withOthers("50g, Cheese")
                .withSteps("Edited step", "Heat pan to medium heat")
                .withGoals("Wholesome Wholemeal").build();
        expectedModel.setRecipe(recipeToEditSteps, expectedRecipe);

        assertCommandSuccess(editStepCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredRecipeList().size() + 1);
        EditStepCommand editStepCommand = new EditStepCommand(outOfBoundIndex, indexFirstStep, editedStep);

        assertCommandFailure(editStepCommand, model, Messages.MESSAGE_INVALID_RECIPE_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidStepIndexUnfilteredList_throwsCommandException() {
        EditStepCommand editStepCommand = new EditStepCommand(INDEX_FIRST_RECIPE, indexOutOfBoundsStep, editedStep);

        assertCommandFailure(editStepCommand, model, EditStepCommand.MESSAGE_INVALID_STEP_INDEX);
    }

    @Test
    public void execute_invalidIndexAndStepIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredRecipeList().size() + 1);
        EditStepCommand editStepCommand = new EditStepCommand(outOfBoundIndex, indexOutOfBoundsStep, editedStep);

        // The error for RECIPE index out of bounds should be thrown first even though STEP index is also out of bounds
        assertCommandFailure(editStepCommand, model, Messages.MESSAGE_INVALID_RECIPE_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validRecipeAndStepIndexAndStepFieldFilteredList_success() {
        showRecipeAtIndex(model, INDEX_SECOND_RECIPE);

        Recipe recipeToEditSteps = model.getFilteredRecipeList().get(INDEX_FIRST_RECIPE.getZeroBased());
        EditStepCommand editStepCommand = new EditStepCommand(INDEX_FIRST_RECIPE, indexFirstStep, editedStep);

        String expectedMessageTemplate = EditStepCommand.MESSAGE_EDIT_STEPS_SUCCESS;
        String expectedMessage = String.format(expectedMessageTemplate,
                indexFirstStep + 1, recipeToEditSteps.getName().toString());

        ModelManager expectedModel = new ModelManager(model.getRecipeBook(), new UserPrefs(),
                model.getRecordBook(), new PlannedBook());
        Recipe expectedRecipe = new RecipeBuilder().withName("Grilled Sandwich")
                .withTime("10")
                .withGrains("50g, Bread")
                .withOthers("50g, Cheese")
                .withSteps("Edited step", "Heat pan to medium heat")
                .withGoals("Wholesome Wholemeal").build();
        expectedModel.setRecipe(recipeToEditSteps, expectedRecipe);

        assertCommandSuccess(editStepCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showRecipeAtIndex(model, INDEX_FIRST_RECIPE);

        Index outOfBoundIndex = INDEX_SECOND_RECIPE;
        // Ensures that outOfBoundIndex is still in bounds of recipe book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getRecipeBook().getRecipeList().size());

        EditStepCommand editStepCommand = new EditStepCommand(outOfBoundIndex, indexFirstStep, editedStep);

        assertCommandFailure(editStepCommand, model, Messages.MESSAGE_INVALID_RECIPE_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidStepIndexFilteredList_throwsCommandException() {
        showRecipeAtIndex(model, INDEX_FIRST_RECIPE);

        EditStepCommand editStepCommand = new EditStepCommand(INDEX_FIRST_RECIPE, indexOutOfBoundsStep, editedStep);

        assertCommandFailure(editStepCommand, model, EditStepCommand.MESSAGE_INVALID_STEP_INDEX);
    }

    @Test
    public void execute_invalidIndexAndStepIndexFilteredList_throwsCommandException() {
        showRecipeAtIndex(model, INDEX_FIRST_RECIPE);

        Index outOfBoundIndex = INDEX_SECOND_RECIPE;
        // Ensures that outOfBoundIndex is still in bounds of recipe book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getRecipeBook().getRecipeList().size());

        EditStepCommand editStepCommand = new EditStepCommand(outOfBoundIndex, indexOutOfBoundsStep, editedStep);

        // The error for RECIPE index out of bounds should be thrown first even though STEP index is also out of bounds
        assertCommandFailure(editStepCommand, model, Messages.MESSAGE_INVALID_RECIPE_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        // Base command for comparison
        EditStepCommand editStepFirstCommand = new EditStepCommand(INDEX_FIRST_RECIPE, indexFirstStep, editedStep);
        // Different recipe, same step index, same step
        EditStepCommand editStepSecondCommand = new EditStepCommand(INDEX_SECOND_RECIPE, indexFirstStep, editedStep);
        // Same recipe, same step index, different step
        EditStepCommand editStepThirdCommand = new EditStepCommand(INDEX_FIRST_RECIPE, indexFirstStep, differentStep);
        // Same recipe, different step index, same step
        EditStepCommand editStepFourthCommand = new EditStepCommand(INDEX_FIRST_RECIPE, indexSecondStep, editedStep);

        // same object -> returns true
        assertTrue(editStepFirstCommand.equals(editStepFirstCommand));

        // same values -> returns true
        EditStepCommand editStepFirstCommandCopy = new EditStepCommand(
                INDEX_FIRST_RECIPE, indexFirstStep, editedStep);
        assertTrue(editStepFirstCommand.equals(editStepFirstCommandCopy));

        // different types -> returns false
        assertFalse(editStepFirstCommand.equals(1));

        // null -> returns false
        assertFalse(editStepFirstCommand.equals(null));

        // different recipe, same step index, same step -> returns false
        assertFalse(editStepFirstCommand.equals(editStepSecondCommand));

        // same recipe, same step index, different step -> returns false
        assertFalse(editStepFirstCommand.equals(editStepThirdCommand));

        // same recipe, different step index, same step -> returns false
        assertFalse(editStepFirstCommand.equals(editStepFourthCommand));
    }
}
