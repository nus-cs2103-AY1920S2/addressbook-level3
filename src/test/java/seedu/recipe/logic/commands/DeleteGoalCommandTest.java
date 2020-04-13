package seedu.recipe.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_GOAL_GRAIN;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_GOAL_VEGE;
import static seedu.recipe.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.recipe.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.recipe.testutil.TypicalIndexes.INDEX_FIRST_RECIPE;
import static seedu.recipe.testutil.TypicalIndexes.INDEX_SECOND_RECIPE;
import static seedu.recipe.testutil.TypicalRecipes.getTypicalRecipeBook;
import static seedu.recipe.testutil.TypicalRecords.getTypicalRecordBook;

import org.junit.jupiter.api.Test;

import seedu.recipe.commons.core.Messages;
import seedu.recipe.commons.core.index.Index;
import seedu.recipe.logic.commands.recipe.DeleteGoalCommand;
import seedu.recipe.model.Model;
import seedu.recipe.model.ModelManager;
import seedu.recipe.model.UserPrefs;
import seedu.recipe.model.achievement.QuoteBook;
import seedu.recipe.model.plan.PlannedBook;
import seedu.recipe.model.recipe.Recipe;
import seedu.recipe.testutil.RecipeBuilder;
import seedu.recipe.ui.tab.Tab;

class DeleteGoalCommandTest {

    private Model model = new ModelManager(getTypicalRecipeBook(), new UserPrefs(),
            getTypicalRecordBook(), new PlannedBook(), new QuoteBook());

    @Test
    public void execute_validRecipeIndexAndGoalUnfilteredList_success() {
        Recipe recipeToDeleteGoal = model.getFilteredRecipeList().get(INDEX_SECOND_RECIPE.getZeroBased());
        DeleteGoalCommand deleteGoalCommand = new DeleteGoalCommand(INDEX_SECOND_RECIPE, VALID_GOAL_GRAIN);

        String expectedMessageTemplate = DeleteGoalCommand.MESSAGE_DELETE_GOAL_SUCCESS;
        String expectedMessage = String.format(expectedMessageTemplate, recipeToDeleteGoal.getName().toString());

        ModelManager expectedModel = new ModelManager(model.getRecipeBook(), new UserPrefs(),
                model.getRecordBook(), new PlannedBook(), new QuoteBook());
        Recipe expectedRecipe = new RecipeBuilder().withName("Grilled Sandwich")
                .withTime("10")
                .withGrains("150g, Bread")
                .withOthers("50g, Cheese")
                .withSteps("Spread butter on bread", "Heat pan to medium heat")
                .build();
        expectedModel.setRecipe(recipeToDeleteGoal, expectedRecipe);

        CommandResult expectedCommandResult =
                new CommandResult(expectedMessage, false, false, Tab.RECIPES, false);

        assertCommandSuccess(deleteGoalCommand, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredRecipeList().size() + 1);
        DeleteGoalCommand deleteGoalCommand = new DeleteGoalCommand(outOfBoundIndex, VALID_GOAL_GRAIN);

        assertCommandFailure(deleteGoalCommand, model, Messages.MESSAGE_INVALID_RECIPE_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        // Base command for comparison
        DeleteGoalCommand deleteGoalFirstCommand = new DeleteGoalCommand(INDEX_FIRST_RECIPE, VALID_GOAL_GRAIN);
        // Different recipe, same goal
        DeleteGoalCommand deleteGoalSecondCommand = new DeleteGoalCommand(INDEX_SECOND_RECIPE, VALID_GOAL_GRAIN);
        // Same recipe, different goal
        DeleteGoalCommand deleteGoalThirdCommand = new DeleteGoalCommand(INDEX_FIRST_RECIPE, VALID_GOAL_VEGE);

        // same object -> returns true
        assertTrue(deleteGoalFirstCommand.equals(deleteGoalFirstCommand));

        // same values -> returns true
        DeleteGoalCommand deleteGoalFirstCommandCopy = new DeleteGoalCommand(INDEX_FIRST_RECIPE, VALID_GOAL_GRAIN);
        assertTrue(deleteGoalFirstCommand.equals(deleteGoalFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteGoalFirstCommand.equals("Wholesome Wholemeal"));

        // null -> returns false
        assertFalse(deleteGoalFirstCommandCopy.equals(null));

        // different recipe, same goal -> returns false
        assertFalse(deleteGoalFirstCommand.equals(deleteGoalSecondCommand));

        // same recipe, different goal -> returns false
        assertFalse(deleteGoalFirstCommand.equals(deleteGoalThirdCommand));
    }
}
