package seedu.address.logic.commands.product;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BAG;
import static seedu.address.logic.commands.CommandTestUtil.DESC_WATCH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_WATCH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRICE_WATCH;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showProductAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PRODUCT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PRODUCT;
import static seedu.address.testutil.product.TypicalProducts.ABACUS_ID;
import static seedu.address.testutil.product.TypicalProducts.getTypicalInventorySystem;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.product.EditProductCommand.EditProductDescriptor;
import seedu.address.model.InventorySystem;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.product.Product;
import seedu.address.testutil.product.EditProductDescriptorBuilder;
import seedu.address.testutil.product.ProductBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests
 * for EditProductCommand.
 */
public class EditProductCommandTest {

    private Model model = new ModelManager(getTypicalInventorySystem(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Product editedProduct = new ProductBuilder(ABACUS_ID).build();
        EditProductDescriptor descriptor = new EditProductDescriptorBuilder(editedProduct).build();

        EditProductCommand editProductCommand = new EditProductCommand(INDEX_FIRST_PRODUCT, descriptor);

        String expectedMessage = String.format(EditProductCommand.MESSAGE_EDIT_PRODUCT_SUCCESS, editedProduct);

        Model expectedModel = new ModelManager(new InventorySystem(model.getInventorySystem()), new UserPrefs());
        expectedModel.setProduct(model.getFilteredProductList().get(0), editedProduct);

        assertCommandSuccess(editProductCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastProduct = Index.fromOneBased(model.getFilteredProductList().size());
        Product lastProduct = model.getFilteredProductList().get(indexLastProduct.getZeroBased());

        ProductBuilder productInList = new ProductBuilder(lastProduct);
        Product editedProduct = productInList.withDescription(VALID_DESCRIPTION_WATCH)
                .withPrice(VALID_PRICE_WATCH).build();

        EditProductDescriptor descriptor = new EditProductDescriptorBuilder(editedProduct)
                .withDescription(VALID_DESCRIPTION_WATCH)
                .withPrice(VALID_PRICE_WATCH).build();
        EditProductCommand editProductCommand = new EditProductCommand(indexLastProduct, descriptor);

        String expectedMessage = String.format(EditProductCommand.MESSAGE_EDIT_PRODUCT_SUCCESS, editedProduct);

        Model expectedModel = new ModelManager(new InventorySystem(model.getInventorySystem()), new UserPrefs());
        expectedModel.setProduct(lastProduct, editedProduct);

        assertCommandSuccess(editProductCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        Product editedProduct = model.getFilteredProductList().get(INDEX_FIRST_PRODUCT.getZeroBased());

        EditProductCommand editProductCommand = new EditProductCommand(INDEX_FIRST_PRODUCT,
                new EditProductDescriptorBuilder(editedProduct).build());
        String expectedMessage = String.format(EditProductCommand.MESSAGE_EDIT_PRODUCT_SUCCESS, editedProduct);

        Model expectedModel = new ModelManager(new InventorySystem(model.getInventorySystem()), new UserPrefs());

        assertCommandSuccess(editProductCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showProductAtIndex(model, INDEX_FIRST_PRODUCT);

        Product productInFilteredList = model.getFilteredProductList().get(INDEX_FIRST_PRODUCT.getZeroBased());
        Product editedProduct = new
                ProductBuilder(productInFilteredList).withDescription(VALID_DESCRIPTION_WATCH).build();

        EditProductCommand editProductCommand = new EditProductCommand(INDEX_FIRST_PRODUCT,
                new EditProductDescriptorBuilder(productInFilteredList)
                        .withDescription(VALID_DESCRIPTION_WATCH).build());

        String expectedMessage = String.format(EditProductCommand.MESSAGE_EDIT_PRODUCT_SUCCESS, editedProduct);

        Model expectedModel = new ModelManager(new InventorySystem(model.getInventorySystem()), new UserPrefs());
        expectedModel.setProduct(model.getFilteredProductList().get(0), editedProduct);

        //        assertCommandSuccess(editProductCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateProductUnfilteredList_failure() {
        Product firstProduct = model.getFilteredProductList().get(INDEX_FIRST_PRODUCT.getZeroBased());
        EditProductDescriptor descriptor = new EditProductDescriptorBuilder(firstProduct).build();
        EditProductCommand editProductCommand = new EditProductCommand(INDEX_SECOND_PRODUCT, descriptor);

        assertCommandFailure(editProductCommand, model, Messages.MESSAGE_DUPLICATE_PRODUCT);
    }

    @Test
    public void execute_duplicateProductFilteredList_failure() {
        showProductAtIndex(model, INDEX_FIRST_PRODUCT);

        // edit product in filtered list into a duplicate in address book
        Product productInList = model.getInventorySystem().getProductList().get(INDEX_SECOND_PRODUCT.getZeroBased());
        EditProductCommand editProductCommand = new EditProductCommand(INDEX_FIRST_PRODUCT,
                new EditProductDescriptorBuilder(productInList).build());

        assertCommandFailure(editProductCommand, model, Messages.MESSAGE_DUPLICATE_PRODUCT);
    }

    @Test
    public void execute_invalidProductIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredProductList().size() + 1);
        Product editedProduct = new ProductBuilder(ABACUS_ID).build();
        EditProductDescriptor descriptor = new EditProductDescriptorBuilder(editedProduct).build();
        EditProductCommand editProductCommand = new EditProductCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editProductCommand, model, Messages.MESSAGE_INVALID_PRODUCT_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidProductIndexFilteredList_failure() {
        showProductAtIndex(model, INDEX_FIRST_PRODUCT);
        Index outOfBoundIndex = INDEX_SECOND_PRODUCT;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getInventorySystem().getProductList().size());

        Product editedProduct = new ProductBuilder(ABACUS_ID).build();
        EditProductDescriptor descriptor = new EditProductDescriptorBuilder(editedProduct).build();

        EditProductCommand editProductCommand = new EditProductCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editProductCommand, model, Messages.MESSAGE_INVALID_PRODUCT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditProductCommand standardCommand = new EditProductCommand(INDEX_FIRST_PRODUCT, DESC_BAG);

        // same values -> returns true
        EditProductDescriptor copyDescriptor = new EditProductDescriptor(DESC_BAG);
        EditProductCommand commandWithSameValues = new EditProductCommand(INDEX_FIRST_PRODUCT, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearProductCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditProductCommand(INDEX_SECOND_PRODUCT, DESC_BAG)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditProductCommand(INDEX_FIRST_PRODUCT, DESC_WATCH)));
    }
}

