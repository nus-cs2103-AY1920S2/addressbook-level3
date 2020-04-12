package seedu.recipe.logic.commands.plan;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.recipe.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.recipe.testutil.Assert.assertThrows;
import static seedu.recipe.testutil.TypicalIndexes.INDEX_FIRST_RECIPE;
import static seedu.recipe.testutil.TypicalIndexes.INDEX_SECOND_RECIPE;
import static seedu.recipe.testutil.TypicalPlans.getTypicalPlannedBook;
import static seedu.recipe.testutil.TypicalRecipes.getTypicalRecipeBook;
import static seedu.recipe.testutil.TypicalRecords.getTypicalRecordBook;

import org.junit.jupiter.api.Test;

import seedu.recipe.commons.core.index.Index;
import seedu.recipe.logic.commands.CommandResult;
import seedu.recipe.logic.commands.exceptions.CommandException;
import seedu.recipe.model.Model;
import seedu.recipe.model.ModelManager;
import seedu.recipe.model.UserPrefs;
import seedu.recipe.model.achievement.QuoteBook;
import seedu.recipe.model.cooked.CookedRecordBook;
import seedu.recipe.model.plan.Plan;
import seedu.recipe.model.plan.PlannedBook;
import seedu.recipe.model.recipe.Recipe;
import seedu.recipe.ui.tab.Tab;

public class DeletePlanCommandTest {

    private Model model = new ModelManager(getTypicalRecipeBook(), new UserPrefs(),
            getTypicalRecordBook(), getTypicalPlannedBook(), new QuoteBook());

    @Test
    public void execute_validIndex_success() {
        Plan planToDelete = model.getFilteredPlannedList().get(INDEX_FIRST_RECIPE.getZeroBased());
        Recipe forRecipe = planToDelete.getRecipe();
        DeletePlanCommand deletePlanCommand = new DeletePlanCommand(new Index[] {INDEX_FIRST_RECIPE});

        String deletedPlanMessage = INDEX_FIRST_RECIPE.getOneBased() + " (" + forRecipe.getName()
                + " on " + planToDelete.getDate().toString() + ")";
        String expectedMessage = String.format(DeletePlanCommand.MESSAGE_SUCCESS, deletedPlanMessage);

        Model expectedModel = new ModelManager(model.getRecipeBook(), new UserPrefs(),
                model.getRecordBook(), model.getPlannedBook(), new QuoteBook());
        expectedModel.deletePlan(forRecipe, planToDelete);

        CommandResult expectedCommandResult =
                new CommandResult(expectedMessage, false, false, Tab.PLANNING, false);

        assertCommandSuccess(deletePlanCommand, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_validMultipleIndexes_success() {
        Plan planOneToDelete = model.getFilteredPlannedList().get(INDEX_FIRST_RECIPE.getZeroBased());
        Plan planTwoToDelete = model.getFilteredPlannedList().get(INDEX_SECOND_RECIPE.getZeroBased());

        Recipe firstRecipe = planOneToDelete.getRecipe();
        Recipe secondRecipe = planTwoToDelete.getRecipe();

        Index[] indexes = new Index[] {INDEX_FIRST_RECIPE, INDEX_SECOND_RECIPE};
        DeletePlanCommand deletePlanCommand = new DeletePlanCommand(indexes);

        String deletePlanMessage = INDEX_FIRST_RECIPE.getOneBased() + " (" + firstRecipe.getName()
                + " on " + planOneToDelete.getDate().toString() + "), "
                + INDEX_SECOND_RECIPE.getOneBased() + " (" + secondRecipe.getName()
                + " on " + planTwoToDelete.getDate().toString() + ")";
        String expectedMessage = String.format(DeletePlanCommand.MESSAGE_SUCCESS, deletePlanMessage);

        Model expectedModel = new ModelManager(model.getRecipeBook(), new UserPrefs(),
                model.getRecordBook(), model.getPlannedBook(), new QuoteBook());
        expectedModel.deletePlan(firstRecipe, planOneToDelete);
        expectedModel.deletePlan(secondRecipe, planTwoToDelete);

        CommandResult expectedCommandResult =
                new CommandResult(expectedMessage, false, false, Tab.PLANNING, false);

        assertCommandSuccess(deletePlanCommand, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Model modelWithNoPlans = new ModelManager(getTypicalRecipeBook(), new UserPrefs(),
                new CookedRecordBook(), new PlannedBook(), new QuoteBook());
        Index[] invalidIndexes = new Index[] {INDEX_FIRST_RECIPE};
        DeletePlanCommand deletePlanCommand = new DeletePlanCommand(invalidIndexes);

        assertThrows(CommandException.class, () -> deletePlanCommand.execute(modelWithNoPlans));

    }

    @Test
    public void equals() {
        Index[] indexOne = new Index[] {INDEX_FIRST_RECIPE};
        DeletePlanCommand planOne = new DeletePlanCommand(indexOne);

        // same object -> returns true
        assertTrue(planOne.equals(planOne));

        //same values -> returns true
        DeletePlanCommand planOneCopy = new DeletePlanCommand(indexOne);
        assertTrue(planOne.equals(planOneCopy));

        // different types -> returns false
        assertFalse(planOne.equals(1));

        // null -> returns false
        assertFalse(planOne.equals(null));

        // different indexes -> returns false
        Index[] indexTwo = new Index[] {INDEX_SECOND_RECIPE};
        DeletePlanCommand planTwo = new DeletePlanCommand(indexTwo);
        assertFalse(planOne.equals(planTwo));
    }

}
