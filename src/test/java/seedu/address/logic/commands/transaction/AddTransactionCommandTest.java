package seedu.address.logic.commands.transaction;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.customer.TypicalCustomers.ALICE;
import static seedu.address.testutil.product.TypicalProducts.BAG;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

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
import seedu.address.model.transaction.TransactionFactory;
import seedu.address.testutil.product.ProductBuilder;
import seedu.address.testutil.transaction.TransactionFactoryBuilder;

public class AddTransactionCommandTest {

    @Test
    public void constructor_nullTransaction_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddTransactionCommand(null));
    }

    @Test
    public void execute_transactionAcceptedByModel_addSuccessful() throws Exception {
        AddTransactionCommandTest.ModelStubAcceptingTransactionAdded modelStub =
                new AddTransactionCommandTest.ModelStubAcceptingTransactionAdded();
        TransactionFactory validTransactionFactory = new TransactionFactoryBuilder().build();
        Transaction validTransaction = validTransactionFactory.createTransaction(modelStub);
        Product validProductToEdit = BAG;
        Product validEditedProduct = new ProductBuilder(BAG).withQuantity(
                BAG.getQuantity().minus(validTransaction.getQuantity()).toString()
        ).build();

        CommandResult commandResult = new AddTransactionCommand(validTransactionFactory).execute(modelStub);

        assertEquals(String.format(AddTransactionCommand.MESSAGE_SUCCESS, validTransaction),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validTransaction), modelStub.transactionsAdded);
        assertEquals(validProductToEdit, modelStub.productToEdit);
        assertEquals(validEditedProduct, modelStub.editedProduct);
    }

    @Test
    public void execute_duplicateTransaction_throwsCommandException() throws Exception {
        AddTransactionCommandTest.ModelStubWithTransaction modelStub =
                new AddTransactionCommandTest.ModelStubWithTransaction();

        TransactionFactory validTransactionFactory = new TransactionFactoryBuilder().build();
        Transaction validTransaction = validTransactionFactory.createTransaction(modelStub);

        AddTransactionCommand addTransactionCommand = new AddTransactionCommand(validTransactionFactory);
        modelStub.setTransaction(validTransaction);

        assertThrows(CommandException.class,
                Messages.MESSAGE_DUPLICATE_TRANSACTION, () -> addTransactionCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        TransactionFactory tOne = new TransactionFactoryBuilder().withDescription("under discount").build();
        TransactionFactory tTwo = new TransactionFactoryBuilder().withDescription("NA").build();
        AddTransactionCommand addTOneCommand = new AddTransactionCommand(tOne);
        AddTransactionCommand addTTwoCommand = new AddTransactionCommand(tTwo);

        // same object -> returns true
        assertTrue(addTOneCommand.equals(addTOneCommand));

        // same values -> returns true
        AddTransactionCommand addTOneCommandCopy = new AddTransactionCommand(tOne);
        assertTrue(addTOneCommand.equals(addTOneCommandCopy));

        // different types -> returns false
        assertFalse(addTOneCommand.equals(1));

        // null -> returns false
        assertFalse(addTOneCommand.equals(null));

        // different transaction -> returns false
        assertFalse(addTOneCommand.equals(addTTwoCommand));
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
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Product findProductById(UUID id) {
            throw new AssertionError("This method should not be called.");
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
        public void deleteProduct(Product product) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteTransaction(Transaction target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Customer target, Customer editedTransaction) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setProduct(Product target, Product editedProduct) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setTransaction(Transaction target, Transaction editedTransaction) {
            throw new AssertionError("This method should not be called.");
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
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredCustomerList(Predicate<Customer> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredCustomerList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredProductList(Predicate<Product> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredProductList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredTransactionList(Predicate<Transaction> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredTransactionList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Product> getFilteredProductList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Transaction> getFilteredTransactionList() {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single transaction.
     */
    private class ModelStubWithTransaction extends AddTransactionCommandTest.ModelStub {
        private Transaction transaction;
        private final FilteredList<Customer> filteredCustomerList =
                new FilteredList<>(FXCollections.observableArrayList(ALICE));
        private final FilteredList<Product> filteredProductList =
                new FilteredList<>(FXCollections.observableArrayList(BAG));


        public void setTransaction(Transaction transaction) {
            requireNonNull(transaction);
            this.transaction = transaction;
        }

        @Override
        public boolean hasTransaction(Transaction transaction) {
            requireNonNull(transaction);
            return this.transaction.isSameTransaction(transaction);
        }

        @Override
        public ObservableList<Customer> getFilteredCustomerList() {
            return filteredCustomerList;
        }

        @Override
        public ObservableList<Product> getFilteredProductList() {
            return filteredProductList;
        }

    }

    /**
     * A Model stub that always accept the transaction being added.
     */
    private class ModelStubAcceptingTransactionAdded extends AddTransactionCommandTest.ModelStub {
        final ArrayList<Transaction> transactionsAdded = new ArrayList<>();
        final FilteredList<Customer> filteredCustomerList =
                new FilteredList<>(FXCollections.observableArrayList(ALICE));
        final FilteredList<Product> filteredProductList =
                new FilteredList<>(FXCollections.observableArrayList(BAG));

        private Product productToEdit;
        private Product editedProduct;

        @Override
        public ObservableList<Customer> getFilteredCustomerList() {
            return filteredCustomerList;
        }

        @Override
        public ObservableList<Product> getFilteredProductList() {
            return filteredProductList;
        }

        @Override
        public boolean hasTransaction(Transaction transaction) {
            requireNonNull(transaction);
            return transactionsAdded.stream().anyMatch(transaction::isSameTransaction);
        }

        @Override
        public void addTransaction(Transaction transaction) {
            requireNonNull(transaction);
            transactionsAdded.add(transaction);
        }

        @Override
        public void setProduct(Product target, Product editedProduct) {
            productToEdit = target;
            this.editedProduct = editedProduct;
        }

        @Override
        public void updateFilteredProductList() {

        }

        public Product getProductToEdit() {
            return productToEdit;
        }

        public Product getEditedProduct() {
            return editedProduct;
        }

        @Override
        public ReadOnlyInventorySystem getInventorySystem() {
            return new InventorySystem();
        }
    }

}

