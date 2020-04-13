package seedu.address.logic.commands.cart;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.cart.CartAddIngredientCommand.MESSAGE_SUCCESS;
import static seedu.address.testutil.TypicalIngredients.ALMOND;
import static seedu.address.testutil.TypicalIngredients.APPLE;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class CartAddIngredientCommandTest {

    @Test
    public void constructor_validInput() {
        CartAddIngredientCommand c = new CartAddIngredientCommand(ALMOND);
        assertEquals(c, new CartAddIngredientCommand(ALMOND));
    }

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new CartAddIngredientCommand(null));
    }

    @Test
    public void execute_validInput() throws CommandException {
        CartAddIngredientCommand c = new CartAddIngredientCommand(ALMOND);
        Model model = new ModelManager();
        assertEquals(c.execute(model), new CommandResult(String.format(MESSAGE_SUCCESS, ALMOND)));
        assertTrue(model.hasCartIngredient(ALMOND));
    }

    @Test
    public void execute_null_throwsNullPointerException() {
        CartAddIngredientCommand c = new CartAddIngredientCommand(ALMOND);
        assertThrows(NullPointerException.class, () -> c.execute(null));
    }

    @Test
    public void equalsMethod() {
        CartAddIngredientCommand c = new CartAddIngredientCommand(ALMOND);
        assertEquals(c, new CartAddIngredientCommand(ALMOND));
        assertNotEquals(c, new CartAddIngredientCommand(APPLE));
        assertNotEquals(c, null);
    }
}
