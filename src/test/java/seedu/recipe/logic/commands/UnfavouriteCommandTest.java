package seedu.recipe.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.recipe.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.recipe.testutil.TypicalIndexes.INDEX_FIRST_RECIPE;
import static seedu.recipe.testutil.TypicalIndexes.INDEX_SECOND_RECIPE;
import static seedu.recipe.testutil.TypicalRecipes.getTypicalRecipeBook;
import static seedu.recipe.testutil.TypicalRecords.getTypicalRecordBook;

import org.junit.jupiter.api.Test;

import seedu.recipe.commons.core.index.Index;
import seedu.recipe.logic.commands.recipe.UnfavouriteCommand;
import seedu.recipe.model.Model;
import seedu.recipe.model.ModelManager;
import seedu.recipe.model.UserPrefs;
import seedu.recipe.model.achievement.QuoteBook;
import seedu.recipe.model.plan.PlannedBook;
import seedu.recipe.model.recipe.Recipe;
import seedu.recipe.testutil.RecipeBuilder;
import seedu.recipe.ui.tab.Tab;

public class UnfavouriteCommandTest {
    private Model model = new ModelManager(getTypicalRecipeBook(), new UserPrefs(),
            getTypicalRecordBook(), new PlannedBook(), new QuoteBook());

    private final Recipe favouritedFirstRecipe = new RecipeBuilder().withName("Caesar Salad")
            .withTime("10")
            .withVegetables("300g, Lettuce")
            .withGrains("200g, Bread")
            .withSteps("Cut tomatoes", "Remove honeydew skin")
            .withGoals("Herbivore", "Wholesome Wholemeal")
            .withFavourite(true).build();

    @Test
    public void execute_markFavouriteAsUnfavourite_success() {
        Recipe recipeToUnfavourite = model.getFilteredRecipeList().get(INDEX_FIRST_RECIPE.getZeroBased());
        model.setRecipe(recipeToUnfavourite, favouritedFirstRecipe);
        UnfavouriteCommand unfavouriteCommand = new UnfavouriteCommand(new Index[] {INDEX_FIRST_RECIPE});

        String expectedMessageTemplate = UnfavouriteCommand.MESSAGE_SUCCESS;
        String expectedMessage =
                String.format(expectedMessageTemplate, recipeToUnfavourite.getName().toString()) + "\n";

        ModelManager expectedModel = new ModelManager(model.getRecipeBook(), new UserPrefs(),
                model.getRecordBook(), new PlannedBook(), new QuoteBook());

        CommandResult expectedCommandResult =
                new CommandResult(expectedMessage, false, false, Tab.RECIPES, false);

        assertCommandSuccess(unfavouriteCommand, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_markUnfavouriteAsUnfavourite_success() {
        Recipe recipeToUnfavourite = model.getFilteredRecipeList().get(INDEX_FIRST_RECIPE.getZeroBased());
        UnfavouriteCommand unfavouriteCommand = new UnfavouriteCommand(new Index[] {INDEX_FIRST_RECIPE});

        String expectedMessageTemplate = UnfavouriteCommand.MESSAGE_ALREADY_NOT_FAVOURITE;
        String expectedMessage = String.format(expectedMessageTemplate, recipeToUnfavourite.getName().toString());

        ModelManager expectedModel = new ModelManager(model.getRecipeBook(), new UserPrefs(),
                model.getRecordBook(), new PlannedBook(), new QuoteBook());

        CommandResult expectedCommandResult =
                new CommandResult(expectedMessage, false, false, Tab.RECIPES, false);

        assertCommandSuccess(unfavouriteCommand, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_markUnfavouriteAndFavouriteAsUnfavourite_success() {
        Recipe firstRecipeToUnfavourite = model.getFilteredRecipeList().get(INDEX_FIRST_RECIPE.getZeroBased());
        Recipe secondRecipeToUnfavourite = model.getFilteredRecipeList().get(INDEX_SECOND_RECIPE.getZeroBased());

        UnfavouriteCommand unfavouriteCommand =
                new UnfavouriteCommand(new Index[] {INDEX_FIRST_RECIPE, INDEX_SECOND_RECIPE});

        String expectedMessage =
                String.format(UnfavouriteCommand.MESSAGE_SUCCESS,
                        firstRecipeToUnfavourite.getName().toString()) + "\n"
                        + String.format(UnfavouriteCommand.MESSAGE_ALREADY_NOT_FAVOURITE,
                        secondRecipeToUnfavourite.getName().toString());

        ModelManager expectedModel = new ModelManager(model.getRecipeBook(), new UserPrefs(),
                model.getRecordBook(), new PlannedBook(), new QuoteBook());

        model.setRecipe(firstRecipeToUnfavourite, favouritedFirstRecipe);

        CommandResult expectedCommandResult =
                new CommandResult(expectedMessage, false, false, Tab.RECIPES, false);

        assertCommandSuccess(unfavouriteCommand, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void equals() {
        UnfavouriteCommand unfavouriteFirstCommand = new UnfavouriteCommand(new Index[]{INDEX_FIRST_RECIPE});
        UnfavouriteCommand unfavouriteSecondCommand = new UnfavouriteCommand(new Index[]{INDEX_SECOND_RECIPE});

        // same object -> returns true
        assertTrue(unfavouriteFirstCommand.equals(unfavouriteFirstCommand));

        // same values -> returns true
        UnfavouriteCommand unfavouriteFirstCommandCopy = new UnfavouriteCommand(new Index[]{INDEX_FIRST_RECIPE});
        assertTrue(unfavouriteFirstCommand.equals(unfavouriteFirstCommandCopy));

        // different types -> returns false
        assertFalse(unfavouriteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(unfavouriteFirstCommand.equals(null));

        // different recipe -> returns false
        assertFalse(unfavouriteFirstCommand.equals(unfavouriteSecondCommand));
    }
}
