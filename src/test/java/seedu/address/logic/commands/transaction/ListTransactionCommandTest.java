package seedu.address.logic.commands.transaction;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showTransactionAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TRANSACTION;
import static seedu.address.testutil.transaction.TypicalTransactions.ALICE_BUY_ONE_BAG_MARCH_FIRST;
import static seedu.address.testutil.transaction.TypicalTransactions.ALICE_BUY_ONE_BAG_MARCH_SECOND;
import static seedu.address.testutil.transaction.TypicalTransactions.ALICE_BUY_TWO_BAG_MARCH_FIRST;
import static seedu.address.testutil.transaction.TypicalTransactions.BENSON_BUY_ONE_BAG_MARCH_FIRST;

import java.nio.file.Path;
import java.util.UUID;
import java.util.function.Predicate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyInventorySystem;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.customer.Customer;
import seedu.address.model.product.Product;
import seedu.address.model.transaction.Transaction;

public class ListTransactionCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ListTransactionCommandTest.ModelStubWithTransaction();
        expectedModel = new ListTransactionCommandTest.ModelStubWithTransaction();
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListTransactionCommand(), model,
                ListTransactionCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showTransactionAtIndex(model, INDEX_FIRST_TRANSACTION);
        assertCommandSuccess(new ListTransactionCommand(), model,
                ListTransactionCommand.MESSAGE_SUCCESS, expectedModel);
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
    private class ModelStubWithTransaction extends ListTransactionCommandTest.ModelStub {
        private final FilteredList<Transaction> filteredTransactionList =
                new FilteredList<>(FXCollections.observableArrayList(
                        ALICE_BUY_ONE_BAG_MARCH_FIRST,
                        ALICE_BUY_TWO_BAG_MARCH_FIRST,
                        ALICE_BUY_ONE_BAG_MARCH_SECOND,
                        BENSON_BUY_ONE_BAG_MARCH_FIRST
                ));

        @Override
        public void updateFilteredTransactionList(Predicate<Transaction> predicate) {
            filteredTransactionList.setPredicate(predicate);
        }

        @Override
        public ObservableList<Transaction> getFilteredTransactionList() {
            return filteredTransactionList;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }

            if (!(obj instanceof ModelStubWithTransaction)) {
                return false;
            }

            ModelStubWithTransaction other = (ModelStubWithTransaction) obj;
            return filteredTransactionList.equals(other.filteredTransactionList);
        }
    }
}
