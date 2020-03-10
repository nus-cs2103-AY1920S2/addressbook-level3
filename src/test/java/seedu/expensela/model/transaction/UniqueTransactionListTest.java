package seedu.expensela.model.transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.expensela.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.expensela.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
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

public class UniqueTransactionListTest {

    private final UniqueTransactionList uniqueTransactionList = new UniqueTransactionList();

    @Test
    public void contains_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTransactionList.contains(null));
    }

    @Test
    public void contains_personNotInList_returnsFalse() {
        assertFalse(uniqueTransactionList.contains(ALICE));
    }

    @Test
    public void contains_personInList_returnsTrue() {
        uniqueTransactionList.add(ALICE);
        assertTrue(uniqueTransactionList.contains(ALICE));
    }

    @Test
    public void contains_personWithSameIdentityFieldsInList_returnsTrue() {
        uniqueTransactionList.add(ALICE);
        Transaction editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(uniqueTransactionList.contains(editedAlice));
    }

    @Test
    public void add_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTransactionList.add(null));
    }

    @Test
    public void add_duplicatePerson_throwsDuplicatePersonException() {
        uniqueTransactionList.add(ALICE);
        assertThrows(DuplicateTransactionException.class, () -> uniqueTransactionList.add(ALICE));
    }

    @Test
    public void setPerson_nullTargetPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTransactionList.setPerson(null, ALICE));
    }

    @Test
    public void setPerson_nullEditedPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTransactionList.setPerson(ALICE, null));
    }

    @Test
    public void setPerson_targetPersonNotInList_throwsPersonNotFoundException() {
        assertThrows(TransactionNotFoundException.class, () -> uniqueTransactionList.setPerson(ALICE, ALICE));
    }

    @Test
    public void setPerson_editedPersonIsSamePerson_success() {
        uniqueTransactionList.add(ALICE);
        uniqueTransactionList.setPerson(ALICE, ALICE);
        UniqueTransactionList expectedUniqueTransactionList = new UniqueTransactionList();
        expectedUniqueTransactionList.add(ALICE);
        assertEquals(expectedUniqueTransactionList, uniqueTransactionList);
    }

    @Test
    public void setPerson_editedPersonHasSameIdentity_success() {
        uniqueTransactionList.add(ALICE);
        Transaction editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        uniqueTransactionList.setPerson(ALICE, editedAlice);
        UniqueTransactionList expectedUniqueTransactionList = new UniqueTransactionList();
        expectedUniqueTransactionList.add(editedAlice);
        assertEquals(expectedUniqueTransactionList, uniqueTransactionList);
    }

    @Test
    public void setPerson_editedPersonHasDifferentIdentity_success() {
        uniqueTransactionList.add(ALICE);
        uniqueTransactionList.setPerson(ALICE, BOB);
        UniqueTransactionList expectedUniqueTransactionList = new UniqueTransactionList();
        expectedUniqueTransactionList.add(BOB);
        assertEquals(expectedUniqueTransactionList, uniqueTransactionList);
    }

    @Test
    public void setPerson_editedPersonHasNonUniqueIdentity_throwsDuplicatePersonException() {
        uniqueTransactionList.add(ALICE);
        uniqueTransactionList.add(BOB);
        assertThrows(DuplicateTransactionException.class, () -> uniqueTransactionList.setPerson(ALICE, BOB));
    }

    @Test
    public void remove_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTransactionList.remove(null));
    }

    @Test
    public void remove_personDoesNotExist_throwsPersonNotFoundException() {
        assertThrows(TransactionNotFoundException.class, () -> uniqueTransactionList.remove(ALICE));
    }

    @Test
    public void remove_existingPerson_removesPerson() {
        uniqueTransactionList.add(ALICE);
        uniqueTransactionList.remove(ALICE);
        UniqueTransactionList expectedUniqueTransactionList = new UniqueTransactionList();
        assertEquals(expectedUniqueTransactionList, uniqueTransactionList);
    }

    @Test
    public void setPersons_nullUniquePersonList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTransactionList.setPersons((UniqueTransactionList) null));
    }

    @Test
    public void setPersons_uniquePersonList_replacesOwnListWithProvidedUniquePersonList() {
        uniqueTransactionList.add(ALICE);
        UniqueTransactionList expectedUniqueTransactionList = new UniqueTransactionList();
        expectedUniqueTransactionList.add(BOB);
        uniqueTransactionList.setPersons(expectedUniqueTransactionList);
        assertEquals(expectedUniqueTransactionList, uniqueTransactionList);
    }

    @Test
    public void setPersons_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTransactionList.setPersons((List<Transaction>) null));
    }

    @Test
    public void setPersons_list_replacesOwnListWithProvidedList() {
        uniqueTransactionList.add(ALICE);
        List<Transaction> transactionList = Collections.singletonList(BOB);
        uniqueTransactionList.setPersons(transactionList);
        UniqueTransactionList expectedUniqueTransactionList = new UniqueTransactionList();
        expectedUniqueTransactionList.add(BOB);
        assertEquals(expectedUniqueTransactionList, uniqueTransactionList);
    }

    @Test
    public void setPersons_listWithDuplicatePersons_throwsDuplicatePersonException() {
        List<Transaction> listWithDuplicateTransactions = Arrays.asList(ALICE, ALICE);
        assertThrows(DuplicateTransactionException.class, (
        ) -> uniqueTransactionList.setPersons(listWithDuplicateTransactions));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueTransactionList.asUnmodifiableObservableList().remove(0));
    }
}
