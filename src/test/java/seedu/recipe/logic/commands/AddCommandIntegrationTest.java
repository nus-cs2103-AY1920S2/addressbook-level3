package seedu.recipe.logic.commands;

import static seedu.recipe.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.recipe.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.recipe.testutil.TypicalRecipes.getTypicalRecipeBook;
import static seedu.recipe.testutil.TypicalRecords.getTypicalRecordBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.recipe.logic.commands.recipe.AddCommand;
import seedu.recipe.model.Model;
import seedu.recipe.model.ModelManager;
import seedu.recipe.model.UserPrefs;
import seedu.recipe.model.achievement.QuoteBook;
import seedu.recipe.model.plan.PlannedBook;
import seedu.recipe.model.recipe.Recipe;
import seedu.recipe.testutil.RecipeBuilder;
import seedu.recipe.ui.tab.Tab;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalRecipeBook(), new UserPrefs(), getTypicalRecordBook(), new PlannedBook(),
                new QuoteBook());
    }


    @Test
    public void execute_newRecipe_success() {
        Recipe validRecipe = new RecipeBuilder().build();

        Model expectedModel = new ModelManager(model.getRecipeBook(), new UserPrefs(),
                model.getRecordBook(), new PlannedBook(), new QuoteBook());

        expectedModel.addRecipe(validRecipe);

        CommandResult expectedCommandResult =
                new CommandResult(String.format(AddCommand.MESSAGE_SUCCESS, validRecipe), false,
                        false, Tab.RECIPES, false);

        assertCommandSuccess(new AddCommand(validRecipe), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_duplicateRecipe_throwsCommandException() {
        Recipe recipeInList = model.getRecipeBook().getRecipeList().get(0);
        assertCommandFailure(new AddCommand(recipeInList), model, AddCommand.MESSAGE_DUPLICATE_RECIPE);
    }

}
