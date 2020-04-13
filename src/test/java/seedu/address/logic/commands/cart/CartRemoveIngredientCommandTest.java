package seedu.address.logic.commands.cart;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.testutil.TypicalIngredients.ALMOND;
import static seedu.address.testutil.TypicalIngredients.BUTTER;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class CartRemoveIngredientCommandTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new CartRemoveIngredientCommand(null, Optional.empty()));
        assertThrows(NullPointerException.class, () -> new CartRemoveIngredientCommand(ALMOND.getName(), null));
        assertThrows(NullPointerException.class, () -> new CartRemoveIngredientCommand(null, null));
    }

    @Test
    public void execute_validInput() throws CommandException {
        CartRemoveIngredientCommand commandWithQuantity =
                new CartRemoveIngredientCommand(ALMOND.getName(), Optional.of(ALMOND.getQuantity()));
        CartRemoveIngredientCommand commandWithoutQuantity =
                new CartRemoveIngredientCommand(ALMOND.getName(), Optional.empty());
        Model model = new ModelManager();

        // removing existing ingredient with quantity specified
        model.addCartIngredient(ALMOND);
        assertEquals(commandWithQuantity.execute(model),
                new CommandResult(String.format(CartRemoveIngredientCommand.MESSAGE_SUCCESS, ALMOND)));

        // removing existing ingredient without quantity specified
        model.addCartIngredient(ALMOND);
        assertEquals(commandWithoutQuantity.execute(model),
                new CommandResult(String.format(CartRemoveIngredientCommand.MESSAGE_SUCCESS,
                    CartRemoveIngredientCommand.ALL_KEYWORD + " " + ALMOND.getName())));

        // error thrown after removing ALMOND
        assertThrows(CommandException.class, () -> commandWithQuantity.execute(model));
        assertThrows(CommandException.class, () -> commandWithoutQuantity.execute(model));

        assertFalse(model.hasCartIngredient(ALMOND));
    }

    @Test
    public void execute_null_throwsNullPointerException() {
        CartRemoveIngredientCommand c = new CartRemoveIngredientCommand(ALMOND.getName(),
                Optional.of(ALMOND.getQuantity()));
        assertThrows(NullPointerException.class, () -> c.execute(null));
    }

    @Test
    public void execute_invalidInput_throwsIngredientNotFoundException() {
        CartRemoveIngredientCommand c = new CartRemoveIngredientCommand(ALMOND.getName(),
                Optional.of(ALMOND.getQuantity()));
        Model model = new ModelManager();

        assertThrows(CommandException.class, () -> c.execute(model));
    }

    @Test
    public void equalsMethod() {
        CartRemoveIngredientCommand c = new CartRemoveIngredientCommand(ALMOND.getName(),
                Optional.of(ALMOND.getQuantity()));
        assertEquals(c, new CartRemoveIngredientCommand(ALMOND.getName(), Optional.of(ALMOND.getQuantity())));
        assertNotEquals(c, new CartRemoveIngredientCommand(BUTTER.getName(), Optional.of(BUTTER.getQuantity())));
        assertNotEquals(c, null);
    }
}
