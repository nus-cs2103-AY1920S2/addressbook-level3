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

import java.util.ArrayList;
import java.util.List;

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
 * {@code AddStepCommand}.
 */
public class AddStepCommandTest {

    private Model model = new ModelManager(getTypicalRecipeBook(), new UserPrefs(),
            getTypicalRecordBook(), new PlannedBook());
    private final Step firstNewStep = new Step("New step 1");
    private final Step secondNewStep = new Step("New step 2");

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Recipe recipeToAddSteps = model.getFilteredRecipeList().get(INDEX_SECOND_RECIPE.getZeroBased());

        List<Step> stepsToAdd = new ArrayList<>();
        stepsToAdd.add(firstNewStep);
        stepsToAdd.add(secondNewStep);
        AddStepCommand addStepCommand = new AddStepCommand(INDEX_SECOND_RECIPE, stepsToAdd);

        String expectedMessageTemplate = "Successfully added step(s) to %1$s!";
        String expectedMessage = String.format(expectedMessageTemplate, recipeToAddSteps.getName().toString());

        ModelManager expectedModel = new ModelManager(model.getRecipeBook(), new UserPrefs(),
                model.getRecordBook(), new PlannedBook());
        Recipe expectedRecipe = new RecipeBuilder().withName("Grilled Sandwich")
                .withTime("10")
                .withGrains("50g, Bread")
                .withOthers("50g, Cheese")
                .withSteps("Spread butter on bread", "Heat pan to medium heat", "New step 1", "New step 2")
                .withGoals("Wholesome Wholemeal").build();
        expectedModel.setRecipe(recipeToAddSteps, expectedRecipe);

        assertCommandSuccess(addStepCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredRecipeList().size() + 1);
        List<Step> stepsToAdd = new ArrayList<>();
        stepsToAdd.add(firstNewStep);
        stepsToAdd.add(secondNewStep);
        AddStepCommand addStepCommand = new AddStepCommand(outOfBoundIndex, stepsToAdd);

        assertCommandFailure(addStepCommand, model, Messages.MESSAGE_INVALID_RECIPE_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showRecipeAtIndex(model, INDEX_SECOND_RECIPE);

        Recipe recipeToAddSteps = model.getFilteredRecipeList().get(INDEX_FIRST_RECIPE.getZeroBased());

        List<Step> stepsToAdd = new ArrayList<>();
        stepsToAdd.add(firstNewStep);
        stepsToAdd.add(secondNewStep);
        AddStepCommand addStepCommand = new AddStepCommand(INDEX_FIRST_RECIPE, stepsToAdd);

        String expectedMessageTemplate = "Successfully added step(s) to %1$s!";
        String expectedMessage = String.format(expectedMessageTemplate, recipeToAddSteps.getName().toString());

        ModelManager expectedModel = new ModelManager(model.getRecipeBook(), new UserPrefs(),
                model.getRecordBook(), new PlannedBook());
        Recipe expectedRecipe = new RecipeBuilder().withName("Grilled Sandwich")
                .withTime("10")
                .withGrains("50g, Bread")
                .withOthers("50g, Cheese")
                .withSteps("Spread butter on bread", "Heat pan to medium heat", "New step 1", "New step 2")
                .withGoals("Wholesome Wholemeal").build();
        expectedModel.setRecipe(recipeToAddSteps, expectedRecipe);

        assertCommandSuccess(addStepCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showRecipeAtIndex(model, INDEX_FIRST_RECIPE);

        Index outOfBoundIndex = INDEX_SECOND_RECIPE;
        // Ensures that outOfBoundIndex is still in bounds of recipe book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getRecipeBook().getRecipeList().size());

        List<Step> stepsToAdd = new ArrayList<>();
        stepsToAdd.add(firstNewStep);
        stepsToAdd.add(secondNewStep);
        AddStepCommand addStepCommand = new AddStepCommand(outOfBoundIndex, stepsToAdd);

        assertCommandFailure(addStepCommand, model, Messages.MESSAGE_INVALID_RECIPE_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        List<Step> stepsToAdd = new ArrayList<>();
        stepsToAdd.add(firstNewStep);
        stepsToAdd.add(secondNewStep);

        List<Step> differentStepsToAdd = new ArrayList<>();
        differentStepsToAdd.add(firstNewStep);

        // Base command for comparison
        AddStepCommand addStepFirstCommand = new AddStepCommand(INDEX_FIRST_RECIPE, stepsToAdd);
        // Different recipe, same list of steps
        AddStepCommand addStepSecondCommand = new AddStepCommand(INDEX_SECOND_RECIPE, stepsToAdd);
        // Same recipe, different list of steps
        AddStepCommand addStepThirdCommand = new AddStepCommand(INDEX_FIRST_RECIPE, differentStepsToAdd);

        // same object -> returns true
        assertTrue(addStepFirstCommand.equals(addStepFirstCommand));

        // same values -> returns true
        AddStepCommand addStepFirstCommandCopy = new AddStepCommand(INDEX_FIRST_RECIPE, stepsToAdd);
        assertTrue(addStepFirstCommand.equals(addStepFirstCommandCopy));

        // different types -> returns false
        assertFalse(addStepFirstCommand.equals(1));

        // null -> returns false
        assertFalse(addStepFirstCommand.equals(null));

        // different recipe, same list of steps -> returns false
        assertFalse(addStepFirstCommand.equals(addStepSecondCommand));

        // same recipe, different list of steps -> returns false
        assertFalse(addStepFirstCommand.equals(addStepThirdCommand));
    }
}
