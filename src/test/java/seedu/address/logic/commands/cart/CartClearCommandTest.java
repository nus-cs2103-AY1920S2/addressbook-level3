package seedu.address.logic.commands.cart;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.commands.cart.CartClearCommand.MESSAGE_SUCCESS;
import static seedu.address.logic.commands.cart.CartClearCommand.MESSAGE_SUCCESS_EMPTY;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.testutil.TypicalIngredients;

public class CartClearCommandTest {

    @Test
    public void execute_success() throws CommandException {
        Model model = new ModelManager();
        CartClearCommand c = new CartClearCommand();

        // empty cart
        assertEquals(c.execute(model), new CommandResult(MESSAGE_SUCCESS_EMPTY));

        // with ingredients inside the cart
        model.addCartIngredient(TypicalIngredients.ALMOND);
        model.addCartIngredient(TypicalIngredients.BANANA);
        model.addCartIngredient(TypicalIngredients.CHICKEN);
        assertEquals(c.execute(model), new CommandResult(MESSAGE_SUCCESS));
        assertFalse(model.hasCartIngredient(TypicalIngredients.ALMOND));
        assertFalse(model.hasCartIngredient(TypicalIngredients.BANANA));
        assertFalse(model.hasCartIngredient(TypicalIngredients.CHICKEN));
    }

    @Test
    public void equalsMethod() {
        assertEquals(new CartClearCommand(), new CartClearCommand());
        assertNotEquals(new CartClearCommand(), null);
    }
}
