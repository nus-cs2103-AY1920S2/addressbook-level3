package seedu.expensela.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.expensela.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.expensela.commons.core.GuiSettings;
import seedu.expensela.logic.commands.exceptions.CommandException;
import seedu.expensela.model.Balance;
import seedu.expensela.model.ExpenseLa;
import seedu.expensela.model.Filter;
import seedu.expensela.model.GlobalData;
import seedu.expensela.model.Model;
import seedu.expensela.model.ReadOnlyExpenseLa;
import seedu.expensela.model.ReadOnlyUserPrefs;
import seedu.expensela.model.ToggleView;
import seedu.expensela.model.monthlydata.MonthlyData;
import seedu.expensela.model.transaction.Transaction;
import seedu.expensela.testutil.TransactionBuilder;

public class AddCommandTest {

    @Test
    public void constructor_nullTransaction_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCommand(null));
    }

    @Test
    public void execute_transactionAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingTransactionAdded modelStub = new ModelStubAcceptingTransactionAdded();
        Transaction validTransaction = new TransactionBuilder().build();

        CommandResult commandResult = new AddCommand(validTransaction).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validTransaction), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validTransaction), modelStub.transactionsAdded);
    }

    @Test
    public void execute_duplicateTransaction_throwsCommandException() {
        Transaction validTransaction = new TransactionBuilder().build();
        AddCommand addCommand = new AddCommand(validTransaction);
        ModelStub modelStub = new ModelStubWithTransaction(validTransaction);

        assertThrows(CommandException.class,
                AddCommand.MESSAGE_DUPLICATE_TRANSACTION, () -> addCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Transaction pizza = new TransactionBuilder().withName("Pizza").build();
        Transaction airpods = new TransactionBuilder().withName("Airpods").build();
        AddCommand addPizzaCommand = new AddCommand(pizza);
        AddCommand addAirpodsCommand = new AddCommand(airpods);

        // same object -> returns true
        assertTrue(addPizzaCommand.equals(addPizzaCommand));

        // same values -> returns true
        AddCommand addPizzaCommandCopy = new AddCommand(pizza);
        assertTrue(addPizzaCommand.equals(addPizzaCommandCopy));

        // different types -> returns false
        assertFalse(addPizzaCommand.equals(1));

        // null -> returns false
        assertFalse(addPizzaCommand.equals(null));

        // different transaction -> returns false
        assertFalse(addPizzaCommand.equals(addAirpodsCommand));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public String getCommandFromHistory(int offset) {
            return null;
        }

        @Override
        public void addToCommandHistory(String command) {

        }

        @Override
        public void deleteFromCommandHistory(String command) {

        }

        @Override
        public int getCommandHistorySize() {
            return 0;
        }

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
        public Path getExpenseLaFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setExpenseLaFilePath(Path expenseLaFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getGlobalDataFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGlobalDataFilePath(Path expenseLaFilePath) {

        }

        @Override
        public void addTransaction(Transaction transaction) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setExpenseLa(ReadOnlyExpenseLa newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyExpenseLa getExpenseLa() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasTransaction(Transaction transaction) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteTransaction(Transaction target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setTransaction(Transaction target, Transaction editedTransaction) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Transaction> getFilteredTransactionList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredTransactionList(Predicate<Transaction> predicate1,
                                                  Predicate<Transaction> predicate2) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public MonthlyData getMonthlyData() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setMonthlyData(MonthlyData monthlyData) {

        }

        @Override
        public Filter getFilter() {
            return new Filter(null, null);
        }

        @Override
        public void setFilter(Filter filter) {

        }

        @Override
        public boolean getIsFilterMonth() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ToggleView getToggleView() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void switchToggleView() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GlobalData getGlobalData() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGlobalData(GlobalData globalData) {

        }

        @Override
        public Balance getTotalBalance() {
            throw new AssertionError("This method should not be called.");
        }


        @Override
        public void updateTotalBalance(Balance balance) {
        }

        @Override
        public void addTransactionToGlobalData(Transaction transaction) {

        }

        @Override
        public void clearRecurringTransactions() {

        }
    }

    /**
     * A Model stub that contains a single transaction.
     */
    private class ModelStubWithTransaction extends ModelStub {
        private final Transaction transaction;

        ModelStubWithTransaction(Transaction transaction) {
            requireNonNull(transaction);
            this.transaction = transaction;
        }

        @Override
        public boolean hasTransaction(Transaction transaction) {
            requireNonNull(transaction);
            return this.transaction.isSameTransaction(transaction);
        }
    }

    /**
     * A Model stub that always accept the transaction being added.
     */
    private class ModelStubAcceptingTransactionAdded extends ModelStub {
        final ArrayList<Transaction> transactionsAdded = new ArrayList<>();

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
        public ReadOnlyExpenseLa getExpenseLa() {
            return new ExpenseLa();
        }
    }

}
