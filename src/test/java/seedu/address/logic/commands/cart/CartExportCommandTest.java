package seedu.address.logic.commands.cart;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.commands.cart.CartExportCommand.MESSAGE_SUCCESS;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class CartExportCommandTest {
    @Test
    public void execute_success() throws CommandException {
        Model model = new ModelManager();
        CartExportCommand c = new CartExportCommand();

        // empty cart, cart.pdf must not be open
        assertEquals(c.execute(model), new CommandResult(MESSAGE_SUCCESS));
    }

    @Test
    public void equalsMethod() {
        assertEquals(new CartExportCommand(), new CartExportCommand());
        assertNotEquals(new CartExportCommand(), null);
    }
}
