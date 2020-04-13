package seedu.address.logic.commands.cart;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.cart.CartMoveCommand.MESSAGE_SUCCESS;
import static seedu.address.logic.commands.cart.CartMoveCommand.MESSAGE_SUCCESS_EMPTY;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.testutil.TypicalIngredients;


public class CartMoveCommandTest {

    @Test
    public void execute_success() throws CommandException {
        Model model = new ModelManager();
        CartMoveCommand c = new CartMoveCommand();

        // cart is initially empty
        assertEquals(c.execute(model), new CommandResult(MESSAGE_SUCCESS_EMPTY));

        // add ingredients into the cart
        model.addCartIngredient(TypicalIngredients.ALMOND);
        model.addCartIngredient(TypicalIngredients.APPLE);
        model.addCartIngredient(TypicalIngredients.BANANA);
        assertEquals(c.execute(model), new CommandResult(MESSAGE_SUCCESS));
        assertTrue(model.hasInventoryIngredient(TypicalIngredients.ALMOND));
        assertTrue(model.hasInventoryIngredient(TypicalIngredients.APPLE));
        assertTrue(model.hasInventoryIngredient(TypicalIngredients.BANANA));
        assertTrue(model.getCart().getIngredientList().size() == 0);
    }

    @Test
    public void equalsMethod() {
        assertEquals(new CartMoveCommand(), new CartMoveCommand());
        assertNotEquals(new CartMoveCommand(), null);
    }
}
