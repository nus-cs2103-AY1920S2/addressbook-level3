package seedu.address.logic.commands.product;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.product.TypicalProducts.ABACUS_ID;
import static seedu.address.testutil.product.TypicalProducts.BOOK_ID;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.InventorySystem;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyInventorySystem;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.customer.Customer;
import seedu.address.model.product.Product;
import seedu.address.model.transaction.Transaction;
import seedu.address.testutil.product.ProductBuilder;

public class AddProductCommandTest {

    @Test
    public void constructor_nullProduct_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddProductCommand(null));
    }

    @Test
    public void execute_productAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingProductAdded modelStub = new ModelStubAcceptingProductAdded();
        Product validProduct = new ProductBuilder(ABACUS_ID).build();

        CommandResult commandResult = new AddProductCommand(validProduct).execute(modelStub);

        assertEquals(String.format(AddProductCommand.MESSAGE_SUCCESS, validProduct),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validProduct), modelStub.productsAdded);
    }

    @Test
    public void execute_duplicateProduct_throwsCommandException() {
        Product validProduct = new ProductBuilder(ABACUS_ID).build();
        AddProductCommand addProductCommand = new AddProductCommand(validProduct);
        ModelStub modelStub = new ModelStubWithProduct(validProduct);

        assertThrows(CommandException.class,
                Messages.MESSAGE_DUPLICATE_PRODUCT, () -> addProductCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Product alice = new ProductBuilder(ABACUS_ID).withDescription("Abacus").build();
        Product bob = new ProductBuilder(BOOK_ID).withDescription("Book").build();
        AddProductCommand addAliceCommand = new AddProductCommand(alice);
        AddProductCommand addBobCommand = new AddProductCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddProductCommand addAliceCommandCopy = new AddProductCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different product -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getInventorySystemFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setInventorySystemFilePath(Path inventorySystemFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPerson(Customer customer) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addProduct(Product product) {

        }

        @Override
        public Product findProductById(UUID id) {
            return null;
        }

        @Override
        public void setInventorySystem(ReadOnlyInventorySystem inventorySystem, String commandWord) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyInventorySystem getInventorySystem() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Customer customer) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasProduct(Product product) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Customer target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteProduct(Product target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteTransaction(Transaction target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Customer target, Customer editedProduct) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setProduct(Product target, Product editedProduct) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setTransaction(Transaction target, Transaction editedTransaction) {

        }

        @Override
        public ObservableList<Customer> getFilteredCustomerList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasTransaction(Transaction transaction) {
            return false;
        }

        @Override
        public void addTransaction(Transaction transaction) {

        }

        @Override
        public ObservableList<Transaction> filterTransaction(Predicate<Transaction> predicate) {
            return null;
        }

        @Override
        public void updateFilteredCustomerList(Predicate<Customer> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredCustomerList() {

        }

        @Override
        public void updateFilteredProductList(Predicate<Product> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredProductList() {

        }

        @Override
        public void updateFilteredTransactionList(Predicate<Transaction> predicate) {

        }

        @Override
        public void updateFilteredTransactionList() {

        }

        @Override
        public ObservableList<Product> getFilteredProductList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Transaction> getFilteredTransactionList() {
            return null;
        }
    }

    /**
     * A Model stub that contains a single product.
     */
    private class ModelStubWithProduct extends ModelStub {
        private final Product product;

        ModelStubWithProduct(Product product) {
            requireNonNull(product);
            this.product = product;
        }

        @Override
        public boolean hasProduct(Product product) {
            requireNonNull(product);
            return this.product.isSameProduct(product);
        }
    }

    /**
     * A Model stub that always accept the product being added.
     */
    private class ModelStubAcceptingProductAdded extends ModelStub {
        final ArrayList<Product> productsAdded = new ArrayList<>();

        @Override
        public boolean hasProduct(Product product) {
            requireNonNull(product);
            return productsAdded.stream().anyMatch(product::isSameProduct);
        }

        @Override
        public void addProduct(Product product) {
            requireNonNull(product);
            productsAdded.add(product);
        }

        @Override
        public ReadOnlyInventorySystem getInventorySystem() {
            return new InventorySystem();
        }
    }

}

