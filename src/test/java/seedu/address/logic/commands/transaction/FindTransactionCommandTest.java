package seedu.address.logic.commands.transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.transaction.TypicalTransactions.ALICE_BUY_ONE_BAG_MARCH_FIRST;
import static seedu.address.testutil.transaction.TypicalTransactions.ALICE_BUY_ONE_BAG_MARCH_SECOND;
import static seedu.address.testutil.transaction.TypicalTransactions.ALICE_BUY_TWO_BAG_MARCH_FIRST;
import static seedu.address.testutil.transaction.TypicalTransactions.BENSON_BUY_ONE_BAG_MARCH_FIRST;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyInventorySystem;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.customer.Customer;
import seedu.address.model.product.Product;
import seedu.address.model.transaction.CustomerContainsKeywordPredicate;
import seedu.address.model.transaction.DateTime;
import seedu.address.model.transaction.DateTimeEqualsPredicate;
import seedu.address.model.transaction.Transaction;
import seedu.address.testutil.transaction.DateTimeBuilder;

public class FindTransactionCommandTest {

    @Test
    public void equals() {
        CustomerContainsKeywordPredicate predicateAlice =
                new CustomerContainsKeywordPredicate(Collections.singletonList("Alice"));
        CustomerContainsKeywordPredicate predicateBob =
                new CustomerContainsKeywordPredicate(Collections.singletonList("Bob"));

        FindTransactionCommand findNamesHasAlice = new FindTransactionCommand(predicateAlice);
        FindTransactionCommand findNameHasBob = new FindTransactionCommand(predicateBob);

        // same object -> returns true
        assertTrue(findNamesHasAlice.equals(findNamesHasAlice));

        // same values -> returns true
        FindTransactionCommand findNamesHasAliceCopy = new FindTransactionCommand(predicateAlice);
        assertTrue(findNamesHasAlice.equals(findNamesHasAliceCopy));

        // different types -> return false
        assertFalse(findNamesHasAlice.equals(1));

        // null -> returns false
        assertFalse(findNamesHasAlice.equals(null));

        // different product -> returns false
        assertFalse(findNamesHasAlice.equals(findNameHasBob));
    }

    @Test
    public void execute_zeroKeywords_noTransactionFound() {
        CustomerContainsKeywordPredicate predicate =
                new CustomerContainsKeywordPredicate(Collections.emptyList());
        FindTransactionCommandTest.ModelStubWithTransaction modelStub =
                new FindTransactionCommandTest.ModelStubWithTransaction();

        FindTransactionCommand command = new FindTransactionCommand(predicate);

        CommandResult commandResult = command.execute(modelStub);

        assertEquals(String.format(Messages.MESSAGE_TRANSACTIONS_LISTED_OVERVIEW, 0),
                commandResult.getFeedbackToUser());
        assertEquals(Collections.emptyList(), modelStub.getFilteredTransactionList());
    }

    @Test
    public void execute_oneCustomerNameKeyword_oneTransactionFound() {
        CustomerContainsKeywordPredicate predicate =
                new CustomerContainsKeywordPredicate(Arrays.asList("Benson"));
        FindTransactionCommandTest.ModelStubWithTransaction modelStub =
                new FindTransactionCommandTest.ModelStubWithTransaction();

        FindTransactionCommand command = new FindTransactionCommand(predicate);

        CommandResult commandResult = command.execute(modelStub);

        assertEquals(String.format(Messages.MESSAGE_TRANSACTIONS_LISTED_OVERVIEW, 1),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(BENSON_BUY_ONE_BAG_MARCH_FIRST), modelStub.getFilteredTransactionList());
    }

    @Test
    public void execute_oneCustomerNameKeyword_threeTransactionFound() {
        CustomerContainsKeywordPredicate predicate =
                new CustomerContainsKeywordPredicate(Arrays.asList("Alice"));
        FindTransactionCommandTest.ModelStubWithTransaction modelStub =
                new FindTransactionCommandTest.ModelStubWithTransaction();

        FindTransactionCommand command = new FindTransactionCommand(predicate);

        CommandResult commandResult = command.execute(modelStub);

        assertEquals(String.format(Messages.MESSAGE_TRANSACTIONS_LISTED_OVERVIEW, 3),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(
                ALICE_BUY_ONE_BAG_MARCH_FIRST,
                ALICE_BUY_TWO_BAG_MARCH_FIRST,
                ALICE_BUY_ONE_BAG_MARCH_SECOND
        ), modelStub.getFilteredTransactionList());
    }

    @Test
    public void execute_oneDateTime_oneTransactionFound() {
        DateTime marchFirst = new DateTimeBuilder("2020-03-01 10:00").build();

        DateTimeEqualsPredicate predicate =
                new DateTimeEqualsPredicate(marchFirst);
        FindTransactionCommandTest.ModelStubWithTransaction modelStub =
                new FindTransactionCommandTest.ModelStubWithTransaction();

        FindTransactionCommand command = new FindTransactionCommand(predicate);

        CommandResult commandResult = command.execute(modelStub);

        assertEquals(String.format(Messages.MESSAGE_TRANSACTIONS_LISTED_OVERVIEW, 3),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(
                ALICE_BUY_ONE_BAG_MARCH_FIRST,
                ALICE_BUY_TWO_BAG_MARCH_FIRST,
                BENSON_BUY_ONE_BAG_MARCH_FIRST
        ), modelStub.getFilteredTransactionList());
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
    private class ModelStubWithTransaction extends FindTransactionCommandTest.ModelStub {
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
    }
}
