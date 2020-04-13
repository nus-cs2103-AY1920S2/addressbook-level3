package seedu.address.logic.commands.inventory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.inventory.InventoryAddIngredientCommand.MESSAGE_SUCCESS;
import static seedu.address.testutil.TypicalIngredients.ALMOND;
import static seedu.address.testutil.TypicalIngredients.BUTTER;
import static seedu.address.testutil.TypicalIngredients.CHICKEN;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class InventoryAddIngredientCommandTest {
    @Test
    public void constructor_validInput() {
        InventoryAddIngredientCommand c = new InventoryAddIngredientCommand(ALMOND);
        assertEquals(c, new InventoryAddIngredientCommand(ALMOND));
    }

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new InventoryAddIngredientCommand(null));
    }

    @Test
    public void execute_validInput() throws CommandException {
        Model model = new ModelManager();

        InventoryAddIngredientCommand c = new InventoryAddIngredientCommand(ALMOND);
        assertEquals(c.execute(model), new CommandResult(String.format(MESSAGE_SUCCESS, ALMOND)));

        InventoryAddIngredientCommand d = new InventoryAddIngredientCommand(BUTTER);
        assertEquals(d.execute(model), new CommandResult(String.format(MESSAGE_SUCCESS, BUTTER)));

        InventoryAddIngredientCommand e = new InventoryAddIngredientCommand(CHICKEN);
        assertEquals(e.execute(model), new CommandResult(String.format(MESSAGE_SUCCESS, CHICKEN)));

        assertTrue(model.hasInventoryIngredient(ALMOND));
        assertTrue(model.hasInventoryIngredient(BUTTER));
        assertTrue(model.hasInventoryIngredient(CHICKEN));

        // adding duplicate ingredients updates its count
        model.addInventoryIngredient(ALMOND);
        assertEquals(c.execute(model), new CommandResult(String.format(MESSAGE_SUCCESS, ALMOND)));
    }

    @Test
    public void execute_null_throwsNullPointerException() {
        InventoryAddIngredientCommand c = new InventoryAddIngredientCommand(ALMOND);
        assertThrows(NullPointerException.class, () -> c.execute(null));
    }

    @Test
    public void equalsMethod() {
        InventoryAddIngredientCommand c = new InventoryAddIngredientCommand(ALMOND);
        assertEquals(c, new InventoryAddIngredientCommand(ALMOND));
        assertNotEquals(c, new InventoryAddIngredientCommand(BUTTER));
        assertNotEquals(c, null);
    }
}
