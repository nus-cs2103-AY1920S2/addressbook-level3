package seedu.address.logic.commands.cookbook;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.commands.cookbook.CookbookRemoveCommand.MESSAGE_SUCCESS;
import static seedu.address.testutil.TypicalRecipes.AGLIO_OLIO;
import static seedu.address.testutil.TypicalRecipes.CARBONARA;
import static seedu.address.testutil.TypicalRecipes.SPAGHETTI_BOLOGNESE;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class CookbookRemoveCommandTest {

    private static final Index VALID_RECIPE_INDEX = new Index(0);
    private static final Index OUT_OF_BOUNDS_RECIPE_INDEX = new Index(1);

    @Test
    public void constructor_validInput() {
        CookbookRemoveCommand c = new CookbookRemoveCommand(VALID_RECIPE_INDEX);
        assertEquals(c, new CookbookRemoveCommand(VALID_RECIPE_INDEX));
    }

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new CookbookRemoveCommand(null));
    }

    @Test
    public void execute_validInput() throws CommandException {
        CookbookRemoveCommand c = new CookbookRemoveCommand(VALID_RECIPE_INDEX);
        Model model = new ModelManager();
        model.addCookbookRecipe(AGLIO_OLIO);
        assertEquals(c.execute(model), new CommandResult(String.format(MESSAGE_SUCCESS, AGLIO_OLIO.getName())));

        // after adding multiple recipes
        model.addCookbookRecipe(AGLIO_OLIO);
        model.addCookbookRecipe(CARBONARA);
        model.addCookbookRecipe(SPAGHETTI_BOLOGNESE);
        assertEquals(c.execute(model), new CommandResult(String.format(MESSAGE_SUCCESS, AGLIO_OLIO.getName())));

        assertFalse(model.hasCookbookRecipe(AGLIO_OLIO));
    }

    @Test
    public void execute_invalidInput() {
        Model model = new ModelManager();

        // index greater than size of UniqueRecipeList in Cookbook
        CookbookRemoveCommand c = new CookbookRemoveCommand(OUT_OF_BOUNDS_RECIPE_INDEX);
        assertThrows(CommandException.class, () -> c.execute(model));
    }

    @Test
    public void equalsMethod() {
        CookbookRemoveCommand c = new CookbookRemoveCommand(VALID_RECIPE_INDEX);
        assertEquals(c, new CookbookRemoveCommand(VALID_RECIPE_INDEX));
        assertNotEquals(c, new CookbookRemoveCommand(OUT_OF_BOUNDS_RECIPE_INDEX));
        assertNotEquals(c, null);
    }
}
