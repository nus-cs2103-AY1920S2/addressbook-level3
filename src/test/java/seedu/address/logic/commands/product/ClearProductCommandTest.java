package seedu.address.logic.commands.product;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.product.TypicalProducts.getTypicalInventorySystem;

import org.junit.jupiter.api.Test;

import seedu.address.model.InventorySystem;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ClearProductCommandTest {

    @Test
    public void execute_emptyInventorySystem_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearProductCommand(), model, ClearProductCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyInventorySystem_success() {
        Model model = new ModelManager(getTypicalInventorySystem(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalInventorySystem(), new UserPrefs());
        expectedModel.setInventorySystem(new InventorySystem(), ClearProductCommand.COMMAND_WORD);

        assertCommandSuccess(new ClearProductCommand(), model, ClearProductCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
