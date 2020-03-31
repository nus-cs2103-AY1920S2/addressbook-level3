package seedu.recipe.logic.commands;

import static seedu.recipe.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.recipe.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.recipe.testutil.TypicalRecipes.getTypicalRecipeBook;
import static seedu.recipe.testutil.TypicalRecords.getTypicalRecordBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.recipe.model.Model;
import seedu.recipe.model.ModelManager;
import seedu.recipe.model.UserPrefs;
import seedu.recipe.model.plan.PlannedBook;
import seedu.recipe.model.recipe.Recipe;
import seedu.recipe.testutil.RecipeBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
<<<<<<< HEAD
        model = new ModelManager(getTypicalRecipeBook(), new UserPrefs(), getTypicalRecordBook());
=======
        model = new ModelManager(getTypicalRecipeBook(), new PlannedBook(), new UserPrefs());
>>>>>>> upstream/master
    }

    @Test
    public void execute_newRecipe_success() {
        Recipe validRecipe = new RecipeBuilder().build();

<<<<<<< HEAD
        Model expectedModel = new ModelManager(model.getRecipeBook(), new UserPrefs(), model.getRecordBook());
        // System.out.println(validRecipe); // todo remove later
=======
        Model expectedModel = new ModelManager(model.getRecipeBook(), new PlannedBook(), new UserPrefs());
>>>>>>> upstream/master
        expectedModel.addRecipe(validRecipe);

        assertCommandSuccess(new AddCommand(validRecipe), model,
                String.format(AddCommand.MESSAGE_SUCCESS, validRecipe), expectedModel);
    }

    @Test
    public void execute_duplicateRecipe_throwsCommandException() {
        Recipe recipeInList = model.getRecipeBook().getRecipeList().get(0);
        assertCommandFailure(new AddCommand(recipeInList), model, AddCommand.MESSAGE_DUPLICATE_RECIPE);
    }

}
