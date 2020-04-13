package seedu.address.logic.commands.cart;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.cart.CartAddRecipeIngredientCommand.MESSAGE_SUCCESS;
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

public class CartAddRecipeIngredientCommandTest {

    private static final Index VALID_RECIPE_INDEX = new Index(0);
    private static final Index INVALID_RECIPE_INDEX = new Index(100000);

    @Test
    public void constructor_validInput() {
        CartAddRecipeIngredientCommand c = new CartAddRecipeIngredientCommand(VALID_RECIPE_INDEX);
        assertEquals(c, new CartAddRecipeIngredientCommand(VALID_RECIPE_INDEX));
    }

    @Test
    public void execute_validInput() throws CommandException {
        CartAddRecipeIngredientCommand c = new CartAddRecipeIngredientCommand(VALID_RECIPE_INDEX);
        Model model = new ModelManager();
        model.addCookbookRecipe(CARBONARA);
        assertEquals(c.execute(model), new CommandResult(String.format(MESSAGE_SUCCESS, CARBONARA.getName())));

        // after adding multiple recipes
        model.addCookbookRecipe(AGLIO_OLIO);
        model.addCookbookRecipe(SCRAMBLED_EGG);
        model.addCookbookRecipe(SPAGHETTI_BOLOGNESE);
        assertEquals(c.execute(model), new CommandResult(String.format(MESSAGE_SUCCESS, AGLIO_OLIO.getName())));
        assertTrue(model.hasCookbookRecipe(AGLIO_OLIO));
        assertTrue(model.hasCookbookRecipe(SCRAMBLED_EGG));
        assertTrue(model.hasCookbookRecipe(SPAGHETTI_BOLOGNESE));
    }

    @Test
    public void execute_invalidInput() {
        Model model = new ModelManager();

        // invalid index
        CartAddRecipeIngredientCommand c = new CartAddRecipeIngredientCommand(INVALID_RECIPE_INDEX);
        assertThrows(CommandException.class, () -> c.execute(model));

        // index greater than size of UniqueRecipeList in Cookbook
        CartAddRecipeIngredientCommand d = new CartAddRecipeIngredientCommand(VALID_RECIPE_INDEX);
        assertThrows(CommandException.class, () -> d.execute(model));
    }

    @Test
    public void equalsMethod() {
        CartAddRecipeIngredientCommand c = new CartAddRecipeIngredientCommand(VALID_RECIPE_INDEX);
        assertEquals(c, new CartAddRecipeIngredientCommand(VALID_RECIPE_INDEX));
        assertNotEquals(c, new CartAddRecipeIngredientCommand(INVALID_RECIPE_INDEX));
        assertNotEquals(c, null);
    }
}
