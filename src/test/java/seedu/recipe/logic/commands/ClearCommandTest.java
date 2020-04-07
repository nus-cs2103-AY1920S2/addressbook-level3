package seedu.recipe.logic.commands;

import static seedu.recipe.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.recipe.testutil.TypicalRecipes.getTypicalRecipeBook;
import static seedu.recipe.testutil.TypicalRecords.getTypicalRecordBook;

import org.junit.jupiter.api.Test;

import seedu.recipe.model.Model;
import seedu.recipe.model.ModelManager;
import seedu.recipe.model.RecipeBook;
import seedu.recipe.model.UserPrefs;
import seedu.recipe.model.plan.PlannedBook;
import seedu.recipe.ui.tab.Tab;

public class ClearCommandTest {

    @Test
    public void execute_emptyRecipeBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        CommandResult expectedCommandResult =
                new CommandResult(ClearCommand.MESSAGE_SUCCESS, false, Tab.RECIPES, false);
        assertCommandSuccess(new ClearCommand(), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_nonEmptyRecipeBook_success() {
        Model model = new ModelManager(getTypicalRecipeBook(), new UserPrefs(),
                getTypicalRecordBook(), new PlannedBook());
        Model expectedModel = new ModelManager(getTypicalRecipeBook(), new UserPrefs(),
                getTypicalRecordBook(), new PlannedBook());

        expectedModel.setRecipeBook(new RecipeBook());

        CommandResult expectedCommandResult =
                new CommandResult(ClearCommand.MESSAGE_SUCCESS, false, Tab.RECIPES, false);
        assertCommandSuccess(new ClearCommand(), model, expectedCommandResult, expectedModel);
    }

}
