package seedu.address.logic.commands.inventory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.commands.inventory.InventoryCookCommand.MESSAGE_SUCCESS;
import static seedu.address.testutil.TypicalRecipes.AGLIO_OLIO;
import static seedu.address.testutil.TypicalRecipes.CARBONARA;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;

import seedu.address.logic.commands.CommandResult;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ingredient.Ingredient;
import seedu.address.model.ingredient.IngredientName;
import seedu.address.model.ingredient.IngredientQuantity;

public class InventoryCookCommandTest {

    private static final Index VALID_RECIPE_INDEX = new Index(0);
    private static final Index OUT_OF_BOUNDS_RECIPE_INDEX = new Index(100);

    @Test
    public void constructor_validInput() {
        InventoryCookCommand c = new InventoryCookCommand(VALID_RECIPE_INDEX);
        assertEquals(c, new InventoryCookCommand(VALID_RECIPE_INDEX));
    }

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new InventoryCookCommand(null));
    }

    @Test
    public void execute_validInput() throws CommandException {
        InventoryCookCommand c = new InventoryCookCommand(VALID_RECIPE_INDEX);
        Model model = new ModelManager();
        model.addCookbookRecipe(AGLIO_OLIO);
        AGLIO_OLIO.getIngredients().stream()
                .forEach(recipeIngredient -> model.addInventoryIngredient(recipeIngredient));

        assertEquals(c.execute(model), new CommandResult(String.format(MESSAGE_SUCCESS,
                VALID_RECIPE_INDEX.getOneBased())));

        // after adding multiple recipes
        model.addCookbookRecipe(CARBONARA);
        AGLIO_OLIO.getIngredients().stream()
                .forEach(recipeIngredient -> model.addInventoryIngredient(recipeIngredient));
        assertEquals(c.execute(model), new CommandResult(String.format(MESSAGE_SUCCESS,
                VALID_RECIPE_INDEX.getOneBased())));

        //no more ingredient in inventory
        assertThrows(CommandException.class, () -> c.execute(model));

        //insufficient ingredient in inventory
        AGLIO_OLIO.getIngredients().stream()
                .forEach(recipeIngredient -> model.addInventoryIngredient(recipeIngredient));
        model.removeInventoryIngredient(new Ingredient(new IngredientName("Garlic"),
                new IngredientQuantity("3 cloves")));
        assertThrows(CommandException.class, () -> c.execute(model));
    }

    @Test
    public void execute_invalidInput() {
        Model model = new ModelManager();

        // index greater than size of UniqueRecipeList in Cookbook
        InventoryCookCommand c = new InventoryCookCommand(OUT_OF_BOUNDS_RECIPE_INDEX);
        assertThrows(CommandException.class, () -> c.execute(model));
    }

    @Test
    public void equalsMethod() {
        InventoryCookCommand c = new InventoryCookCommand(VALID_RECIPE_INDEX);
        assertEquals(c, new InventoryCookCommand(VALID_RECIPE_INDEX));
        assertNotEquals(c, new InventoryCookCommand(OUT_OF_BOUNDS_RECIPE_INDEX));
        assertNotEquals(c, null);
    }
}
