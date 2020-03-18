package seedu.expensela.model.transaction;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.expensela.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.expensela.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.expensela.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.expensela.testutil.TypicalTransactions.ALICE;
import static seedu.expensela.testutil.TypicalTransactions.BOB;

import org.junit.jupiter.api.Test;

import seedu.expensela.testutil.TransactionBuilder;

public class TransactionTest {

    @Test
    public void isSameTransaction() {
        // same object -> returns true
        assertTrue(ALICE.isSameTransaction(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSameTransaction(null));

        // different phone and email -> returns false
        Transaction editedAlice = new TransactionBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.isSameTransaction(editedAlice));

        // different name -> returns false
        editedAlice = new TransactionBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.isSameTransaction(editedAlice));

        // same name, same phone, different attributes -> returns true
        editedAlice = new TransactionBuilder(ALICE).withAddress(VALID_ADDRESS_BOB)
                .build();
        assertTrue(ALICE.isSameTransaction(editedAlice));

        // same name, same email, different attributes -> returns true
        editedAlice = new TransactionBuilder(ALICE).withPhone(VALID_PHONE_BOB).withAddress(VALID_ADDRESS_BOB)
                .build();
        assertTrue(ALICE.isSameTransaction(editedAlice));

        // same name, same phone, same email, different attributes -> returns true
        editedAlice = new TransactionBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).build();
        assertTrue(ALICE.isSameTransaction(editedAlice));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Transaction aliceCopy = new TransactionBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different transaction -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Transaction editedAlice = new TransactionBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new TransactionBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new TransactionBuilder(ALICE).build();
        assertFalse(ALICE.equals(editedAlice));

        // different address -> returns false
        editedAlice = new TransactionBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new TransactionBuilder(ALICE).build();
        assertFalse(ALICE.equals(editedAlice));
    }
}
