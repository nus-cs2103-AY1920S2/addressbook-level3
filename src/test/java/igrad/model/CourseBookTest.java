package igrad.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static igrad.testutil.Assert.assertThrows;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import igrad.logic.commands.CommandTestUtil;
import igrad.testutil.Assert;
import igrad.testutil.PersonBuilder;
import igrad.testutil.TypicalPersons;
import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import igrad.model.person.Person;
import igrad.model.person.exceptions.DuplicatePersonException;

public class CourseBookTest {

    private final CourseBook courseBook = new CourseBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), courseBook.getPersonList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> courseBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        CourseBook newData = TypicalPersons.getTypicalAddressBook();
        courseBook.resetData(newData);
        assertEquals(newData, courseBook);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two persons with the same identity fields
        Person editedAlice = new PersonBuilder(TypicalPersons.ALICE).withAddress(CommandTestUtil.VALID_ADDRESS_BOB).withTags(CommandTestUtil.VALID_TAG_HUSBAND)
                .build();
        List<Person> newPersons = Arrays.asList(TypicalPersons.ALICE, editedAlice);
        CourseBookStub newData = new CourseBookStub(newPersons);

        Assert.assertThrows(DuplicatePersonException.class, () -> courseBook.resetData(newData));
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> courseBook.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(courseBook.hasPerson(TypicalPersons.ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        courseBook.addPerson(TypicalPersons.ALICE);
        assertTrue(courseBook.hasPerson(TypicalPersons.ALICE));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInAddressBook_returnsTrue() {
        courseBook.addPerson(TypicalPersons.ALICE);
        Person editedAlice = new PersonBuilder(TypicalPersons.ALICE).withAddress(CommandTestUtil.VALID_ADDRESS_BOB).withTags(CommandTestUtil.VALID_TAG_HUSBAND)
                .build();
        assertTrue(courseBook.hasPerson(editedAlice));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        Assert.assertThrows(UnsupportedOperationException.class, () -> courseBook.getPersonList().remove(0));
    }

    /**
     * A stub ReadOnlyCourseBook whose persons list can violate interface constraints.
     */
    private static class CourseBookStub implements ReadOnlyCourseBook {
        private final ObservableList<Person> persons = FXCollections.observableArrayList();

        CourseBookStub(Collection<Person> persons) {
            this.persons.setAll(persons);
        }

        @Override
        public ObservableList<Person> getPersonList() {
            return persons;
        }
    }

}
