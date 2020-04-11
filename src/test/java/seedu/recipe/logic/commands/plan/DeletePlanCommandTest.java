package seedu.recipe.logic.commands.plan;

import static seedu.recipe.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.recipe.testutil.TypicalIndexes.INDEX_FIRST_RECIPE;
import static seedu.recipe.testutil.TypicalPlans.getTypicalPlannedBook;
import static seedu.recipe.testutil.TypicalRecipes.getTypicalRecipeBook;
import static seedu.recipe.testutil.TypicalRecords.getTypicalRecordBook;

import org.junit.jupiter.api.Test;

import seedu.recipe.commons.core.index.Index;
import seedu.recipe.logic.commands.CommandResult;
import seedu.recipe.model.Model;
import seedu.recipe.model.ModelManager;
import seedu.recipe.model.UserPrefs;
import seedu.recipe.model.achievement.QuoteBook;
import seedu.recipe.model.plan.Plan;
import seedu.recipe.model.recipe.Recipe;
import seedu.recipe.ui.tab.Tab;

class DeletePlanCommandTest {

    private Model model = new ModelManager(getTypicalRecipeBook(), new UserPrefs(),
            getTypicalRecordBook(), getTypicalPlannedBook(), new QuoteBook());

    @Test
    public void execute_validIndex_success() {
        Plan planToDelete = model.getFilteredPlannedList().get(INDEX_FIRST_RECIPE.getZeroBased());
        Recipe forRecipe = planToDelete.getRecipe();
        DeletePlanCommand deletePlanCommand = new DeletePlanCommand(new Index[] {INDEX_FIRST_RECIPE});

        String deletedPlanMessage = INDEX_FIRST_RECIPE.getOneBased() + " (" + planToDelete.getRecipe().getName()
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
    public void execute_invalidIndex_throwsCommandException() {


    }

    @Test
    public void equals() {

    }

}
