package seedu.expensela.model.transaction;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.expensela.logic.commands.CommandTestUtil.VALID_REMARK_AIRPODS;
import static seedu.expensela.logic.commands.CommandTestUtil.VALID_NAME_AIRPODS;
import static seedu.expensela.logic.commands.CommandTestUtil.VALID_AMOUNT_AIRPODS;
import static seedu.expensela.testutil.TypicalTransactions.PIZZA;
import static seedu.expensela.testutil.TypicalTransactions.AIRPODS;

import org.junit.jupiter.api.Test;

import seedu.expensela.testutil.TransactionBuilder;

public class TransactionTest {

    @Test
    public void isSameTransaction() {
        // same object -> returns true
        assertTrue(PIZZA.isSameTransaction(PIZZA));

        // null -> returns false
        assertFalse(PIZZA.isSameTransaction(null));

        // different amount and email -> returns false
        Transaction editedAlice = new TransactionBuilder(PIZZA).withAmount(VALID_AMOUNT_AIRPODS, false).build();
        assertFalse(PIZZA.isSameTransaction(editedAlice));

        // different name -> returns false
        editedAlice = new TransactionBuilder(PIZZA).withName(VALID_NAME_AIRPODS).build();
        assertFalse(PIZZA.isSameTransaction(editedAlice));

        // same name, same amount, different attributes -> returns true
        editedAlice = new TransactionBuilder(PIZZA).withRemark(VALID_REMARK_AIRPODS)
                .build();
        assertTrue(PIZZA.isSameTransaction(editedAlice));

        // same name, same email, different attributes -> returns true
        editedAlice = new TransactionBuilder(PIZZA).withAmount(VALID_AMOUNT_AIRPODS, false).withRemark(VALID_REMARK_AIRPODS)
                .build();
        assertTrue(PIZZA.isSameTransaction(editedAlice));

        // same name, same amount, same email, different attributes -> returns true
        editedAlice = new TransactionBuilder(PIZZA).withRemark(VALID_REMARK_AIRPODS).build();
        assertTrue(PIZZA.isSameTransaction(editedAlice));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Transaction aliceCopy = new TransactionBuilder(PIZZA).build();
        assertTrue(PIZZA.equals(aliceCopy));

        // same object -> returns true
        assertTrue(PIZZA.equals(PIZZA));

        // null -> returns false
        assertFalse(PIZZA.equals(null));

        // different type -> returns false
        assertFalse(PIZZA.equals(5));

        // different transaction -> returns false
        assertFalse(PIZZA.equals(AIRPODS));

        // different name -> returns false
        Transaction editedAlice = new TransactionBuilder(PIZZA).withName(VALID_NAME_AIRPODS).build();
        assertFalse(PIZZA.equals(editedAlice));

        // different amount -> returns false
        editedAlice = new TransactionBuilder(PIZZA).withAmount(VALID_AMOUNT_AIRPODS, false).build();
        assertFalse(PIZZA.equals(editedAlice));

        // different email -> returns false
        editedAlice = new TransactionBuilder(PIZZA).build();
        assertFalse(PIZZA.equals(editedAlice));

        // different remark -> returns false
        editedAlice = new TransactionBuilder(PIZZA).withRemark(VALID_REMARK_AIRPODS).build();
        assertFalse(PIZZA.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new TransactionBuilder(PIZZA).build();
        assertFalse(PIZZA.equals(editedAlice));
    }
}
