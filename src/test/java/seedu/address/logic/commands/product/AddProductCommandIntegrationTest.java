package seedu.address.logic.commands.product;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.product.TypicalProducts.getTypicalInventorySystem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.product.Product;
import seedu.address.testutil.product.TypicalProducts;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCustomerCommand}.
 */
public class AddProductCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalInventorySystem(), new UserPrefs());
    }

    @Test
    public void execute_newProduct_success() {
        Product validProduct = TypicalProducts.WATCH;

        Model expectedModel = new ModelManager(model.getInventorySystem(), new UserPrefs());
        expectedModel.addProduct(validProduct);

        assertCommandSuccess(new AddProductCommand(validProduct), model,
                String.format(AddProductCommand.MESSAGE_SUCCESS, validProduct), expectedModel);
    }

    @Test
    public void execute_duplicateProduct_throwsCommandException() {
        Product productInList = model.getInventorySystem().getProductList().get(0);
        assertCommandFailure(new AddProductCommand(productInList), model,
                Messages.MESSAGE_DUPLICATE_PRODUCT);
    }

}
