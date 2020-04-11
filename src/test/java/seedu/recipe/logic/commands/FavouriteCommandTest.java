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
import seedu.recipe.logic.commands.recipe.FavouriteCommand;
import seedu.recipe.model.Model;
import seedu.recipe.model.ModelManager;
import seedu.recipe.model.UserPrefs;
import seedu.recipe.model.achievement.QuoteBook;
import seedu.recipe.model.plan.PlannedBook;
import seedu.recipe.model.recipe.Recipe;
import seedu.recipe.testutil.RecipeBuilder;
import seedu.recipe.ui.tab.Tab;

public class FavouriteCommandTest {
    private Model model = new ModelManager(getTypicalRecipeBook(), new UserPrefs(),
            getTypicalRecordBook(), new PlannedBook(), new QuoteBook());

    private final Recipe favouritedFirstRecipe = new RecipeBuilder().withName("Caesar Salad")
            .withTime("10")
            .withVegetables("300g, Lettuce")
            .withGrains("200g, Bread")
            .withSteps("Cut tomatoes", "Remove honeydew skin")
            .withGoals("Herbivore", "Wholesome Wholemeal")
            .withFavourite(true).build();

    private final Recipe favouritedSecondRecipe = new RecipeBuilder().withName("Grilled Sandwich")
            .withTime("10")
            .withGrains("150g, Bread")
            .withOthers("50g, Cheese")
            .withSteps("Spread butter on bread", "Heat pan to medium heat")
            .withGoals("Wholesome Wholemeal")
            .withFavourite(true).build();

    @Test
    public void execute_markUnfavouriteAsFavourite_success() {
        Recipe recipeToFavourite = model.getFilteredRecipeList().get(INDEX_FIRST_RECIPE.getZeroBased());
        FavouriteCommand favouriteCommand = new FavouriteCommand(new Index[] {INDEX_FIRST_RECIPE});

        String expectedMessageTemplate = FavouriteCommand.MESSAGE_SUCCESS;
        String expectedMessage = String.format(expectedMessageTemplate, recipeToFavourite.getName().toString()) + "\n";

        ModelManager expectedModel = new ModelManager(model.getRecipeBook(), new UserPrefs(),
                model.getRecordBook(), new PlannedBook(), new QuoteBook());
        expectedModel.setRecipe(recipeToFavourite, favouritedFirstRecipe);

        CommandResult expectedCommandResult =
                new CommandResult(expectedMessage, false, false, Tab.RECIPES, false);

        assertCommandSuccess(favouriteCommand, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_markFavouriteAsFavourite_success() {
        Recipe recipeToFavourite = model.getFilteredRecipeList().get(INDEX_FIRST_RECIPE.getZeroBased());
        Recipe favouritedRecipe = new RecipeBuilder(recipeToFavourite).withFavourite(true).build();
        FavouriteCommand favouriteCommand = new FavouriteCommand(new Index[] {INDEX_FIRST_RECIPE});

        String expectedMessageTemplate = FavouriteCommand.MESSAGE_ALREADY_FAVOURITE;
        String expectedMessage = String.format(expectedMessageTemplate, recipeToFavourite.getName().toString());

        ModelManager expectedModel = new ModelManager(model.getRecipeBook(), new UserPrefs(),
                model.getRecordBook(), new PlannedBook(), new QuoteBook());
        model.setRecipe(recipeToFavourite, favouritedRecipe);
        expectedModel.setRecipe(recipeToFavourite, favouritedFirstRecipe);

        CommandResult expectedCommandResult =
                new CommandResult(expectedMessage, false, false, Tab.RECIPES, false);

        assertCommandSuccess(favouriteCommand, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_markUnfavouriteAndFavouriteAsFavourite_success() {
        Recipe firstRecipeToFavourite = model.getFilteredRecipeList().get(INDEX_FIRST_RECIPE.getZeroBased());

        Recipe secondRecipeToFavourite = model.getFilteredRecipeList().get(INDEX_SECOND_RECIPE.getZeroBased());
        Recipe setSecondRecipeAsFavourite = new RecipeBuilder(secondRecipeToFavourite).withFavourite(true).build();

        FavouriteCommand favouriteCommand = new FavouriteCommand(new Index[] {INDEX_FIRST_RECIPE, INDEX_SECOND_RECIPE});

        String expectedMessage =
                String.format(FavouriteCommand.MESSAGE_SUCCESS,
                        firstRecipeToFavourite.getName().toString()) + "\n"
                + String.format(FavouriteCommand.MESSAGE_ALREADY_FAVOURITE,
                        secondRecipeToFavourite.getName().toString());

        ModelManager expectedModel = new ModelManager(model.getRecipeBook(), new UserPrefs(),
                model.getRecordBook(), new PlannedBook(), new QuoteBook());

        model.setRecipe(secondRecipeToFavourite, setSecondRecipeAsFavourite);
        expectedModel.setRecipe(firstRecipeToFavourite, favouritedFirstRecipe);
        expectedModel.setRecipe(secondRecipeToFavourite, favouritedSecondRecipe);

        CommandResult expectedCommandResult =
                new CommandResult(expectedMessage, false, false, Tab.RECIPES, false);

        assertCommandSuccess(favouriteCommand, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void equals() {
        FavouriteCommand favouriteFirstCommand = new FavouriteCommand(new Index[] {INDEX_FIRST_RECIPE});
        FavouriteCommand favouriteSecondCommand = new FavouriteCommand(new Index[] {INDEX_SECOND_RECIPE});

        // same object -> returns true
        assertTrue(favouriteFirstCommand.equals(favouriteFirstCommand));

        // same values -> returns true
        FavouriteCommand favouriteFirstCommandCopy = new FavouriteCommand(new Index[] {INDEX_FIRST_RECIPE});
        assertTrue(favouriteFirstCommand.equals(favouriteFirstCommandCopy));

        // different types -> returns false
        assertFalse(favouriteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(favouriteFirstCommand.equals(null));

        // different recipe -> returns false
        assertFalse(favouriteFirstCommand.equals(favouriteSecondCommand));
    }
}
