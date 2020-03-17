package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.getTypicalExerciseList;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.testutil.PersonBuilder;

public class ExerciseListTest {

    private final ExerciseList exerciseList = new ExerciseList();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), exerciseList.getPersonList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> exerciseList.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyExerciseList_replacesData() {
        ExerciseList newData = getTypicalExerciseList();
        exerciseList.resetData(newData);
        assertEquals(newData, exerciseList);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two persons with the same identity fields
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Person> newPersons = Arrays.asList(ALICE, editedAlice);
        ExerciseListStub newData = new ExerciseListStub(newPersons);

        assertThrows(DuplicatePersonException.class, () -> exerciseList.resetData(newData));
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> exerciseList.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInExerciseList_returnsFalse() {
        assertFalse(exerciseList.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInExerciseList_returnsTrue() {
        exerciseList.addPerson(ALICE);
        assertTrue(exerciseList.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInExerciseList_returnsTrue() {
        exerciseList.addPerson(ALICE);
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(exerciseList.hasPerson(editedAlice));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> exerciseList.getPersonList().remove(0));
    }

    /**
     * A stub ReadOnlyAddressBook whose persons list can violate interface constraints.
     */
    private static class ExerciseListStub implements readOnlyExerciseList {
        private final ObservableList<Person> persons = FXCollections.observableArrayList();

        ExerciseListStub(Collection<Person> persons) {
            this.persons.setAll(persons);
        }

        @Override
        public ObservableList<Person> getPersonList() {
            return persons;
        }
    }

}
