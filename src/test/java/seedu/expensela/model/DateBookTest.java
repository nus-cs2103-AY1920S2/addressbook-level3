package seedu.expensela.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.expensela.logic.commands.CommandTestUtil.VALID_REMARK_AIRPODS;
import static seedu.expensela.testutil.Assert.assertThrows;
import static seedu.expensela.testutil.TypicalTransactions.PIZZA;
import static seedu.expensela.testutil.TypicalTransactions.getTypicalExpenseLa;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.expensela.model.monthlydata.MonthlyData;
import seedu.expensela.model.transaction.Transaction;
import seedu.expensela.model.transaction.exceptions.DuplicateTransactionException;
import seedu.expensela.testutil.MonthlyDataBuilder;
import seedu.expensela.testutil.TransactionBuilder;

public class DateBookTest {

    private final ExpenseLa expenseLa = new ExpenseLa();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), expenseLa.getTransactionList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> expenseLa.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyExpenseLa_replacesData() {
        ExpenseLa newData = getTypicalExpenseLa();
        expenseLa.resetData(newData);
        assertEquals(newData, expenseLa);
    }

    /*
    @Test
    public void resetData_withDuplicateTransactions_throwsDuplicateTransactionException() {
        // Two transactions with the same identity fields
        Transaction editedPizza = new TransactionBuilder(PIZZA).withRemark(VALID_REMARK_AIRPODS)
                .build();
        List<Transaction> newTransactions = Arrays.asList(PIZZA, editedPizza);
        ExpenseLaStub newData = new ExpenseLaStub(newTransactions);

        assertThrows(DuplicateTransactionException.class, () -> expenseLa.resetData(newData));
    } */

    @Test
    public void hasTransaction_nullTransaction_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> expenseLa.hasTransaction(null));
    }

    @Test
    public void hasTransaction_transactionNotInExpenseLa_returnsFalse() {
        assertFalse(expenseLa.hasTransaction(PIZZA));
    }

    @Test
    public void hasTransaction_transactionInExpenseLa_returnsTrue() {
        expenseLa.addTransaction(PIZZA);
        assertTrue(expenseLa.hasTransaction(PIZZA));
    }

    @Test
    public void hasTransaction_transactionWithDiffFieldsInExpenseLa_returnsFalse() {
        expenseLa.addTransaction(PIZZA);
        Transaction editedPizza = new TransactionBuilder(PIZZA).withRemark(VALID_REMARK_AIRPODS)
                .build();
        assertFalse(expenseLa.hasTransaction(editedPizza));
    }

    @Test
    public void getTransactionList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> expenseLa.getTransactionList().remove(0));
    }

    /**
     * A stub ReadOnlyExpenseLa whose transactions list can violate interface constraints.
     */
    private static class ExpenseLaStub implements ReadOnlyExpenseLa {
        private final ObservableList<Transaction> transactions = FXCollections.observableArrayList();
        private final MonthlyData monthlyData = new MonthlyDataBuilder().build();

        ExpenseLaStub(Collection<Transaction> transactions) {
            this.transactions.setAll(transactions);
        }

        @Override
        public ObservableList<Transaction> getTransactionList() {
            return transactions;
        }

        @Override
        public MonthlyData getMonthlyData() {
            return monthlyData;
        }
    }

}
