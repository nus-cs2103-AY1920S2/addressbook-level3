package cookbuddy.logic.commands;

import static cookbuddy.logic.commands.CommandTestUtil.assertCommandSuccess;
import static cookbuddy.testutil.TypicalRecipes.getTypicalRecipeBook;

import cookbuddy.model.Model;
import cookbuddy.model.ModelManager;
import cookbuddy.model.RecipeBook;
import cookbuddy.model.UserPrefs;
import org.junit.jupiter.api.Test;

public class ResetCommandTest {

    @Test
    public void execute_emptyRecipeBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        assertCommandSuccess(new ResetCommand(), model, ResetCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyRecipeBook_success() {
        Model model = new ModelManager(getTypicalRecipeBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalRecipeBook(), new UserPrefs());
        expectedModel.setRecipeBook(new RecipeBook());

        assertCommandSuccess(new ResetCommand(), model, ResetCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
