package seedu.address.logic.commands.customer;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.customer.TypicalCustomers.getTypicalInventorySystem;

import org.junit.jupiter.api.Test;

import seedu.address.model.InventorySystem;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ClearCustomerCommandTest {

    @Test
    public void execute_emptyInventorySystem_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCustomerCommand(), model, ClearCustomerCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyInventorySystem_success() {
        Model model = new ModelManager(getTypicalInventorySystem(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalInventorySystem(), new UserPrefs());
        expectedModel.setInventorySystem(new InventorySystem(), ClearCustomerCommand.COMMAND_WORD);

        assertCommandSuccess(new ClearCustomerCommand(), model, ClearCustomerCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
