package seedu.recipe.logic.commands.plan;

import static seedu.recipe.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.recipe.logic.commands.plan.GroceryListCommand.MESSAGE_SUCCESS;
import static seedu.recipe.testutil.Assert.assertThrows;
import static seedu.recipe.testutil.TypicalPlans.getTypicalPlannedBook;
import static seedu.recipe.testutil.TypicalRecipes.getTypicalRecipeBook;
import static seedu.recipe.testutil.TypicalRecords.getTypicalRecordBook;

import org.junit.jupiter.api.Test;

import seedu.recipe.logic.commands.CommandResult;
import seedu.recipe.logic.commands.exceptions.CommandException;
import seedu.recipe.model.Model;
import seedu.recipe.model.ModelManager;
import seedu.recipe.model.UserPrefs;
import seedu.recipe.model.achievement.QuoteBook;
import seedu.recipe.model.cooked.CookedRecordBook;
import seedu.recipe.model.plan.PlannedBook;
import seedu.recipe.ui.tab.Tab;

public class GroceryListCommandTest {

    @Test
    public void execute_nonEmptyPlan_success() {
        Model newModel = new ModelManager(getTypicalRecipeBook(), new UserPrefs(),
                getTypicalRecordBook(), getTypicalPlannedBook(), new QuoteBook());
        Model expectedModel = new ModelManager(getTypicalRecipeBook(), new UserPrefs(),
                getTypicalRecordBook(), getTypicalPlannedBook(), new QuoteBook());

        CommandResult expectedCommandResult = new CommandResult(MESSAGE_SUCCESS, true,
                false, Tab.PLANNING, false);
        assertCommandSuccess(new GroceryListCommand(), newModel, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_emptyPlan_throwsCommandException() {
        Model modelWithNoPlans = new ModelManager(getTypicalRecipeBook(), new UserPrefs(),
                new CookedRecordBook(), new PlannedBook(), new QuoteBook());

        GroceryListCommand groceryListCommand = new GroceryListCommand();
        assertThrows(CommandException.class, () -> groceryListCommand.execute(modelWithNoPlans));
    }

}
