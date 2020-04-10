package seedu.address.logic.commands.customer;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.customer.TypicalCustomers.getTypicalInventorySystem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.customer.Customer;
import seedu.address.testutil.customer.CustomerBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCustomerCommand}.
 */
public class AddCustomerCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalInventorySystem(), new UserPrefs());
    }

    @Test
    public void execute_newPerson_success() {
        Customer validCustomer = new CustomerBuilder("a4365691-ea10-47ad-b33b-fc038f1e5e80").build();

        Model expectedModel = new ModelManager(model.getInventorySystem(), new UserPrefs());
        expectedModel.addPerson(validCustomer);

        assertCommandSuccess(new AddCustomerCommand(validCustomer), model,
                String.format(AddCustomerCommand.MESSAGE_SUCCESS, validCustomer), expectedModel);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Customer customerInList = model.getInventorySystem().getPersonList().get(0);
        assertCommandFailure(new AddCustomerCommand(customerInList), model,
                Messages.MESSAGE_DUPLICATE_PERSON);
    }

}
