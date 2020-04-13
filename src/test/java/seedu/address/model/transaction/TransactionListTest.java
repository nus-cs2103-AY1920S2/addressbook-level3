package seedu.address.model.transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalWallet.DUCK_RICE;
import static seedu.address.testutil.TypicalWallet.MRT_CONCESSION;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.transaction.exceptions.TransactionNotFoundException;

public class TransactionListTest {

    private final TransactionList<Expense> transactionList = new TransactionList<>();

    @Test
    public void contains_nullTransaction_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> transactionList.contains(null));
    }

    @Test
    public void contains_transactionNotInList_returnsFalse() {
        assertFalse(transactionList.contains(DUCK_RICE));
    }

    @Test
    public void contains_transactionInList_returnsTrue() {
        transactionList.add(DUCK_RICE);
        assertTrue(transactionList.contains(DUCK_RICE));
    }

    @Test
    public void add_nullTransaction_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> transactionList.add(null));
    }

    @Test
    public void setTransaction_nullTargetTransaction_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> transactionList.setTransaction(null, DUCK_RICE));
    }

    @Test
    public void setTransaction_nullEditedTransaction_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> transactionList.setTransaction(DUCK_RICE, null));
    }

    @Test
    public void setTransaction_targetTransactionNotInList_throwsTransactionNotFoundException() {
        assertThrows(TransactionNotFoundException.class, () -> transactionList.setTransaction(DUCK_RICE, DUCK_RICE));
    }

    @Test
    public void setTransaction_validTransaction_success() {
        transactionList.add(DUCK_RICE);
        transactionList.setTransaction(DUCK_RICE, MRT_CONCESSION);
        TransactionList<Expense> expectedTransactionList = new TransactionList<>();
        expectedTransactionList.add(MRT_CONCESSION);
        assertEquals(expectedTransactionList, transactionList);
    }

    @Test
    public void remove_nullTransaction_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> transactionList.remove(null));
    }

    @Test
    public void remove_transactionDoesNotExist_throwsTransactionNotFoundException() {
        assertThrows(TransactionNotFoundException.class, () -> transactionList.remove(DUCK_RICE));
    }

    @Test
    public void remove_existingTransaction_removesTransaction() {
        transactionList.add(DUCK_RICE);
        transactionList.remove(DUCK_RICE);
        TransactionList<Expense> expectedTransactionList = new TransactionList<>();
        assertEquals(expectedTransactionList, transactionList);
    }

    @Test
    public void setTransactions_nullTransactionList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                transactionList.setTransactions((TransactionList<Expense>) null));
    }

    @Test
    public void setTransactions_transactionList_replacesOwnListWithProvidedUniqueTransactionList() {
        transactionList.add(DUCK_RICE);
        TransactionList<Expense> expectedTransactionList = new TransactionList<>();
        expectedTransactionList.add(MRT_CONCESSION);
        transactionList.setTransactions(expectedTransactionList);
        assertEquals(expectedTransactionList, transactionList);
    }

    @Test
    public void setTransactions_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> transactionList.setTransactions((List<Expense>) null));
    }

    @Test
    public void setTransactions_list_replacesOwnListWithProvidedList() {
        transactionList.add(DUCK_RICE);
        List<Expense> newList = Collections.singletonList(MRT_CONCESSION);
        transactionList.setTransactions(newList);
        TransactionList<Expense> expectedTransactionList = new TransactionList<>();
        expectedTransactionList.add(MRT_CONCESSION);
        assertEquals(expectedTransactionList, transactionList);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> transactionList.asUnmodifiableObservableList().remove(0));
    }
}
