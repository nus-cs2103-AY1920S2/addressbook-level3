package seedu.address.logic.commands.recipe;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.commands.recipe.RecipeAddIngredientCommand.MESSAGE_SUCCESS;
import static seedu.address.testutil.TypicalIngredients.ALMOND;
import static seedu.address.testutil.TypicalIngredients.BUTTER;
import static seedu.address.testutil.TypicalRecipes.AGLIO_OLIO;
import static seedu.address.testutil.TypicalRecipes.CARBONARA;
import static seedu.address.testutil.TypicalRecipes.SCRAMBLED_EGG;
import static seedu.address.testutil.TypicalRecipes.SPAGHETTI_BOLOGNESE;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class RecipeAddIngredientCommandTest {
    private static final Index VALID_RECIPE_INDEX = new Index(0);
    private static final Index OUT_OF_BOUNDS_RECIPE_INDEX = new Index(1);

    @Test
    public void constructor_validInput() {
        RecipeAddIngredientCommand c = new RecipeAddIngredientCommand(VALID_RECIPE_INDEX, ALMOND);
        assertEquals(c, new RecipeAddIngredientCommand(VALID_RECIPE_INDEX, ALMOND));
    }

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new RecipeAddIngredientCommand(VALID_RECIPE_INDEX, null));
        assertThrows(NullPointerException.class, () -> new RecipeAddIngredientCommand(null, ALMOND));
        assertThrows(NullPointerException.class, () -> new RecipeAddIngredientCommand(null, null));
    }

    @Test
    public void execute_validInput() throws CommandException {
        RecipeAddIngredientCommand c = new RecipeAddIngredientCommand(VALID_RECIPE_INDEX, ALMOND);
        Model model = new ModelManager();
        model.addCookbookRecipe(CARBONARA);
        assertEquals(c.execute(model), new CommandResult(String.format(MESSAGE_SUCCESS, ALMOND, CARBONARA.getName())));

        // after adding multiple recipes
        model.addCookbookRecipe(AGLIO_OLIO);
        model.addCookbookRecipe(SCRAMBLED_EGG);
        model.addCookbookRecipe(SPAGHETTI_BOLOGNESE);
        assertEquals(c.execute(model), new CommandResult(String.format(MESSAGE_SUCCESS, ALMOND, AGLIO_OLIO.getName())));
    }

    @Test
    public void execute_null_throwsNullPointerException() {
        RecipeAddIngredientCommand c = new RecipeAddIngredientCommand(VALID_RECIPE_INDEX, ALMOND);
        assertThrows(NullPointerException.class, () -> c.execute(null));
    }

    @Test
    public void execute_invalidInput_throwsCommandException() {
        Model model = new ModelManager();

        // empty Cookbook
        RecipeAddIngredientCommand c = new RecipeAddIngredientCommand(VALID_RECIPE_INDEX, ALMOND);
        assertThrows(CommandException.class, () -> c.execute(model));

        // index out of bounds
        RecipeAddIngredientCommand d = new RecipeAddIngredientCommand(OUT_OF_BOUNDS_RECIPE_INDEX, ALMOND);
        assertThrows(CommandException.class, () -> d.execute(model));
    }

    @Test
    public void equalsMethod() {
        RecipeAddIngredientCommand c = new RecipeAddIngredientCommand(VALID_RECIPE_INDEX, ALMOND);
        assertEquals(c, new RecipeAddIngredientCommand(VALID_RECIPE_INDEX, ALMOND));
        assertNotEquals(c, new RecipeAddIngredientCommand(VALID_RECIPE_INDEX, BUTTER));
        assertNotEquals(c, null);
    }
}
