package seedu.address.logic.commands.product;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.product.TypicalProducts.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.InventorySystem;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ClearProductCommandTest {

    @Test
    public void execute_emptyAddressBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearProductCommand(), model, ClearProductCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.setAddressBook(new InventorySystem(), ClearProductCommand.COMMAND_WORD);

        assertCommandSuccess(new ClearProductCommand(), model, ClearProductCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
