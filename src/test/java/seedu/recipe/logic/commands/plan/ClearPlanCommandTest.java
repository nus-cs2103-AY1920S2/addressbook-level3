package seedu.recipe.logic.commands.plan;

import static seedu.recipe.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.recipe.testutil.TypicalPlans.getTypicalPlannedBook;
import static seedu.recipe.testutil.TypicalRecipes.getTypicalRecipeBook;

import org.junit.jupiter.api.Test;

import seedu.recipe.logic.commands.CommandResult;
import seedu.recipe.model.Model;
import seedu.recipe.model.ModelManager;
import seedu.recipe.model.UserPrefs;
import seedu.recipe.model.achievement.QuoteBook;
import seedu.recipe.model.cooked.CookedRecordBook;
import seedu.recipe.model.plan.PlannedBook;
import seedu.recipe.ui.tab.Tab;

public class ClearPlanCommandTest {

    @Test
    public void execute_emptyPlannedBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        CommandResult expectedCommandResult = new CommandResult(ClearPlanCommand.MESSAGE_SUCCESS,
                false, false, Tab.PLANNING, false);
        assertCommandSuccess(new ClearPlanCommand(), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_nonEmptyPlannedBook_success() {
        Model model = new ModelManager(getTypicalRecipeBook(), new UserPrefs(),
                new CookedRecordBook(), getTypicalPlannedBook(), new QuoteBook());
        Model expectedModel = new ModelManager(getTypicalRecipeBook(), new UserPrefs(),
                new CookedRecordBook(), getTypicalPlannedBook(), new QuoteBook());

        expectedModel.setPlannedBook(new PlannedBook());

        CommandResult expectedCommandResult = new CommandResult(ClearPlanCommand.MESSAGE_SUCCESS,
                false, false, Tab.PLANNING, false);
        assertCommandSuccess(new ClearPlanCommand(), model, expectedCommandResult, expectedModel);

    }
}
