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

public class ClearCommandTest {

    @Test
    public void execute_emptyRecipeBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyRecipeBook_success() {
<<<<<<< HEAD
        Model model = new ModelManager(getTypicalRecipeBook(), new UserPrefs(), getTypicalRecordBook());
        Model expectedModel = new ModelManager(getTypicalRecipeBook(), new UserPrefs(), getTypicalRecordBook());
=======
        Model model = new ModelManager(getTypicalRecipeBook(), new PlannedBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalRecipeBook(), new PlannedBook(), new UserPrefs());
>>>>>>> upstream/master
        expectedModel.setRecipeBook(new RecipeBook());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
