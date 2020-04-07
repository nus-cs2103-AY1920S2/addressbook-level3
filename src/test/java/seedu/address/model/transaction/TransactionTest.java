package seedu.address.model.transaction;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.transaction.TypicalTransactions.ALICE_BUY_ONE_BAG_MARCH_FIRST;
import static seedu.address.testutil.transaction.TypicalTransactions.ALICE_BUY_ONE_BAG_MARCH_SECOND;
import static seedu.address.testutil.transaction.TypicalTransactions.ALICE_BUY_ONE_BOOK_MARCH_FIRST;
import static seedu.address.testutil.transaction.TypicalTransactions.ALICE_BUY_TWO_BAG_MARCH_FIRST;
import static seedu.address.testutil.transaction.TypicalTransactions.BENSON_BUY_ONE_ABACUS_MARCH_FIRST;
import static seedu.address.testutil.transaction.TypicalTransactions.BENSON_BUY_ONE_BAG_MARCH_FIRST;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.transaction.TransactionBuilder;

public class TransactionTest {

    @Test
    public void isSameTransaction() {
        //same transaction -> returns true
        assertTrue(ALICE_BUY_ONE_BAG_MARCH_FIRST.isSameTransaction(ALICE_BUY_ONE_BAG_MARCH_FIRST));

        //null -> returns false
        assertFalse(ALICE_BUY_ONE_BAG_MARCH_FIRST.isSameTransaction(null));

        //different quantity while same customer, product, and time -> returns true
        assertTrue(ALICE_BUY_ONE_BAG_MARCH_FIRST.isSameTransaction(ALICE_BUY_TWO_BAG_MARCH_FIRST));

        //different customer and product, while same date -> return false
        assertFalse(ALICE_BUY_ONE_BAG_MARCH_FIRST.isSameTransaction(BENSON_BUY_ONE_ABACUS_MARCH_FIRST));

        //same customer, product, and quantity, while different date -> returns false
        assertFalse(ALICE_BUY_ONE_BAG_MARCH_FIRST.isSameTransaction(ALICE_BUY_ONE_BAG_MARCH_SECOND));

        //same customer, quantity, dateTime, while different product -> returns false
        assertFalse(ALICE_BUY_ONE_BAG_MARCH_FIRST.isSameTransaction(ALICE_BUY_ONE_BOOK_MARCH_FIRST));

        //same customer, product, dateTime, and quantity, while different money -> returns false
        Transaction editedTransaction = new TransactionBuilder(ALICE_BUY_ONE_BAG_MARCH_FIRST)
                .withMoney(100).build();
        assertTrue(ALICE_BUY_ONE_BAG_MARCH_FIRST.isSameTransaction(editedTransaction));

        //different description while same other attributes -> returns true
        editedTransaction = new TransactionBuilder(ALICE_BUY_ONE_BAG_MARCH_FIRST)
                .withDescription("new description").build();
        assertTrue(ALICE_BUY_ONE_BAG_MARCH_FIRST.isSameTransaction(editedTransaction));
    }

    @Test
    public void equals() {
        //same values -> return true
        Transaction aliceCopy = new TransactionBuilder(ALICE_BUY_ONE_BAG_MARCH_FIRST).build();
        assertTrue(ALICE_BUY_ONE_BAG_MARCH_FIRST.equals(aliceCopy));

        //same object -> returns true
        assertTrue(ALICE_BUY_ONE_BAG_MARCH_FIRST.equals(ALICE_BUY_ONE_BAG_MARCH_FIRST));

        //null -> returns false
        assertFalse(ALICE_BUY_ONE_BAG_MARCH_FIRST.equals(null));

        //different customer -> returns false
        assertFalse(ALICE_BUY_ONE_BAG_MARCH_FIRST.equals(BENSON_BUY_ONE_BAG_MARCH_FIRST));

        //different product -> returns false
        assertFalse(ALICE_BUY_ONE_BAG_MARCH_FIRST.equals(ALICE_BUY_ONE_BOOK_MARCH_FIRST));

        //different dateTime -> returns false
        assertFalse(ALICE_BUY_ONE_BAG_MARCH_FIRST.equals(ALICE_BUY_ONE_BAG_MARCH_SECOND));

        //different quantity -> returns true
        assertTrue(ALICE_BUY_ONE_BAG_MARCH_FIRST.equals(ALICE_BUY_TWO_BAG_MARCH_FIRST));

        //different money -> returns true
        Transaction editedTransaction = new TransactionBuilder(ALICE_BUY_ONE_BAG_MARCH_FIRST)
                .withMoney(100).build();
        assertTrue(ALICE_BUY_ONE_BAG_MARCH_FIRST.equals(editedTransaction));

        //different description -> returns true
        editedTransaction = new TransactionBuilder(ALICE_BUY_ONE_BAG_MARCH_FIRST)
                .withDescription("new description").build();
        assertTrue(ALICE_BUY_ONE_BAG_MARCH_FIRST.equals(editedTransaction));

    }
}
