package seedu.recipe.logic.commands;

import static seedu.recipe.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.recipe.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.recipe.testutil.TypicalIndexes.INDEX_SECOND_RECIPE;
import static seedu.recipe.testutil.TypicalRecipes.getTypicalRecipeBook;
import static seedu.recipe.testutil.TypicalRecords.getTypicalRecordBook;

import org.junit.jupiter.api.Test;

import seedu.recipe.commons.core.Messages;
import seedu.recipe.commons.core.index.Index;
import seedu.recipe.model.Date;
import seedu.recipe.model.Model;
import seedu.recipe.model.ModelManager;
import seedu.recipe.model.UserPrefs;
import seedu.recipe.model.achievement.QuoteBook;
import seedu.recipe.model.cooked.Record;
import seedu.recipe.model.plan.PlannedBook;
import seedu.recipe.model.recipe.Recipe;
import seedu.recipe.ui.tab.Tab;

class CookedCommandTest {

    private Model model = new ModelManager(getTypicalRecipeBook(), new UserPrefs(),
            getTypicalRecordBook(), new PlannedBook(), new QuoteBook());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Recipe recipeToCook = model.getFilteredRecipeList().get(INDEX_SECOND_RECIPE.getZeroBased());
        CookedCommand cookedCommand = new CookedCommand(new Index[] {INDEX_SECOND_RECIPE});

        String expectedMessageTemplate = "Cooked %1$s!";
        String expectedMessage = String.format(expectedMessageTemplate, recipeToCook.getName().toString());
        ModelManager expectedModel = new ModelManager(model.getRecipeBook(), new UserPrefs(),
                model.getRecordBook(), new PlannedBook(), new QuoteBook());

        Record toAdd = new Record(recipeToCook.getName(), new Date(), recipeToCook.getGoals());
        expectedModel.addRecord(toAdd);
        expectedModel.updateGoalsTally(toAdd);

        CommandResult expectedCommandResult =
                new CommandResult(expectedMessage, false, false, Tab.GOALS, false);

        assertCommandSuccess(cookedCommand, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredRecipeList().size() + 1);
        CookedCommand cookedCommand = new CookedCommand(new Index[] {outOfBoundIndex});

        assertCommandFailure(cookedCommand, model, Messages.MESSAGE_INVALID_RECIPE_DISPLAYED_INDEX);
    }
}
