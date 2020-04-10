package seedu.eylah.expensesplitter.model;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static seedu.eylah.expensesplitter.testutil.TypicalPersons.ALICE;
import static seedu.eylah.expensesplitter.testutil.TypicalPersons.getTypicalPersonAmountBook;
import static seedu.eylah.testutil.Assert.assertThrows;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.eylah.expensesplitter.model.person.Amount;
import seedu.eylah.expensesplitter.model.person.Person;
import seedu.eylah.expensesplitter.model.person.UniquePersonList;
import seedu.eylah.expensesplitter.model.person.exceptions.DuplicatePersonException;
import seedu.eylah.expensesplitter.testutil.PersonBuilder;

public class PersonAmountBookTest {

    private final PersonAmountBook personAmountBook = new PersonAmountBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), personAmountBook.getPersonList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> personAmountBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        PersonAmountBook newData = getTypicalPersonAmountBook();
        personAmountBook.resetData(newData);
        assertEquals(newData, personAmountBook);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two persons with the same identity fields
        Person editedAlice = new PersonBuilder(ALICE).build();
        List<Person> newPersons = Arrays.asList(ALICE, editedAlice);
        PersonAmountBookStub newData = new PersonAmountBookStub(newPersons);

        assertThrows(DuplicatePersonException.class, () -> personAmountBook.resetData(newData));
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> personAmountBook.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInPersonAmountBook_returnsFalse() {
        assertFalse(personAmountBook.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInPersonAmountBook_returnsTrue() {
        personAmountBook.addPerson(ALICE);
        assertTrue(personAmountBook.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInAddressBook_returnsTrue() {
        personAmountBook.addPerson(ALICE);
        Person editedAlice = new PersonBuilder(ALICE).build();
        assertTrue(personAmountBook.hasPerson(editedAlice));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> personAmountBook.getPersonList().remove(0));
    }

    @Test
    public void addAmount_addAmountCorrect_returnsTrue() {
        Person alice = ALICE;
        Amount amount = new Amount(new BigDecimal("10.00"));
        personAmountBook.addPerson(alice);
        personAmountBook.addAmount(alice, amount);

        Person editedAlice = new PersonBuilder().withName("alice seah")
                .withAmount(new BigDecimal("13.50")).build();

        assertEquals(alice, editedAlice);
    }

    @Test
    public void removeAmount_removeAmountCorrect_returnsTrue() {
        Person alice = ALICE;
        Amount amount = new Amount(new BigDecimal("1.00"));
        personAmountBook.addPerson(alice);
        personAmountBook.removeAmount(alice, amount);

        Person editedAlice = new PersonBuilder().withName("alice seah")
                .withAmount(new BigDecimal("2.50")).build();

        assertEquals(alice, editedAlice);
    }

    /**
     * A stub ReadOnlyAddressBook whose persons list can violate interface constraints.
     */
    private static class PersonAmountBookStub implements ReadOnlyPersonAmountBook {
        private final ObservableList<Person> personsList = FXCollections.observableArrayList();
        private final UniquePersonList persons = new UniquePersonList();

        PersonAmountBookStub(Collection<Person> persons) {
            this.personsList.setAll(persons);
        }

        @Override
        public ObservableList<Person> getPersonList() {
            return personsList;
        }

        @Override
        public Person getPersonByIndex(int indexOfPerson) {
            requireNonNull(indexOfPerson);
            return persons.getPersonUsingIndex(indexOfPerson);
        }
    }
}
