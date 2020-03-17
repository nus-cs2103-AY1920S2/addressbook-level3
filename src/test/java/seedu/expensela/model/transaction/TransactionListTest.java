package seedu.expensela.model.transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.expensela.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.expensela.testutil.Assert.assertThrows;
import static seedu.expensela.testutil.TypicalPersons.ALICE;
import static seedu.expensela.testutil.TypicalPersons.BOB;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.expensela.model.transaction.exceptions.DuplicateTransactionException;
import seedu.expensela.model.transaction.exceptions.TransactionNotFoundException;
import seedu.expensela.testutil.PersonBuilder;

public class TransactionListTest {

    private final TransactionList transactionList = new TransactionList();

    @Test
    public void contains_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> transactionList.contains(null));
    }

    @Test
    public void contains_personNotInList_returnsFalse() {
        assertFalse(transactionList.contains(ALICE));
    }

    @Test
    public void contains_personInList_returnsTrue() {
        transactionList.add(ALICE);
        assertTrue(transactionList.contains(ALICE));
    }

    @Test
    public void contains_personWithSameIdentityFieldsInList_returnsTrue() {
        transactionList.add(ALICE);
        Transaction editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB)
                .build();
        assertTrue(transactionList.contains(editedAlice));
    }

    @Test
    public void add_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> transactionList.add(null));
    }

    @Test
    public void add_duplicatePerson_throwsDuplicatePersonException() {
        transactionList.add(ALICE);
        assertThrows(DuplicateTransactionException.class, () -> transactionList.add(ALICE));
    }

    @Test
    public void setPerson_nullTargetPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> transactionList.setTransaction(null, ALICE));
    }

    @Test
    public void setPerson_nullEditedPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> transactionList.setTransaction(ALICE, null));
    }

    @Test
    public void setPerson_targetPersonNotInList_throwsPersonNotFoundException() {
        assertThrows(TransactionNotFoundException.class, () -> transactionList.setTransaction(ALICE, ALICE));
    }

    @Test
    public void setPerson_editedPersonIsSamePerson_success() {
        transactionList.add(ALICE);
        transactionList.setTransaction(ALICE, ALICE);
        TransactionList expectedTransactionList = new TransactionList();
        expectedTransactionList.add(ALICE);
        assertEquals(expectedTransactionList, transactionList);
    }

    @Test
    public void setPerson_editedPersonHasSameIdentity_success() {
        transactionList.add(ALICE);
        Transaction editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB)
                .build();
        transactionList.setTransaction(ALICE, editedAlice);
        TransactionList expectedTransactionList = new TransactionList();
        expectedTransactionList.add(editedAlice);
        assertEquals(expectedTransactionList, transactionList);
    }

    @Test
    public void setPerson_editedPersonHasDifferentIdentity_success() {
        transactionList.add(ALICE);
        transactionList.setTransaction(ALICE, BOB);
        TransactionList expectedTransactionList = new TransactionList();
        expectedTransactionList.add(BOB);
        assertEquals(expectedTransactionList, transactionList);
    }

    @Test
    public void setPerson_editedPersonHasNonUniqueIdentity_throwsDuplicatePersonException() {
        transactionList.add(ALICE);
        transactionList.add(BOB);
        assertThrows(DuplicateTransactionException.class, () -> transactionList.setTransaction(ALICE, BOB));
    }

    @Test
    public void remove_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> transactionList.remove(null));
    }

    @Test
    public void remove_personDoesNotExist_throwsPersonNotFoundException() {
        assertThrows(TransactionNotFoundException.class, () -> transactionList.remove(ALICE));
    }

    @Test
    public void remove_existingPerson_removesPerson() {
        transactionList.add(ALICE);
        transactionList.remove(ALICE);
        TransactionList expectedTransactionList = new TransactionList();
        assertEquals(expectedTransactionList, transactionList);
    }

    @Test
    public void setPersons_nullUniquePersonList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> transactionList.setTransaction((TransactionList) null));
    }

    @Test
    public void setPersons_uniquePersonList_replacesOwnListWithProvidedUniquePersonList() {
        transactionList.add(ALICE);
        TransactionList expectedTransactionList = new TransactionList();
        expectedTransactionList.add(BOB);
        transactionList.setTransaction(expectedTransactionList);
        assertEquals(expectedTransactionList, transactionList);
    }

    @Test
    public void setPersons_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> transactionList.setTransaction((List<Transaction>) null));
    }

    @Test
    public void setPersons_list_replacesOwnListWithProvidedList() {
        transactionList.add(ALICE);
        List<Transaction> transactionList = Collections.singletonList(BOB);
        this.transactionList.setTransaction(transactionList);
        TransactionList expectedTransactionList = new TransactionList();
        expectedTransactionList.add(BOB);
        assertEquals(expectedTransactionList, this.transactionList);
    }

    @Test
    public void setPersons_listWithDuplicatePersons_throwsDuplicatePersonException() {
        List<Transaction> listWithDuplicateTransactions = Arrays.asList(ALICE, ALICE);
        assertThrows(DuplicateTransactionException.class, (
        ) -> transactionList.setTransaction(listWithDuplicateTransactions));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> transactionList.asUnmodifiableObservableList().remove(0));
    }
}
