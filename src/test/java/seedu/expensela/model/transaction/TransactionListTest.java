package seedu.expensela.model.transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.expensela.logic.commands.CommandTestUtil.VALID_REMARK_AIRPODS;
import static seedu.expensela.testutil.Assert.assertThrows;
import static seedu.expensela.testutil.TypicalTransactions.AIRPODS;
import static seedu.expensela.testutil.TypicalTransactions.PIZZA;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.expensela.model.transaction.exceptions.DuplicateTransactionException;
import seedu.expensela.model.transaction.exceptions.TransactionNotFoundException;
import seedu.expensela.testutil.TransactionBuilder;

public class TransactionListTest {

    private final TransactionList transactionList = new TransactionList();

    @Test
    public void contains_nullTransaction_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> transactionList.contains(null));
    }

    @Test
    public void contains_transactionNotInList_returnsFalse() {
        assertFalse(transactionList.contains(PIZZA));
    }

    @Test
    public void contains_transactionInList_returnsTrue() {
        transactionList.add(PIZZA);
        assertTrue(transactionList.contains(PIZZA));
    }

    @Test
    public void add_nullTransaction_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> transactionList.add(null));
    }

    @Test
    public void add_duplicateTransaction_throwsDuplicateTransactionException() {
        transactionList.add(PIZZA);
        assertThrows(DuplicateTransactionException.class, () -> transactionList.add(PIZZA));
    }

    @Test
    public void setTransaction_nullTargetTransaction_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> transactionList.setTransaction(null, PIZZA));
    }

    @Test
    public void setTransaction_nullEditedTransaction_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> transactionList.setTransaction(PIZZA, null));
    }

    @Test
    public void setTransaction_targetTransactionNotInList_throwsTransactionNotFoundException() {
        assertThrows(TransactionNotFoundException.class, () -> transactionList.setTransaction(PIZZA, PIZZA));
    }

    @Test
    public void setTransaction_editedTransactionIsSameTransaction_success() {
        transactionList.add(PIZZA);
        transactionList.setTransaction(PIZZA, PIZZA);
        TransactionList expectedTransactionList = new TransactionList();
        expectedTransactionList.add(PIZZA);
        assertEquals(expectedTransactionList, transactionList);
    }

    @Test
    public void setTransaction_editedTransactionHasSameIdentity_success() {
        transactionList.add(PIZZA);
        Transaction editedPizza = new TransactionBuilder(PIZZA).withRemark(VALID_REMARK_AIRPODS)
                .build();
        transactionList.setTransaction(PIZZA, editedPizza);
        TransactionList expectedTransactionList = new TransactionList();
        expectedTransactionList.add(editedPizza);
        assertEquals(expectedTransactionList, transactionList);
    }

    @Test
    public void setTransaction_editedTransactionHasDifferentIdentity_success() {
        transactionList.add(PIZZA);
        transactionList.setTransaction(PIZZA, AIRPODS);
        TransactionList expectedTransactionList = new TransactionList();
        expectedTransactionList.add(AIRPODS);
        assertEquals(expectedTransactionList, transactionList);
    }

    @Test
    public void setTransaction_editedTransactionHasNonUniqueIdentity_throwsDuplicateTransactionException() {
        transactionList.add(PIZZA);
        transactionList.add(AIRPODS);
        assertThrows(DuplicateTransactionException.class, () -> transactionList.setTransaction(PIZZA, AIRPODS));
    }

    @Test
    public void remove_nullTransaction_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> transactionList.remove(null));
    }

    @Test
    public void remove_transactionDoesNotExist_throwsTransactionNotFoundException() {
        assertThrows(TransactionNotFoundException.class, () -> transactionList.remove(PIZZA));
    }

    @Test
    public void remove_existingTransaction_removesTransaction() {
        transactionList.add(PIZZA);
        transactionList.remove(PIZZA);
        TransactionList expectedTransactionList = new TransactionList();
        assertEquals(expectedTransactionList, transactionList);
    }

    @Test
    public void setTransactions_nullUniqueTransactionList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> transactionList.setTransaction((TransactionList) null));
    }

    @Test
    public void setTransactions_uniqueTransactionList_replacesOwnListWithProvidedUniqueTransactionList() {
        transactionList.add(PIZZA);
        TransactionList expectedTransactionList = new TransactionList();
        expectedTransactionList.add(AIRPODS);
        transactionList.setTransaction(expectedTransactionList);
        assertEquals(expectedTransactionList, transactionList);
    }

    @Test
    public void setTransactions_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> transactionList.setTransaction((List<Transaction>) null));
    }

    @Test
    public void setTransactions_list_replacesOwnListWithProvidedList() {
        transactionList.add(PIZZA);
        List<Transaction> transactionList = Collections.singletonList(AIRPODS);
        this.transactionList.setTransaction(transactionList);
        TransactionList expectedTransactionList = new TransactionList();
        expectedTransactionList.add(AIRPODS);
        assertEquals(expectedTransactionList, this.transactionList);
    }

    /*
    @Test
    public void setTransactions_listWithDuplicateTransactions_throwsDuplicateTransactionException() {
        List<Transaction> listWithDuplicateTransactions = Arrays.asList(PIZZA, PIZZA);
        assertThrows(DuplicateTransactionException.class, (
        ) -> transactionList.setTransaction(listWithDuplicateTransactions));
    } */

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> transactionList.asUnmodifiableObservableList().remove(0));
    }
}
