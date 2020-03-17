package seedu.expensela.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.expensela.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.expensela.testutil.Assert.assertThrows;
import static seedu.expensela.testutil.TypicalPersons.ALICE;
import static seedu.expensela.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.expensela.model.transaction.Transaction;
import seedu.expensela.model.transaction.exceptions.DuplicateTransactionException;
import seedu.expensela.testutil.PersonBuilder;

public class DateBookTest {

    private final ExpenseLa expenseLa = new ExpenseLa();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), expenseLa.getPersonList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> expenseLa.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        ExpenseLa newData = getTypicalAddressBook();
        expenseLa.resetData(newData);
        assertEquals(newData, expenseLa);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two persons with the same identity fields
        Transaction editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB)
                .build();
        List<Transaction> newTransactions = Arrays.asList(ALICE, editedAlice);
        ExpenseLaStub newData = new ExpenseLaStub(newTransactions);

        assertThrows(DuplicateTransactionException.class, () -> expenseLa.resetData(newData));
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> expenseLa.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(expenseLa.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        expenseLa.addPerson(ALICE);
        assertTrue(expenseLa.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInAddressBook_returnsTrue() {
        expenseLa.addPerson(ALICE);
        Transaction editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB)
                .build();
        assertTrue(expenseLa.hasPerson(editedAlice));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> expenseLa.getPersonList().remove(0));
    }

    /**
     * A stub ReadOnlyExpenseLa whose persons list can violate interface constraints.
     */
    private static class ExpenseLaStub implements ReadOnlyExpenseLa {
        private final ObservableList<Transaction> transactions = FXCollections.observableArrayList();

        ExpenseLaStub(Collection<Transaction> transactions) {
            this.transactions.setAll(transactions);
        }

        @Override
        public ObservableList<Transaction> getPersonList() {
            return transactions;
        }
    }

}
